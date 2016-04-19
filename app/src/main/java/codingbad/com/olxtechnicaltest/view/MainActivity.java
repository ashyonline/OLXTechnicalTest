package codingbad.com.olxtechnicaltest.view;

import android.os.Bundle;

import com.google.inject.Inject;

import java.util.List;

import codingbad.com.olxtechnicaltest.R;
import codingbad.com.olxtechnicaltest.model.Category;
import codingbad.com.olxtechnicaltest.mvp.MainContract;
import codingbad.com.olxtechnicaltest.utils.UIUtils;

/**
 * Created by Ayelen Chavez on 4/18/16.
 * <p/>
 * Main and only Activity.
 * It will show the corresponding fragment and communicate with them.
 * <p/>
 * It has an instance to the SearchService and does the main search.
 */
public class MainActivity extends AbstractAppCompatActivity implements
        MainFragment.Callbacks,
        ShowCategoriesFragment.Callbacks,
        ShowListingFragment.Callbacks, LoadingView.Callback,
        MainContract.View {

    private static final String SELECTED_CATEGORY = "selected_category";

    protected LoadingView loadingView;

    @Inject
    protected MainContract.Presenter presenter;

    private Category selectedCategory;

    private MainFragment mainFragment;
    private ShowCategoriesFragment showCategoryFragment;

    @Override
    protected void setInitialFragment() {
        if (presenter.isFirstTime()) {
            showCategoryFragment = (ShowCategoriesFragment) ShowCategoriesFragment.newInstance();
            setInitialFragment(showCategoryFragment);
        } else {
            mainFragment = (MainFragment) MainFragment.newInstance();
            setInitialFragment(mainFragment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attachMVPView(this);

        loadingView = new LoadingView(this);

        if (savedInstanceState != null) {
            selectedCategory = savedInstanceState.getParcelable(SELECTED_CATEGORY);
        }

        presenter.onCreate();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachMVPView();
    }

    @Override
    public void showCategories() {
        replaceFragment(ShowCategoriesFragment.newInstance());
    }

    @Override
    public Category getMainCategory() {
        return presenter.getMainCategory();
    }

    @Override
    public void showListingForMainCategory() {
        this.selectedCategory = getMainCategory();
        showListingFor();
    }

    @Override
    public void onCategoryClicked(Category category) {
        category.addClick();
        // update database
        presenter.saveOneMoreClick(category);
        presenter.calculateCategories();
        this.selectedCategory = category;
        showListingFor();
    }

    private void showListingFor() {
        replaceFragment(ShowListingFragment.newInstance());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (selectedCategory != null) {
            outState.putParcelable(SELECTED_CATEGORY, selectedCategory);
        }
    }

    @Override
    public List<String> getImages() {
        return this.selectedCategory.getUrls();
    }

    @Override
    public String getCategoryName() {
        return selectedCategory.getName();
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onServerError() {
        loadingView.showErrorView();
    }

    @Override
    public void exceededQuota() {
        // show snackbar but continue
        UIUtils.showDismissibleSnackBar(mainLayout,
                R.string.error_quota_exceeded);
    }

    @Override
    public void dataIsReady(Category category) {
        loadingView.dismiss();

        if (mainFragment != null) {
            mainFragment.setModel(category);
        }

        if (showCategoryFragment != null) {
            showCategoryFragment.setModel(presenter.getCategories());
        }
    }

    @Override
    public void readingFromDatabaseStarted() {
        // show loading view
        loadingView.attach(mainLayout, true, this);
    }

    @Override
    public void readingFromDatabaseFinished(Category category) {
        loadingView.dismiss();
        if (mainFragment != null) {
            mainFragment.setModel(category);
        }
    }
}
