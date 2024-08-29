package org.example.client.controller;

import org.example.client.view.ClientFrame;
import org.example.client.view.main_panels.online_game.EditBagPanel;
import org.example.client.view.main_panels.online_game.OnlineGamePanel;
import org.example.client.view.main_panels.online_game.WaitingPanel;
import org.example.client.view.main_panels.user_starter_panels.ChatRoomPanel;
import org.example.client.view.main_panels.user_starter_panels.NotificationsPanel;
import org.example.client.view.main_panels.user_starter_panels.PvPanel;
import org.example.client.view.main_panels.user_starter_panels.StorePanel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
public class Client {
    private int coin;
    private int diamond;
    private int serverPort;
    private boolean online = false;
    private String serverIP;
    private String username;
    private Socket socket;
    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private ClientListenThread listenThread;
    private ClientFrame clientFrame;
    private ChatRoomPanel chatRoomPanel;
    private NotificationsPanel notificationsPanel;
    private PvPanel pvPanel;
    private StorePanel storePanel;
    private OnlineGamePanel onlineGamePanel;
    private EditBagPanel editBagPanel;
    private WaitingPanel waitingPanel;
    public Client(ClientFrame clientFrame){
        this.clientFrame = clientFrame;
    }
    public void Connect(String serverIP, int port) {
        try {
            this.serverIP = serverIP;
            this.serverPort = port;
            this.socket = new Socket(this.serverIP, this.serverPort);
            sOutput = new ObjectOutputStream(this.socket.getOutputStream());
            sInput = new ObjectInputStream(this.socket.getInputStream());
            listenThread = new ClientListenThread(this);
            this.listenThread.start();
            online = true;
        } catch (IOException ex) {
            online = false;
        }
    }
    public void send(Object message) {
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean isOnline() {
        return online;
    }

    public ClientFrame getClientFrame() {
        return clientFrame;
    }

    public ObjectInputStream getsInput() {
        return sInput;
    }

    public ChatRoomPanel getChatRoomPanel() {
        return chatRoomPanel;
    }

    public void setChatRoomPanel(ChatRoomPanel chatRoomPanel) {
        this.chatRoomPanel = chatRoomPanel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public NotificationsPanel getNotificationsPanel() {
        return notificationsPanel;
    }

    public void setNotificationsPanel(NotificationsPanel notificationsPanel) {
        this.notificationsPanel = notificationsPanel;
    }

    public PvPanel getPvPanel() {
        return pvPanel;
    }

    public void setPvPanel(PvPanel pvPanel) {
        this.pvPanel = pvPanel;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public StorePanel getStorePanel() {
        return storePanel;
    }

    public void setStorePanel(StorePanel storePanel) {
        this.storePanel = storePanel;
    }

    public OnlineGamePanel getOnlineGamePanel() {
        return onlineGamePanel;
    }

    public void setOnlineGamePanel(OnlineGamePanel onlineGamePanel) {
        this.onlineGamePanel = onlineGamePanel;
    }

    public EditBagPanel getEditBagPanel() {
        return editBagPanel;
    }

    public void setEditBagPanel(EditBagPanel editBagPanel) {
        this.editBagPanel = editBagPanel;
    }

    public WaitingPanel getWaitingPanel() {
        return waitingPanel;
    }

    public void setWaitingPanel(WaitingPanel waitingPanel) {
        this.waitingPanel = waitingPanel;
    }

}

