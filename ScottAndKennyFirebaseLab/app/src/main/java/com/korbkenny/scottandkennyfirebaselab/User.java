package com.korbkenny.scottandkennyfirebaselab;

/**
 * Created by KorbBookProReturns on 12/9/16.
 */

public class User {
    int mColor;
    String mUsername;

    public User(){

    }
    
    public User(int color, String username) {
        mColor = color;
        mUsername = username;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }
}
