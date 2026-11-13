package com.socialhub.app.ui.details;

import androidx.lifecycle.ViewModel;
import com.socialhub.app.core.di.Injection;
import com.socialhub.app.data.model.Comment;
import com.socialhub.app.data.model.User;
import com.socialhub.app.data.repository.PostRepository;

import java.util.List;

public class PostDetailsViewModel extends ViewModel {

    private final PostRepository postRepository;

    public PostDetailsViewModel() {
        this.postRepository = Injection.providePostRepository();
    }

    public List<Comment> getComments(String postId) {
        return postRepository.getComments(postId);
    }

    public void addComment(String postId, User author, String content) {
        postRepository.addComment(postId, author, content);
    }
}
