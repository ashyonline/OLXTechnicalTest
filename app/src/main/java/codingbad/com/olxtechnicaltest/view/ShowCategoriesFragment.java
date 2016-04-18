package codingbad.com.olxtechnicaltest.view;

import com.google.inject.Inject;

import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.Bind;
import codingbad.com.olxtechnicaltest.R;
import codingbad.com.olxtechnicaltest.manager.DataManager;
import codingbad.com.olxtechnicaltest.model.Category;

/**
 * Created by Ayelen Chavez on 4/18/16.
 * Show all categories when the most clicked will be bigger than the less clicked.
 */
public class ShowCategoriesFragment extends AbstractFragment<ShowCategoriesFragment.Callbacks>
        implements CategoryEventListener {

    public static final int COLUMN_WIDTH = 100;

    @Bind(R.id.fragment_show_categories_categories)
    protected AsymmetricGridView categories;

    @Inject
    protected DataManager dataManager;

    private CategoryListAdapter adapter;

    public static Fragment newInstance() {
        return new ShowCategoriesFragment();
    }

    @Override
    protected int getMainLayoutResId() {
        return R.layout.fragment_show_categories_layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categories.setRequestedColumnWidth(Utils.dpToPx(this.getContext(), COLUMN_WIDTH));
        categories.setAllowReordering(true);
        adapter = new CategoryListAdapter(this.getContext(), android.R.layout.activity_list_item,
                dataManager.getCategories(), this);
        AsymmetricGridViewAdapter asymmetricAdapter =
                new AsymmetricGridViewAdapter<>(this.getContext(), categories, adapter);
        categories.setAdapter(asymmetricAdapter);
    }

    @Override
    public void onItemClickListener(Category category) {
        callbacks.onCategoryClicked(category);
    }

    public interface Callbacks {

        void onCategoryClicked(Category category);
    }
}
