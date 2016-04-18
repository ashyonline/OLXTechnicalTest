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

    @Bind(R.id.activity_main_image)
    protected ImageView mainCategory;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Category category = callbacks.getMainCategory();

        int size = (int) getResources().getDimension(R.dimen.big_image);

        Picasso.with(getContext())
                .load(category.getImageUrl())
                .centerCrop()
                .resize(size, size)
                .placeholder(R.drawable.placeholder)
                .into(mainCategory);
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
