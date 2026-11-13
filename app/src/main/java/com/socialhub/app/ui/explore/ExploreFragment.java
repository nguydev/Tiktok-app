package com.socialhub.app.ui.explore;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.socialhub.app.core.base.BaseFragment;
import com.socialhub.app.databinding.FragmentExploreBinding;

public class ExploreFragment extends BaseFragment<FragmentExploreBinding> {

    @Override
    protected FragmentExploreBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentExploreBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        getBinding().tilSearch.setEndIconOnClickListener(v -> {
            String query = getBinding().etSearch.getText() != null ? getBinding().etSearch.getText().toString().trim() : "";
            if (!query.isEmpty()) {
                showToast("Searching for: " + query);
            }
        });
    }
}
