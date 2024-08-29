package org.example.client;

import org.example.client.view.ClientFrame;

public class MainClient {
    public static void main(String[] args) {
//        try {
//            ManageUser.getInstance().readUser();
//            for (int i = 0; i < ManageUser.getInstance().getUsers().size(); i++){
//                System.out.println(ManageUser.getInstance().getUsers().get(i).getUsername());
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        new ClientFrame();
        new ClientFrame();

    }
}
