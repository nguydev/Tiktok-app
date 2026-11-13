package com.socialhub.app.ui.auth;

import android.content.Intent;
import android.view.LayoutInflater;
import androidx.lifecycle.ViewModelProvider;
import com.socialhub.app.core.base.BaseActivity;
import com.socialhub.app.core.network.Resource;
import com.socialhub.app.databinding.ActivityRegisterBinding;
import com.socialhub.app.ui.main.MainActivity;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    private AuthViewModel viewModel;

    @Override
    protected ActivityRegisterBinding inflateBinding(LayoutInflater inflater) {
        return ActivityRegisterBinding.inflate(inflater);
    }

    @Override
    protected void initViews() {
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        getBinding().btnRegister.setOnClickListener(v -> performRegister());
        getBinding().tvGoToLogin.setOnClickListener(v -> finish());
    }

    private void performRegister() {
        String fullName = getBinding().etFullName.getText() != null ? getBinding().etFullName.getText().toString().trim() : "";
        String username = getBinding().etUsername.getText() != null ? getBinding().etUsername.getText().toString().trim() : "";
        String email = getBinding().etEmail.getText() != null ? getBinding().etEmail.getText().toString().trim() : "";
        String password = getBinding().etPassword.getText() != null ? getBinding().etPassword.getText().toString().trim() : "";

        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.length() < 6) {
            showToast("Please fill in all fields with valid information (password min 6 chars)");
            return;
        }

        viewModel.register(fullName, username, email, password).observe(this, resource -> {
            if (resource.status == Resource.Status.SUCCESS) {
                showToast("Account created successfully!");
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else if (resource.status == Resource.Status.ERROR) {
                showToast(resource.message != null ? resource.message : "Registration failed");
            }
        });
    }
}
