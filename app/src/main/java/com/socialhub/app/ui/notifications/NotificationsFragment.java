package com.socialhub.app.ui.notifications;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.socialhub.app.core.base.BaseFragment;
import com.socialhub.app.core.di.Injection;
import com.socialhub.app.data.repository.NotificationRepository;
import com.socialhub.app.databinding.FragmentNotificationsBinding;
import com.socialhub.app.ui.notifications.adapter.NotificationAdapter;

public class NotificationsFragment extends BaseFragment<FragmentNotificationsBinding> {

    private NotificationAdapter adapter;
    private NotificationRepository repository;

    @Override
    protected FragmentNotificationsBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentNotificationsBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        repository = Injection.provideNotificationRepository();
        adapter = new NotificationAdapter();
        getBinding().rvNotifications.setAdapter(adapter);

        repository.getNotifications().observe(getViewLifecycleOwner(), items -> {
            if (items != null && !items.isEmpty()) {
                adapter.submitList(items);
                getBinding().tvEmptyState.setVisibility(android.view.View.GONE);
            } else {
                getBinding().tvEmptyState.setVisibility(android.view.View.VISIBLE);
            }
        });
    }
}
