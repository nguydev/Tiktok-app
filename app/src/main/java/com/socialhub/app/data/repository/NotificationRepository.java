package com.socialhub.app.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.socialhub.app.data.model.NotificationItem;
import com.socialhub.app.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class NotificationRepository {

    private static volatile NotificationRepository instance;
    private final MutableLiveData<List<NotificationItem>> notificationsLiveData = new MutableLiveData<>();
    private final List<NotificationItem> list = new ArrayList<>();

    private NotificationRepository() {
        initMockNotifications();
    }

    public static NotificationRepository getInstance() {
        if (instance == null) {
            synchronized (NotificationRepository.class) {
                if (instance == null) {
                    instance = new NotificationRepository();
                }
            }
        }
        return instance;
    }

    private void initMockNotifications() {
        long now = System.currentTimeMillis();
        User u1 = new User("u1", "sarah_tech", "Sarah Jenkins", "", "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400", true);
        User u2 = new User("u2", "david_wander", "David Chen", "", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400", true);

        list.add(new NotificationItem("n1", u1, "Sarah Jenkins liked your post", "LIKE", now - 1000 * 60 * 15, false));
        list.add(new NotificationItem("n2", u2, "David Chen commented: 'Awesome work!'", "COMMENT", now - 1000 * 60 * 45, false));
        list.add(new NotificationItem("n3", u1, "Sarah Jenkins started following you", "FOLLOW", now - 1000 * 60 * 180, true));

        notificationsLiveData.setValue(new ArrayList<>(list));
    }

    public LiveData<List<NotificationItem>> getNotifications() {
        return notificationsLiveData;
    }
}
