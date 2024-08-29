package org.example.mario_survival.model;

import org.example.server.model.Item;
import org.example.server.model.SpecialBag;

import java.util.LinkedList;

public class PlayerSurvival {
    private String username;
    private SpecialBag specialBag;
    private boolean sword;
    private boolean alive = true;
    private LinkedList<Item> allItems = new LinkedList<>();
    private LinkedList<Integer> damageDealt = new LinkedList<>();
    private MarioTeam marioTeam;
    private int damageReceived;
    private int countItems;
    public PlayerSurvival(String username, SpecialBag specialBag, boolean sword) {
        this.username = username;
        this.specialBag = specialBag;
        this.sword = sword;
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
    public int damageDealt(){
        int damage = 0;
        for(int i = 0; i < damageDealt.size(); i++){
            damage += damageDealt.get(i);
        }
        return damage;
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

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public MarioTeam getMarioTeam() {
        return marioTeam;
    }

    public void setMarioTeam(MarioTeam marioTeam) {
        this.marioTeam = marioTeam;
    }

    public LinkedList<Integer> getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(LinkedList<Integer> damageDealt) {
        this.damageDealt = damageDealt;
    }

    public int getDamageReceived() {
        return damageReceived;
    }

    public void setDamageReceived(int damageReceived) {
        this.damageReceived = damageReceived;
    }

    public int getCountItems() {
        return countItems;
    }

    public void setCountItems(int countItems) {
        this.countItems = countItems;
    }
}
