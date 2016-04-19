package codingbad.com.olxtechnicaltest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.felipecsl.asymmetricgridview.library.Utils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import codingbad.com.olxtechnicaltest.R;
import codingbad.com.olxtechnicaltest.model.Category;

/**
 * Created by Ayelen Chavez on 4/18/16.
 */
public class CategoryView extends FrameLayout {

    @Bind(R.id.category_view_image)
    protected ImageView categoryView;

    @Bind(R.id.category_view_letter)
    protected TextView categoryLetter;

    private CategoryListener categoryListener;

    public CategoryView(Context context) {
        super(context);
        init();
    }

    public CategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CategoryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.category_view, this);
        ButterKnife.bind(this, view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            categoryListener.onClick();
        }
        return super.onTouchEvent(event);
    }

    public void setModel(Category category) {
        int size = Utils
                .dpToPx(this.getContext(), getResources().getDimension(R.dimen.category_height));
        categoryLetter.setText(category.getCategoryFirstLetter());

        int ratio = category.widthRatio();

        Picasso.with(getContext())
                .load(category.getImageUrl())
                .centerCrop()
                .resize(size * ratio, size)
                .placeholder(R.drawable.placeholder)
                .into(categoryView);
    }

    public void setCategoryClickListener(CategoryListener categoryListener) {
        this.categoryListener = categoryListener;
    }

    public interface CategoryListener {

        void onClick();
    }
}
