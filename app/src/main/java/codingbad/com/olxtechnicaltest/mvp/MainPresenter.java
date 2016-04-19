package codingbad.com.olxtechnicaltest.mvp;

import android.util.Log;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import codingbad.com.olxtechnicaltest.OlxTechnicalTestApplication;
import codingbad.com.olxtechnicaltest.database.DummyData;
import codingbad.com.olxtechnicaltest.dto.ImageSearchResult;
import codingbad.com.olxtechnicaltest.dto.ResponseDto;
import codingbad.com.olxtechnicaltest.model.Category;
import codingbad.com.olxtechnicaltest.service.SearchService;
import retrofit2.HttpException;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ayelen Chavez on 4/19/16.
 */
public class MainPresenter implements MainContract.Presenter {

    private static final String TAG = MainPresenter.class.getSimpleName();

    protected MainContract.Model mainModel;

    private MainContract.View mainView;

    private Scheduler ioScheduler = Schedulers.io();

    private Scheduler mainThreadScheduler = AndroidSchedulers.mainThread();

    private CompositeSubscription compositeSubscription;

    @Inject
    private SearchService searchService;

    @Inject
    public MainPresenter() {
        OlxTechnicalTestApplication.injectMembers(this);
        mainModel = new MainModel(this);
    }

    @Override
    public void attachMVPView(MainContract.View view) {
        this.mainView = view;
        compositeSubscription = new CompositeSubscription();

    }

    public void getOneImageUrl(final String searchCriteria, final String categoryName,
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
                        mainView.onServerError();
                        if (e instanceof HttpException) {
                            if (((HttpException) e).code() == 403) {
                                // just in case I used my quota but want to test the rest of the app
                                String url = DummyData.getUrlFor(categoryName);
                                List<String> urls = DummyData.getUrlsFor(categoryName);
                                mainModel.addCategory(
                                        new Category(categoryName, url, initials, clicks, urls));
                                mainView.exceededQuota();
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

                        mainModel.addCategory(
                                new Category(categoryName, url, initials, clicks, urls));

                        if (mainModel.isReady()) {
                            mainView.dataIsReady(mainModel.getMainCategory());

                        }
                    }
                });

        this.compositeSubscription.add(subscription);
    }

    @Override
    public void onCreate() {

        mainModel.onCreate();

    }

    @Override
    public void readingFromDatabaseStarted() {
        mainView.readingFromDatabaseStarted();
    }

    @Override
    public void readingFromDatabaseFinished(Category category) {
        mainView.readingFromDatabaseFinished(category);
    }

    @Override
    public void saveOneMoreClick(Category category) {
        mainModel.saveOneMoreClick(category);
    }

    @Override
    public boolean isFirstTime() {
        return mainModel.isFirstTime();
    }

    @Override
    public Category getMainCategory() {
        return mainModel.getMainCategory();
    }

    @Override
    public void calculateCategories() {
        mainModel.calculateCategories();
    }

    @Override
    public List<Category> getCategories() {
        return mainModel.getCategories();
    }

    private List<String> getUrls(List<ImageSearchResult> result) {
        List<String> urls = new ArrayList<>();

        for (ImageSearchResult imageSearchResult : result) {
            urls.add(imageSearchResult.getImageUrl());
        }

        return urls;
    }

    @Override
    public void detachMVPView() {
        this.mainView = null;
        if (compositeSubscription != null) {
            compositeSubscription.clear();
        }
    }
}
