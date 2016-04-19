package codingbad.com.olxtechnicaltest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import codingbad.com.olxtechnicaltest.R;

/**
 * Created by Ayelen Chavez on 4/18/16.
 */
public class ListingView extends LinearLayout {

    @Bind(R.id.listing_view_image)
    protected ImageView imageView;

    public ListingView(Context context) {
        super(context);
        init();
    }

    public ListingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.listing_view, this);
        ButterKnife.bind(this, view);
    }


    public void setModel(String url, boolean isShowingList) {

        // now sure this should be here
        int height;
        int width;
        if (isShowingList) {
            height = (int) getContext().getResources().getDimension(R.dimen.item_size);
            width = height * 4;
        } else {
            height = (int) getContext().getResources().getDimension(R.dimen.item_size_grid);
            width = height;
        }

        Picasso.with(getContext())
                .load(url)
                .centerCrop()
                .resize(width, height)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
    }
}
