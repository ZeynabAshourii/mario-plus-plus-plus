package org.example.team_game.controller;

import org.example.config_loader.*;
import org.example.mario_survival.model.entities.*;
import org.example.order.Order;
import org.example.team_game.model.PlayerTeamGame;
import org.example.team_game.model.entity.Entity;
import org.example.team_game.model.entity.FireBall;
import org.example.team_game.model.entity.Player;
import org.example.team_game.model.entity.enemies.Goomba;
import org.example.team_game.model.entity.enemies.Koopa;
import org.example.team_game.model.entity.enemies.Spiny;
import org.example.team_game.model.entity.items.*;
import org.example.team_game.model.enums.Id;
import org.example.team_game.model.enums.PlayerState;
import org.example.team_game.model.tile.Flag;
import org.example.team_game.model.tile.Tile;
import org.example.team_game.model.tile.blocks.*;
import org.example.team_game.model.tile.pipes.PiranhaTelePipe;
import org.example.team_game.model.tile.pipes.PiranhaTrapPipe;
import org.example.team_game.model.tile.pipes.SimplePipe;
import org.example.team_game.model.tile.pipes.TeleSimplePipe;
import org.example.team_game.send_object.paint_entity.PaintEntity;
import org.example.team_game.send_object.paint_tile.PaintTile;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class HandlerTeamGame{
    private double deathY = 0;
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
    private LinkedList<Entity> entities = new LinkedList<>();
    private LinkedList<Tile> tiles = new LinkedList<>();
    private LinkedList<PaintEntity> paintEntities = new LinkedList<>();
    private LinkedList<PaintTile> paintTiles = new LinkedList<>();
    private LinkedList<Section> sections = new LinkedList<>();
    private LinkedList<PlayerTeamGame> players = new LinkedList<>();
    private String senderName;
    private String typeOfData;
    private boolean sendData = false;
    private boolean end = false;

    public HandlerTeamGame(LinkedList<Section> sections , LinkedList<PlayerTeamGame> players , double speedPotionMultiplier,
                           int speedPotionPeriod, int invisibilityPotionPeriod, int healthPotionHPPercent,
                           double damageBombBlockArea, int damageBombPercent, double speedBombBlockArea,
                           double speedBombMultiplier, int hammerStunPeriod, double swordBlockRange, int swordPercentDamage,
                           double swordPushBackBlock, int swordCoolDown){
        this.sections = sections;
        this.players = players;
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

        createLevel();
    }

    public void update() {
        for(int i = 0; i < entities.size(); i++){
            Entity entity = entities.get(i);
            entity.update();
        }
        for(int i = 0; i < tiles.size(); i++){
            Tile tile = tiles.get(i);
            tile.update();
        }
        if (end()){
            end = true;
        }
    }
    public boolean end(){
        for (int i = 0; i < players.size(); i++){
            if (!players.get(i).isEnd()){
                return false;
            }
        }
        return true;
    }
    public void createLevel(){
        for(int i = 0; i < sections.size(); i++){
            Section section = sections.get(i);
            loadEnemies(section.enemies , 0 , -1 , i);
            loadBlocks(section.blocks , 0 , 0 , i);
            loadPipes(section.pipes , 0 , 0 , i);
            int length = section.length;
            for(int j = -1; j <= length+1; j++){
                addTile(new EmptyBlock((j)*64 , 0 , 64 , 64 , Id.EMPTY_BLOCK , this , i));
            }
            addTile(new Flag((length+1) * 64, (-3) * 64, 64, 48, Id.FLAG, this , i));
        }
        loadPlayers();
    }
    public LinkedList<PaintEntity> paintEntitiesOfMario(String username){
        LinkedList<PaintEntity> paintEntityLinkedList = new LinkedList<>();
        Player player = player(username);
        if (player != null) {
            int section = player.getSection();
            for (int i = 0; i < paintEntities.size(); i++) {
                if (paintEntities.get(i).getSection() == section) {
                    paintEntityLinkedList.add(paintEntities.get(i));
                }
            }
        }
        return paintEntityLinkedList;
    }
    public LinkedList<PaintTile> paintTiles(String username){
        LinkedList<PaintTile> paintTilesLinkedList = new LinkedList<>();
        Player player = player(username);
        if (player != null) {
            int section = player.getSection();
            for (int i = 0; i < paintTiles.size(); i++) {
                if (paintTiles.get(i).getSection() == section) {
                    paintTilesLinkedList.add(paintTiles.get(i));
                }
            }
        }
        return paintTilesLinkedList;
    }

    public void loadPlayers(){
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getMarioState() == 0) {
                addEntity(new Player(64, -2 * 64, 64, 64, Id.PLAYER, this, PlayerState.MINI , players.get(i) , 0 ));
            } else if (players.get(i).getMarioState() == 1) {
                addEntity(new Player(64, -2 * 64, 64, 128, Id.PLAYER, this, PlayerState.MEGA , players.get(i) , 0 ));
            } else if (players.get(i).getMarioState() == 2) {
                addEntity(new Player(64, -2 * 64, 64, 128, Id.PLAYER, this, PlayerState.FIRE , players.get(i) , 0 ));
            }
        }
    }
    public void loadEnemies(LinkedList<Enemy> enemies , int x , int y , int section){
        for(int i = 0; i < enemies.size() ; i++){
            Enemy enemy = enemies.get(i);
            if(enemy.type.equals("GOOMBA")){
                addEntity(new Goomba((x + enemy.x)*64 , (y - enemy.y )*(64) , 64 , 64 , Id.GOOMBA , this , section));
            } else if (enemy.type.equals("KOOPA")) {
                addEntity(new Koopa((x + enemy.x)*64 , (y - enemy.y )*(64) , 64 , 64 , Id.KOOPA , this , section));
            } else if (enemy.type.equals("SPINY")) {
                addEntity(new Spiny((x + enemy.x)*64 , (y - enemy.y )*(64) , 64 , 64 , Id.SPINY, this , section));
            }
        }
    }
    public void loadBlocks(LinkedList<Block> blocks , int x , int y , int section){

        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if (block.type.equals("SIMPLE")) {
                addTile(new SimpleBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.SIMPLE_BLOCK, this , section));
            } else if (block.type.equals("COIN")) {
                addTile(new OneCoinBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.ONE_COIN_BLOCK, this , section));
            } else if (block.type.equals("COINS")) {
                addTile(new MultiCoinsBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.MULTI_COINS_BLOCK, this , section));
            } else if (block.type.equals("EMPTY")) {
                addTile(new EmptyBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.EMPTY_BLOCK, this , section));
            } else if (block.type.equals("QUESTION")) {
                Item item = null;
                if (block.item == null) {
                    int random = new Random().nextInt(10);
                    if(random == 0){
                        item = new Coin((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.COIN, this , section);
                    } else if (random == 1 || random == 2) {
                        item = new MagicStar((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.MAGIC_STAR, this , section);
                    } else if (random == 3 || random == 4 || random == 5) {
                        item = new MagicMushroom((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.MAGIC_MUSHROOM, this , section);
                    } else if (random == 6 || random == 7 || random == 8 || random == 9) {
                        item = new MagicFlower((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.MAGIC_FLOWER, this , section);
                    }
                }
                else if (block.item.equals("COIN")||block.item.equals("COINS")) {
                    item = new Coin((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.COIN, this , section);
                } else if (block.item.equals("STAR")) {
                    item = new MagicStar((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.MAGIC_STAR, this , section);
                } else if (block.item.equals("MUSHROOM")) {
                    item = new MagicMushroom((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.MAGIC_MUSHROOM, this , section);
                } else if (block.item.equals("FLOWER")) {
                    item = new MagicFlower((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.MAGIC_FLOWER, this , section);
                }
                addTile(new QuestionBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.QUESTION_BLOCK, this, item , section));
            }
        }
    }

    public void loadPipes(LinkedList<Pipe> pipes , int x , int y , int section){
        Pipe hideSectionPipe = null;
        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            if(pipe.section != null) {
                hideSectionPipe = pipe;
                break;
            }
        }
        if(hideSectionPipe == null){
            for(int i = 0; i < pipes.size(); i++){
                Pipe pipe = pipes.get(i);
                if(pipe.type.equals("SIMPLE")){
                    addTile(new SimplePipe((x+pipe.x)*64 , (y-pipe.y-1)*64, 64 , 64*3 , Id.SIMPLE_PIPE , this , section ));
                } else if (pipe.type.equals("TELE_SIMPLE")) {
                    addTile(new TeleSimplePipe((x+pipe.x)*64 , (y-pipe.y-1)*64 , 64 , 64*3 , Id.TELE_SIMPLE_PIPE , this , 2 , section));
                } else if (pipe.type.equals("PIRANHA_TRAP")) {
                    addTile(new PiranhaTrapPipe((x+pipe.x)*64 , (y-pipe.y-1)*64 , 64 , 64*3 , Id.PIRHANA_TRAP , this , section));
                }else if (pipe.type.equals("TELE_PIRANHA")) {
                    addTile(new PiranhaTelePipe((x+pipe.x)*64 , (y-pipe.y-1)*64 , 64 , 64*3 , Id.PIRHANA_TELE , this , 2 , section));
                }
            }
        }
        else {
            loadHideSection(hideSectionPipe , x , y , section);
        }
    }
    public void loadHideSection(Pipe hideSectionPipe , int x , int y , int section){
        if (hideSectionPipe.type.equals("SIMPLE") || hideSectionPipe.type.equals("TELE_SIMPLE")) {
            addTile(new TeleSimplePipe((x+hideSectionPipe.x) * 64, (y-hideSectionPipe.y-1)*64, 64, 64 * 3, Id.TELE_SIMPLE_PIPE, this, 2 , section));
        } else if (hideSectionPipe.type.equals("PIRANHA_TRAP") || hideSectionPipe.type.equals("TELE_PIRANHA")) {
            addTile(new PiranhaTelePipe((x+hideSectionPipe.x) * 64, (y-hideSectionPipe.y-1)*64, 64, 64 * 3, Id.PIRHANA_TELE, this, 2 , section));
        }
        Section hideSection = hideSectionPipe.section;
        Pipe spawnPipe = hideSection.spawnPipe;
        if (spawnPipe.type.equals("SIMPLE") || spawnPipe.type.equals("TELE_SIMPLE")) {
            addTile(new TeleSimplePipe(spawnPipe.x * 64, (y-spawnPipe.y-1)*64, 64, 64 * 3, Id.TELE_SIMPLE_PIPE, this, 0 , section));
        } else if (spawnPipe.type.equals("PIRANHA_TRAP") || spawnPipe.type.equals("TELE_PIRANHA")) {
            addTile(new PiranhaTelePipe((x+spawnPipe.x) * 64, (y-spawnPipe.y-1)*64, 64, 64 * 3, Id.PIRHANA_TELE, this, 0 , section));
        }
        int distance = Math.abs(spawnPipe.x - hideSectionPipe.x) + 1;
        int left = (hideSection.length - distance)/2 - 3;

        loadEnemies(hideSection.enemies , left-8 , 6 , section);
        loadBlocks(hideSection.blocks , left , 7 , section);
        loadPipes(hideSection.pipes , left , 8 , section);
    }
    public double getDeathY() {
        for(int i =0 ; i < tiles.size(); i++){
            if(tiles.get(i).getY() > deathY){
                deathY = tiles.get(i).getY();
            }
        }
        return deathY;
    }
    public void addEntity(Entity entity){
        entities.add(entity);
    }
    public void removeEntity(Entity entity){
        entities.remove(entity);
    }
    public void addTile(Tile tile){
        tiles.add(tile);
    }
    public void removeTile(Tile tile){
        tiles.remove(tile);
    }
    public void addPaintEntity(PaintEntity paintEntity){
        paintEntities.add(paintEntity);
    }
    public void addPaintTile(PaintTile paintTile){
        paintTiles.add(paintTile);
    }
    public void removePaintEntity(PaintEntity paintEntity){
        paintEntities.remove(paintEntity);
    }
    public void removePaintTiles(PaintTile paintTile){
        paintTiles.remove(paintTile);
    }

    public void wKey(String username){
        Player player = player(username);
        if (player == null){
            return;
        }  else if (player.isGoingDownPipe()) {
            return;
        }
        if (!player.isJumping() && !player.isSitting()) {
            player.setJumping(true);
            player.setGravity(10.0);
        }
    }
    public void sKey(String username){
        Player player = player(username);
        if (player == null){
            return;
        }  else if (player.isGoingDownPipe()) {
            return;
        }
        for (int j = 0; j < tiles.size(); j++) {
            Tile tile = tiles.get(j);
            if (tile.getId() == Id.TELE_SIMPLE_PIPE) {
                TeleSimplePipe teleSimplePipe = (TeleSimplePipe) tile;
                if (teleSimplePipe.getFacing() == 2 && player.getBoundsBottom().intersects(tile.getBounds())) {
                    if (!player.isGoingDownPipe()) {
                        player.setGoingDownPipe(true);
                    }
                }
            }
            if (tile.getId() == Id.PIRHANA_TELE) {
                PiranhaTelePipe piranhaTelePipe = (PiranhaTelePipe) tile;
                if (piranhaTelePipe.getFacing() == 2 && player.getBoundsBottom().intersects(tile.getBounds())) {
                    if (!player.isGoingDownPipe()) {
                        player.setGoingDownPipe(true);
                    }
                }
            }
            if (player.getPlayerState() != PlayerState.MINI && !player.isJumping()) {
                if (!player.isSitting()) {
                    player.setY(player.getY() + 64);
                    player.setSitting(true);
                }
                player.setHeight(64);
            }
        }
    }
    public void aKey(String username){
        Player player = player(username);
        if (player == null){
            return;
        }  else if (player.isGoingDownPipe()) {
            return;
        }
        player.setVelX(-5);
        player.setFacing(0);
        player.getPaintPlayer().setFacing(0);
    }
    public void dKey(String username){
        Player player = player(username);
        if (player == null){
            return;
        } else if (player.isGoingDownPipe()) {
            return;
        }
        player.setVelX(5);
        player.setFacing(1);
        player.getPaintPlayer().setFacing(1);

    }
    public void enterKey(String username){
        Player player = player(username);
        if (player == null){
            return;
        }  else if (player.isGoingDownPipe()) {
            return;
        }
        if (player.getPlayerState() == PlayerState.FIRE) {
            if(System.nanoTime() / 1000000000L - player.getStartTimeFireBall() >= 2 ) {
                switch (player.getFacing()) {
                    case 0:
                        player.setStartTimeFireBall(System.nanoTime() / 1000000000L);
                        addEntity(new FireBall(player.getX() - 24, player.getY() + 16, 24, 24, Id.FIRE_BALL, this, player.getFacing() , player.getSection()));
                        break;
                    case 1:
                        player.setStartTimeFireBall(System.nanoTime() / 1000000000L);
                        addEntity(new FireBall(player.getX() + player.getWidth(), player.getY() + 16, 24, 24, Id.FIRE_BALL, this, player.getFacing() , player.getSection()));
                        break;
                }
            }
        }
    }
    public Player player(String username){
        for(int i = 0; i < entities.size(); i++){
            Entity entity = entities.get(i);
            if(entity.getId() == Id.PLAYER){
                if (((Player)entity).getPlayerTeamGame().getUsername().equals(username)) {
                    return ((Player) entity);
                }
            }
        }
        return null;
    }
    public void releasedUpKey(String username){
        Player player = player(username);
        if (player == null){
            return;
        }  else if (player.isGoingDownPipe()) {
            return;
        }
        player.setVelY(0);
    }
    public void releasedDownKey(String username){
        Player player = player(username);
        if (player == null){
            return;
        }  else if (player.isGoingDownPipe()) {
            return;
        }
        player.setVelY(0);
        if (player.getPlayerState() != PlayerState.MINI && !player.isJumping()) {
            player.setY(player.getY() - 64);
            player.setHeight(128);
            player.setSitting(false);
        }
    }
    public void releasedRightKey(String username){
        Player player = player(username);
        if (player == null){
            return;
        }  else if (player.isGoingDownPipe()) {
            return;
        }
        player.setVelX(0);
    }
    public void releasedLeftKey(String username){
        Player player = player(username);
        if (player == null){
            return;
        }  else if (player.isGoingDownPipe()) {
            return;
        }
        player.setVelX(0);
    }
    public LinkedList<org.example.server.model.Item> searchItem(String username){
        for(int i =0 ; i < players.size(); i++){
            if(players.get(i).getUsername().equals(username)){
                return players.get(i).getAllItems();
            }
        }
        return null;
    }
    public Order info(String username){
        for(int i =0 ; i < players.size(); i++){
            if(players.get(i).getUsername().equals(username)){
                return new Order(Order.OrderType.TEAM_GAME , players.get(i).getCoin() , players.get(i).getScore() , players.get(i).getLive() , players.get(i).getSection());
            }
        }
        return null;
    }
    public void itemKey(String username , int key){
        Player player = player(username);
        if (player == null){
            return;
        }
        if (key <= player.getPlayerTeamGame().getAllItems().size()) {
            useItem(player.getPlayerTeamGame().getAllItems().get(key - 1), player);
        }
    }
    public void deleteItem(org.example.server.model.Item item , Player player){
        LinkedList<org.example.server.model.Item> items = player.getPlayerTeamGame().getAllItems();
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getItemType().equals(item.getItemType())){
                items.remove(items.get(i));
                setTypeOfData("items");
                setSenderName(player.getPaintPlayer().getUsername());
                setSendData(true);
                return;
            }
        }
    }
    public void useItem(org.example.server.model.Item item , Player player){
        if(item == null){
            return;
        }else {
            if(item.getItemType().equals(org.example.server.model.Item.ItemType.INVISIBLE)){
                invisible(item , player);
            } else if (item.getItemType().equals(org.example.server.model.Item.ItemType.SPEED)) {
                speed(item , player);
            } else if (item.getItemType().equals(org.example.server.model.Item.ItemType.HEAL)) {
                deleteItem(item , player);
            } else if (item.getItemType().equals(org.example.server.model.Item.ItemType.EXPLOSIVE_BOMB)) {
                explosiveBomb(item , player);
            } else if (item.getItemType().equals(org.example.server.model.Item.ItemType.SPEED_BOMB)) {
                deleteItem(item , player);
                //check
        //        addEntity(new SpeedBomb(player.getX() + 64 , player.getY() , 48 , 48 , org.example.marathon_mario.model.enums.Id.SPEED_BOMB , this , player.getPaintPlayer().getUsername() , way(player)));
            } else if (item.getItemType().equals(org.example.server.model.Item.ItemType.HAMMER)) {
                deleteItem(item , player);
                //check
       //         addEntity(new Hammer(player.getX() + 64 , player.getY() , 48 , 48 , org.example.marathon_mario.model.enums.Id.HAMMER , this , mario.getPaintMario().getUsername() , way(mario)));
            } else if (item.getItemType().equals(org.example.server.model.Item.ItemType.SWORD)) {
                //check
        //        deleteItem(item , mario);
        //        addEntity(new Sword(0 , 0 , 6 , 72 , org.example.marathon_mario.model.enums.Id.SWORD , this , mario));
            }
        }
    }
    public void explosiveBomb(org.example.server.model.Item item , Player player){
        deleteItem(item , player);
        int x;
        if (player.getX() <= (15)*32){
            x = (15)*64 - 96;
        }else {
            x = 64;
        }
        //check
   //     addEntity(new ExplosiveBomb(x , 0 , 48 , 48 , org.example.marathon_mario.model.enums.Id.EXPLOSIVE_BOMB , this , mario));
    }
    public void invisible(org.example.server.model.Item item , Player player){
        deleteItem(item , player);
        player.getPaintPlayer().setInvisible(true);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                player.getPaintPlayer().setInvisible(false);
            }
        };
        timer.schedule(timerTask , invisibilityPotionPeriod*1000);
    }
    public void speed(org.example.server.model.Item item , Player player){
        deleteItem(item , player);
        player.setSpeed(true);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                player.setSpeed(false);
            }
        };
        timer.schedule(timerTask , speedPotionPeriod*1000);
    }
    public boolean way(Player player){
        if (player.getFacing() == 1){
            return true;
        }
        return false;
    }

    public LinkedList<Tile> getTiles() {
        return tiles;
    }

    public LinkedList<Entity> getEntities() {
        return entities;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getTypeOfData() {
        return typeOfData;
    }

    public void setTypeOfData(String typeOfData) {
        this.typeOfData = typeOfData;
    }

    public boolean isSendData() {
        return sendData;
    }

    public void setSendData(boolean sendData) {
        this.sendData = sendData;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
