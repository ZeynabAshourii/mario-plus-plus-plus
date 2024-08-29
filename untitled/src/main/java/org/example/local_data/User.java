package org.example.local_data;

import org.example.offline_game.view.OfflineGame;
import org.example.order.Notification;
import org.example.server.model.Item;
import org.example.server.model.SpecialBag;
import java.io.*;
import java.util.LinkedList;

public class User implements Serializable {
    private String username;
    private String password;
    private int coin = 500;
    private int diamond = 20;
    private int maxScore = 0;
    private int level = 2;

    private int offlineScore;
    private int onlineScore = 5;
    private int chosenSpecialBag = 0;
    private LinkedList<Integer> scores = new LinkedList<>();
    private LinkedList<Notification> notifications = new LinkedList<>();
    private LinkedList<Item> items = new LinkedList<>();
    private SpecialBag[] specialBags = new SpecialBag[3];
    private OfflineGame[] games = new OfflineGame[3];

    public User(String username , String password) throws FileNotFoundException {
        this.username = username;
        this.password = password;
        initItems();
        initSpecialBags();
    }
    public void initSpecialBags(){
        Item[] itemsOfFirstSpecialBag = new Item[5];

        itemsOfFirstSpecialBag[0] = new Item( username);
        itemsOfFirstSpecialBag[1] = new Item( username);
        itemsOfFirstSpecialBag[2] = new Item( username);
        itemsOfFirstSpecialBag[3] = new Item( username);
        itemsOfFirstSpecialBag[4] = new Item( username);

        SpecialBag firstSpecialBag = new SpecialBag(itemsOfFirstSpecialBag);

        Item[] itemsOfSecondSpecialBag = new Item[5];

        itemsOfSecondSpecialBag[0] = new Item( username);
        itemsOfSecondSpecialBag[1] = new Item( username);
        itemsOfSecondSpecialBag[2] = new Item( username);
        itemsOfSecondSpecialBag[3] = new Item( username);
        itemsOfSecondSpecialBag[4] = new Item( username);

        SpecialBag secondSpecialBag = new SpecialBag(itemsOfSecondSpecialBag);

        Item[] itemsOfThirdSpecialBag = new Item[5];

        itemsOfThirdSpecialBag[0] = new Item( username);
        itemsOfThirdSpecialBag[1] = new Item( username);
        itemsOfThirdSpecialBag[2] = new Item( username);
        itemsOfThirdSpecialBag[3] = new Item( username);
        itemsOfThirdSpecialBag[4] = new Item( username);

        SpecialBag thirdSpecialBag = new SpecialBag(itemsOfThirdSpecialBag);

        specialBags[0] = firstSpecialBag;
        specialBags[1] = secondSpecialBag;
        specialBags[2] = thirdSpecialBag;
    }
    public void initItems(){
        Item invisible = new Item(Item.ItemType.INVISIBLE , username);
        Item speed = new Item(Item.ItemType.SPEED , username);
        Item heal = new Item(Item.ItemType.HEAL , username);
        Item explosiveBomb = new Item(Item.ItemType.EXPLOSIVE_BOMB , username);
        Item speedBomb = new Item(Item.ItemType.SPEED_BOMB , username);
        Item hammer = new Item(Item.ItemType.HAMMER , username);
        Item sword = new Item(Item.ItemType.SWORD , username);
        items.add(invisible);
        items.add(speed);
        items.add(heal);
        items.add(explosiveBomb);
        items.add(speedBomb);
        items.add(hammer);
        items.add(sword);
    }
    public static LinkedList<User> sortResultUsers(){
//        LinkedList<User> sortUsers = new LinkedList<>();
//        for(int i = 0; i < users.size(); i++){
//            sortUsers.add(users.get(i));
//        }
//        for (int i = 0; i < users.size(); i++) {
//            for (int j = i+1 ; j < users.size(); j++) {
//                if (sortUsers.get(i).maxScore < sortUsers.get(j).maxScore) {
//                    User user = sortUsers.get(i);
//                    sortUsers.set(i , sortUsers.get(j));
//                    sortUsers.set(j , user);
//                }
//            }
//        }
//        return sortUsers;
        return null;
    }
    public int highestScore() {
        for (int i = 0; i < this.scores.size(); i++) {
            if (this.scores.get(i) > maxScore) {
                maxScore = this.scores.get(i);
            }
        }
        return maxScore;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public LinkedList<Integer> getScores() {
        return scores;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
    public OfflineGame[] getGames() {
        return games;
    }

    public void setGames(OfflineGame[] games) {
        this.games = games;
    }

    public LinkedList<Notification> getNotifications() {
        return notifications;
    }


    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public SpecialBag[] getSpecialBags() {
        return specialBags;
    }

    public void setSpecialBags(SpecialBag[] specialBags) {
        this.specialBags = specialBags;
    }

    public int getOnlineScore() {
        return onlineScore;
    }

    public void setOnlineScore(int onlineScore) {
        this.onlineScore = onlineScore;
    }

    public int getChosenSpecialBag() {
        return chosenSpecialBag;
    }

    public void setChosenSpecialBag(int chosenSpecialBag) {
        this.chosenSpecialBag = chosenSpecialBag;
    }


}

