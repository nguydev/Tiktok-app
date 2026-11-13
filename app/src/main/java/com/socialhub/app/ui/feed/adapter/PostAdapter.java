package com.socialhub.app.ui.feed.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.socialhub.app.R;
import com.socialhub.app.core.utils.DateTimeUtils;
import com.socialhub.app.core.utils.ImageLoader;
import com.socialhub.app.data.model.Post;
import com.socialhub.app.databinding.ItemPostBinding;

public class PostAdapter extends ListAdapter<Post, PostAdapter.PostViewHolder> {

    public interface OnPostInteractionListener {
        void onLikeClicked(Post post);
        void onCommentClicked(Post post);
        void onShareClicked(Post post);
        void onAuthorClicked(Post post);
    }

    private final OnPostInteractionListener listener;

    public PostAdapter(OnPostInteractionListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<Post> DIFF_CALLBACK = new DiffUtil.ItemCallback<Post>() {
        @Override
        public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final ItemPostBinding binding;

        public PostViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Post post) {
            if (post.getAuthor() != null) {
                binding.tvAuthorName.setText(post.getAuthor().getFullName());
                ImageLoader.loadAvatar(binding.ivAuthorAvatar, post.getAuthor().getAvatarUrl());
                binding.ivVerifiedBadge.setVisibility(post.getAuthor().isVerified() ? View.VISIBLE : View.GONE);
            }

            binding.tvContent.setText(post.getContent());
            binding.tvPostTime.setText(DateTimeUtils.getRelativeTimeSpan(post.getCreatedAt()));
            binding.tvLikesCount.setText(String.valueOf(post.getLikesCount()));
            binding.tvCommentsCount.setText(String.valueOf(post.getCommentsCount()));

            // Like status toggle icon
            if (post.isLiked()) {
                binding.btnLike.setImageResource(R.drawable.ic_heart_filled);
                binding.btnLike.setColorFilter(ContextCompat.getColor(binding.getRoot().getContext(), R.color.like_heart));
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_heart_outline);
                binding.btnLike.clearColorFilter();
            }

            // Attached Media
            if (post.getMediaUrl() != null && !post.getMediaUrl().trim().isEmpty()) {
                binding.ivPostMedia.setVisibility(View.VISIBLE);
                ImageLoader.load(binding.ivPostMedia, post.getMediaUrl());
            } else {
                binding.ivPostMedia.setVisibility(View.GONE);
            }

            // Click listeners
            binding.btnLike.setOnClickListener(v -> {
                if (listener != null) listener.onLikeClicked(post);
            });
            binding.btnComment.setOnClickListener(v -> {
                if (listener != null) listener.onCommentClicked(post);
            });
            binding.btnShare.setOnClickListener(v -> {
                if (listener != null) listener.onShareClicked(post);
            });
            binding.ivAuthorAvatar.setOnClickListener(v -> {
                if (listener != null) listener.onAuthorClicked(post);
            });
            binding.tvAuthorName.setOnClickListener(v -> {
                if (listener != null) listener.onAuthorClicked(post);
            });
        }
    }
}
