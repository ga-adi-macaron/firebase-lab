package com.ezequielc.firebaselab;

/**
 * Created by student on 12/9/16.
 */

public class ChatMessage {
    String mUser, mMessage;

    public ChatMessage(String mUser, String mMessage) {
        this.mUser = mUser;
        this.mMessage = mMessage;
    }
    public ChatMessage(){
    }

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
