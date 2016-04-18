package codingbad.com.olxtechnicaltest.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import codingbad.com.olxtechnicaltest.OlxTechnicalTestApplication;
import codingbad.com.olxtechnicaltest.R;

/**
 * Created by Ayelen Chavez on 4/18/16.
 */
public abstract class AbstractAppCompatActivity extends AppCompatActivity {

    @Bind(R.id.material_toolbar)
    protected Toolbar toolbar;

    protected FrameLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OlxTechnicalTestApplication.injectMembers(this);
        setInitialFragment();
        ButterKnife.bind(this);
        setToolbar();
    }

    /**
     * set-up toolbar
     */
    protected void setToolbar() {
        if (toolbar != null) {
            ViewCompat
                    .setElevation(toolbar, getResources().getDimensionPixelSize(R.dimen.elevation));
            setSupportActionBar(toolbar);
        }
    }

    /**
     * set material status bar color to the one defined as primary_dark, only if the Android
     * version is Lollipop or newer
     */
    protected void setMaterialStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    /**
     * set material status bar color to the one defined as parameter, only if the Android version
     * is Lollipop or newer
     *
     * @param colorResId the desired color resource id
     */
    protected void setMaterialStatusBarColor(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, colorResId));
        }
    }


    /**
     * If home item is clicked in the menu, and there is nothing in the fragment stack to pop,
     * finish the activity
     *
     * @param item menu item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!popBackStack()) {
                    finish();
                }
                return true;
            default:
                return false;
        }
    }

    /**
     * This method defines which is the initial Fragment. Classes that extends
     * this Class should override it and tells the Parent Class which is the
     * initial fragment to load.
     */
    protected abstract void setInitialFragment();


    /**
     * This method loads the initial fragment, it should be called inside
     * setInitialFragment().
     *
     * @param layoutResId the activity layout
     * @param viewId      the Main view id.
     * @param fragment    the initial Fragment
     */
    protected void setInitialFragment(int layoutResId, int viewId, Fragment fragment) {
        setContentView(layoutResId);
        mainLayout = (FrameLayout) findViewById(viewId);
        setInitialFragment(mainLayout, fragment);
    }

    /**
     * This method loads the initial fragment, it should be called inside
     * setInitialFragment().
     *
     * @param viewId   the Main view id.
     * @param fragment the initial Fragment
     */
    protected void setInitialFragment(int viewId, Fragment fragment) {
        mainLayout = (FrameLayout) findViewById(viewId);
        setInitialFragment(mainLayout, fragment);
    }

    /**
     * This method loads the initial fragment, it should be called inside
     * setInitialFragment().
     *
     * @param fragment the initial Fragment
     */
    protected void setInitialFragment(Fragment fragment) {
        setInitialFragment(getBaseLayoutResId(), R.id.fragment_container, fragment);
    }

    protected int getBaseLayoutResId() {
        return R.layout.activity_single_fragment;
    }

    /**
     * This method loads the initial fragment
     *
     * @param view     the Main View, it is recommended to use a FrameLayout
     * @param fragment the initial fragment
     */
    private void setInitialFragment(View view, Fragment fragment) {
        if (getCurrentFragment() == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(view.getId(), fragment).commit();
        }
    }

    /**
     * This method replace the existing fragment with a current one. This new
     * fragment is added to the back stack.
     *
     * @param newFragment - The new Fragment that will replace the current visible fragment.
     */
    protected void replaceFragment(Fragment newFragment) {
        this.replaceFragment(newFragment, true);
    }

    /**
     * This method replace the existing fragment with a current one. This new
     * fragment could be added or not to the back stack.
     *
     * @param newFragment    The new Fragment that will replace the current visible fragment.
     * @param addToBackStack True if the new Fragment should be added to the back stack, false
     *                       otherwise.
     */
    protected void replaceFragment(Fragment newFragment, boolean addToBackStack) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.replace(mainLayout.getId(), newFragment).commit();
    }

    protected boolean popBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager.popBackStackImmediate();
    }

    /**
     * Returns the current showing fragment or null if no fragment is added.
     *
     * @return the fragment previously added or null
     */
    protected Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(mainLayout.getId());
    }
}
