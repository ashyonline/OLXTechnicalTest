package codingbad.com.olxtechnicaltest.view;

import com.google.inject.Inject;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import codingbad.com.olxtechnicaltest.OlxTechnicalTestApplication;
import codingbad.com.olxtechnicaltest.dto.ImageSearchResult;
import codingbad.com.olxtechnicaltest.dto.ResponseDto;
import codingbad.com.olxtechnicaltest.manager.DataManager;
import codingbad.com.olxtechnicaltest.manager.SessionManager;
import codingbad.com.olxtechnicaltest.model.Categories;
import codingbad.com.olxtechnicaltest.model.Category;
import codingbad.com.olxtechnicaltest.service.SearchService;
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
 *
 */
public class MainActivity extends AbstractAppCompatActivity implements MainFragment.Callbacks {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    protected SessionManager sessionManager;

    @Inject
    protected DataManager dataManager;

    @Inject
    protected Categories categories;

    private CompositeSubscription compositeSubscription;

    @Inject
    private SearchService searchService;

    private Scheduler ioScheduler = Schedulers.io();

    private Scheduler mainThreadScheduler = AndroidSchedulers.mainThread();

    @Override
    protected void setInitialFragment() {
        setInitialFragment(MainFragment.newInstance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeSubscription != null) {
            compositeSubscription.clear();
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
                    }

                    @Override
                    public void onNext(ResponseDto responseDto) {
                        Log.i(TAG, "Search Next");
                        List<String> urls = getUrls(responseDto.getResult());

                        // get one random image from the first RANDOMIZE_SIZE
                        int index = new Random().nextInt(OlxTechnicalTestApplication.RANDOMIZE_SIZE);
                        String url = responseDto.getResult().get(index).getImageUrl();

                        dataManager.addCategory(new Category(categoryName, url, initials, clicks, urls));
                    }
                });

        this.compositeSubscription.add(subscription);
    }

    private List<String> getUrls(List<ImageSearchResult> result) {
        List<String> urls = new ArrayList<>();
        for (ImageSearchResult imageSearchResult : result) {
            urls.add(imageSearchResult.getImageUrl());
        }

        return urls;
    }
}
