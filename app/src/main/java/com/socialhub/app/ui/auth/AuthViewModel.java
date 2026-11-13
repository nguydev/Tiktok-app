package com.socialhub.app.ui.auth;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.socialhub.app.core.di.Injection;
import com.socialhub.app.core.network.Resource;
import com.socialhub.app.data.model.User;
import com.socialhub.app.data.repository.AuthRepository;

public class AuthViewModel extends AndroidViewModel {

    private final AuthRepository authRepository;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        this.authRepository = Injection.provideAuthRepository(application);
    }

    public LiveData<Resource<User>> login(String usernameOrEmail, String password) {
        return authRepository.login(usernameOrEmail, password);
    }

    public LiveData<Resource<User>> register(String fullName, String username, String email, String password) {
        return authRepository.register(fullName, username, email, password);
    }

    public boolean isLoggedIn() {
        return authRepository.isLoggedIn();
    }
}
