package com.socialhub.app.ui.feed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.socialhub.app.core.di.Injection;
import com.socialhub.app.core.network.Resource;
import com.socialhub.app.data.model.Post;
import com.socialhub.app.data.model.Story;
import com.socialhub.app.data.repository.PostRepository;

import java.util.List;

public class FeedViewModel extends ViewModel {

    private final PostRepository postRepository;

    public FeedViewModel() {
        this.postRepository = Injection.providePostRepository();
    }

    public LiveData<Resource<List<Post>>> getPosts() {
        return postRepository.getFeedPosts();
    }

    public LiveData<List<Story>> getStories() {
        return postRepository.getStories();
    }

    public void refresh() {
        postRepository.refreshFeed();
    }

    public void toggleLike(String postId) {
        postRepository.toggleLike(postId);
    }
}
