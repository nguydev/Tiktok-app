package com.socialhub.app.ui.feed;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.socialhub.app.core.base.BaseFragment;
import com.socialhub.app.core.network.Resource;
import com.socialhub.app.data.model.Post;
import com.socialhub.app.databinding.FragmentFeedBinding;
import com.socialhub.app.ui.details.PostDetailsActivity;
import com.socialhub.app.ui.feed.adapter.PostAdapter;
import com.socialhub.app.ui.feed.adapter.StoryAdapter;

public class FeedFragment extends BaseFragment<FragmentFeedBinding> implements PostAdapter.OnPostInteractionListener {

    private FeedViewModel viewModel;
    private PostAdapter postAdapter;
    private StoryAdapter storyAdapter;

    @Override
    protected FragmentFeedBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentFeedBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        viewModel = new ViewModelProvider(this).get(FeedViewModel.class);

        // Setup Story Adapter
        storyAdapter = new StoryAdapter(story -> {
            showToast("Viewing story by " + (story.getUser() != null ? story.getUser().getUsername() : ""));
        });
        getBinding().rvStories.setAdapter(storyAdapter);

        // Setup Post Adapter
        postAdapter = new PostAdapter(this);
        getBinding().rvPosts.setAdapter(postAdapter);

        // Pull to refresh
        getBinding().swipeRefreshLayout.setOnRefreshListener(() -> viewModel.refresh());
    }

    @Override
    protected void observeViewModel() {
        viewModel.getStories().observe(getViewLifecycleOwner(), stories -> {
            if (stories != null) {
                storyAdapter.submitList(stories);
            }
        });

        viewModel.getPosts().observe(getViewLifecycleOwner(), resource -> {
            if (resource.status == Resource.Status.LOADING) {
                if (!getBinding().swipeRefreshLayout.isRefreshing()) {
                    getBinding().loadingIndicator.setVisibility(View.VISIBLE);
                }
            } else if (resource.status == Resource.Status.SUCCESS) {
                getBinding().loadingIndicator.setVisibility(View.GONE);
                getBinding().swipeRefreshLayout.setRefreshing(false);
                if (resource.data != null) {
                    postAdapter.submitList(resource.data);
                }
            } else if (resource.status == Resource.Status.ERROR) {
                getBinding().loadingIndicator.setVisibility(View.GONE);
                getBinding().swipeRefreshLayout.setRefreshing(false);
                showToast(resource.message != null ? resource.message : "Failed to load feed");
            }
        });
    }

    @Override
    public void onLikeClicked(Post post) {
        viewModel.toggleLike(post.getId());
    }

    @Override
    public void onCommentClicked(Post post) {
        Intent intent = new Intent(requireContext(), PostDetailsActivity.class);
        intent.putExtra("EXTRA_POST", post);
        startActivity(intent);
    }

    @Override
    public void onShareClicked(Post post) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this post on SocialHub");
        shareIntent.putExtra(Intent.EXTRA_TEXT, post.getContent());
        startActivity(Intent.createChooser(shareIntent, "Share post via"));
    }

    @Override
    public void onAuthorClicked(Post post) {
        showToast("Profile of " + (post.getAuthor() != null ? post.getAuthor().getFullName() : ""));
    }
}
