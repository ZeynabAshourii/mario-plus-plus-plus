package org.example.server;

import org.example.server.controller.Server;

public class Start {
    public static void main(String[] args) {
        Server server = new Server(5090);
        server.ListenClientConnectionRequests();
    }
}
