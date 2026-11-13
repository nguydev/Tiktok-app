package com.socialhub.app.ui.feed.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.socialhub.app.core.utils.ImageLoader;
import com.socialhub.app.data.model.Story;
import com.socialhub.app.databinding.ItemStoryBinding;

import java.util.ArrayList;
import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    public interface OnStoryClickListener {
        void onStoryClick(Story story);
    }

    private final List<Story> storyList = new ArrayList<>();
    private final OnStoryClickListener listener;

    public StoryAdapter(OnStoryClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Story> stories) {
        storyList.clear();
        if (stories != null) {
            storyList.addAll(stories);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStoryBinding binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        holder.bind(storyList.get(position));
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    class StoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemStoryBinding binding;

        public StoryViewHolder(ItemStoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Story story) {
            if (story.getUser() != null) {
                binding.tvStoryUsername.setText(story.getUser().getUsername());
                ImageLoader.loadAvatar(binding.ivStoryAvatar, story.getUser().getAvatarUrl());
            }

            // Dim gradient ring if already viewed
            binding.vStoryRing.setAlpha(story.isViewed() ? 0.3f : 1.0f);

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onStoryClick(story);
            });
        }
    }
}
