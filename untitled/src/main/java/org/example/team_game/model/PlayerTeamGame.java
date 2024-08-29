package org.example.team_game.model;

import org.example.server.model.Item;
import org.example.server.model.SpecialBag;

import java.util.LinkedList;

public class PlayerTeamGame {
    private int marioState;
    private int coin = 0;
    private int score = 0;
    private String username;
    private SpecialBag specialBag;
    private boolean sword;
    private int live = 3;
    private int section = 0;
    private LinkedList<Item> allItems = new LinkedList<>();
    private boolean end = false;

    public PlayerTeamGame(String username, SpecialBag specialBag, boolean sword , int marioState) {
        this.username = username;
        this.specialBag = specialBag;
        this.sword = sword;
        this.marioState = marioState;
        if(specialBag != null) {
            for (int i = 0; i < specialBag.getItems().length; i++) {
                Item item = specialBag.getItems()[i];
                if (item != null) {
                    allItems.add(item);
                }
            }
        }
        if (sword){
            Item item = new Item(Item.ItemType.SWORD , username);
            allItems.add(item);
        }
    }


    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SpecialBag getSpecialBag() {
        return specialBag;
    }

    public void setSpecialBag(SpecialBag specialBag) {
        this.specialBag = specialBag;
    }

    public boolean isSword() {
        return sword;
    }

    public void setSword(boolean sword) {
        this.sword = sword;
    }
    public LinkedList<Item> getAllItems() {
        return allItems;
    }

    public void setAllItems(LinkedList<Item> allItems) {
        this.allItems = allItems;
    }

    public int getMarioState() {
        return marioState;
    }

    public void setMarioState(int marioState) {
        this.marioState = marioState;
    }

    public int getLive() {
        return live;
    }

    public void setLive(int live) {
        this.live = live;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
