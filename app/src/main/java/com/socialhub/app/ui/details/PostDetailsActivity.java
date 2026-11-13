package com.socialhub.app.ui.details;

import android.view.LayoutInflater;
import androidx.lifecycle.ViewModelProvider;
import com.socialhub.app.SocialApp;
import com.socialhub.app.core.base.BaseActivity;
import com.socialhub.app.core.utils.PreferenceManager;
import com.socialhub.app.data.model.Post;
import com.socialhub.app.data.model.User;
import com.socialhub.app.databinding.ActivityPostDetailsBinding;
import com.socialhub.app.ui.details.adapter.CommentAdapter;

public class PostDetailsActivity extends BaseActivity<ActivityPostDetailsBinding> {

    private PostDetailsViewModel viewModel;
    private CommentAdapter adapter;
    private Post post;

    @Override
    protected ActivityPostDetailsBinding inflateBinding(LayoutInflater inflater) {
        return ActivityPostDetailsBinding.inflate(inflater);
    }

    @Override
    protected void initViews() {
        viewModel = new ViewModelProvider(this).get(PostDetailsViewModel.class);
        post = (Post) getIntent().getSerializableExtra("EXTRA_POST");

        getBinding().toolbar.setNavigationOnClickListener(v -> finish());

        adapter = new CommentAdapter();
        getBinding().rvComments.setAdapter(adapter);

        if (post != null) {
            loadComments();
        }

        getBinding().btnSendComment.setOnClickListener(v -> {
            String text = getBinding().etComment.getText() != null ? getBinding().etComment.getText().toString().trim() : "";
            if (text.isEmpty() || post == null) return;

            PreferenceManager prefs = SocialApp.getInstance().getPreferenceManager();
            User currentUser = new User(
                    prefs.getUserId(),
                    prefs.getUsername(),
                    prefs.getFullName(),
                    "",
                    "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400",
                    true
            );

            viewModel.addComment(post.getId(), currentUser, text);
            getBinding().etComment.setText("");
            loadComments();
            showToast("Comment added");
        });
    }

    private void loadComments() {
        adapter.setComments(viewModel.getComments(post.getId()));
    }
}
