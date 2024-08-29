package org.example.local_data;

import org.example.order.Message;
import java.io.Serializable;
import java.util.LinkedList;

public class ChatPV implements Serializable {
    private String username1;
    private String username2;
    private LinkedList<Message> messages = new LinkedList<>();
    private static LinkedList<ChatPV> allPV = new LinkedList<>();
    private long lastTimeMessage = 0;

    public ChatPV(String user1, String user2) {
        this.username1 = user1;
        this.username2 = user2;
        allPV.add(this);
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }

    public String getUsername1() {
        return username1;
    }

    public String getUsername2() {
        return username2;
    }
    public static ChatPV findPV(String senderUsername , String receiverUsername){
        for(int i = 0; i < allPV.size(); i++){
            if((allPV.get(i).getUsername1().equals(senderUsername) && allPV.get(i).getUsername2().equals(receiverUsername))||(allPV.get(i).getUsername1().equals(receiverUsername) && allPV.get(i).getUsername2().equals(senderUsername))){
                return allPV.get(i);
            }
        }

        return new ChatPV(senderUsername, receiverUsername);
    }
    public static LinkedList<ChatPV> userPV(String username){
        LinkedList<ChatPV> chatPVs = new LinkedList<>();
        for(int i = 0; i < allPV.size(); i++){
            if((allPV.get(i).getUsername1().equals(username)|| allPV.get(i).getUsername2().equals(username))){
                if(allPV.get(i).getMessages().size() != 0) {
                    chatPVs.add(allPV.get(i));
                }
            }
        }
        return chatPVs;
    }

    public static LinkedList<ChatPV> getAllPV() {
        return allPV;
    }

    public long getLastTimeMessage() {
        return lastTimeMessage;
    }

    public void setLastTimeMessage(long lastTimeMessage) {
        this.lastTimeMessage = lastTimeMessage;
    }

    @Override
    public String toString() {
        return "lastTimeMessage=" + lastTimeMessage +
                '}';
    }
}
