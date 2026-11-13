package com.socialhub.app.ui.details.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.socialhub.app.core.utils.DateTimeUtils;
import com.socialhub.app.core.utils.ImageLoader;
import com.socialhub.app.data.model.Comment;
import com.socialhub.app.databinding.ItemCommentBinding;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private final List<Comment> comments = new ArrayList<>();

    public void setComments(List<Comment> list) {
        comments.clear();
        if (list != null) {
            comments.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.bind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        private final ItemCommentBinding binding;

        public CommentViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Comment comment) {
            if (comment.getAuthor() != null) {
                binding.tvCommentAuthor.setText(comment.getAuthor().getUsername());
                ImageLoader.loadAvatar(binding.ivCommentAvatar, comment.getAuthor().getAvatarUrl());
            }
            binding.tvCommentText.setText(comment.getContent());
            binding.tvCommentTime.setText(DateTimeUtils.getRelativeTimeSpan(comment.getCreatedAt()));
        }
    }
}
