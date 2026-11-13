package com.socialhub.app.data.model;

import java.io.Serializable;

public class Story implements Serializable {

    private String id;
    private User user;
    private String mediaUrl;
    private boolean isViewed;

    public Story(String id, User user, String mediaUrl, boolean isViewed) {
        this.id = id;
        this.user = user;
        this.mediaUrl = mediaUrl;
        this.isViewed = isViewed;
    }

    public String getId() { return id; }
    public User getUser() { return user; }
    public String getMediaUrl() { return mediaUrl; }
    public boolean isViewed() { return isViewed; }
    public void setViewed(boolean viewed) { isViewed = viewed; }
}
