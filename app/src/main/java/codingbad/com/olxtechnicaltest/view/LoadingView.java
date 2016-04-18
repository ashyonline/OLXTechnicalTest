package codingbad.com.olxtechnicaltest.view;

import com.google.inject.Inject;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import codingbad.com.olxtechnicaltest.R;

/**
 * Created by Ayelen Chavez on 4/18/16.
 *
 * View that shows progress to the user and manages errors
 */
public class LoadingView extends FrameLayout implements View.OnClickListener {

    @Bind(R.id.loading_view_progress_bar)
    ProgressBar progressBar;

    @Bind(R.id.loading_view_linear_layout)
    LinearLayout linearLayout;

    @Bind(R.id.loading_view_retry_button)
    Button retryButton;

    private Callback callback;

    private ViewGroup viewGroup;

    @Inject
    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.loading_view, this);
        ButterKnife.bind(this, view);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(fetchThemeColor(), android.graphics.PorterDuff.Mode.SRC_ATOP);
        setBackgroundColor(fetchThemeBackgroundColor());

        retryButton.setOnClickListener(this);
    }

    private int fetchThemeColor() {
        TypedValue typedValue = new TypedValue();

        TypedArray a = getContext()
                .obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    private int fetchThemeBackgroundColor() {
        TypedValue typedValue = new TypedValue();

        TypedArray a = getContext().obtainStyledAttributes(typedValue.data,
                new int[]{android.R.attr.windowBackground});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    public void attach(final ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
        final FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        if (viewGroup instanceof RelativeLayout || viewGroup instanceof FrameLayout) {
            viewGroup.addView(this, layoutParams);

        } else if (viewGroup instanceof LinearLayout || viewGroup instanceof CoordinatorLayout) {
            viewGroup.addView(this, 0, layoutParams);
        }
    }

    public void attach(final ViewGroup viewGroup, boolean show) {
        attach(viewGroup);

        if (show) {
            show();
        }
    }

    public void attach(final ViewGroup viewGroup, boolean show, Callback callback) {
        attach(viewGroup, true);
        this.callback = callback;
    }

    public void show() {
        setVisibility(VISIBLE);
        linearLayout.setVisibility(GONE);
    }

    public void showErrorView() {
        progressBar.setVisibility(GONE);
        linearLayout.setVisibility(VISIBLE);
    }

    public void showLoadingView() {
        progressBar.setVisibility(VISIBLE);
        linearLayout.setVisibility(GONE);
    }

    public void showLoadingView(Callback callback) {
        progressBar.setVisibility(VISIBLE);
        linearLayout.setVisibility(GONE);
        this.callback = callback;
    }

    public void showErrorView(Callback callback) {
        progressBar.setVisibility(GONE);
        linearLayout.setVisibility(VISIBLE);
        this.callback = callback;
    }

    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }

    public void dismiss() {
        setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        showLoadingView();

        if (callback != null) {
            callback.onRetryClick();
        }
    }

    public interface Callback {

        void onRetryClick();
    }
}
