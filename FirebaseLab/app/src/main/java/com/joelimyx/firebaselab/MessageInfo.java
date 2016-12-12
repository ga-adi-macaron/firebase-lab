package com.joelimyx.firebaselab;

/**
 * Created by Joe on 12/9/16.
 */

public class MessageInfo {
    private String username;
    private String message;

    public MessageInfo() {
    }

    public MessageInfo(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
