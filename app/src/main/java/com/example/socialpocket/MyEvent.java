package com.example.socialpocket;

import android.location.Location;

public class MyEvent {
    private Post post;
    public MyEvent(Post post) {
        this.post = post;
    }
    public Post getPost() {
        return post;
    }

    @Override
    public String toString() {
        return "MyEvent{post=" + post.toString() +'}';
    }
}
