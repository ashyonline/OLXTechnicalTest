package codingbad.com.olxtechnicaltest.view;

import com.google.inject.Inject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import codingbad.com.olxtechnicaltest.OlxTechnicalTestApplication;
import codingbad.com.olxtechnicaltest.R;
import codingbad.com.olxtechnicaltest.database.CategoryEventTrackerContract.CategoryEvent;
import codingbad.com.olxtechnicaltest.database.CategoryEventTrackerHelper;
import codingbad.com.olxtechnicaltest.database.DummyData;
import codingbad.com.olxtechnicaltest.dto.ImageSearchResult;
import codingbad.com.olxtechnicaltest.dto.ResponseDto;
import codingbad.com.olxtechnicaltest.manager.DataManager;
import codingbad.com.olxtechnicaltest.manager.SessionManager;
import codingbad.com.olxtechnicaltest.model.Categories;
import codingbad.com.olxtechnicaltest.model.Category;
import codingbad.com.olxtechnicaltest.service.SearchService;
import codingbad.com.olxtechnicaltest.utils.UIUtils;
import retrofit2.HttpException;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ayelen Chavez on 4/18/16.
 *
 * Main and only Activity.
 * It will show the corresponding fragment and communicate with them.
 *
 * It has an instance to the SearchService and does the main search.
 */
public class MainActivity extends AbstractAppCompatActivity implements
        MainFragment.Callbacks,
        ShowCategoriesFragment.Callbacks {

    private static final String TAG = MainActivity.class.getSimpleName();

    // if true, DummyData will be used instead of the Custom Search Service
    private static final boolean TESTING = true;

    @Inject
    protected SessionManager sessionManager;

    @Inject
    protected DataManager dataManager;

    @Inject
    protected Categories categories;

    @Inject
    protected CategoryEventTrackerHelper categoryEventTrackerHelper;

    private CompositeSubscription compositeSubscription;

    @Inject
    private SearchService searchService;

    private Scheduler ioScheduler = Schedulers.io();

    private Scheduler mainThreadScheduler = AndroidSchedulers.mainThread();

    @Override
    protected void setInitialFragment() {
        if (sessionManager.isFirstTime()) {
            setInitialFragment(ShowCategoriesFragment.newInstance());
        } else {
            setInitialFragment(MainFragment.newInstance());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeSubscription = new CompositeSubscription();

        // not sure this should be here
        if (sessionManager.isFirstTime()) {
            initializeDatabase();
            sessionManager.setFirstTime(false);
        }

        // not even this
        if (dataManager.isEmpty()) {
            readFromDatabase();
        }
    }

    private void readFromDatabase() {
        SQLiteDatabase db = categoryEventTrackerHelper.getReadableDatabase();

        String[] projection = {
                CategoryEvent.COLUMN_NAME_CATEGORY_ID,
                CategoryEvent.COLUMN_NAME_INITIAL,
                CategoryEvent.COLUMN_NAME_SEARCH_CRITERIA,
                CategoryEvent.COLUMN_NAME_CLICKS
        };
        Cursor c = db.query(
                CategoryEvent.TABLE_NAME, projection, null, null, null,
                null, null
        );

        int columnIndexName = c.getColumnIndexOrThrow(
                CategoryEvent.COLUMN_NAME_CATEGORY_ID);
        int columnIndexInitials = c.getColumnIndexOrThrow(
                CategoryEvent.COLUMN_NAME_INITIAL);
        int columnIndexSearchCriteria = c.getColumnIndexOrThrow(
                CategoryEvent.COLUMN_NAME_SEARCH_CRITERIA);
        int columnIndexClicks = c.getColumnIndexOrThrow(
                CategoryEvent.COLUMN_NAME_CLICKS);

        String categoryName;
        String initials;
        String searchCriteria;
        int clicks;

        while (c.moveToNext()) {
            // look for the url of the image tha will represent the category
            // and then create the category
            categoryName = c.getString(columnIndexName);
            initials = c.getString(columnIndexInitials);
            searchCriteria = c.getString(columnIndexSearchCriteria);
            clicks = c.getInt(columnIndexClicks);

            if (TESTING) {
                String url = DummyData.getUrlFor(categoryName);
                List<String> urls = DummyData.getUrlsFor(categoryName);
                dataManager.addCategory(new Category(categoryName, url, initials, clicks, urls));
            } else {
                getOneImageUrl(searchCriteria, categoryName, initials, clicks);
            }
        }

        if (TESTING) {
            dataManager.calculateCategories();
        }
        c.close();
    }

    private void initializeDatabase() {
        SQLiteDatabase db = categoryEventTrackerHelper.getWritableDatabase();
        ContentValues values;
        for (Categories.InitialCategory initialCategory : categories.getInitialCategories()) {
            values = new ContentValues();
            values.put(CategoryEvent.COLUMN_NAME_CATEGORY_ID,
                    initialCategory.getName());
            values.put(CategoryEvent.COLUMN_NAME_CLICKS, 1);
            values.put(CategoryEvent.COLUMN_NAME_INITIAL,
                    initialCategory.getInitials());
            values.put(CategoryEvent.COLUMN_NAME_SEARCH_CRITERIA,
                    initialCategory.getSearchCriteria());
            db.insert(CategoryEvent.TABLE_NAME, null, values);
        }
    }

    private void getOneImageUrl(final String searchCriteria, final String categoryName,
            final String initials, final int clicks) {

        Subscription subscription = searchService
                .search(searchCriteria)
                .subscribeOn(ioScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe(new Observer<ResponseDto>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "Search Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "Search Error");
                        if (e instanceof HttpException) {
                            if (((HttpException) e).code() == 403) {
                                // just in case I used my quota but want to test the rest of the app
                                String url = DummyData.getUrlFor(categoryName);
                                List<String> urls = DummyData.getUrlsFor(categoryName);
                                dataManager.addCategory(
                                        new Category(categoryName, url, initials, clicks, urls));
                                // show snackbar but continue
                                UIUtils.showDismissibleSnackBar(MainActivity.this.mainLayout,
                                        R.string.error_quota_exceeded);
                            }
                        }
                    }

                    @Override
                    public void onNext(ResponseDto responseDto) {
                        Log.i(TAG, "Search Next");
                        List<String> urls = getUrls(responseDto.getResult());

                        // get one random image from the first RANDOMIZE_SIZE
                        int index = new Random()
                                .nextInt(OlxTechnicalTestApplication.RANDOMIZE_SIZE);
                        String url = responseDto.getResult().get(index).getImageUrl();

                        dataManager.addCategory(
                                new Category(categoryName, url, initials, clicks, urls));
                    }
                });

        this.compositeSubscription.add(subscription);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeSubscription != null) {
            compositeSubscription.clear();
        }
    }

    private List<String> getUrls(List<ImageSearchResult> result) {
        List<String> urls = new ArrayList<>();
        for (ImageSearchResult imageSearchResult : result) {
            urls.add(imageSearchResult.getImageUrl());
        }

        return urls;
    }

    @Override
    public void showCategories() {
        replaceFragment(ShowCategoriesFragment.newInstance());
    }

    @Override
    public Category getMainCategory() {
        return dataManager.getMainCategory();
    }

    @Override
    public void showListingForMainCategory() {
        // TODO: show listing!
    }
}
