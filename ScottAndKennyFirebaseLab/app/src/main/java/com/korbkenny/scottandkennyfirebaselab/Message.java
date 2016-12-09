package com.korbkenny.scottandkennyfirebaselab;

/**
 * Created by KorbBookProReturns on 12/9/16.
 */

public class Message {
    User mUser;
    String mText;

    public Message(User user, String text) {
        mUser = user;
        mText = text;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}
