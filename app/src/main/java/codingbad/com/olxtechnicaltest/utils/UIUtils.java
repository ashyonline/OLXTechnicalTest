package codingbad.com.olxtechnicaltest.utils;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import codingbad.com.olxtechnicaltest.R;

/**
 * Created by Ayelen Chavez on 4/18/16.
 *
 * Some UI helper functions
 */
public class UIUtils {

    public static void showDismissibleSnackBar(View parentView, String text) {
        final Snackbar snackBar = Snackbar.make(parentView, text, Snackbar.LENGTH_LONG);
        snackBar.setActionTextColor(
                ContextCompat.getColor(parentView.getContext(), R.color.colorPrimaryLight));
        snackBar.setAction(R.string.dismiss, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });

        snackBar.show();
    }

    public static void showDismissibleSnackBar(View parentView, int textResId) {
        String text = parentView.getResources().getString(textResId);
        showDismissibleSnackBar(parentView, text);
    }
}
