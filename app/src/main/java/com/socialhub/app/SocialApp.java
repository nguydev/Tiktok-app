package com.socialhub.app;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
import com.socialhub.app.core.utils.PreferenceManager;

/**
 * Global Application class for initializing singletons, preferences, and themes.
 */
public class SocialApp extends Application {

    private static SocialApp instance;
    private PreferenceManager preferenceManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        preferenceManager = new PreferenceManager(this);

        // Enforce follow system night mode (Light/Dark automatically)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public static SocialApp getInstance() {
        return instance;
    }

    public PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }
}
