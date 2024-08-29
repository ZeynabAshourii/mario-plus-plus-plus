package org.example.server.controller;

import org.example.order.Information;
import org.example.order.Order;
import org.example.send_object.entities.PaintReaction;
import org.example.server.model.Item;
import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.send_object.paint_entity.PaintEntity;
import org.example.team_game.send_object.paint_tile.PaintTile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class TeamGameServer extends Thread{
    private int port;
    private boolean end = false;
    private HandlerTeamGame handler;
    private DatagramSocket datagramSocket;
    public TeamGameServer(int port, HandlerTeamGame handler) {
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
                if(string.contains("UPDATE_ENTITIES")) {
                    int startIndex = getSubString(string, "UPDATE_ENTITIES");
                    String name = string.substring(0, startIndex);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    handler.update();
                    if (handler.isSendData() && name.equals(handler.getSenderName())) {
                        if (handler.getTypeOfData().equals("ITEMS")) {
                            objectOutputStream.writeObject(new Information<Item>(handler.searchItem(name), Information.InformationType.HIT_ITEM));
                        } else if (handler.getTypeOfData().equals("GAME_INFO")) {
                            objectOutputStream.writeObject(handler.info(name));
                        } else if (handler.getTypeOfData().equals("GAME_OVER")){
                            objectOutputStream.writeObject(new Order(Order.OrderType.GAME_OVER));
                        } else if (handler.getTypeOfData().equals("VICTORY")){
                            objectOutputStream.writeObject(new Order(Order.OrderType.VICTORY));
                        }
                        handler.setTypeOfData(null);
                        handler.setSenderName(null);
                        handler.setSendData(false);
                    }else {
                        objectOutputStream.writeObject(new Information<PaintEntity>(handler.paintEntitiesOfMario(name), Information.InformationType.GAME_MARIO_ENTITIES));
                    }
                    byte[] data = byteArrayOutputStream.toByteArray();
                    DatagramPacket datagramPacket = new DatagramPacket(data, data.length, receivePacket.getAddress(), receivePacket.getPort());
                    datagramSocket.send(datagramPacket);
                } else if (string.contains("UPDATE_TILES")) {
                    int startIndex = getSubString(string, "UPDATE_TILES");
                    String name = string.substring(0, startIndex);
                    if (handler.isEnd()){
                        end = true;
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    handler.update();

                    objectOutputStream.writeObject(new Information<PaintTile>(handler.paintTiles(name), Information.InformationType.GAME_MARIO_TILES));

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
    }
    public void handelKeys(String string){
        if (string.contains("RIGHT_PRESSED")) {
            int startIndex = getSubString(string, "RIGHT_PRESSED");
            String name = string.substring(0, startIndex);
            handler.dKey(name);
        } else if (string.contains("LEFT_PRESSED")) {
            int startIndex = getSubString(string, "LEFT_PRESSED");
            String name = string.substring(0, startIndex);
            handler.aKey(name);
        } else if (string.contains("UP_PRESSED")) {
            int startIndex = getSubString(string, "UP_PRESSED");
            String name = string.substring(0, startIndex);
            handler.wKey(name);
        } else if (string.contains("DOWN_PRESSED")) {
            int startIndex = getSubString(string, "DOWN_PRESSED");
            String name = string.substring(0, startIndex);
            handler.sKey(name);
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
        } else if (string.contains("enter")) {
            int startIndex = getSubString(string, "enter");
            String name = string.substring(0, startIndex);
            handler.enterKey(name);
        }
        useItem(string);
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
