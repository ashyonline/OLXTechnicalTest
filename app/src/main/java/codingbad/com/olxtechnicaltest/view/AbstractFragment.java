package codingbad.com.olxtechnicaltest.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import codingbad.com.olxtechnicaltest.OlxTechnicalTestApplication;

/**
 * Created by Ayelen Chavez on 4/18/16.
 *
 * Base Abstract fragment which has reference to it's callbacks (typically implemented by its
 * activity (but could be any object).
 */
public abstract class AbstractFragment<T> extends Fragment {

    protected T callbacks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        OlxTechnicalTestApplication.injectMembers(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbacks = (T) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() + " must implement Callback interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(getMainLayoutResId(), container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected abstract int getMainLayoutResId();

    @Override
    public void onDetach() {
        super.onDetach();
        this.callbacks = null;
    }

    protected AppCompatActivity getAppCompatActivity() {
        return (AppCompatActivity) getActivity();
    }

}