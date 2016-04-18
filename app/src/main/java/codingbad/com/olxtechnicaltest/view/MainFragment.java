package codingbad.com.olxtechnicaltest.view;

import android.support.v4.app.Fragment;

import codingbad.com.olxtechnicaltest.R;

/**
 * Created by Ayelen Chavez on 4/18/16.
 */
public class MainFragment extends AbstractFragment<MainFragment.Callbacks> {

    private static final String TAG = MainFragment.class.getSimpleName();

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getMainLayoutResId() {
        return R.layout.fragment_main_layout;
    }

    public interface Callbacks {

    }
}
