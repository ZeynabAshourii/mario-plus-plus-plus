package org.example.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.client.controller.Client;
import org.example.local_data.Store;
import org.example.server.model.ServerSetting;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Server {
    private volatile int numberLimit = 10;
    private int port;
    private ServerSocket socket;
    private ListenConnectionRequestThread listenConnectionRequestThread;
    private static ArrayList<ServerSideClient> clients = new ArrayList<>();
    private ServerSetting serverSetting;
    private Store store;
    private boolean calculateMarathon = false;
    private ArrayList<ServerSideClient> marathonClients = new ArrayList<>();
    public Server(int port) {
        try {
            this.port = port;
            this.socket = new ServerSocket(this.port);
            this.listenConnectionRequestThread = new ListenConnectionRequestThread(this);
            loadSetting();
            loadStore();
        } catch (IOException ex) {
            System.out.println("There is an error occured when opening the Server on port:" + this.port);
        }
    }
    public void loadSetting(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            this.serverSetting = objectMapper.readValue(new File("untitled/setting.json"), ServerSetting.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadStore(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            this.store = objectMapper.readValue(new File("untitled/store.json"), Store.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void ListenClientConnectionRequests() {
        this.listenConnectionRequestThread.start();
    }
    public ServerSocket getSocket() {
        return socket;
    }

    public static ArrayList<ServerSideClient> getClients() {
        return clients;
    }

    public ServerSetting getServerSetting() {
        return serverSetting;
    }

    public void setServerSetting(ServerSetting serverSetting) {
        this.serverSetting = serverSetting;
    }

    public int getNumberLimit() {
        return numberLimit;
    }

    public void setNumberLimit(int numberLimit) {
        this.numberLimit = numberLimit;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

}
