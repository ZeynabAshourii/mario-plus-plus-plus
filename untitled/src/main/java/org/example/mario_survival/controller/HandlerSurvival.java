package org.example.mario_survival.controller;

import org.example.marathon_mario.model.enums.Id;
import org.example.send_object.entities.PaintEntity;
import org.example.send_object.entities.PaintReaction;
import org.example.send_object.entities.items.PaintAdditionalItem;
import org.example.send_object.tile.PaintTile;
import org.example.server.model.Item;
import org.example.mario_survival.model.MarioTeam;
import org.example.mario_survival.model.PlayerSurvival;
import org.example.mario_survival.model.entities.*;
import org.example.mario_survival.model.tiles.EmptyBlock;
import org.example.mario_survival.model.tiles.Tile;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class HandlerSurvival {
    private LinkedList<PlayerSurvival> playerSoloSurvivals = new LinkedList<>();
    private PaintReaction[] reactions = new PaintReaction[5];
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
    private double survivalDamageMultiplier;
    private double survivalEquipmentMultiplier;
    private int deathY;
    private int length;
    private boolean end;
    private boolean sendData = false;
    private String senderName;
    private String dateType;
    private LinkedList<PaintEntity> paintEntities = new LinkedList<>();
    private LinkedList<PaintTile> paintTiles = new LinkedList<>();
    private LinkedList<Entity> entities = new LinkedList<>();
    private LinkedList<Tile> tiles = new LinkedList<>();
    private LinkedList<MarioTeam> marioTeams = new LinkedList<>();
    public HandlerSurvival(LinkedList<PlayerSurvival> playerSoloSurvivals , LinkedList<MarioTeam> marioTeams ,
                           double speedPotionMultiplier, int speedPotionPeriod, int invisibilityPotionPeriod,
                           int healthPotionHPPercent, double damageBombBlockArea, int damageBombPercent,
                           double speedBombBlockArea, double speedBombMultiplier, int hammerStunPeriod,
                           double swordBlockRange, int swordPercentDamage, double swordPushBackBlock,
                           int swordCoolDown, double survivalDamageMultiplier , double survivalEquipmentMultiplier) {

        this.playerSoloSurvivals = playerSoloSurvivals;
        this.marioTeams = marioTeams;
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
        this.survivalDamageMultiplier = survivalDamageMultiplier;
        this.survivalEquipmentMultiplier = survivalEquipmentMultiplier;

        init();

        createLevel();
    }
    public void init(){
        reactions[0] = new PaintReaction(15*64 + 12 , 10 , 40 , 40 , Id.REACTION , PaintReaction.ReactionType.LIKE );
        reactions[1] = new PaintReaction(15*64 + 12 , 10 + 45 , 40 , 40 , Id.REACTION , PaintReaction.ReactionType.DISLIKE);
        reactions[2] = new PaintReaction(15*64 + 12 , 10 + 45*2 , 40 , 40 , Id.REACTION , PaintReaction.ReactionType.LAUGH);
        reactions[3] = new PaintReaction(15*64 + 12 , 10 + 45*3 , 40 , 40 , Id.REACTION , PaintReaction.ReactionType.ANGER);
        reactions[4] = new PaintReaction(15*64 + 12 , 10 + 45*4 , 40 , 40 , Id.REACTION , PaintReaction.ReactionType.HEART);
    }
    public void createLevel() {
        loadBlock();
        loadItems();
        loadMarios();
    }
    public void loadBlock(){
        length = 17;
        for(int i = 0; i < length; i++){
            tiles.add(new EmptyBlock((i) * 64, 64*10.4, 64, 64, Id.EMPTY_BLOCK , this));
            tiles.add(new EmptyBlock(33*32-8 , 64*(9.4 - i) , 64 , 64 , Id.EMPTY_BLOCK , this));
            tiles.add(new EmptyBlock(-48 , 64*(9.4 - i) , 64 , 64 , Id.EMPTY_BLOCK , this));
            if(i%14 > 1){
                tiles.add(new EmptyBlock((i)*64 , 64*6.7 , 64 , 64 , Id.EMPTY_BLOCK , this));
            }
        }
    }
    public void loadItems(){
        for(int i = 0; i < (playerSoloSurvivals.size())*4; i++){
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
            paintEntities.add(new PaintAdditionalItem(64*9*i + 64*3 , 64*9.4 ,64 , 64 , Id.ADDITIONAL_ITEM , itemType ));
            paintEntities.add(new PaintAdditionalItem(64*9*i + 64*4 , 64*5.7 ,64 , 64 , Id.ADDITIONAL_ITEM , itemType ));
//            if (i%2 != 0) {
//                addEntity(new Goomba(64 * 50 * i, -64, 64, 64, Id.GOOMBA, this));
//                addEntity(new Goomba(64 * 50 * i + 64 * 20, -64 * 4.5, 64, 64, Id.GOOMBA, this));
//            }
        }
    }
    public void loadMarios(){
        for(int i = 0; i < playerSoloSurvivals.size(); i++){
            addEntity(new Mario(64, 64*9.4 , 64 , 64 , Id.MARIO , this , playerSoloSurvivals.get(i)));
        }
    }
    public void reactionKey(String username , int i){
        PaintReaction reaction = reactions[i];
        reaction.setNumberOfReaction(reaction.getNumberOfReaction() + 1);
        setDateType("reactions");
        setSenderName(username);
        setSendData(true);
    }
    public void update(){
        for(int i = 0; i < entities.size(); i++){
            Entity entity = entities.get(i);
            entity.update();
        }
        if (numberOfAliveTeam() <= 1){
            for (int i = 0; i < playerSoloSurvivals.size(); i++){
                PlayerSurvival playerSoloSurvival = playerSoloSurvivals.get(i);
                playerSoloSurvival.setDamageReceived(calculateDamageReceived(playerSoloSurvival));
            }
            end = true;
        }
    }
    public void addEntity(Entity entity){
        entities.add(entity);
    }

    public void addTile(Tile tile){
        tiles.add(tile);
    }
    public int calculateDamageReceived(PlayerSurvival player){
        if (searchMario(player.getUsername()) != null) {
            return 100 - searchMario(player.getUsername()).getPaintMario().getLife();
        }else {
            return 100;
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
    public Spectator searchSpectator(String username){
        for (int i = 0; i < entities.size(); i++){
            if(entities.get(i).getId().equals(Id.SPECTATOR)){
                Spectator spectator = (Spectator) entities.get(i);
                if(spectator.getSpectator().getUsername().equals(username)){
                    return spectator;
                }
            }
        }
        return null;
    }
    public int numberOfAliveTeam(){
        int number = 0;
        for (int i = 0; i < marioTeams.size(); i++){
            if(isAliveTeam(marioTeams.get(i))){
                number++;
            }
        }
        return number;
    }
    public boolean isAliveTeam(MarioTeam marioTeam){
        LinkedList<PlayerSurvival> soloSurvivals = marioTeam.getPlayerSoloSurvivals();
        for (int i = 0; i < soloSurvivals.size(); i++){
            if(soloSurvivals.get(i).isAlive()){
                return true;
            }
        }
        return false;
    }
    public LinkedList<Item> searchItem(String username){
        for(int i =0 ; i < playerSoloSurvivals.size(); i++){
            if(playerSoloSurvivals.get(i).getUsername().equals(username)){
                return playerSoloSurvivals.get(i).getAllItems();
            }
        }
        return null;
    }

    public void UpKey(String username){
        Mario mario = searchMario(username);
        Spectator spectator = searchSpectator(username);
        if (mario == null && spectator == null){
            return;
        }
        if (mario != null) {
            if (!mario.isJumping()) {
                mario.setJumping(true);
                mario.setGravity(10.0);
            }
        }else if (spectator != null){
            spectator.setVelY(-6);
        }
    }
    public void releasedUpKey(String username){
        Mario mario = searchMario(username);
        Spectator spectator = searchSpectator(username);
        if (mario == null && spectator == null){
            return;
        }
        if (mario != null){
            mario.setVelY(0);
        } else if (spectator != null) {
            spectator.setVelY(0);
        }
    }
    public void DownKey(String username){
        Mario mario = searchMario(username);
        Spectator spectator = searchSpectator(username);
        if(mario == null && spectator == null){
            return;
        }
        if (mario != null){

        } else if (spectator != null) {
            spectator.setVelY(6);
        }
    }

    public void releasedDownKey(String username){
        Mario mario = searchMario(username);
        Spectator spectator = searchSpectator(username);
        if (mario == null && spectator == null){
            return;
        }
        if (mario != null) {
            mario.setVelY(0);
        } else if (spectator != null) {
            spectator.setVelY(0);
        }
    }
    public void releasedRightKey(String username){
        Mario mario = searchMario(username);
        Spectator spectator = searchSpectator(username);
        if (mario == null && spectator == null){
            return;
        }
        if (mario != null) {
            mario.setVelX(0);
        } else if (spectator != null) {
            spectator.setVelX(0);
        }
    }
    public void releasedLeftKey(String username){
        Mario mario = searchMario(username);
        Spectator spectator = searchSpectator(username);
        if (mario == null && spectator == null){
            return;
        }
        if (mario != null) {
            mario.setVelX(0);
        } else if (spectator != null) {
            spectator.setVelX(0);
        }
    }

    public void LeftKey(String username){
        Mario mario = searchMario(username);
        Spectator spectator = searchSpectator(username);
        if (mario == null && spectator == null){
            return;
        }
        if (mario != null) {
            if (mario.isSpeed() && mario.isSpeedBomb()) {
                mario.setVelX(-2 * speedPotionMultiplier * speedBombMultiplier);
            } else if (mario.isSpeed() && !mario.isSpeedBomb()) {
                mario.setVelX(-2 * speedPotionMultiplier);
            } else if (!mario.isSpeed() && mario.isSpeedBomb()) {
                mario.setVelX(-2 * speedBombMultiplier);
            } else {
                mario.setVelX(-2);
            }
            mario.getPaintMario().setFacing(0);
        } else if (spectator != null) {
            spectator.setVelX(-8);
            spectator.getSpectator().setFacing(0);
        }
    }

    public void RightKey(String username){
        Mario mario = searchMario(username);
        Spectator spectator = searchSpectator(username);
        if (mario == null && spectator == null){
            return;
        }
        if (mario != null) {
            if (mario.isSpeed() && mario.isSpeedBomb()) {
                mario.setVelX(2 * speedPotionMultiplier * speedBombMultiplier);
            } else if (mario.isSpeed() && !mario.isSpeedBomb()) {
                mario.setVelX(2 * speedPotionMultiplier);
            } else if (!mario.isSpeed() && mario.isSpeedBomb()) {
                mario.setVelX(2 * speedBombMultiplier);
            } else {
                mario.setVelX(2);
            }
            mario.getPaintMario().setFacing(1);
        } else if (spectator != null) {
            spectator.setVelX(8);
            spectator.getSpectator().setFacing(1);
        }
    }

    public void itemKey(String username , int key){
        Mario mario = searchMario(username);
        if (mario == null){
            return;
        }
        if (key <= mario.getPlayer().getAllItems().size()) {
            useItem(mario.getPlayer().getAllItems().get(key - 1), mario);
        }
    }
    public void deleteItem(Item item , Mario mario){
        LinkedList<Item> items = mario.getPlayer().getAllItems();
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getItemType().equals(item.getItemType())){
                mario.getPlayer().setCountItems(mario.getPlayer().getCountItems() + 1);
                items.remove(items.get(i));
                setDateType("items");
                setSenderName(mario.getPaintMario().getUsername());
                setSendData(true);
                return;
            }
        }
    }
    public void useItem(Item item , Mario mario){
        if(item == null){
            return;
        }else {
            if(item.getItemType().equals(Item.ItemType.INVISIBLE)){
                invisible(item , mario);
            } else if (item.getItemType().equals(Item.ItemType.SPEED)) {
                speed(item , mario);
            } else if (item.getItemType().equals(Item.ItemType.HEAL)) {
                deleteItem(item , mario);
                mario.getPaintMario().setLife(Math.min(mario.getPaintMario().getLife() + healthPotionHPPercent , 100));
            } else if (item.getItemType().equals(Item.ItemType.EXPLOSIVE_BOMB)) {
                explosiveBomb(item , mario);
            } else if (item.getItemType().equals(Item.ItemType.SPEED_BOMB)) {
                deleteItem(item , mario);
                addEntity(new SpeedBomb(mario.getX() + 64 , mario.getY() , 48 , 48 , Id.SPEED_BOMB , this , mario.getPaintMario().getUsername() , way(mario)));
            } else if (item.getItemType().equals(Item.ItemType.HAMMER)) {
                deleteItem(item , mario);
                addEntity(new Hammer(mario.getX() + 64 , mario.getY() , 48 , 48 , Id.HAMMER , this , mario.getPaintMario().getUsername() , way(mario)));
            } else if (item.getItemType().equals(Item.ItemType.SWORD)) {
                deleteItem(item , mario);
                addEntity(new Sword(0 , 0 , 6 , 72 , Id.SWORD , this , mario));
            }
        }
    }
    public void explosiveBomb(Item item , Mario mario){
        deleteItem(item , mario);
        int x;
        if (mario.getX() <= (length-1)*32){
            x = (length)*64 - 96;
        }else {
            x = 64;
        }
        addEntity(new ExplosiveBomb(x , 0 , 48 , 48 , Id.EXPLOSIVE_BOMB , this , mario));
    }
    public void invisible(Item item , Mario mario){
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
    public void speed(Item item , Mario mario){
        deleteItem(item , mario);
        mario.setSpeed(true);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mario.setSpeed(false);
            }
        };
        timer.schedule(timerTask , speedPotionPeriod*1000);
    }
    public boolean way(Mario mario){
        if (mario.getPaintMario().getFacing() == 1){
            return true;
        }
        return false;
    }
    public int getDeathY() {
        for(int i = 0 ; i < tiles.size(); i++){
            if(tiles.get(i).getY() > deathY){
                deathY = (int) tiles.get(i).getY();
            }
        }
        return deathY;
    }

    public LinkedList<PlayerSurvival> getPlayerSoloSurvivals() {
        return playerSoloSurvivals;
    }

    public void setPlayerSoloSurvivals(LinkedList<PlayerSurvival> playerSoloSurvivals) {
        this.playerSoloSurvivals = playerSoloSurvivals;
    }

    public double getSpeedPotionMultiplier() {
        return speedPotionMultiplier;
    }

    public void setSpeedPotionMultiplier(double speedPotionMultiplier) {
        this.speedPotionMultiplier = speedPotionMultiplier;
    }

    public int getSpeedPotionPeriod() {
        return speedPotionPeriod;
    }

    public void setSpeedPotionPeriod(int speedPotionPeriod) {
        this.speedPotionPeriod = speedPotionPeriod;
    }

    public int getInvisibilityPotionPeriod() {
        return invisibilityPotionPeriod;
    }

    public void setInvisibilityPotionPeriod(int invisibilityPotionPeriod) {
        this.invisibilityPotionPeriod = invisibilityPotionPeriod;
    }

    public int getHealthPotionHPPercent() {
        return healthPotionHPPercent;
    }

    public void setHealthPotionHPPercent(int healthPotionHPPercent) {
        this.healthPotionHPPercent = healthPotionHPPercent;
    }

    public double getDamageBombBlockArea() {
        return damageBombBlockArea;
    }

    public void setDamageBombBlockArea(double damageBombBlockArea) {
        this.damageBombBlockArea = damageBombBlockArea;
    }

    public int getDamageBombPercent() {
        return damageBombPercent;
    }

    public void setDamageBombPercent(int damageBombPercent) {
        this.damageBombPercent = damageBombPercent;
    }

    public double getSpeedBombBlockArea() {
        return speedBombBlockArea;
    }

    public void setSpeedBombBlockArea(double speedBombBlockArea) {
        this.speedBombBlockArea = speedBombBlockArea;
    }

    public double getSpeedBombMultiplier() {
        return speedBombMultiplier;
    }

    public void setSpeedBombMultiplier(double speedBombMultiplier) {
        this.speedBombMultiplier = speedBombMultiplier;
    }

    public int getHammerStunPeriod() {
        return hammerStunPeriod;
    }

    public void setHammerStunPeriod(int hammerStunPeriod) {
        this.hammerStunPeriod = hammerStunPeriod;
    }

    public double getSwordBlockRange() {
        return swordBlockRange;
    }

    public void setSwordBlockRange(double swordBlockRange) {
        this.swordBlockRange = swordBlockRange;
    }

    public int getSwordPercentDamage() {
        return swordPercentDamage;
    }

    public void setSwordPercentDamage(int swordPercentDamage) {
        this.swordPercentDamage = swordPercentDamage;
    }

    public double getSwordPushBackBlock() {
        return swordPushBackBlock;
    }

    public void setSwordPushBackBlock(double swordPushBackBlock) {
        this.swordPushBackBlock = swordPushBackBlock;
    }

    public int getSwordCoolDown() {
        return swordCoolDown;
    }

    public void setSwordCoolDown(int swordCoolDown) {
        this.swordCoolDown = swordCoolDown;
    }

    public double getSurvivalDamageMultiplier() {
        return survivalDamageMultiplier;
    }

    public void setSurvivalDamageMultiplier(double survivalDamageMultiplier) {
        this.survivalDamageMultiplier = survivalDamageMultiplier;
    }

    public double getSurvivalEquipmentMultiplier() {
        return survivalEquipmentMultiplier;
    }

    public void setSurvivalEquipmentMultiplier(double survivalEquipmentMultiplier) {
        this.survivalEquipmentMultiplier = survivalEquipmentMultiplier;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public boolean isSendData() {
        return sendData;
    }

    public void setSendData(boolean sendData) {
        this.sendData = sendData;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public LinkedList<PaintEntity> getPaintEntities() {
        return paintEntities;
    }

    public void setPaintEntities(LinkedList<PaintEntity> paintEntities) {
        this.paintEntities = paintEntities;
    }
    public LinkedList<PaintTile> getPaintTiles() {
        return paintTiles;
    }

    public void setPaintTiles(LinkedList<PaintTile> paintTiles) {
        this.paintTiles = paintTiles;
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

    public int getLength() {
        return length;
    }
    public PaintReaction[] getReactions() {
        return reactions;
    }

    public void setReactions(PaintReaction[] reactions) {
        this.reactions = reactions;
    }

}
