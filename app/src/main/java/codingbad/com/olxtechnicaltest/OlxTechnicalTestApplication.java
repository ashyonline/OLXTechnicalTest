package codingbad.com.olxtechnicaltest;

import com.google.inject.Guice;
import com.google.inject.Injector;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ayelen Chavez on 4/18/16.
 * Custom implementation of the Application class
 */
public class OlxTechnicalTestApplication extends Application {

    private static final Injector INJECTOR = Guice.createInjector(new MainAppModule());

    private static OlxTechnicalTestApplication appContext;

    public static Context getAppContext() {
        return appContext;
    }

    public static Context getContext() {
        return appContext.getBaseContext();
    }

    public static void injectMembers(final Object object) {
        INJECTOR.injectMembers(object);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        injectMembers(this);
    }
}