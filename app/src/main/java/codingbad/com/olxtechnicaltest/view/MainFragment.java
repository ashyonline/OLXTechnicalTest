package codingbad.com.olxtechnicaltest.view;

import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.OnClick;
import codingbad.com.olxtechnicaltest.R;
import codingbad.com.olxtechnicaltest.model.Category;

/**
 * Created by Ayelen Chavez on 4/18/16.
 * Main fragment shown to the old user.
 *
 * It shows the most clicked category and the option to show it's listing or other categories.
 */
public class MainFragment extends AbstractFragment<MainFragment.Callbacks> {

    private static final String TAG = MainFragment.class.getSimpleName();

    private static final String CATEGORY = "category";

    @Bind(R.id.activity_main_image)
    protected ImageView mainCategory;

    private Category category;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            category = savedInstanceState.getParcelable(CATEGORY);
        }

        if (category != null) {
            initializeView(category);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (category != null) {
            outState.putParcelable(CATEGORY, category);
        }
    }

    /**
     * Case when the view may have been created but the main category wasn't ready
     */
    public void setModel(Category category) {
        this.category = category;
        initializeView(category);
    }

    private void initializeView(Category category) {
        if (isAdded()) {
            int size = (int) getResources().getDimension(R.dimen.big_image);

            Picasso.with(getContext())
                    .load(category.getImageUrl())
                    .centerCrop()
                    .resize(size, size)
                    .placeholder(R.drawable.placeholder)
                    .into(mainCategory);
        }
    }

    @Override
    protected int getMainLayoutResId() {
        return R.layout.fragment_main_layout;
    }

    @OnClick(R.id.activity_main_show_more_categories)
    public void onMoreCategoriesClicked() {
        callbacks.showCategories();
    }

    @OnClick(R.id.activity_main_category_layout)
    public void onMainCategoryClicked() {
        callbacks.showListingForMainCategory();
    }

    public interface Callbacks {

        void showCategories();

        Category getMainCategory();

        void showListingForMainCategory();
    }
}
