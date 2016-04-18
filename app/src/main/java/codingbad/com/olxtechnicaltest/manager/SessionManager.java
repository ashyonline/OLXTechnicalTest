package codingbad.com.olxtechnicaltest.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ayelen Chavez on 4/18/16.
 * Control Session Data.
 */
@Singleton
public class SessionManager {

    private static final String IS_FIRST_TIME = "is_first_time";

    private final String PREF_NAME = "light_pass_app_prefs";

    protected SharedPreferences sharedPref;

    @Inject
    public SessionManager(Context context) {
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // For now it only knows whether the user was using the app before or not
    public boolean isFirstTime() {
        return this.sharedPref.getBoolean(IS_FIRST_TIME, true);
    }

    public void setFirstTime(boolean firstTime) {
        this.saveValue(IS_FIRST_TIME, firstTime);
    }

    protected void saveValue(String key, boolean value) {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
