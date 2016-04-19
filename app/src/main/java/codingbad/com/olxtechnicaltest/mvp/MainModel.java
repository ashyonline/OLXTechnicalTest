package codingbad.com.olxtechnicaltest.mvp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;

import java.util.List;

import codingbad.com.olxtechnicaltest.OlxTechnicalTestApplication;
import codingbad.com.olxtechnicaltest.database.CategoryEventTrackerContract;
import codingbad.com.olxtechnicaltest.database.CategoryEventTrackerHelper;
import codingbad.com.olxtechnicaltest.database.DummyData;
import codingbad.com.olxtechnicaltest.manager.DataManager;
import codingbad.com.olxtechnicaltest.manager.SessionManager;
import codingbad.com.olxtechnicaltest.model.Categories;
import codingbad.com.olxtechnicaltest.model.Category;

/**
 * Created by Ayelen Chavez on 4/19/16.
 */
public class MainModel implements MainContract.Model {

    // if true, DummyData will be used instead of the Custom Search Service
    private static final boolean TESTING = false;

    private final MainContract.Presenter presenter;

    @Inject
    protected SessionManager sessionManager;

    @Inject
    protected DataManager dataManager;

    @Inject
    protected Categories categories;

    @Inject
    protected CategoryEventTrackerHelper categoryEventTrackerHelper;

    public MainModel(MainContract.Presenter presenter) {
        OlxTechnicalTestApplication.injectMembers(this);
        this.presenter = presenter;
    }

    @Override
    public void addCategory(Category category) {
        dataManager.addCategory(category);
    }

    @Override
    public boolean isReady() {
        return dataManager.isReady();
    }

    @Override
    public Category getMainCategory() {
        return dataManager.getMainCategory();
    }

    @Override
    public void onCreate() {
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

    @Override
    public void saveOneMoreClick(Category category) {
        SQLiteDatabase db = categoryEventTrackerHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(CategoryEventTrackerContract.CategoryEvent.COLUMN_NAME_CLICKS,
                category.clicks());

        String selection = CategoryEventTrackerContract.CategoryEvent.COLUMN_NAME_CATEGORY_ID
                + " LIKE ?";
        String[] selectionArgs = {String.valueOf(category.getName())};

        int count = db.update(
                CategoryEventTrackerContract.CategoryEvent.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    @Override
    public boolean isFirstTime() {
        return sessionManager.isFirstTime();
    }

    @Override
    public void calculateCategories() {
        dataManager.calculateCategories();
    }

    @Override
    public List<Category> getCategories() {
        return dataManager.getCategories();
    }

    private void initializeDatabase() {
        SQLiteDatabase db = categoryEventTrackerHelper.getWritableDatabase();
        ContentValues values;
        for (Categories.InitialCategory initialCategory : categories.getInitialCategories()) {
            values = new ContentValues();
            values.put(CategoryEventTrackerContract.CategoryEvent.COLUMN_NAME_CATEGORY_ID,
                    initialCategory.getName());
            values.put(CategoryEventTrackerContract.CategoryEvent.COLUMN_NAME_CLICKS, 1);
            values.put(CategoryEventTrackerContract.CategoryEvent.COLUMN_NAME_INITIAL,
                    initialCategory.getInitials());
            values.put(CategoryEventTrackerContract.CategoryEvent.COLUMN_NAME_SEARCH_CRITERIA,
                    initialCategory.getSearchCriteria());
            db.insert(CategoryEventTrackerContract.CategoryEvent.TABLE_NAME, null, values);
        }
    }

    private void readFromDatabase() {
        presenter.readingFromDatabaseStarted();

        SQLiteDatabase db = categoryEventTrackerHelper.getReadableDatabase();

        String[] projection = {
                CategoryEventTrackerContract.CategoryEvent.COLUMN_NAME_CATEGORY_ID,
                CategoryEventTrackerContract.CategoryEvent.COLUMN_NAME_INITIAL,
                CategoryEventTrackerContract.CategoryEvent.COLUMN_NAME_SEARCH_CRITERIA,
                CategoryEventTrackerContract.CategoryEvent.COLUMN_NAME_CLICKS
        };
        Cursor c = db.query(
                CategoryEventTrackerContract.CategoryEvent.TABLE_NAME, projection, null, null, null,
                null, null
        );

        int columnIndexName = c.getColumnIndexOrThrow(
                CategoryEventTrackerContract.CategoryEvent.COLUMN_NAME_CATEGORY_ID);
        int columnIndexInitials = c.getColumnIndexOrThrow(
                CategoryEventTrackerContract.CategoryEvent.COLUMN_NAME_INITIAL);
        int columnIndexSearchCriteria = c.getColumnIndexOrThrow(
                CategoryEventTrackerContract.CategoryEvent.COLUMN_NAME_SEARCH_CRITERIA);
        int columnIndexClicks = c.getColumnIndexOrThrow(
                CategoryEventTrackerContract.CategoryEvent.COLUMN_NAME_CLICKS);

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
                presenter.getOneImageUrl(searchCriteria, categoryName, initials, clicks);
            }
        }

        if (TESTING) {
            presenter.readingFromDatabaseFinished(dataManager.getMainCategory());
        }
        c.close();
    }

}
