package com.joelimyx.firebaselab;

/**
 * Created by Joe on 12/9/16.
 */

public class MessageInfo {
    private String mUsername;
    private String mMessage;

    public MessageInfo() {
    }

    public MessageInfo(String username, String message) {
        mUsername = username;
        mMessage = message;
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
