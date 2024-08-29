package org.example.server.controller;

import org.example.marathon_mario.controller.HandlerMarathon;
import org.example.marathon_mario.model.PlayerMarathon;
import org.example.send_object.entities.PaintEntity;
import org.example.order.Information;
import org.example.order.Order;
import org.example.server.model.Item;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
public class MarathonServer extends Thread {
    private int port;
    private boolean end = false;
    private HandlerMarathon handler;
    private DatagramSocket datagramSocket;
    public MarathonServer(int port, HandlerMarathon handler) {
        this.port = port;
        this.handler = handler;
        init();
        start();
    }
    public void init(){
        try {
            datagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run() {
        try {
            while (!end) {
                byte[] receiveDate = new byte[256];
                DatagramPacket receivePacket = new DatagramPacket(receiveDate, receiveDate.length);
                datagramSocket.receive(receivePacket);
                String string = new String((receiveDate));
                if(string.contains("UPDATE")) {
                    int startIndex = getSubString(string, "UPDATE");
                    String name = string.substring(0, startIndex);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    handler.update();
                    if (handler.isEnd()){
                        objectOutputStream.writeObject(new Order(Order.OrderType.END_MARATHON_MARIO));
                        end = true;
                    }else {
                        if (handler.isSendItems() && name.equals(handler.getSenderName())) {
                            objectOutputStream.writeObject(new Information<Item>(handler.searchItem(name), Information.InformationType.HIT_ITEM));
                            handler.setSenderName(null);
                            handler.setSendItems(false);
                        } else {
                            objectOutputStream.writeObject(new Information<PaintEntity>(handler.getPaintEntities(), Information.InformationType.GAME_MARIO));
                        }
                    }
                    byte[] data = byteArrayOutputStream.toByteArray();
                    DatagramPacket datagramPacket = new DatagramPacket(data, data.length, receivePacket.getAddress(), receivePacket.getPort());
                    datagramSocket.send(datagramPacket);
                }
                handelKeys(string);
            }
            datagramSocket.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(handler.isEnd()){
            endGame();
        }
    }
    public void handelKeys(String string){
        if (string.contains("RIGHT")) {
            int startIndex = getSubString(string, "RIGHT");
            String name = string.substring(0, startIndex);
            handler.RightKey(name);
        } else if (string.contains("LEFT")) {
            int startIndex = getSubString(string, "LEFT");
            String name = string.substring(0, startIndex);
            handler.LeftKey(name);
        } else if (string.contains("UP")) {
            int startIndex = getSubString(string, "UP");
            String name = string.substring(0, startIndex);
            handler.UpKey(name);
        } else if (string.contains("DOWN")) {
        } useItem(string);
    }
    public void useItem(String string){
        if (string.contains("1key")) {
            int startIndex = getSubString(string, "1key");
            String name = string.substring(0, startIndex);
            handler.itemKey(name , 1);
        }  else if (string.contains("2key")) {
            int startIndex = getSubString(string, "2key");
            String name = string.substring(0, startIndex);
            handler.itemKey(name , 2);
        }  else if (string.contains("3key")) {
            int startIndex = getSubString(string, "3key");
            String name = string.substring(0, startIndex);
            handler.itemKey(name , 3);
        }  else if (string.contains("4key")) {
            int startIndex = getSubString(string, "4key");
            String name = string.substring(0, startIndex);
            handler.itemKey(name , 4);
        }  else if (string.contains("5key")) {
            int startIndex = getSubString(string, "5key");
            String name = string.substring(0, startIndex);
            handler.itemKey(name , 5);
        }  else if (string.contains("6key")) {
            int startIndex = getSubString(string, "6key");
            String name = string.substring(0, startIndex);
            handler.itemKey(name , 6);
        }  else if (string.contains("7key")) {
            int startIndex = getSubString(string, "7key");
            String name = string.substring(0, startIndex);
            handler.itemKey(name , 7);
        }  else if (string.contains("8key")) {
            int startIndex = getSubString(string, "8key");
            String name = string.substring(0, startIndex);
            handler.itemKey(name , 8);
        }  else if (string.contains("9key")) {
            int startIndex = getSubString(string, "9key");
            String name = string.substring(0, startIndex);
            handler.itemKey(name , 9);
        }
    }
    public int calculateScore(PlayerMarathon playerMarathon){
        int maxScore = (int) ((playerMarathon.getMaxLifeTime() - handler.getMarathonMinLifeTime())
                * handler.getMarathonLifeTimeMultiplier()
                + (playerMarathon.getMaxDistance() - handler.getMarathonMinDistance())
                * handler.getMarathonDistanceMultiplier());
        return maxScore;
    }

    public void sortPlayers(){
        for(int i = 0; i < handler.getPlayerMarathons().size(); i++){
            for (int j = i+1; j < handler.getPlayerMarathons().size(); j++){
                if(calculateScore(handler.getPlayerMarathons().get(i)) < calculateScore(handler.getPlayerMarathons().get(j))){
                    PlayerMarathon playerMarathon = handler.getPlayerMarathons().get(i);
                    handler.getPlayerMarathons().set(i , handler.getPlayerMarathons().get(j));
                    handler.getPlayerMarathons().set(j , playerMarathon);
                }
            }
        }
    }
    public void endGame(){
        sortPlayers();
        for(int i = 0; i < handler.getPlayerMarathons().size(); i++) {
            ServerSideClient serverSideClient = getClient(handler.getPlayerMarathons().get(i).getUsername());
            serverSideClient.getUser().setOnlineScore(serverSideClient.getUser().getOnlineScore() + calculateScore(handler.getPlayerMarathons().get(i)));
            int maxDiamond = (int) (handler.getPlayerMarathons().size()/Math.pow(2 , i+1));
            serverSideClient.getUser().setDiamond(serverSideClient.getUser().getDiamond() + maxDiamond);
            serverSideClient.send(new Order(Order.OrderType.END_MARATHON_MARIO));
        }
        /***
            send online scores and diamonds to all clients for score table
        ***/
    }
    public int getSubString(String orgString , String subString){
        int startIndex = 0;
        boolean contain = true;
        for(int i = 0 ; i <= orgString.length()-subString.length(); i++){
            for(int j = 0; j < subString.length(); j++){
                if(!(orgString.charAt(i+j) == subString.charAt(j))){
                    contain = false;
                    break;
                }
            }
            if(contain){
                startIndex = i;
            }
            contain = true;
        }
        return startIndex;
    }
    public ServerSideClient getClient(String string){
        for(int j = 0; j < Server.getClients().size(); j++){
            if (Server.getClients().get(j).getUser() != null) {
                if (Server.getClients().get(j).getUser().getUsername().equals(string)) {
                    return Server.getClients().get(j);
                }
            }
        }
        return null;
    }
}

