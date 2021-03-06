package codingbad.com.olxtechnicaltest;

import android.content.Context;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;

import codingbad.com.olxtechnicaltest.mvp.MainContract;
import codingbad.com.olxtechnicaltest.mvp.MainPresenter;

/**
 * Created by Ayelen Chavez on 4/18/16.
 * <p/>
 * AppModule for Guice to indicate how objects should be injected
 */
public class MainAppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Context.class).toProvider(new Provider<Context>() {
            @Override
            public Context get() {
                return OlxTechnicalTestApplication.getContext();
            }
        });
        bind(OlxTechnicalTestApplication.class)
                .toProvider(new Provider<OlxTechnicalTestApplication>() {
                    @Override
                    public OlxTechnicalTestApplication get() {
                        return (OlxTechnicalTestApplication) OlxTechnicalTestApplication
                                .getAppContext();
                    }
                });
        bind(MainContract.Presenter.class).to(MainPresenter.class);
    }
}
