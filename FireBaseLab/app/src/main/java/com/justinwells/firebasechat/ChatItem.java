package com.justinwells.firebasechat;

/**
 * Created by justinwells on 12/9/16.
 */

public class ChatItem {
    String userName, text;

    public ChatItem() {
    }

    public ChatItem(String userName, String text) {
        this.userName = userName;
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public String getText() {
        return text;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setText(String text) {
        this.text = text;
    }
}
