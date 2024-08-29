package org.example.offline_game.controller;

import org.example.offline_game.model.entity.enemies.Bowser;
import org.example.offline_game.model.entity.enemies.Goomba;
import org.example.offline_game.model.entity.enemies.Koopa;
import org.example.offline_game.model.entity.enemies.Spiny;
import org.example.offline_game.model.entity.Entity;
import org.example.offline_game.model.entity.FireBall;
import org.example.offline_game.model.entity.items.*;
import org.example.offline_game.model.entity.Player;
import org.example.offline_game.model.enums.Id;
import org.example.offline_game.model.enums.PlayerState;
import org.example.offline_game.model.tile.blocks.*;
import org.example.offline_game.model.tile.Flag;
import org.example.offline_game.model.tile.Gate;
import org.example.offline_game.model.tile.pipes.PiranhaTelePipe;
import org.example.offline_game.model.tile.pipes.PiranhaTrapPipe;
import org.example.offline_game.model.tile.pipes.SimplePipe;
import org.example.offline_game.model.tile.pipes.TeleSimplePipe;
import org.example.offline_game.model.tile.Tile;
import org.example.offline_game.view.OfflineGame;
import org.example.offline_game.view.StopPanel;
import org.example.config_loader.*;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

public class HandlerOfflineGame implements Cloneable , Serializable {
    private int deathY = 0;
    private int length;
    private int marioState;
    private int numberOfCheckPoint;
    private long startTimeFireBall;
    private OfflineGame game;
    private Level currentLevel;
    private Section currentSection;
    private CheckPoint checkPoint;
    private LinkedList<Entity> entities = new LinkedList<>();
    private LinkedList<Tile> tiles = new LinkedList<>();
    private LinkedList<Level> levels;

