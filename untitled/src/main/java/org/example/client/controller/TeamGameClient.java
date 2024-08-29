package org.example.client.controller;

import org.example.client.view.main_panels.online_game.OnlineGamePanel;
import org.example.client.view.main_panels.online_game.TeamGamePanel;
import org.example.order.Information;
import org.example.order.Order;
import org.example.server.model.Item;
import org.example.team_game.send_object.paint_entity.PaintEntity;
import org.example.team_game.send_object.paint_tile.PaintTile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class TeamGameClient extends Thread{
    private int port;
    private boolean end = false;
    private Client client;
    private TeamGamePanel gamePanel;
    private DatagramSocket datagramSocket;
    public TeamGameClient(Client client , int port){
        this.client = client;
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
                byte[] receiveDate = new byte[2048 * 16];

                DatagramPacket receivePacket = new DatagramPacket(receiveDate, receiveDate.length);
                datagramSocket.receive(receivePacket);

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receiveDate);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Object object = objectInputStream.readObject();
                if (object instanceof Information<?>){
                    if(((Information<?>) object).getInformationType().equals(Information.InformationType.GAME_MARIO_ENTITIES)) {
                        LinkedList<PaintEntity> entities = (LinkedList<PaintEntity>) ((Information<?>) object).getLinkedList();
                        gamePanel.setEntities(entities);
                    }
                    else if(((Information<?>) object).getInformationType().equals(Information.InformationType.GAME_MARIO_TILES)) {
                        LinkedList<PaintTile> tiles = (LinkedList<PaintTile>) ((Information<?>) object).getLinkedList();
                        gamePanel.setTiles(tiles);
                    } else if (((Information<?>) object).getInformationType().equals(Information.InformationType.HIT_ITEM)) {
                        LinkedList<Item> items = (LinkedList<Item>) ((Information<?>) object).getLinkedList();
                        gamePanel.setItems(items);
                    }

                } else if (object instanceof Order) {
                    if (((Order) object).getOrderType().equals(Order.OrderType.GAME_OVER)){
                        gamePanel.setGameOver(true);
                        endGame();
                    } else if (((Order) object).getOrderType().equals(Order.OrderType.VICTORY)) {
                        gamePanel.setFinishedGame(true);
                        endGame();
                    } else if (((Order) object).getOrderType().equals(Order.OrderType.TEAM_GAME)) {
                        gamePanel.setCoin(((Order) object).getCoin());
                        gamePanel.setScore(((Order) object).getScore());
                        gamePanel.setLives(((Order) object).getLive());
                        gamePanel.setSection(((Order) object).getSection());
                    }
                }

            }
            datagramSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
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
    public void endGame(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                client.getClientFrame().setContentPane(new OnlineGamePanel(client));
            }
        };
        timer.schedule(timerTask , 2000);
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public TeamGamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(TeamGamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
}
