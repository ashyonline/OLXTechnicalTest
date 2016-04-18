package codingbad.com.olxtechnicaltest.view;

import android.support.v4.app.Fragment;

import codingbad.com.olxtechnicaltest.R;

/**
 * Created by Ayelen Chavez on 4/18/16.
 */
public class ShowListingFragment extends AbstractFragment<ShowListingFragment.Callbacks> {

    public static Fragment newInstance() {
        return new ShowListingFragment();
    }

    @Override
    protected int getMainLayoutResId() {
        return R.layout.fragment_show_listing_layout;
    }

    public interface Callbacks {

    }
}
