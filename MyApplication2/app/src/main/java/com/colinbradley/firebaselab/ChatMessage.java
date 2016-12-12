package com.colinbradley.firebaselab;

/**
 * Created by colinbradley on 12/9/16.
 */

public class ChatMessage {
    String name;
    String body;

    public ChatMessage(){}

    public ChatMessage(String name, String body) {
        this.name = name;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }
}
