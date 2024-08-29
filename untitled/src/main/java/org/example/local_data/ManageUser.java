package org.example.local_data;

import org.example.offline_game.view.OfflineGame;
import java.io.*;
import java.util.LinkedList;

public class ManageUser {
    private static final ManageUser instance = new ManageUser();
    private LinkedList<User> users = new LinkedList<>();
    private ManageUser(){

    }
    public void writeUser(User user) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("saveUser.txt" , false));
        users.add(user);
        for(int i = 0; i < users.size(); i++){
            objectOutputStream.writeObject(users.get(i));
        }
    }
    public LinkedList<User> readUsers() throws IOException {
        LinkedList<User> thisUser = new LinkedList<>();
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("saveUser.txt"));
        Object obj = null;
        try {
            while (((obj = objectInputStream.readObject()) instanceof User)) {
                thisUser.add((User) obj);
            }
        } catch (Exception e) {

        }
        return thisUser;
    }
    public void readUser() throws IOException {
        users.clear();
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("saveUser.txt"));
        Object obj = null;
        try {
            while (((obj = objectInputStream.readObject()) instanceof User)) {
                users.add((User) obj);
            }
        } catch (Exception e) {

        }
    }
    public void resetUser() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("saveUser.txt" , false));
        for(int i = 0; i < users.size(); i++){
            objectOutputStream.writeObject(users.get(i));
        }
    }
    public User findUser(String username){
        try {
            readUser();
        }catch (Exception e){

        }
        for (int i = 0; i < users.size(); i++){
            if(users.get(i).getUsername().equals(username)){
                return users.get(i);
            }
        }
        return null;
    }
    public User findUsers(String username){
        try {
            for (int i = 0; i < readUsers().size(); i++) {
                if (readUsers().get(i).getUsername().equals(username)) {
                    return readUsers().get(i);
                }
            }
        }catch (Exception e){

        }
        return null;
    }

    public void handelGame(String username, int i, OfflineGame game) {
        findUser(username).getGames()[i] = game;
    }
    public OfflineGame getGame(String username , int i){
        return findUser(username).getGames()[i];
    }
    public static ManageUser getInstance(){
        return instance;
    }
    public LinkedList<User> getUsers() {
        return users;
    }

    public void setUsers(LinkedList<User> users) {
        this.users = users;
    }
}
