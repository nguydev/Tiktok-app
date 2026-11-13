package com.socialhub.app.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.socialhub.app.core.network.Resource;
import com.socialhub.app.core.utils.PreferenceManager;
import com.socialhub.app.data.model.User;

public class AuthRepository {

    private static volatile AuthRepository instance;
    private final PreferenceManager preferenceManager;

    private AuthRepository(PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
    }

    public static AuthRepository getInstance(PreferenceManager preferenceManager) {
        if (instance == null) {
            synchronized (AuthRepository.class) {
                if (instance == null) {
                    instance = new AuthRepository(preferenceManager);
                }
            }
        }
        return instance;
    }

    public LiveData<Resource<User>> login(String usernameOrEmail, String password) {
        MutableLiveData<Resource<User>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));

        // Simulate async network latency (300ms)
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            if (usernameOrEmail != null && !usernameOrEmail.trim().isEmpty() && password.length() >= 6) {
                User user = new User("u100", usernameOrEmail.toLowerCase(), "Alex Morgan", "Software Engineer & Creator", "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400", true);
                preferenceManager.saveUserSession("mock_jwt_token_12345", user.getId(), user.getUsername(), user.getFullName());
                result.setValue(Resource.success(user));
            } else {
                result.setValue(Resource.error("Invalid credentials. Password must be at least 6 characters.", null));
            }
        }, 300);

        return result;
    }

    public LiveData<Resource<User>> register(String fullName, String username, String email, String password) {
        MutableLiveData<Resource<User>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));

        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            User user = new User("u100", username.toLowerCase(), fullName, "New member of SocialHub", "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400", false);
            preferenceManager.saveUserSession("mock_jwt_token_12345", user.getId(), user.getUsername(), user.getFullName());
            result.setValue(Resource.success(user));
        }, 300);

        return result;
    }

    public boolean isLoggedIn() {
        return preferenceManager.isLoggedIn();
    }

    public void logout() {
        preferenceManager.clearSession();
    }
}
