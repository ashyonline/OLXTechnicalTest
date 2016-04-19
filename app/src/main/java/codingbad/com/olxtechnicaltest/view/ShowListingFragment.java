package codingbad.com.olxtechnicaltest.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import codingbad.com.olxtechnicaltest.R;

/**
 * Created by Ayelen Chavez on 4/18/16.
 * <p/>
 * Show list of element for the given Category.
 * <p/>
 * The user may choose between list or grid view.
 */
public class ShowListingFragment extends AbstractFragment<ShowListingFragment.Callbacks>
        implements ListingItemAdapter.RecyclerViewListener {

    private static final java.lang.String SHOWING_LISTING = "showing_listing";

    private static final int COLUMNS = 2;

    @Bind(R.id.fragment_show_listing_list)
    protected RecyclerView itemList;

    @Bind(R.id.fragment_show_listing_title)
    protected TextView categoryName;

    private boolean showingList = true;

    private ListingItemAdapter itemsAdapter;

    public static Fragment newInstance() {
        return new ShowListingFragment();
    }

    @Override
    protected int getMainLayoutResId() {
        return R.layout.fragment_show_listing_layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            showingList = savedInstanceState.getBoolean(SHOWING_LISTING, true);
        }

        categoryName.setText(
                getContext().getString(R.string.listing_title, callbacks.getCategoryName()));
        setupItems(showingList);
    }

    private void setupItems(boolean showingList) {
        if (showingList) {
            showList();
        } else {
            showGrid();
        }
        itemList.setHasFixedSize(true);

        itemsAdapter = new ListingItemAdapter(this.getContext(), this);
        itemsAdapter.isShowingList(showingList);
        itemsAdapter.addItemList(callbacks.getImages());
        itemList.setAdapter(itemsAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SHOWING_LISTING, showingList);
    }

    @OnCheckedChanged(R.id.fragment_show_listing_button)
    public void toggleView(boolean checked) {
        itemsAdapter.isShowingList(!checked);
        if (checked) {
            showingList = false;
            showGrid();
        } else {
            showingList = true;
            showList();
        }
    }

    private void showList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        itemList.setLayoutManager(layoutManager);
    }

    private void showGrid() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(),
                COLUMNS);
        itemList.setLayoutManager(layoutManager);
    }

    @Override
    public void onItemClickListener(View view, int position) {

    }

    public interface Callbacks {

        List<String> getImages();

        String getCategoryName();
    }
}
