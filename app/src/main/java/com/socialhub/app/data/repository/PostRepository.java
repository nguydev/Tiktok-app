package com.socialhub.app.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.socialhub.app.core.network.Resource;
import com.socialhub.app.data.model.Comment;
import com.socialhub.app.data.model.Post;
import com.socialhub.app.data.model.Story;
import com.socialhub.app.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PostRepository {

    private static volatile PostRepository instance;
    private final List<Post> feedPosts = new ArrayList<>();
    private final List<Story> stories = new ArrayList<>();
    private final Map<String, List<Comment>> postCommentsMap = new HashMap<>();

    private final MutableLiveData<Resource<List<Post>>> postsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Story>> storiesLiveData = new MutableLiveData<>();

    private PostRepository() {
        initMockData();
    }

    public static PostRepository getInstance() {
        if (instance == null) {
            synchronized (PostRepository.class) {
                if (instance == null) {
                    instance = new PostRepository();
                }
            }
        }
        return instance;
    }

    private void initMockData() {
        User u1 = new User("u1", "sarah_tech", "Sarah Jenkins", "Android Tech Lead @Google", "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400", true);
        User u2 = new User("u2", "david_wander", "David Chen", "Travel Photographer & Explorer", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400", true);
        User u3 = new User("u3", "elena_designs", "Elena Rostova", "UI/UX & Design Systems", "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=400", false);
        User u4 = new User("u4", "kevin_code", "Kevin Patel", "Open source enthusiast", "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=400", false);

        // Stories
        stories.add(new Story("s1", u1, "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400", false));
        stories.add(new Story("s2", u2, "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400", false));
        stories.add(new Story("s3", u3, "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=400", true));
        stories.add(new Story("s4", u4, "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=400", true));
        storiesLiveData.setValue(new ArrayList<>(stories));

        // Posts
        long now = System.currentTimeMillis();
        Post p1 = new Post("p1", u1, "Excited to share our newest design system for modern mobile applications! Seamless animations, Material 3 theming, and 60 FPS performance. 🚀✨ #android #design #uiux", "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=800", 342, 28, now - 1000 * 60 * 45);
        Post p2 = new Post("p2", u2, "Sunset over the mountains in Banff National Park. Nature never fails to inspire. What's your favorite travel destination? 🏔️🌅 #travel #photography", "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800", 819, 64, now - 1000 * 60 * 180);
        Post p3 = new Post("p3", u3, "Clean code is not written by following a set of rules. Clean code is written by caring deeply about simplicity and readability.", null, 156, 12, now - 1000 * 60 * 360);

        feedPosts.add(p1);
        feedPosts.add(p2);
        feedPosts.add(p3);
        postsLiveData.setValue(Resource.success(new ArrayList<>(feedPosts)));

        // Comments for p1
        List<Comment> cList = new ArrayList<>();
        cList.add(new Comment("c1", "p1", u2, "Looks incredible! Love the typography and clean hierarchy.", now - 1000 * 60 * 25));
        cList.add(new Comment("c2", "p1", u3, "Great job Sarah! Looking forward to testing it.", now - 1000 * 60 * 10));
        postCommentsMap.put("p1", cList);
    }

    public LiveData<Resource<List<Post>>> getFeedPosts() {
        return postsLiveData;
    }

    public LiveData<List<Story>> getStories() {
        return storiesLiveData;
    }

    public void refreshFeed() {
        postsLiveData.setValue(Resource.loading(feedPosts));
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            postsLiveData.setValue(Resource.success(new ArrayList<>(feedPosts)));
        }, 500);
    }

    public void toggleLike(String postId) {
        for (Post p : feedPosts) {
            if (p.getId().equals(postId)) {
                if (p.isLiked()) {
                    p.setLiked(false);
                    p.setLikesCount(Math.max(0, p.getLikesCount() - 1));
                } else {
                    p.setLiked(true);
                    p.setLikesCount(p.getLikesCount() + 1);
                }
                break;
            }
        }
        postsLiveData.setValue(Resource.success(new ArrayList<>(feedPosts)));
    }

    public void createPost(String content, String mediaUrl, User author) {
        Post newPost = new Post(
                "p_" + UUID.randomUUID().toString().substring(0, 8),
                author,
                content,
                mediaUrl,
                0,
                0,
                System.currentTimeMillis()
        );
        feedPosts.add(0, newPost); // Add at the top of feed
        postsLiveData.setValue(Resource.success(new ArrayList<>(feedPosts)));
    }

    public List<Comment> getComments(String postId) {
        List<Comment> comments = postCommentsMap.get(postId);
        return comments != null ? new ArrayList<>(comments) : new ArrayList<>();
    }

    public void addComment(String postId, User author, String content) {
        List<Comment> list = postCommentsMap.computeIfAbsent(postId, k -> new ArrayList<>());
        list.add(new Comment(
                "c_" + UUID.randomUUID().toString().substring(0, 8),
                postId,
                author,
                content,
                System.currentTimeMillis()
        ));

        // Increment count on post
        for (Post p : feedPosts) {
            if (p.getId().equals(postId)) {
                p.setCommentsCount(p.getCommentsCount() + 1);
                break;
            }
        }
        postsLiveData.setValue(Resource.success(new ArrayList<>(feedPosts)));
    }
}
