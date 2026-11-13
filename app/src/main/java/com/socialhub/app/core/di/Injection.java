package com.socialhub.app.core.di;

import android.content.Context;
import com.socialhub.app.core.utils.PreferenceManager;
import com.socialhub.app.data.repository.AuthRepository;
import com.socialhub.app.data.repository.NotificationRepository;
import com.socialhub.app.data.repository.PostRepository;

/**
 * Service locator / Dependency provider for ViewModels and Repositories.
 */
public class Injection {

    public static AuthRepository provideAuthRepository(Context context) {
        PreferenceManager preferenceManager = new PreferenceManager(context.getApplicationContext());
        return AuthRepository.getInstance(preferenceManager);
    }

    public static PostRepository providePostRepository() {
        return PostRepository.getInstance();
    }

    public static NotificationRepository provideNotificationRepository() {
        return NotificationRepository.getInstance();
    }
}
