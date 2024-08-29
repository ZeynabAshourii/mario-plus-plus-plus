package org.example.server.controller;

import java.io.IOException;
import java.net.Socket;

public class ListenConnectionRequestThread extends Thread {
    private Server server;
    public ListenConnectionRequestThread(Server server){
        this.server = server;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Socket nSocket = this.server.getSocket().accept();
                ServerSideClient client = new ServerSideClient(nSocket , server);
                client.Listen();
                Server.getClients().add(client);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
