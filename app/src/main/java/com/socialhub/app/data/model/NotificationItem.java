package com.socialhub.app.data.model;

import java.io.Serializable;

public class NotificationItem implements Serializable {

    private String id;
    private User actor;
    private String message;
    private String type; // LIKE, COMMENT, FOLLOW
    private long timestamp;
    private boolean isRead;

    public NotificationItem(String id, User actor, String message, String type, long timestamp, boolean isRead) {
        this.id = id;
        this.actor = actor;
        this.message = message;
        this.type = type;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    public String getId() { return id; }
    public User getActor() { return actor; }
    public String getMessage() { return message; }
    public String getType() { return type; }
    public long getTimestamp() { return timestamp; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
}
