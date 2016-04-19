package codingbad.com.olxtechnicaltest;

import android.app.Application;
import android.content.Context;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by Ayelen Chavez on 4/18/16.
 * Custom implementation of the Application class
 */
public class OlxTechnicalTestApplication extends Application {

    public static final int RESULT_SIZE = 10;

    public static final int RANDOMIZE_SIZE = 10;

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
