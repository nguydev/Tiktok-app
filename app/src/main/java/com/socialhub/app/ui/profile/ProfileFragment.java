package com.socialhub.app.ui.profile;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.socialhub.app.SocialApp;
import com.socialhub.app.core.base.BaseFragment;
import com.socialhub.app.core.utils.ImageLoader;
import com.socialhub.app.core.utils.PreferenceManager;
import com.socialhub.app.databinding.FragmentProfileBinding;
import com.socialhub.app.ui.auth.LoginActivity;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {

    @Override
    protected FragmentProfileBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentProfileBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        PreferenceManager prefs = SocialApp.getInstance().getPreferenceManager();

        getBinding().tvFullName.setText(prefs.getFullName());
        getBinding().tvUsername.setText("@" + prefs.getUsername());
        getBinding().tvBio.setText("Passionate Android developer & creator. Building high-impact mobile apps.");

        getBinding().tvPostsCount.setText("12");
        getBinding().tvFollowersCount.setText("1.2K");
        getBinding().tvFollowingCount.setText("384");

        ImageLoader.loadAvatar(getBinding().ivAvatar, "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400");

        getBinding().btnProfileEdit.setOnClickListener(v -> {
            showToast("Edit profile dialog opened");
        });

        // Long click to logout
        getBinding().btnProfileEdit.setOnLongClickListener(v -> {
            prefs.clearSession();
            showToast("Logged out");
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
            return true;
        });
    }
}
