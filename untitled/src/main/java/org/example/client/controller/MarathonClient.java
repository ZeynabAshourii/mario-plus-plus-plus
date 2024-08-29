package org.example.client.controller;

import org.example.client.view.main_panels.online_game.MarathonMarioPanel;
import org.example.send_object.entities.PaintEntity;
import org.example.order.Information;
import org.example.order.Order;
import org.example.server.model.Item;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.LinkedList;
public class MarathonClient extends Thread{
    private int port;
    private boolean end = false;
    private String username;
    private MarathonMarioPanel gamePanel;
    private DatagramSocket datagramSocket;
    public MarathonClient(String name , int port){
        this.username = name;
        this.port = port;
        try {
            datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        start();
    }
    @Override
    public void run() {
        try {
            while (!end) {
                byte[] receiveDate = new byte[2048*16];
                DatagramPacket receivePacket = new DatagramPacket(receiveDate, receiveDate.length);
                datagramSocket.receive(receivePacket);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receiveDate);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Object object = objectInputStream.readObject();
                if (object instanceof Information<?>){
                    if(((Information<?>) object).getInformationType().equals(Information.InformationType.GAME_MARIO)) {
                        LinkedList<PaintEntity> entities = (LinkedList<PaintEntity>) ((Information<?>) object).getLinkedList();
                        gamePanel.setEntities(entities);
                    }else if(((Information<?>) object).getInformationType().equals(Information.InformationType.HIT_ITEM)) {
                        LinkedList<Item> items = (LinkedList<Item>) ((Information<?>) object).getLinkedList();
                        gamePanel.setItems(items);
                    }
                } else if (object instanceof Order) {
                    if(((Order) object).getOrderType().equals(Order.OrderType.END_MARATHON_MARIO)){
                        gamePanel.setEnd(true);
                        end = true;
                    }
                }
            }
            datagramSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void send(byte[] sendData) {
        try {
            DatagramPacket sendPacket = new DatagramPacket(sendData , sendData.length , InetAddress.getLocalHost() , port);
            datagramSocket.send(sendPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        return username;
    }

    public MarathonMarioPanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(MarathonMarioPanel gamePanel) {
        this.gamePanel = gamePanel;
    }
}

