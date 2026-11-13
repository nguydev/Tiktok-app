package com.socialhub.app.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Handles persistent local storage of User authentication tokens and session status.
 */
public class PreferenceManager {

    private static final String PREF_NAME = "socialhub_secure_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_FULL_NAME = "full_name";

    private final SharedPreferences prefs;

    public PreferenceManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserSession(String token, String userId, String username, String fullName) {
        prefs.edit()
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .putString(KEY_AUTH_TOKEN, token)
                .putString(KEY_USER_ID, userId)
                .putString(KEY_USERNAME, username)
                .putString(KEY_FULL_NAME, fullName)
                .apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, "Guest");
    }

    public String getFullName() {
        return prefs.getString(KEY_FULL_NAME, "SocialHub User");
    }

    public String getUserId() {
        return prefs.getString(KEY_USER_ID, "");
    }

    public void clearSession() {
        prefs.edit().clear().apply();
    }
}
