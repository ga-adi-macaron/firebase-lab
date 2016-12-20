package com.jonathanlieblich.firebaselabapplication;

/**
 * Created by jonlieblich on 12/9/16.
 */

public class ChatPost {
    private String mUsername;
    private String mMessage;

    public ChatPost() {

    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
