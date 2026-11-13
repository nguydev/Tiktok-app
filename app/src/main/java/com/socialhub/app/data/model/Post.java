package com.socialhub.app.data.model;

import java.io.Serializable;
import java.util.Objects;

public class Post implements Serializable {

    private String id;
    private User author;
    private String content;
    private String mediaUrl;
    private long likesCount;
    private long commentsCount;
    private long sharesCount;
    private boolean isLiked;
    private boolean isBookmarked;
    private long createdAt;

    public Post() {
    }

    public Post(String id, User author, String content, String mediaUrl, long likesCount, long commentsCount, long createdAt) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getMediaUrl() { return mediaUrl; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }

    public long getLikesCount() { return likesCount; }
    public void setLikesCount(long likesCount) { this.likesCount = likesCount; }

    public long getCommentsCount() { return commentsCount; }
    public void setCommentsCount(long commentsCount) { this.commentsCount = commentsCount; }

    public long getSharesCount() { return sharesCount; }
    public void setSharesCount(long sharesCount) { this.sharesCount = sharesCount; }

    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }

    public boolean isBookmarked() { return isBookmarked; }
    public void setBookmarked(boolean bookmarked) { isBookmarked = bookmarked; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return likesCount == post.likesCount &&
                commentsCount == post.commentsCount &&
                isLiked == post.isLiked &&
                isBookmarked == post.isBookmarked &&
                Objects.equals(id, post.id) &&
                Objects.equals(content, post.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, likesCount, isLiked);
    }
}
