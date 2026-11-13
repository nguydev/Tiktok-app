package com.socialhub.app.ui.notifications.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.socialhub.app.core.utils.DateTimeUtils;
import com.socialhub.app.core.utils.ImageLoader;
import com.socialhub.app.data.model.NotificationItem;
import com.socialhub.app.databinding.ItemNotificationBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final List<NotificationItem> items = new ArrayList<>();

    public void submitList(List<NotificationItem> list) {
        items.clear();
        if (list != null) {
            items.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NotificationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private final ItemNotificationBinding binding;

        public NotificationViewHolder(ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NotificationItem item) {
            if (item.getActor() != null) {
                ImageLoader.loadAvatar(binding.ivActorAvatar, item.getActor().getAvatarUrl());
            }
            binding.tvNotificationMessage.setText(item.getMessage());
            binding.tvNotificationTime.setText(DateTimeUtils.getRelativeTimeSpan(item.getTimestamp()));
            binding.getRoot().setAlpha(item.isRead() ? 0.6f : 1.0f);
        }
    }
}
