package org.example.marathon_mario.model;

import org.example.server.model.Item;
import org.example.server.model.SpecialBag;
import java.util.LinkedList;

public class PlayerMarathon {
    private String username;
    private SpecialBag specialBag;
    private LinkedList<Item> additionalItems = new LinkedList<>();
    private boolean sword;
    private LinkedList<Integer> lifeTimes = new LinkedList<>();

    private LinkedList<Integer> distanceFromStarts = new LinkedList<>();

    public PlayerMarathon(String username, SpecialBag specialBag , boolean sword) {
        this.username = username;
        this.specialBag = specialBag;
        this.sword = sword;
        if (sword){
            Item item = new Item(Item.ItemType.SWORD , username);
            item.setAdditional(true);
            additionalItems.add(item);
        }
    }
    public LinkedList<Item> getAllItems(){
        LinkedList<Item> allItems = new LinkedList<>();
        if(specialBag != null) {
            for (int i = 0; i < specialBag.getItems().length; i++) {
                Item item = specialBag.getItems()[i];
                if (item != null) {
                    allItems.add(item);
                }
            }
        }
        allItems.addAll(additionalItems);
        return allItems;
    }
    public int getMaxLifeTime(){
        int maxLifetime = 0;
        for (int i = 0; i < lifeTimes.size(); i++){
            if (lifeTimes.get(i) > maxLifetime){
                maxLifetime = lifeTimes.get(i);
            }
        }
        return maxLifetime;
    }
    public int getMaxDistance(){
        int maxDistance = 0;
        for (int i = 0; i < distanceFromStarts.size(); i++){
            if(distanceFromStarts.get(i) > maxDistance){
                maxDistance = distanceFromStarts.get(i);
            }
        }
        return maxDistance;
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

    public LinkedList<Item> getAdditionalItems() {
        return additionalItems;
    }

    public void setAdditionalItems(LinkedList<Item> additionalItems) {
        this.additionalItems = additionalItems;
    }
    public LinkedList<Integer> getLifeTimes() {
        return lifeTimes;
    }

    public void setLifeTimes(LinkedList<Integer> lifeTimes) {
        this.lifeTimes = lifeTimes;
    }

    public LinkedList<Integer> getDistanceFromStarts() {
        return distanceFromStarts;
    }

    public void setDistanceFromStarts(LinkedList<Integer> distanceFromStarts) {
        this.distanceFromStarts = distanceFromStarts;
    }
}
