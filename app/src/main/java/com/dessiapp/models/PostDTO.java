package com.dessiapp.models;

public class PostDTO {
    String id;
    String post_title;

    public PostDTO(String id, String post_title) {
        this.id = id;
        this.post_title = post_title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    @Override
    public String toString() {
        return post_title;
    }
}
