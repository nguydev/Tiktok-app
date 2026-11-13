package com.socialhub.app.ui.create;

import android.view.LayoutInflater;
import android.view.View;
import com.socialhub.app.SocialApp;
import com.socialhub.app.core.base.BaseActivity;
import com.socialhub.app.core.di.Injection;
import com.socialhub.app.core.utils.ImageLoader;
import com.socialhub.app.core.utils.PreferenceManager;
import com.socialhub.app.data.model.User;
import com.socialhub.app.databinding.ActivityCreatePostBinding;

public class CreatePostActivity extends BaseActivity<ActivityCreatePostBinding> {

    private String attachedImageUrl = null;

    @Override
    protected ActivityCreatePostBinding inflateBinding(LayoutInflater inflater) {
        return ActivityCreatePostBinding.inflate(inflater);
    }

    @Override
    protected void initViews() {
        PreferenceManager prefs = SocialApp.getInstance().getPreferenceManager();
        getBinding().tvAuthorName.setText(prefs.getFullName());

        getBinding().toolbar.setNavigationOnClickListener(v -> finish());

        // Sample image attach demo
        getBinding().btnAddMedia.setOnClickListener(v -> {
            attachedImageUrl = "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=800";
            getBinding().ivAttachedPreview.setVisibility(View.VISIBLE);
            ImageLoader.load(getBinding().ivAttachedPreview, attachedImageUrl);
            showToast("Photo attached");
        });

        getBinding().btnPublishPost.setOnClickListener(v -> {
            String content = getBinding().etPostContent.getText() != null ? getBinding().etPostContent.getText().toString().trim() : "";
            if (content.isEmpty() && attachedImageUrl == null) {
                showToast("Please enter text or attach an image");
                return;
            }

            User author = new User(
                    prefs.getUserId(),
                    prefs.getUsername(),
                    prefs.getFullName(),
                    "",
                    "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400",
                    true
            );

            Injection.providePostRepository().createPost(content, attachedImageUrl, author);
            showToast("Post published!");
            finish();
        });
    }
}
