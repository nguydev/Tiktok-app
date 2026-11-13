package com.socialhub.app.core.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

/**
 * BaseActivity providing ViewBinding inflation and common UI helper utilities.
 * @param <VB> ViewBinding type
 */
public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    private VB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflateBinding(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        observeViewModel();
    }

    /**
     * Inflate layout using ViewBinding
     */
    protected abstract VB inflateBinding(LayoutInflater inflater);

    /**
     * Setup listeners, adapters, and UI widgets
     */
    protected abstract void initViews();

    /**
     * Observe LiveData from ViewModels
     */
    protected void observeViewModel() {
        // Optional override
    }

    public VB getBinding() {
        return binding;
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
