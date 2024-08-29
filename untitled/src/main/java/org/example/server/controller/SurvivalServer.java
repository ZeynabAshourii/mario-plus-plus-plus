package org.example.server.controller;

import org.example.order.Information;
import org.example.order.Order;
import org.example.send_object.entities.PaintEntity;
import org.example.send_object.entities.PaintReaction;
import org.example.server.model.Item;
import org.example.mario_survival.controller.HandlerSurvival;
import org.example.mario_survival.model.PlayerSurvival;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class SurvivalServer extends Thread{
    private int port;
    private int send = 0;
    private boolean end = false;
    private HandlerSurvival handler;
    private DatagramSocket datagramSocket;
    public SurvivalServer(int port, HandlerSurvival handler) {
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
                    if (send%(handler.getPlayerSoloSurvivals().size()/2) == 0) {
                        handler.update();
                    }
                    send++;
                    if (handler.isEnd()){
                        objectOutputStream.writeObject(new Order(Order.OrderType.END_SOLO_MARIO_SURVIVAL));
                        end = true;
                    }else {
                        if (handler.isSendData() && name.equals(handler.getSenderName())) {
                            if (handler.getDateType().equals("items")) {
                                objectOutputStream.writeObject(new Information<Item>(handler.searchItem(name), Information.InformationType.HIT_ITEM));
                            } else if (handler.getDateType().equals("reactions")) {
                                objectOutputStream.writeObject(new Information<PaintReaction>(handler.getReactions(), Information.InformationType.REACTIONS));
                            }
                            handler.setDateType(null);
                            handler.setSenderName(null);
                            handler.setSendData(false);
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
        if (string.contains("RIGHT_PRESSED")) {
            int startIndex = getSubString(string, "RIGHT_PRESSED");
            String name = string.substring(0, startIndex);
            handler.RightKey(name);
        } else if (string.contains("LEFT_PRESSED")) {
            int startIndex = getSubString(string, "LEFT_PRESSED");
            String name = string.substring(0, startIndex);
            handler.LeftKey(name);
        } else if (string.contains("UP_PRESSED")) {
            int startIndex = getSubString(string, "UP_PRESSED");
            String name = string.substring(0, startIndex);
            handler.UpKey(name);
        } else if (string.contains("DOWN_PRESSED")) {
            int startIndex = getSubString(string, "UP_PRESSED");
            String name = string.substring(0, startIndex);
            handler.DownKey(name);
        } else if (string.contains("RIGHT_RELEASED")) {
            int startIndex = getSubString(string, "RIGHT_RELEASED");
            String name = string.substring(0, startIndex);
            handler.releasedRightKey(name);
        } else if (string.contains("LEFT_RELEASED")) {
            int startIndex = getSubString(string, "LEFT_RELEASED");
            String name = string.substring(0, startIndex);
            handler.releasedLeftKey(name);
        } else if (string.contains("UP_RELEASED")) {
            int startIndex = getSubString(string, "UP_RELEASED");
            String name = string.substring(0, startIndex);
            handler.releasedUpKey(name);
        } else if (string.contains("DOWN_RELEASED")) {
            int startIndex = getSubString(string, "DOWN_RELEASED");
            String name = string.substring(0, startIndex);
            handler.releasedDownKey(name);
        }
        useItem(string);
        reaction(string);
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
    public void reaction(String string){
        if (string.contains("qKey")) {
            int startIndex = getSubString(string, "qKey");
            String name = string.substring(0, startIndex);
            handler.reactionKey(name , 0);
        } else if (string.contains("wKey")) {
            int startIndex = getSubString(string, "wKey");
            String name = string.substring(0, startIndex);
            handler.reactionKey(name , 1);
        } else if (string.contains("eKey")) {
            int startIndex = getSubString(string, "eKey");
            String name = string.substring(0, startIndex);
            handler.reactionKey(name , 2);
        } else if (string.contains("rKey")) {
            int startIndex = getSubString(string, "rKey");
            String name = string.substring(0, startIndex);
            handler.reactionKey(name , 3);
        } else if (string.contains("tKey")) {
            int startIndex = getSubString(string, "tKey");
            String name = string.substring(0, startIndex);
            handler.reactionKey(name , 4);
        }
    }
    public int calculateScore(PlayerSurvival player){
        int maxScore = (int) ((player.damageDealt() - player.getDamageReceived() )*handler.getSurvivalDamageMultiplier() - player.getCountItems()*handler.getSurvivalEquipmentMultiplier() );

        return maxScore;
    }

    public void sortPlayers(){
        for(int i = 0; i < handler.getPlayerSoloSurvivals().size(); i++){
            for (int j = i+1; j < handler.getPlayerSoloSurvivals().size(); j++){
                if(calculateScore(handler.getPlayerSoloSurvivals().get(i)) < calculateScore(handler.getPlayerSoloSurvivals().get(j))){
                    PlayerSurvival player = handler.getPlayerSoloSurvivals().get(i);
                    handler.getPlayerSoloSurvivals().set(i , handler.getPlayerSoloSurvivals().get(j));
                    handler.getPlayerSoloSurvivals().set(j , player);
                }
            }
        }
    }
    public void endGame(){
        sortPlayers();
        for(int i = 0; i < handler.getPlayerSoloSurvivals().size(); i++) {
            ServerSideClient serverSideClient = getClient(handler.getPlayerSoloSurvivals().get(i).getUsername());
            serverSideClient.getUser().setOnlineScore(serverSideClient.getUser().getOnlineScore() + calculateScore(handler.getPlayerSoloSurvivals().get(i)));
            int maxDiamond;
            if (i < handler.getPlayerSoloSurvivals().size()/4){
                maxDiamond = 2;
            } else if (i < handler.getPlayerSoloSurvivals().size()/2) {
                maxDiamond = 1;
            } else if (i < handler.getPlayerSoloSurvivals().size()*3/4) {
                maxDiamond = 0;
            } else {
                maxDiamond = -1;
            }
            serverSideClient.getUser().setDiamond(serverSideClient.getUser().getDiamond() + maxDiamond);
            serverSideClient.send(new Order(Order.OrderType.END_SOLO_MARIO_SURVIVAL));
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
