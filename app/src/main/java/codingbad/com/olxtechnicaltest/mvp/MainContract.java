package codingbad.com.olxtechnicaltest.mvp;

import codingbad.com.olxtechnicaltest.model.Category;

/**
 * Created by Ayelen Chavez on 4/19/16.
 *
 * Contract between the main model, view and presenter.
 */
public interface MainContract {

    interface View {

        void onServerError();

        void exceededQuota();

        void dataIsReady(Category category);

        void readingFromDatabaseStarted();

        void readingFromDatabaseFinished(Category category);
    }

    interface Presenter {

        void attachMVPView(View view);

        void detachMVPView();

        void getOneImageUrl(final String searchCriteria, final String categoryName,
                final String initials, final int clicks);

        void onCreate();

        void readingFromDatabaseStarted();

        void readingFromDatabaseFinished(Category category);

        void saveOneMoreClick(Category category);

        boolean isFirstTime();

        Category getMainCategory();

        void calculateCategories();
    }

    interface Model {

        void addCategory(Category category);

        boolean isReady();

        Category getMainCategory();

        void onCreate();

        void saveOneMoreClick(Category category);

        boolean isFirstTime();

        void calculateCategories();
    }
}
