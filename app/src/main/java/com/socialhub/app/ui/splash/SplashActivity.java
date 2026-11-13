package com.socialhub.app.ui.splash;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import com.socialhub.app.core.base.BaseActivity;
import com.socialhub.app.core.di.Injection;
import com.socialhub.app.data.repository.AuthRepository;
import com.socialhub.app.databinding.ActivitySplashBinding;
import com.socialhub.app.ui.auth.LoginActivity;
import com.socialhub.app.ui.main.MainActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected ActivitySplashBinding inflateBinding(LayoutInflater inflater) {
        return ActivitySplashBinding.inflate(inflater);
    }

    @Override
    protected void initViews() {
        AuthRepository authRepository = Injection.provideAuthRepository(this);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (authRepository.isLoggedIn()) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish();
        }, 1200);
    }
}
