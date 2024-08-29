package org.example.marathon_mario.controller;

import org.example.marathon_mario.model.entities.*;
import org.example.marathon_mario.model.enums.Id;
import org.example.marathon_mario.model.PlayerMarathon;
import org.example.marathon_mario.model.tiles.EmptyBlock;
import org.example.marathon_mario.model.tiles.Tile;
import org.example.send_object.entities.PaintEntity;
import org.example.send_object.entities.items.PaintAdditionalItem;
import org.example.send_object.tile.PaintTile;
import org.example.server.model.Item;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class HandlerMarathon {
    private LinkedList<PlayerMarathon> playerMarathons = new LinkedList<>();
    private LinkedList<Entity> entities = new LinkedList<>();
    private LinkedList<Tile> tiles = new LinkedList<>();
    private LinkedList<PaintEntity> paintEntities = new LinkedList<>();
    private LinkedList<PaintTile> paintTiles = new LinkedList<>();

    private double speedPotionMultiplier;
    private int speedPotionPeriod;
    private int invisibilityPotionPeriod;
    private int healthPotionHPPercent;
    private double damageBombBlockArea;
    private int damageBombPercent;
    private double speedBombBlockArea;
    private double speedBombMultiplier;
    private int hammerStunPeriod;
    private double swordBlockRange;
    private int swordPercentDamage;
    private double swordPushBackBlock;
    private int swordCoolDown;
    private double marathonMultiplierSpeed;
    private double marathonMultiplierSlowDown;
    private int marathonPeriodSlowDown;
    private double marathonLifeTimeMultiplier;
    private double marathonDistanceMultiplier;
    private int marathonMinLifeTime;
    private int marathonMinDistance;
    private int length;
    private int time = 100;
    private boolean sendItems = false;
    private String senderName;
    private int deathY;
    private long startTime;
    private boolean end = false;

    public HandlerMarathon(LinkedList<PlayerMarathon> playerMarathons , double speedPotionMultiplier,
                           int speedPotionPeriod, int invisibilityPotionPeriod, int healthPotionHPPercent,
                           double damageBombBlockArea, int damageBombPercent, double speedBombBlockArea,
                           double speedBombMultiplier, int hammerStunPeriod, double swordBlockRange, int swordPercentDamage,
                           double swordPushBackBlock, int swordCoolDown, double marathonMultiplierSpeed,
                           double marathonMultiplierSlowDown, int marathonPeriodSlowDown, double marathonLifeTimeMultiplier,
                           double marathonDistanceMultiplier, int marathonMinLifeTime, int marathonMinDistance) {
        this.playerMarathons = playerMarathons;
        this.speedPotionMultiplier = speedPotionMultiplier;
        this.speedPotionPeriod = speedPotionPeriod;
        this.invisibilityPotionPeriod = invisibilityPotionPeriod;
        this.healthPotionHPPercent = healthPotionHPPercent;
        this.damageBombBlockArea = damageBombBlockArea;
        this.damageBombPercent = damageBombPercent;
        this.speedBombBlockArea = speedBombBlockArea;
        this.speedBombMultiplier = speedBombMultiplier;
        this.hammerStunPeriod = hammerStunPeriod;
        this.swordBlockRange = swordBlockRange;
        this.swordPercentDamage = swordPercentDamage;
        this.swordPushBackBlock = swordPushBackBlock;
        this.swordCoolDown = swordCoolDown;
        this.marathonMultiplierSpeed = marathonMultiplierSpeed;
        this.marathonMultiplierSlowDown = marathonMultiplierSlowDown;
        this.marathonPeriodSlowDown = marathonPeriodSlowDown;
        this.marathonLifeTimeMultiplier = marathonLifeTimeMultiplier;
        this.marathonDistanceMultiplier = marathonDistanceMultiplier;
        this.marathonMinLifeTime = marathonMinLifeTime;
        this.marathonMinDistance = marathonMinDistance;


        createLevel();
    }
    public void update() {
        long endTime = System.nanoTime()/1000000000L - startTime;
        if (endTime > time){
            end = true;
        }
        for(int i = 0; i < entities.size(); i++){
            Entity entity = entities.get(i);
            entity.update();
            if (end){
                if(entity.getId().equals(Id.MARIO)){
                    ((Mario)entity).die();
                }
            }
        }
    }
    public void addEntity(Entity entity){
        entities.add(entity);
    }
    public void addTile(Tile tile){
        tiles.add(tile);
    }
    public void createLevel() {
        loadBlock();
        loadItems();
        loadMarios();
        startTime = System.nanoTime()/1000000000L;
    }
    public void loadBlock(){
        length = 100;
        for(int i = -1; i <= length+1; i++){
            if (i%30 != 0) {
                addTile(new EmptyBlock((i) * 64, 0, 64, 64, Id.EMPTY_BLOCK, this));
            }
            if(i%35 > 4){
                addTile(new EmptyBlock((i)*64 ,- 64*3.5 , 64 , 64 , Id.EMPTY_BLOCK , this));
            }
        }
    }
    public void loadItems(){
        for(int i = 1; i < (playerMarathons.size() + 4)*4; i++){
            Item.ItemType itemType;
            if (i%6 == 0){
                itemType = Item.ItemType.INVISIBLE;
            } else if (i%6 == 1) {
                itemType = Item.ItemType.SPEED;
            } else if (i%6 == 2) {
                itemType = Item.ItemType.HEAL;
            } else if (i%6 == 3) {
                itemType = Item.ItemType.EXPLOSIVE_BOMB;
            } else if (i%6 == 4) {
                itemType = Item.ItemType.SPEED_BOMB;
            } else {
                itemType = Item.ItemType.HAMMER;
            }
            paintEntities.add(new PaintAdditionalItem(64*40*i , -64 ,64 , 64 , Id.ADDITIONAL_ITEM , itemType ));
            paintEntities.add(new PaintAdditionalItem(64*40*i + 64*10 , -64*4.5 ,64 , 64 , Id.ADDITIONAL_ITEM , itemType ));
            if (i%2 != 0) {
                addEntity(new Goomba(64 * 50 * i, -64, 64, 64, Id.GOOMBA, this));
                addEntity(new Goomba(64 * 50 * i + 64 * 20, -64 * 4.5, 64, 64, Id.GOOMBA, this));
            }
        }
    }
    public void loadMarios(){
        for(int i = 0; i < playerMarathons.size(); i++){
            addEntity(new Mario(64*2, -64*3 , 64 , 64 , Id.MARIO , this , playerMarathons.get(i)));
        }

    }
    public Mario searchMario(String username){
        for (int i = 0; i < entities.size(); i++){
            if(entities.get(i).getId().equals(Id.MARIO)){
                Mario mario = (Mario) entities.get(i);
                if(mario.getPaintMario().getUsername().equals(username)){
                    return mario;
                }
            }
        }
        return null;
    }
    public LinkedList<Item> searchItem(String username){
        for(int i =0 ; i < playerMarathons.size(); i++){
            if(playerMarathons.get(i).getUsername().equals(username)){
                return playerMarathons.get(i).getAllItems();
            }
        }
        return null;
    }
    public void UpKey(String username){
        Mario mario = searchMario(username);
        if (mario == null){
            return;
        }
        if (!mario.isJumping() ) {
            mario.setJumping(true);
            mario.setGravity(10.0);
        }
    }
    public void DownKey(){

    }
    public void LeftKey(String username){
        Mario mario = searchMario(username);
        if (mario == null){
            return;
        }
        if(mario.getVelX() != 0) {
            mario.setVelX(mario.getVelX() / marathonMultiplierSpeed);
        }else {
            mario.setVelX(1.8);
        }
        mario.getPaintMario().setFacing(0);
    }
    public void RightKey(String username){
        Mario mario = searchMario(username);
        if (mario == null){
            return;
        }
        if (mario.getVelX() != 0) {
            mario.setVelX(mario.getVelX() * marathonMultiplierSpeed);
        }else {
            mario.setVelX(1.8);
        }
        mario.getPaintMario().setFacing(1);
    }
    public void itemKey(String username , int key){
        Mario mario = searchMario(username);
        if (mario == null){
            return;
        }
        if (key <= mario.getPlayerMarathon().getAllItems().size()) {
            useItem(mario.getPlayerMarathon().getAllItems().get(key - 1), mario);
        }
    }

    public void deleteItem(Item item , Mario mario){
        if(item.isAdditional()){
            mario.getPlayerMarathon().getAdditionalItems().remove(item);
            setSenderName(mario.getPaintMario().getUsername());
            setSendItems(true);
        }else{
            Item[] items = mario.getPlayerMarathon().getSpecialBag().getItems();
            for(int i = 0; i < items.length; i++){
                if(items[i].getItemType().equals(item.getItemType())){
                    items[i] = null;
                    setSenderName(mario.getPaintMario().getUsername());
                    setSendItems(true);
                    return;
                }
            }
        }
    }
    public void useItem(Item item , Mario mario){
        if(item == null){
            return;
        }else {
            if(item.getItemType().equals(Item.ItemType.INVISIBLE)){
                invisibility(item , mario);
            } else if (item.getItemType().equals(Item.ItemType.SPEED)) {
                speed(item , mario);
            } else if (item.getItemType().equals(Item.ItemType.HEAL)) {
                deleteItem(item , mario);
                mario.getPaintMario().setLife(Math.min(mario.getPaintMario().getLife() + healthPotionHPPercent , 100));
            } else if (item.getItemType().equals(Item.ItemType.EXPLOSIVE_BOMB)) {
                deleteItem(item , mario);
                addEntity(new ExplosiveBomb(mario.getX() + 64*10 , -64*9 , 48 , 48 , Id.EXPLOSIVE_BOMB , this , mario.getPaintMario().getUsername()));
            } else if (item.getItemType().equals(Item.ItemType.SPEED_BOMB)) {
                deleteItem(item , mario);
                addEntity(new SpeedBomb(mario.getX() + 64 , mario.getY() , 48 , 48 , Id.SPEED_BOMB , this , mario.getPaintMario().getUsername()));
            } else if (item.getItemType().equals(Item.ItemType.HAMMER)) {
                deleteItem(item , mario);
                addEntity(new Hammer(mario.getX() + 64 , mario.getY() , 48 , 48 , Id.HAMMER , this , mario.getPaintMario().getUsername()));
            } else if (item.getItemType().equals(Item.ItemType.SWORD)) {
                deleteItem(item , mario);
                addEntity(new Sword(0 , 0 , 6 , 72 , Id.SWORD , this , mario));
            }
        }

    }
    public void speed(Item item , Mario mario){
        deleteItem(item , mario);

        mario.setVelX(mario.getVelX()*speedPotionMultiplier);

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mario.setVelX(mario.getVelX()/speedPotionMultiplier);
            }
        };
        timer.schedule(timerTask , speedPotionPeriod*1000);
    }
    public void invisibility(Item item , Mario mario){
        deleteItem(item , mario);
        mario.getPaintMario().setInvisible(true);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mario.getPaintMario().setInvisible(false);
            }
        };
        timer.schedule(timerTask , invisibilityPotionPeriod*1000);
    }
    public int getDeathY() {
        for(int i = 0 ; i < tiles.size(); i++){
            if(tiles.get(i).getY() > deathY){
                deathY = (int) tiles.get(i).getY();
            }
        }
        return deathY;
    }

    public boolean isSendItems() {
        return sendItems;
    }
    public void setSendItems(boolean sendItems) {
        this.sendItems = sendItems;
    }
    public String getSenderName() {
        return senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    public double getDamageBombBlockArea() {
        return damageBombBlockArea;
    }
    public int getDamageBombPercent() {
        return damageBombPercent;
    }
    public double getSpeedBombBlockArea() {
        return speedBombBlockArea;
    }
    public double getSpeedBombMultiplier() {
        return speedBombMultiplier;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
    public int getHammerStunPeriod() {
        return hammerStunPeriod;
    }
    public double getSwordBlockRange() {
        return swordBlockRange;
    }
    public int getSwordPercentDamage() {
        return swordPercentDamage;
    }
    public double getSwordPushBackBlock() {
        return swordPushBackBlock;
    }
    public int getSwordCoolDown() {
        return swordCoolDown;
    }
    public double getMarathonMultiplierSpeed() {
        return marathonMultiplierSpeed;
    }
    public int getMarathonPeriodSlowDown() {
        return marathonPeriodSlowDown;
    }
    public boolean isEnd() {
        return end;
    }
    public double getMarathonLifeTimeMultiplier() {
        return marathonLifeTimeMultiplier;
    }
    public double getMarathonDistanceMultiplier() {
        return marathonDistanceMultiplier;
    }
    public int getMarathonMinLifeTime() {
        return marathonMinLifeTime;
    }
    public int getMarathonMinDistance() {
        return marathonMinDistance;
    }
    public double getMarathonMultiplierSlowDown() {
        return marathonMultiplierSlowDown;
    }
    public LinkedList<PlayerMarathon> getPlayerMarathons() {
        return playerMarathons;
    }

    public LinkedList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(LinkedList<Entity> entities) {
        this.entities = entities;
    }

    public LinkedList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(LinkedList<Tile> tiles) {
        this.tiles = tiles;
    }

    public LinkedList<PaintEntity> getPaintEntities() {
        return paintEntities;
    }
    public LinkedList<PaintTile> getPaintTiles() {
        return paintTiles;
    }

}
