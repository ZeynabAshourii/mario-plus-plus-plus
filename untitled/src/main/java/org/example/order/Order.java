package org.example.order;

import org.example.client.view.main_panels.online_game.GameMode;
import org.example.server.model.Item;

import java.io.Serializable;

public class Order implements Serializable {
    private OrderType orderType;
    private String username;
    private String password;
    private String otherSideUsername;
    private int coin;
    private int diamond;
    private int score;
    private int live;
    private Item item;
    private int option;
    private int whichBag;
    private int whichItem;
    private GameMode gameMode;
    private int section;
    public Order(OrderType orderType , int coin , int score , int live , int section){
        this.orderType = orderType;
        this.coin = coin;
        this.score = score;
        this.live = live;
        this.section = section;
    }

    public Order(OrderType orderType , int whichBag){
        this.orderType = orderType;
        this.whichBag = whichBag;
    }
    public Order(OrderType orderType , GameMode gameMode , int whichBag){
        this.orderType = orderType;
        this.gameMode = gameMode;
        this.whichBag = whichBag;
    }
    public Order(OrderType orderType , Item item , int option){
        this.orderType = orderType;
        this.item = item;
        this.option = option;
    }

    public Order(OrderType orderType , String username , int coin, int diamond) {
        this.orderType = orderType;
        this.username = username;
        this.coin = coin;
        this.diamond = diamond;
    }
    public Order(OrderType orderType , int whichItem , int whichBag , int option ){
        this.orderType = orderType;
        this.whichItem = whichItem;
        this.whichBag = whichBag;
        this.option = option;
    }

    public Order(OrderType orderType){
        this.orderType = orderType;
    }
    public Order(OrderType orderType , String username){
        this.orderType = orderType;
        this.username = username;
    }
    public Order(OrderType orderType , String username , String string){
        this.orderType = orderType;
        this.username = username;
        if(orderType.equals(OrderType.NEW_USER) || orderType.equals(OrderType.SIGN_IN)) {
            this.password = string;
        } else if (orderType.equals(OrderType.PV_PANEL)) {
            this.otherSideUsername = string;
        }
    }
    public Order(OrderType orderType , int coin , int diamond){
        this.orderType = orderType;
        this.coin = coin;
        this.diamond = diamond;
    }
    public enum OrderType {
        NEW_USER , SIGN_IN , SEARCH_PV , RESET_PV , PV_PANEL , RESET_NOTIFICATION , RESET_ITEM , EXCHANGE , BUY_ITEM , OPEN_ONLINE_GAME_PANEL , OPEN_BAG , EDIT_BAG , INSUFFICIENT_INVENTORY , REQUEST_GAME_MARIO , START_MARATHON_MARIO , END_MARATHON_MARIO , UPDATE_MARATHON_MARIO , END_WAITING_PANEL , END_SOLO_MARIO_SURVIVAL , TEAM_GAME , GAME_OVER , VICTORY ;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getOtherSideUsername() {
        return otherSideUsername;
    }

    public int getCoin() {
        return coin;
    }

    public int getDiamond() {
        return diamond;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public int getWhichBag() {
        return whichBag;
    }

    public void setWhichBag(int whichBag) {
        this.whichBag = whichBag;
    }

    public int getWhichItem() {
        return whichItem;
    }

    public void setWhichItem(int whichItem) {
        this.whichItem = whichItem;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public int getScore() {
        return score;
    }

    public int getLive() {
        return live;
    }

    public int getSection() {
        return section;
    }
}
