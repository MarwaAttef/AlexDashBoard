package com.example.wedad.design.welcomscreens.beanspkg;

import java.io.Serializable;

/**
 * Created by wedad on 5/1/2017.
 */

public class Tips implements Serializable {
    String text;
    String numberOfLikes;
    String url;
    User user;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(String numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
