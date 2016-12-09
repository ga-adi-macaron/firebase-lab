package com.korbkenny.scottandkennyfirebaselab;

/**
 * Created by KorbBookProReturns on 12/9/16.
 */

public class User {
    String mColor, mUsername;

    public User(String color, String username) {
        mColor = color;
        mUsername = username;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }
}
