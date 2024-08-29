package org.example.server.controller;

import org.example.client.view.main_panels.online_game.GameMode;
import org.example.local_data.User;
import org.example.marathon_mario.controller.HandlerMarathon;
import org.example.mario_survival.controller.HandlerSurvival;
import org.example.team_game.controller.HandlerTeamGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSideClient {
    private Socket socket;
    private ObjectInputStream cInput;
    private ObjectOutputStream cOutput;
    private ClientListenThread clientListenThread;
    private HandlerMarathon handlerMarathon;
    private HandlerSurvival handlerSurvival;
    private HandlerTeamGame handlerTeamGame;
    private Server server;
    private User user;
    private boolean requestTeamGame = false;
    private boolean requestMarathonMario = false;
    private boolean requestTeamMarioSurvival = false;
    private boolean requestSoloMarioSurvival = false;
    private LinkedList<ServerSideClient> nearScoreClient = new LinkedList<>();
    public ServerSideClient(Socket socket , Server server) {
        try {
            this.socket = socket;
            this.server = server;
            this.cOutput = new ObjectOutputStream(this.socket.getOutputStream());
            this.cInput = new ObjectInputStream(this.socket.getInputStream());
            this.clientListenThread = new ClientListenThread(this);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void send(Object msg) {
        try {
            this.cOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(ServerSideClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void calculateNearScoreAtTeamGame(){
        nearScoreClient.clear();
        for(int i = 0; i < Server.getClients().size(); i++){
            ServerSideClient client = Server.getClients().get(i);
            if (client.isRequestTeamGame()) {
                if (Math.abs(getUser().getOnlineScore() - client.getUser().getOnlineScore()) <= 10) {
                    if (nearScoreClient.size() <= 7) {
                        nearScoreClient.add(client);
                    }else {
                        return;
                    }
                }
            }
        }
    }
    public void calculateNearScoreAtMarathon(){
        nearScoreClient.clear();
        for(int i = 0; i < Server.getClients().size(); i++){
            ServerSideClient client = Server.getClients().get(i);
            if (client.isRequestMarathonMario()) {
                if (Math.abs(getUser().getOnlineScore() - client.getUser().getOnlineScore()) <= 10) {
                    if (nearScoreClient.size() <= 7) {
                        nearScoreClient.add(client);
                    }else {
                        return;
                    }
                }
            }
        }
    }
    public void calculateNearScoreAtSoloSurvival(){
        nearScoreClient.clear();
        for(int i = 0; i < Server.getClients().size(); i++){
            ServerSideClient client = Server.getClients().get(i);
            if (client.isRequestSoloMarioSurvival()) {
                if (Math.abs(getUser().getOnlineScore() - client.getUser().getOnlineScore()) <= 10) {
                    if (nearScoreClient.size() <= 7) {
                        nearScoreClient.add(client);
                    }else {
                        return;
                    }
                }
            }
        }
    }
    public void calculateNearScoreAtTeamSurvival(){
        nearScoreClient.clear();
        for(int i = 0; i < Server.getClients().size(); i++){
            ServerSideClient client = Server.getClients().get(i);
            if (client.isRequestTeamMarioSurvival()) {
                if (Math.abs(getUser().getOnlineScore() - client.getUser().getOnlineScore()) <= 10) {
                    if (nearScoreClient.size() <= 7) {
                        nearScoreClient.add(client);
                    }else {
                        return;
                    }
                }
            }
        }
    }
    public void Listen() {
        this.clientListenThread.start();
    }

    public ObjectInputStream getcInput() {
        return cInput;
    }

    public Server getServer() {
        return server;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isRequestMarathonMario() {
        return requestMarathonMario;
    }

    public void setRequestMarathonMario(boolean requestMarathonMario) {
        this.requestMarathonMario = requestMarathonMario;
    }

    public boolean isRequestTeamGame() {
        return requestTeamGame;
    }

    public void setRequestTeamGame(boolean requestTeamGame) {
        this.requestTeamGame = requestTeamGame;
    }

    public boolean isRequestTeamMarioSurvival() {
        return requestTeamMarioSurvival;
    }

    public void setRequestTeamMarioSurvival(boolean requestTeamMarioSurvival) {
        this.requestTeamMarioSurvival = requestTeamMarioSurvival;
    }

    public boolean isRequestSoloMarioSurvival() {
        return requestSoloMarioSurvival;
    }

    public void setRequestSoloMarioSurvival(boolean requestSoloMarioSurvival) {
        this.requestSoloMarioSurvival = requestSoloMarioSurvival;
    }

    public LinkedList<ServerSideClient> getNearScoreClient(GameMode gameMode) {
        if (gameMode.equals(GameMode.TEAM_GAME)){
            calculateNearScoreAtTeamGame();
        } else if (gameMode.equals(GameMode.MARIO_MARATHON)) {
            calculateNearScoreAtMarathon();
        } else if (gameMode.equals(GameMode.SOLO_MARIO_SURVIVAL)) {
            calculateNearScoreAtSoloSurvival();
        } else if (gameMode.equals(GameMode.TEAM_MARIO_SURVIVAL)) {
            calculateNearScoreAtTeamSurvival();
        }
        return nearScoreClient;
    }

    public void setNearScoreClient(LinkedList<ServerSideClient> nearScoreClient) {
        this.nearScoreClient = nearScoreClient;
    }

    public HandlerMarathon getHandlerMarathon() {
        return handlerMarathon;
    }

    public void setHandlerMarathon(HandlerMarathon handlerMarathon) {
        this.handlerMarathon = handlerMarathon;
    }

    public HandlerSurvival getHandlerSurvival() {
        return handlerSurvival;
    }

    public void setHandlerSurvival(HandlerSurvival handlerSurvival) {
        this.handlerSurvival = handlerSurvival;
    }

    public HandlerTeamGame getHandlerTeamGame() {
        return handlerTeamGame;
    }

    public void setHandlerTeamGame(HandlerTeamGame handlerTeamGame) {
        this.handlerTeamGame = handlerTeamGame;
    }

    @Override
    public String toString() {
        return "ServerSideClient{" +
                "user=" + user.getOnlineScore() + user.getUsername() +
                ", requestMarathonMario=" + requestMarathonMario +
                '}';
    }
}
