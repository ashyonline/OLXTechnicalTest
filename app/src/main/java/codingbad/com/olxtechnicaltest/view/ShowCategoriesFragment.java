package codingbad.com.olxtechnicaltest.view;

import android.support.v4.app.Fragment;

import codingbad.com.olxtechnicaltest.R;

/**
 * Created by Ayelen Chavez on 4/18/16.
 */
public class ShowCategoriesFragment extends AbstractFragment<ShowCategoriesFragment.Callbacks> {

    public static Fragment newInstance() {
        return new ShowCategoriesFragment();
    }

    @Override
    protected int getMainLayoutResId() {
        return R.layout.fragment_show_categories_layout;
    }

    public interface Callbacks {

    }
}