    public HandlerOfflineGame(OfflineGame game , LinkedList<Level> levels , int marioState){
        this.game = game;
        this.levels = levels;
        this.marioState = marioState;
    }
    public void paint(Graphics g){
        if (entities != null) {
            for (int i = 0; i < entities.size(); i++) {
                Entity entity = entities.get(i);
                entity.paint(g);
            }
        }
        if (tiles != null) {
            for (int i = 0; i < tiles.size(); i++) {
                Tile tile = tiles.get(i);
                tile.paint(g);
            }
        }
    }
    public void update() {
        if (entities != null) {
            for (int i = 0; i < entities.size(); i++) {
                Entity entity = entities.get(i);
                entity.update();
            }
        }
        if (tiles != null) {
            for (int i = 0; i < tiles.size(); i++) {
                Tile tile = tiles.get(i);
                tile.update();
            }
        }
    }
    public void createLevel() {
        deathY = 0;
        numberOfCheckPoint = -1;
        currentLevel = levels.get(game.getLevel());
        loadSection(currentLevel.sections.get(game.getSection()));
        new CheckPoint(this);
    }
    public void clearLevel(){
        entities.clear();
        tiles.clear();
    }
    public void loadSection(Section section){
        currentSection = section;
        length = currentSection.length;
        int timeLimit = currentSection.time;
        game.setTimeLimit(timeLimit);

        loadEnemies(section.enemies , 0 , -1);
        loadBlocks(section.blocks , 0 , 0);
        loadPipes(section.pipes , 0 , 0);

        if(!game.isBossFight()) {
            addTile(new Flag((length+1) * 64, (-3) * 64, 64, 48, Id.flag, this));
        }
        addTile(new Gate((length)*32 , (-9)*64 ,64 , 64 , Id.gate , this));

        if(marioState == 0){
            addEntity(new Player(64 , -2*64 , 64 , 64 , Id.player , this , PlayerState.MINI , game.getClient()));
        } else if (marioState == 1) {
            addEntity(new Player(64 , -2*64 , 64 , 128 , Id.player , this , PlayerState.MEGA , game.getClient()));
        } else if (marioState == 2) {
            addEntity(new Player(64 , -2*64 , 64 , 128 , Id.player , this , PlayerState.FIRE , game.getClient()));
        }
    }
    public void loadEnemies(LinkedList<Enemy> enemies , int x , int y){
        for(int i = 0; i < enemies.size() ; i++){
            Enemy enemy = enemies.get(i);
            if(enemy.type.equals("GOOMBA")){
                addEntity(new Goomba((x + enemy.x)*64 , (y - enemy.y )*(64) , 64 , 64 , Id.goomba , this));
            } else if (enemy.type.equals("KOOPA")) {
                addEntity(new Koopa((x + enemy.x)*64 , (y - enemy.y )*(64) , 64 , 64 , Id.koopa , this));
            } else if (enemy.type.equals("SPINY")) {
                addEntity(new Spiny((x + enemy.x)*64 , (y - enemy.y )*(64) , 64 , 64 , Id.spiny, this));
            } else if (enemy.type.equals("BOWSER")) {
                game.setBossFight(true);
                addEntity(new Bowser((x + enemy.x)*64 , (y - enemy.y )*(64)-64*4 , 64 , 64*5 , Id.bowser , this));
            }
        }
    }
    public void loadBlocks(LinkedList<Block> blocks , int x , int y){
        loadNormalBlock(x , y);

        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if (block.type.equals("SIMPLE")) {
                addTile(new SimpleBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.simpleBlock, this));
            } else if (block.type.equals("COIN")) {
                addTile(new OneCoinBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.oneCoinBlock, this));
            } else if (block.type.equals("COINS")) {
                addTile(new MultiCoinsBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.multiCoinsBlock, this));
            } else if (block.type.equals("EMPTY")) {
                addTile(new EmptyBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.emptyBlock, this));
            } else if (block.type.equals("QUESTION")) {
                Item item = null;
                if (block.item == null) {
                    int random = new Random().nextInt(10);
                    if(random == 0){
                        item = new Coin((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.coin, this);
                    } else if (random == 1 || random == 2) {
                        item = new MagicStar((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.magicStar, this);
                    } else if (random == 3 || random == 4 || random == 5) {
                        item = new MagicMushroom((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.magicMushroom, this);
                    } else if (random == 6 || random == 7 || random == 8 || random == 9) {
                        item = new MagicFlower((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.magicFlower, this);
                    }
                }
                else if (block.item.equals("COIN")||block.item.equals("COINS")) {
                    item = new Coin((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.coin, this);
                } else if (block.item.equals("STAR")) {
                    item = new MagicStar((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.magicStar, this);
                } else if (block.item.equals("MUSHROOM")) {
                    item = new MagicMushroom((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.magicMushroom, this);
                } else if (block.item.equals("FLOWER")) {
                    item = new MagicFlower((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.magicFlower, this);
                }
                addTile(new QuestionBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.questionBlock, this, item));
            }
        }
    }
    public void loadNormalBlock(int x , int y){
        if(game.isBossFight()){
            for(int i = 0; i < length ; i++){
                addTile(new EmptyBlock((x-1)*64 , (y-1-i)*64 , 64 , 64 , Id.emptyBlock , this));
            }
            for(int i = 0 ; i < length; i++){
                addTile(new EmptyBlock(64*(length+1+x) , (y-1-i)*64 , 64 , 64 , Id.emptyBlock , this));
            }
        }

        for(int i = -1; i <= length+1; i++){
            addTile(new EmptyBlock((x+i)*64 , y*64 , 64 , 64 , Id.emptyBlock , this));
        }
    }
    public void loadPipes(LinkedList<Pipe> pipes , int x , int y){
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
                    addTile(new SimplePipe((x+pipe.x)*64 , (y-pipe.y-1)*64, 64 , 64*3 , Id.simplePipe , this ));
                } else if (pipe.type.equals("TELE_SIMPLE")) {
                    addTile(new TeleSimplePipe((x+pipe.x)*64 , (y-pipe.y-1)*64 , 64 , 64*3 , Id.teleSimplePipe , this , 2));
                } else if (pipe.type.equals("PIRANHA_TRAP")) {
                    addTile(new PiranhaTrapPipe((x+pipe.x)*64 , (y-pipe.y-1)*64 , 64 , 64*3 , Id.piranhaTrap , this));
                }else if (pipe.type.equals("TELE_PIRANHA")) {
                    addTile(new PiranhaTelePipe((x+pipe.x)*64 , (y-pipe.y-1)*64 , 64 , 64*3 , Id.piranhaTele , this , 2));
                }
            }
        }
        else {
            loadHideSection(hideSectionPipe , x , y);
        }
    }
    public void loadHideSection(Pipe hideSectionPipe , int x , int y){
        if (hideSectionPipe.type.equals("SIMPLE") || hideSectionPipe.type.equals("TELE_SIMPLE")) {
            addTile(new TeleSimplePipe((x+hideSectionPipe.x) * 64, (y-hideSectionPipe.y-1)*64, 64, 64 * 3, Id.teleSimplePipe, this, 2));
        } else if (hideSectionPipe.type.equals("PIRANHA_TRAP") || hideSectionPipe.type.equals("TELE_PIRANHA")) {
            addTile(new PiranhaTelePipe((x+hideSectionPipe.x) * 64, (y-hideSectionPipe.y-1)*64, 64, 64 * 3, Id.piranhaTele, this, 2));
        }
        Section hideSection = hideSectionPipe.section;
        Pipe spawnPipe = hideSection.spawnPipe;
        if (spawnPipe.type.equals("SIMPLE") || spawnPipe.type.equals("TELE_SIMPLE")) {
            addTile(new TeleSimplePipe(spawnPipe.x * 64, (y-spawnPipe.y-1)*64, 64, 64 * 3, Id.teleSimplePipe, this, 0));
        } else if (spawnPipe.type.equals("PIRANHA_TRAP") || spawnPipe.type.equals("TELE_PIRANHA")) {
            addTile(new PiranhaTelePipe((x+spawnPipe.x) * 64, (y-spawnPipe.y-1)*64, 64, 64 * 3, Id.piranhaTele, this, 0));
        }
        int distance = Math.abs(spawnPipe.x - hideSectionPipe.x) + 1;
        int left = (hideSection.length - distance)/2 - 3;

        loadEnemies(hideSection.enemies , left-8 , 6);
        loadBlocks(hideSection.blocks , left , 7);
        loadPipes(hideSection.pipes , left , 8);
    }
    public void backToCheckPoint(){
        clearLevel();
        for(int i = 0; i < checkPoint.getEntities().size(); i++){
            Entity entity = checkPoint.getEntities().get(i).clone();
            if(entity.getId() == Id.player){
                Player player = (Player) entity;
                player.setState(PlayerState.MINI);
                player.setHeight(64);
            }
            entities.add(entity);
        }
        for(int i = 0; i < checkPoint.getTiles().size(); i++){
            Tile tile = checkPoint.getTiles().get(i).clone();
            tiles.add(tile);
        }
        new CheckPoint(this);
    }
    @Override
    public HandlerOfflineGame clone() {
        try {
            HandlerOfflineGame clone = (HandlerOfflineGame) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    public int getDeathY() {
        for(int i =0 ; i < tiles.size(); i++){
            if(tiles.get(i).getY() > deathY){
                deathY = tiles.get(i).getY();
            }
        }
        return deathY;
    }
    public void setMarioState(){
        for(int i = 0; i < entities.size(); i++){
            if(entities.get(i).getId() == Id.player){
                Player player = (Player) entities.get(i);
                if(player.getState() == PlayerState.MINI){
                    setMarioState(0);
                } else if (player.getState() == PlayerState.MEGA) {
                    setMarioState(1);
                } else if (player.getState() == PlayerState.FIRE) {
                    setMarioState(2);
                }
            }
        }
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
    public void keyPressed(int key){
        if(shouldAction()) return;
        switch (key) {
            case KeyEvent.VK_W:
                player().setSwordW(true);
                if(!player().isSwordS()) {
                    if (!player().isGrabAttack() && !player().isJumpAttack()) {
                        wKey();
                    } else if (player().isJumpAttack()) {
                        dKey();
                    }
                }
                break;
            case KeyEvent.VK_S:
                player().setSwordS(true);
                if(!player().isSwordW()) {
                    if (!player().isGrabAttack() && !player().isJumpAttack()) {
                        sKey();
                    } else if (player().isJumpAttack()) {
                        aKey();
                    }
                }
                break;
            case KeyEvent.VK_D:
                if(!player().isGrabAttack() && !player().isJumpAttack()) {
                    dKey();
                } else if (player().isJumpAttack()) {
                    wKey();
                }
                break;
            case KeyEvent.VK_A:
                if(!player().isGrabAttack() && !player().isJumpAttack()) {
                    aKey();
                } else if (player().isJumpAttack()) {
                    sKey();
                }
                break;
            case KeyEvent.VK_ENTER:
                if(!player().isGrabAttack()) {
                    enterKey();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                getGame().setPaused(true);
                getGame().getClient().getClientFrame().setContentPane(new StopPanel(getGame()));
                break;

            case KeyEvent.VK_X:
                xKey();
                break;
        }
    }
    public void keyReleased(int key) {
        if (shouldAction()) return;
        switch (key) {
            case KeyEvent.VK_W:
                player().setSwordW(false);
                if(!player().isGrabAttack() && !player().isJumpAttack()) {
                    player().setVelY(0);
                } else if (player().isJumpAttack()) {
                    player().setVelX(0);
                }
                break;
            case KeyEvent.VK_S:
                player().setSwordS(false);
                if(!player().isGrabAttack() && !player().isJumpAttack()) {
                    player().setVelY(0);
                    if (player().getState() != PlayerState.MINI && !player().isJumping()) {
                        player().setY(player().getY() - 64);
                        player().setHeight(128);
                        player().setSitting(false);
                    }
                }else if (player().isJumpAttack()) {
                    player().setVelX(0);
                }
                break;
            case KeyEvent.VK_D:
                if(!player().isGrabAttack() && !player().isJumpAttack()) {
                    player().setVelX(0);
                }else if(player().isGrabAttack()) {
                    player().setNumberOfAD(player().getNumberOfAD()+1);
                } else if (player().isJumpAttack()) {
                    player().setVelY(0);
                }
                break;
            case KeyEvent.VK_A:
                if(!player().isGrabAttack() && !player().isJumpAttack()) {
                    player().setVelX(0);
                }else if (player().isGrabAttack()){
                    player().setNumberOfAD(player().getNumberOfAD()+1);
                } else if (player().isJumpAttack()) {
                    player().setVelY(0);
                    if (player().getState() != PlayerState.MINI && !player().isJumping()) {
                        player().setY(player().getY() - 64);
                        player().setHeight(128);
                        player().setSitting(false);
                    }
                }
                break;
        }

    }
    public void wKey(){
        if (!player().isJumping() && !player().isSitting()) {
            player().setJumping(true);
            player().setGravity(10.0);
        }
    }
    public void sKey(){
        for (int j = 0; j < tiles.size(); j++) {
            Tile tile = tiles.get(j);
            if (tile.getId() == Id.teleSimplePipe) {
                TeleSimplePipe teleSimplePipe = (TeleSimplePipe) tile;
                if (teleSimplePipe.getFacing() == 2 && player().getBoundsBottom().intersects(tile.getBounds())) {
                    if (!player().isGoingDownPipe()) {
                        player().setGoingDownPipe(true);
                    }
                }
            }
            if (tile.getId() == Id.piranhaTele) {
                PiranhaTelePipe piranhaTelePipe = (PiranhaTelePipe) tile;
                if (piranhaTelePipe.getFacing() == 2 && player().getBoundsBottom().intersects(tile.getBounds())) {
                    if (!player().isGoingDownPipe()) {
                        player().setGoingDownPipe(true);
                    }
                }
            }
            if (player().getState() != PlayerState.MINI && !player().isJumping()) {
                if (!player().isSitting()) {
                    player().setY(player().getY() + 64);
                    player().setSitting(true);
                }
                player().setHeight(64);
            }
        }
    }
    public void aKey(){
        player().setVelX(-5);
        player().setFacing(0);
    }
    public void dKey(){
        player().setVelX(5);
        player().setFacing(1);
    }
    public void xKey(){
        for(int j = 0; j < entities.size(); j++){
            if(entities.get(j).getId() == Id.bowser){
                Bowser bowser = (Bowser) entities.get(j);
                if(bowser.isCutScene()) {
                    bowser.setxKey(true);
                }
            }
        }
    }
    public void enterKey(){
        if (player().getState() == PlayerState.FIRE) {
            if(System.nanoTime() / 1000000000L - startTimeFireBall >= 2 ) {
                switch (player().getFacing()) {
                    case 0:
                        startTimeFireBall = System.nanoTime() / 1000000000L;
                        addEntity(new FireBall(player().getX() - 24, player().getY() + 16, 24, 24, Id.fireBall, this, player().getFacing()));
                        break;
                    case 1:
                        startTimeFireBall = System.nanoTime() / 1000000000L;
                        addEntity(new FireBall(player().getX() + player().getWidth(), player().getY() + 16, 24, 24, Id.fireBall, this, player().getFacing()));
                        break;
                }
            }
        }
    }
    public Player player(){
        Player player = null;
        for(int i = 0; i < entities.size(); i++){
            Entity entity = entities.get(i);
            if(entity.getId() == Id.player){
                player = (Player) entity;
            }
        }
        return player;
    }
    public boolean shouldAction(){
        if(player() == null) {
            return true;
        } else if (player().isGoingDownPipe() || player().isCutScene()) {
            return true;
        }
        return false;
    }
    public int getNumberOfCheckPoint() {
        return numberOfCheckPoint;
    }
    public void setNumberOfCheckPoint(int numberOfCheckPoint) {
        this.numberOfCheckPoint = numberOfCheckPoint;
    }
    public CheckPoint getCheckPoint() {
        return checkPoint;
    }
    public void setCheckPoint(CheckPoint checkPoint) {
        this.checkPoint = checkPoint;
    }
    public LinkedList<Entity> getEntities() {
        return entities;
    }

    public LinkedList<Tile> getTiles() {
        return tiles;
    }

    public OfflineGame getGame() {
        return game;
    }
    public Level getCurrentLevel() {
        return currentLevel;
    }
    public void setMarioState(int marioState) {
        this.marioState = marioState;
    }
    public int getLength() {
        return length;
    }

    public void setEntities(LinkedList<Entity> entities) {
        this.entities = entities;
    }

    public void setTiles(LinkedList<Tile> tiles) {
        this.tiles = tiles;
    }
}

