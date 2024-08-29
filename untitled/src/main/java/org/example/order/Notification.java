package org.example.order;


import java.io.Serializable;

public class Notification implements Serializable {
    private String topic;
    private String text;
    private NotificationType type;
    private int port;
    public Notification(String topic, String text , NotificationType type) {
        this.topic = topic;
        this.text = text;
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public String getText() {
        return text;
    }

    public NotificationType getType() {
        return type;
    }

    public enum NotificationType implements Serializable {
        NEW_MESSAGE , FRIEND_REQUEST , GAME_REQUEST , EXPULSION;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "topic='" + topic + '\'' +
                ", text='" + text + '\'' +
                ", type=" + type +
                '}';
    }
}
