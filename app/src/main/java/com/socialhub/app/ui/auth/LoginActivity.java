package com.socialhub.app.ui.auth;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import com.socialhub.app.core.base.BaseActivity;
import com.socialhub.app.core.network.Resource;
import com.socialhub.app.databinding.ActivityLoginBinding;
import com.socialhub.app.ui.main.MainActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private AuthViewModel viewModel;

    @Override
    protected ActivityLoginBinding inflateBinding(LayoutInflater inflater) {
        return ActivityLoginBinding.inflate(inflater);
    }

    @Override
    protected void initViews() {
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        getBinding().btnLogin.setOnClickListener(v -> performLogin());

        getBinding().tvGoToRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void performLogin() {
        String username = getBinding().etUsername.getText() != null ? getBinding().etUsername.getText().toString().trim() : "";
        String password = getBinding().etPassword.getText() != null ? getBinding().etPassword.getText().toString().trim() : "";

        if (username.isEmpty()) {
            getBinding().tilUsername.setError("Please enter your username or email");
            return;
        } else {
            getBinding().tilUsername.setError(null);
        }

        if (password.isEmpty()) {
            getBinding().tilPassword.setError("Please enter your password");
            return;
        } else {
            getBinding().tilPassword.setError(null);
        }

        viewModel.login(username, password).observe(this, resource -> {
            if (resource.status == Resource.Status.LOADING) {
                getBinding().progressBar.setVisibility(View.VISIBLE);
                getBinding().btnLogin.setEnabled(false);
            } else if (resource.status == Resource.Status.SUCCESS) {
                getBinding().progressBar.setVisibility(View.GONE);
                getBinding().btnLogin.setEnabled(true);
                showToast("Welcome back, " + (resource.data != null ? resource.data.getFullName() : "User"));
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else if (resource.status == Resource.Status.ERROR) {
                getBinding().progressBar.setVisibility(View.GONE);
                getBinding().btnLogin.setEnabled(true);
                showToast(resource.message != null ? resource.message : "Authentication failed");
            }
        });
    }
}
