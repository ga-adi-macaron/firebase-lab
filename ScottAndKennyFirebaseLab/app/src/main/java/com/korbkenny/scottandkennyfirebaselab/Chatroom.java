package com.korbkenny.scottandkennyfirebaselab;

import java.util.List;

/**
 * Created by Scott Lindley on 12/9/2016.
 */

public class Chatroom {
    private List<Message> mMessages;
    private String mName;

    public Chatroom(String name, List<Message> messages) {
        mMessages = messages;
        mName = name;
    }

    public List<Message> getMessages() {
        return mMessages;
    }

    public String getName() {
        return mName;
    }
}
