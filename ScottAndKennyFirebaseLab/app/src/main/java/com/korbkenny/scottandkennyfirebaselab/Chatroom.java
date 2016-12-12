package com.korbkenny.scottandkennyfirebaselab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Lindley on 12/9/2016.
 */

public class Chatroom {
    private List<Message> mMessages;
    private String mName;

    public Chatroom() {
        mMessages = new ArrayList<>();
    }

    public List<Message> getMessages() {
        return mMessages;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
