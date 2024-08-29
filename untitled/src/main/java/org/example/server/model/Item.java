package org.example.server.model;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {
    private String username;
    private int numberLimit = -1;
    private int numberSoldPerPlayer;
    private int numberAvailable;
    private ItemType itemType;
    private Date startDate = new Date(120 , 1 , 1);
    private Date endDate = new Date(125 , 1 , 1 );
    private boolean coin = true;
    private boolean diamond = true;
    private boolean additional = false;
    private int level;
    public Item(ItemType itemType , String username){
        this.itemType =itemType;
        this.username = username;
        if (itemType == null){
            return;
        }
        if(itemType.equals(ItemType.SWORD)){
            coin = false;
            numberLimit = 1;
        }
        if(itemType.equals(ItemType.HAMMER) || itemType.equals(ItemType.EXPLOSIVE_BOMB) || itemType.equals(ItemType.SPEED_BOMB) || itemType.equals(ItemType.SWORD) ){
            level = 2;
        }
    }
    public Item(String username){
        this.username = username;
    }

    public void back() {
        numberSoldPerPlayer = numberSoldPerPlayer - 1;
        numberAvailable = numberAvailable - 1;
    }

    public enum ItemType implements Serializable{
        INVISIBLE , SPEED , HEAL , EXPLOSIVE_BOMB , SPEED_BOMB , HAMMER , SWORD;
    }
    public boolean buy(){
        if (numberLimit != -1){
            if(numberSoldPerPlayer + 1 <= numberLimit) {
                numberSoldPerPlayer = numberSoldPerPlayer + 1;
                numberAvailable = numberAvailable + 1;
                return true;
            }else {
                return false;
            }
        }else {
            numberSoldPerPlayer = numberSoldPerPlayer + 1;
            numberAvailable = numberAvailable + 1;
            return true;
        }
    }



    public int getNumberSoldPerPlayer() {
        return numberSoldPerPlayer;
    }

    public void setNumberSoldPerPlayer(int numberSoldPerPlayer) {
        this.numberSoldPerPlayer = numberSoldPerPlayer;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isCoin() {
        return coin;
    }

    public void setCoin(boolean coin) {
        this.coin = coin;
    }

    public boolean isDiamond() {
        return diamond;
    }

    public void setDiamond(boolean diamond) {
        this.diamond = diamond;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumberAvailable() {
        return numberAvailable;
    }

    public void setNumberAvailable(int numberAvailable) {
        this.numberAvailable = numberAvailable;
    }

    @Override
    public String toString() {
        return "Item{" +

                ", itemType=" + itemType +

                '}';
    }

    public boolean isAdditional() {
        return additional;
    }

    public void setAdditional(boolean additional) {
        this.additional = additional;
    }
}
