package org.example.order;

import java.io.Serializable;

public class Message implements Serializable {
    private String senderUsername;
    private String receiverUsername;
    private String text;
    private long time;

    public Message(String sender , String receiver , String text) {
        this.senderUsername = sender;
        this.receiverUsername = receiver;
        this.text = text;
        this.time = System.nanoTime()/1000000000L;
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

}
