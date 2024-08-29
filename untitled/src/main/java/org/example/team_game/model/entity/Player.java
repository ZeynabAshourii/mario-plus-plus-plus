package org.example.team_game.model.entity;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.PlayerTeamGame;
import org.example.team_game.model.entity.enemies.Goomba;
import org.example.team_game.model.entity.enemies.Koopa;
import org.example.team_game.model.entity.enemies.Plant;
import org.example.team_game.model.entity.enemies.Spiny;
import org.example.team_game.model.entity.items.Coin;
import org.example.team_game.model.entity.items.MagicFlower;
import org.example.team_game.model.entity.items.MagicMushroom;
import org.example.team_game.model.entity.items.MagicStar;
import org.example.team_game.model.enums.Id;
import org.example.team_game.model.enums.PlayerState;
import org.example.team_game.model.tile.Flag;
import org.example.team_game.model.tile.Tile;
import org.example.team_game.model.tile.blocks.*;
import org.example.team_game.model.tile.pipes.PiranhaTelePipe;
import org.example.team_game.model.tile.pipes.TeleSimplePipe;
import org.example.team_game.send_object.paint_entity.PaintPlayer;

public class Player extends Entity{
    private PlayerState playerState;
    private int pixelsTravelled = 0;
    private long startTimeAntiShock = 0;
    private long startTimeFireBall;
    private double startTime = 0;
    private boolean antiShock = false;
    private boolean goingDownPipe = false;
    private boolean goingUpPipe = false;
    private PlayerTeamGame playerTeamGame;
    private PaintPlayer paintPlayer;
    private boolean speed = false;

    public Player(double x, double y, int width, int height, Id id, HandlerTeamGame handler, PlayerState playerState , PlayerTeamGame playerTeamGame , int section) {
        super(x, y, width, height, id, handler , section);
        this.playerState = playerState;
        this.playerTeamGame = playerTeamGame;
        paintPlayer = new PaintPlayer(x , y , width , height , id , section , playerState , playerTeamGame.getUsername());
        getHandler().addPaintEntity(paintPlayer);
    }

    @Override
    public void update() {
        if(System.nanoTime()/1000000000L - startTimeAntiShock >= 15 && antiShock){
            antiShock = false;
        }

        setX(getX() + getVelX());
        paintPlayer.setX(getX());
        setY(getY() + getVelY());
        paintPlayer.setY(getY());

        if(getY() > getHandler().getDeathY() + 64) fall();

        hitTiles();
        hitEntities();
        mangeGravity();
        mangeGoingDownPipe();
        manageGoingUpPipe();
        if (getVelX() != 0) {
            paintPlayer.manageFrame();
        }
    }
    public void fall(){
        playerTeamGame.setScore(playerTeamGame.getScore()-30);
        getHandler().setTypeOfData("GAME_INFO");
        getHandler().setSenderName(getPlayerTeamGame().getUsername());
        getHandler().setSendData(true);
        die();
        getHandler().removePaintEntity(paintPlayer);
    }
    public void hitTiles(){
        for(int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if(tile.getSection() == getSection()) {
                if (!isGoingDownPipe() && !isGoingUpPipe()) {
                    if (getBoundsTop().intersects(tile.getBounds())) {
                        hitTileFromTop(tile);
                    }
                    if (getBoundsBottom().intersects(tile.getBounds())) {
                        hitTileFromBottom(tile);
                    } else if (!isFalling() && !isJumping()) {
                        setGravity(0.8);
                        setFalling(true);
                    }
                    if (getBoundsRight().intersects(tile.getBounds())) {
                        setVelX(0);
                        setX(tile.getX() - getWidth());
                        paintPlayer.setX(getX());
                    }
                    if (getBoundsLeft().intersects(tile.getBounds())) {
                        setVelX(0);
                        setX(tile.getX() + getWidth());
                        paintPlayer.setX(getX());
                    }
                    if (getBounds().intersects(tile.getBounds())) {
                        if (tile.getId() == Id.FLAG) {
                            hitFlag((Flag) tile);
                        }
                    }
                }
            }
        }
    }
    public void hitTileFromTop(Tile tile){
        setVelY(0);
        if (isJumping()) {
            setJumping(false);
            setGravity(0.8);
            setFalling(true);
        }
        if(tile.getId() == Id.SIMPLE_BLOCK){
            hitSimpleBlock((SimpleBlock) tile);
        }
        else if(tile.getId() == Id.ONE_COIN_BLOCK){
            hitOneCoinBlock((OneCoinBlock) tile);
        } else if (tile.getId() == Id.MULTI_COINS_BLOCK) {
            hitMultiCoinsBlock((MultiCoinsBlock) tile);
        } else if (tile.getId() == Id.QUESTION_BLOCK) {
            hitQuestionBlock((QuestionBlock) tile);
        } else if (tile.getId() == Id.TELE_SIMPLE_PIPE) {
            TeleSimplePipe teleSimplePipe = (TeleSimplePipe) tile;
            if(teleSimplePipe.getFacing() == 0) {
                setGoingUpPipe(true);
            }
        }else if (tile.getId() == Id.PIRHANA_TELE) {
            PiranhaTelePipe piranhaTelePipe = (PiranhaTelePipe) tile;
            if(piranhaTelePipe.getFacing() == 0) {
                setGoingUpPipe(true);
            }
        }
    }
    public void hitTileFromBottom(Tile tile){
        setVelY(0);
        if (isFalling()) {
            setFalling(false);
        }
    }
    public void hitEntities(){
        for(int i = 0 ; i < getHandler().getEntities().size() ; i ++){
            Entity entity = getHandler().getEntities().get(i);
            if (entity.getSection() == getSection()) {
                if (getBounds().intersects(entity.getBounds()) && entity.getId() == Id.COIN) {
                    hitCoin((Coin) entity);
                } else if (getBounds().intersects(entity.getBounds()) && entity.getId() == Id.MAGIC_FLOWER) {
                    hitMagicFlower((MagicFlower) entity);
                } else if (getBounds().intersects(entity.getBounds()) && entity.getId() == Id.MAGIC_MUSHROOM) {
                    hitMushroom((MagicMushroom) entity);
                } else if (getBounds().intersects(entity.getBounds()) && entity.getId() == Id.MAGIC_STAR) {
                    hitStar((MagicStar) entity);
                } else if (entity.getId() == Id.GOOMBA) {
                    if (getBoundsBottom().intersects(entity.getBoundsTop())) {
                        entity.die();
                        getHandler().removePaintEntity(((Goomba) entity).getPaintGoomba());
                    } else if (getBounds().intersects(entity.getBounds())) {
                        hitPlayer(entity);
                    }
                } else if (entity.getId() == Id.KOOPA) {
                    if (getBoundsBottom().intersects(entity.getBoundsTop())) {
                        hitKoopa((Koopa) entity);
                    } else if (getBounds().intersects(entity.getBounds())) {
                        hitPlayer(entity);
                    }
                } else if (getBounds().intersects(entity.getBounds()) && entity.getId() == Id.SPINY) {
                    hitPlayer(entity);
                } else if (getBounds().intersects(entity.getBounds()) && entity.getId() == Id.PLANT) {
                    hitPlayer(entity);
                }
            }
        }
    }
    public void mangeGravity(){
        if(isJumping() && !isGoingDownPipe() && !isGoingUpPipe()){
            setGravity(getGravity() - 0.17);
            setVelY((int)-getGravity());
            if(getGravity() <= 0.5){
                setJumping(false);
                setFalling(true);
            }
        }
        if(isFalling() && !isGoingDownPipe() && !isGoingUpPipe()){
            setGravity(getGravity() + 0.17);
            setVelY((int)getGravity());
        }
    }
    public void manageGoingUpPipe(){
        if(isGoingUpPipe()){
            for(int i = 0; i < getHandler().getTiles().size(); i++){
                Tile tile = getHandler().getTiles().get(i);
                if (tile.getSection() == getSection()) {
                    if (tile.getId() == Id.TELE_SIMPLE_PIPE) {
                        if (getBounds().intersects(tile.getBounds())) upTeleSimplePipe((TeleSimplePipe) tile);
                    }
                    if (tile.getId() == Id.PIRHANA_TELE) {
                        if (getBounds().intersects(tile.getBounds())) upPiranhaTelePipe((PiranhaTelePipe) tile);
                    }
                }
            }
        }
    }
    public void upTeleSimplePipe(TeleSimplePipe teleSimplePipe){
        if(teleSimplePipe.getFacing() == 0){
            setVelY(-5);
            setVelX(0);
            pixelsTravelled += (-1)*getVelY();
        }
        if (pixelsTravelled >= teleSimplePipe.getHeight() + getHeight()) {
            setVelY(0);
            setGoingUpPipe(false);
            pixelsTravelled = 0;
        }
    }
    public void upPiranhaTelePipe(PiranhaTelePipe piranhaTelePipe){
        if(piranhaTelePipe.getFacing() == 0){
            setVelY(-5);
            setVelX(0);
            pixelsTravelled += (-1)*getVelY();
        }
        if (pixelsTravelled >= piranhaTelePipe.getHeight() + getHeight()) {
            setVelY(0);
            setGoingUpPipe(false);
            pixelsTravelled = 0;
        }
    }
    public void mangeGoingDownPipe(){
        if(isGoingDownPipe()){
            for(int i = 0; i < getHandler().getTiles().size(); i++){
                Tile tile = getHandler().getTiles().get(i);
                if (tile.getSection() == getSection()) {
                    if (tile.getId() == Id.TELE_SIMPLE_PIPE) {
                        if (getBounds().intersects(tile.getBounds())) downTeleSimplePipe((TeleSimplePipe) tile);
                    }
                    if (tile.getId() == Id.PIRHANA_TELE) {
                        if (getBounds().intersects(tile.getBounds())) downPiranhaTelePipe((PiranhaTelePipe) tile);
                    }
                }
            }
        }
    }
    public void downTeleSimplePipe(TeleSimplePipe teleSimplePipe){
        if(teleSimplePipe.getFacing() == 2){
            setVelY(5);
            setVelX(0);
            pixelsTravelled += getVelY();
        }
        if (pixelsTravelled > teleSimplePipe.getHeight() + getHeight() - 5) {
            setVelY(0);
            setGoingDownPipe(false);
            pixelsTravelled = 0;
        }
    }
    public void downPiranhaTelePipe(PiranhaTelePipe piranhaTelePipe){
        if(piranhaTelePipe.getFacing() == 2){
            setVelY(5);
            setVelX(0);
            pixelsTravelled += getVelY();
        }
        if (pixelsTravelled > piranhaTelePipe.getHeight() + getHeight() - 5) {
            setVelY(0);
            setGoingDownPipe(false);
            pixelsTravelled = 0;
        }
    }
    public void hitKoopa(Koopa koopa){
        if(!koopa.isHit()){
            if(getX() < koopa.getX()){
                koopa.setX(koopa.getX() + 64);
                koopa.getPaintKoopa().setX(getX());
            }
            else {
                koopa.setX(koopa.getX() - 64);
                koopa.getPaintKoopa().setX(getX());
            }
            koopa.setHit(true);
            koopa.setRun(false);
            koopa.setFistHitTime(System.nanoTime()/1000000000L);
        }
        else{
            koopa.die();
            getHandler().removePaintEntity(koopa.getPaintKoopa());
        }
    }
    public void hitStar(MagicStar magicStar) {
        playerTeamGame.setScore(playerTeamGame.getScore() + 40);
        getHandler().setTypeOfData("GAME_INFO");
        getHandler().setSenderName(getPlayerTeamGame().getUsername());
        getHandler().setSendData(true);
        if(playerState == PlayerState.MINI){
            playerState = PlayerState.MEGA;
            paintPlayer.setPlayerState(PlayerState.MEGA);
            setY(getY() - 64);
            paintPlayer.setY(getY());
            setHeight(128);
            paintPlayer.setHeight(getHeight());
        } else if (playerState == PlayerState.MEGA) {
            playerState = PlayerState.FIRE;
            paintPlayer.setPlayerState(PlayerState.FIRE);
        }
        startTimeAntiShock = System.nanoTime()/1000000000L;
        antiShock = true;
        magicStar.die();
        getHandler().removePaintEntity(magicStar.getPaintMagicStar());
    }
    public void hitQuestionBlock(QuestionBlock questionBlock) {
        if( !questionBlock.isPoppedUp()){
            if(questionBlock.isActivated1()){
                if(!questionBlock.isActiveScore()){
                    playerTeamGame.setScore(playerTeamGame.getScore() + 1);
                    getHandler().setTypeOfData("GAME_INFO");
                    getHandler().setSenderName(getPlayerTeamGame().getUsername());
                    getHandler().setSendData(true);
                    questionBlock.setActiveScore(true);
                }
            }
            questionBlock.setActivated1(true);
        }
    }
    public void hitMultiCoinsBlock(MultiCoinsBlock multiCoinsBlock) {
        if(!multiCoinsBlock.isEmpty()){
            if(multiCoinsBlock.isActivated1()){
                if(!multiCoinsBlock.isActiveScore()){
                    multiCoinsBlock.setHit(multiCoinsBlock.getHit() + 1);
                    playerTeamGame.setCoin(playerTeamGame.getCoin() + 1);
                    playerTeamGame.setScore(playerTeamGame.getScore() + 10);
                    getHandler().setTypeOfData("GAME_INFO");
                    getHandler().setSenderName(getPlayerTeamGame().getUsername());
                    getHandler().setSendData(true);
                    multiCoinsBlock.setActiveScore(true);
                }
                double endTime = (double) System.nanoTime()/1000000000L;
                double timeElapsed = endTime - startTime;
                if(timeElapsed > 0.2) {
                    multiCoinsBlock.setHit(multiCoinsBlock.getHit() + 1);
                    playerTeamGame.setCoin(playerTeamGame.getCoin() + 1);
                    playerTeamGame.setScore(playerTeamGame.getScore() + 10);
                    getHandler().setTypeOfData("GAME_INFO");
                    getHandler().setSenderName(getPlayerTeamGame().getUsername());
                    getHandler().setSendData(true);
                    multiCoinsBlock.setActivated2(true);
                    if(multiCoinsBlock.getHit() >= 5){
                        multiCoinsBlock.setEmpty(true);
                    }
                }
            }
            multiCoinsBlock.setActivated1(true);
            startTime = (double) System.nanoTime()/1000000000L;
        }
    }

    public void hitOneCoinBlock(OneCoinBlock oneCoinBlock) {
        if(oneCoinBlock.isActivated1()){
            if(!oneCoinBlock.isActiveScore()){
                playerTeamGame.setScore(playerTeamGame.getScore() + 1);
                getHandler().setTypeOfData("GAME_INFO");
                getHandler().setSenderName(getPlayerTeamGame().getUsername());
                getHandler().setSendData(true);
                oneCoinBlock.setActiveScore(true);
            }
            double endTime = (double) System.nanoTime()/1000000000L;
            double timeElapsed = endTime - startTime;
            if(timeElapsed > 0.2) {
                playerTeamGame.setScore(playerTeamGame.getScore() + 1);
                getHandler().setTypeOfData("GAME_INFO");
                getHandler().setSenderName(getPlayerTeamGame().getUsername());
                getHandler().setSendData(true);
                oneCoinBlock.setActivated2(true);
            }
        }
        oneCoinBlock.setActivated1(true);
        startTime = (double) System.nanoTime()/1000000000L;
    }
    public void hitSimpleBlock(SimpleBlock simpleBlock) {
        if(playerState != PlayerState.MINI){
            simpleBlock.die();
            getHandler().removePaintTiles(simpleBlock.getPaintSimpleBlock());
            playerTeamGame.setScore(playerTeamGame.getScore() + 1);
            getHandler().setTypeOfData("GAME_INFO");
            getHandler().setSenderName(getPlayerTeamGame().getUsername());
            getHandler().setSendData(true);
        }
    }
    public void hitMushroom(MagicMushroom mushroom) {
        playerTeamGame.setScore(playerTeamGame.getScore() + 30);
        getHandler().setTypeOfData("GAME_INFO");
        getHandler().setSenderName(getPlayerTeamGame().getUsername());
        getHandler().setSendData(true);
        if(playerState == PlayerState.MINI){
            playerState = PlayerState.MEGA;
            paintPlayer.setPlayerState(PlayerState.MEGA);
            setY(getY() - 64);
            paintPlayer.setY(getY());
            setHeight(128);
            paintPlayer.setHeight(getHeight());
        } else if (playerState == PlayerState.MEGA) {
            playerState = PlayerState.FIRE;
            paintPlayer.setPlayerState(PlayerState.FIRE);
        }
        mushroom.die();
        getHandler().removePaintEntity(mushroom.getPaintMagicMushroom());

    }
    public void hitCoin(Coin coin){
        playerTeamGame.setCoin(playerTeamGame.getCoin() + 1);
        playerTeamGame.setScore(playerTeamGame.getScore() + 10);
        getHandler().setTypeOfData("GAME_INFO");
        getHandler().setSenderName(getPlayerTeamGame().getUsername());
        getHandler().setSendData(true);
        getHandler().setTypeOfData("GAME_INFO");
        getHandler().setSenderName(getPlayerTeamGame().getUsername());
        getHandler().setSendData(true);
        coin.die();
        getHandler().removePaintEntity(coin.getPaintCoin());
    }
    public void hitMagicFlower(MagicFlower magicFlower){
        playerTeamGame.setScore(playerTeamGame.getScore() + 20);
        getHandler().setTypeOfData("GAME_INFO");
        getHandler().setSenderName(getPlayerTeamGame().getUsername());
        getHandler().setSendData(true);
        if(playerState == PlayerState.MINI){
            playerState = PlayerState.MEGA;
            paintPlayer.setPlayerState(PlayerState.MEGA);
            setY(getY() - 64);
            paintPlayer.setY(getY());
            setHeight(128);
            paintPlayer.setHeight(getHeight());
        } else if (playerState == PlayerState.MEGA) {
            playerState = PlayerState.FIRE;
            paintPlayer.setPlayerState(PlayerState.FIRE);
        }
        magicFlower.die();
        getHandler().removePaintEntity(magicFlower.getPaintMagicFlower());
    }
    public void hitFlag(Flag flag){
        if (!flag.isActive()) {
            flag.setActive(true);
            flag.setPlayer(this);
        }
    }
    public void hitPlayer(Entity entity){
        if(!antiShock) {
            if (playerState == PlayerState.MINI) {
                if (playerTeamGame.getScore() - 20 >= 0) {
                    playerTeamGame.setScore(playerTeamGame.getScore() - 20);
                    getHandler().setTypeOfData("GAME_INFO");
                    getHandler().setSenderName(getPlayerTeamGame().getUsername());
                    getHandler().setSendData(true);
                }
                die();
                getHandler().removePaintEntity(paintPlayer);
            } else if (playerState == PlayerState.MEGA) {
                playerState = PlayerState.MINI;
                paintPlayer.setPlayerState(PlayerState.MINI);
                setY(getY() + 64);
                paintPlayer.setY(getY());
                setHeight(64);
                paintPlayer.setHeight(getHeight());
                if (getX() < entity.getX()) {
                    setX(getX() - getWidth());
                    paintPlayer.setX(getX());
                } else if (getX() > entity.getX()) {
                    setX(getX() + getWidth());
                    paintPlayer.setX(getX());
                }
            } else if (playerState == PlayerState.FIRE) {
                playerState = PlayerState.MEGA;
                paintPlayer.setPlayerState(PlayerState.MEGA);
            }
        } else {
            if (entity.getId() == Id.GOOMBA) {
                entity.die();
                getHandler().removePaintEntity(((Goomba)entity).getPaintGoomba());
            } else if (entity.getId() == Id.KOOPA) {
                entity.die();
                getHandler().removePaintEntity(((Koopa)entity).getPaintKoopa());
            } else if (entity.getId() == Id.SPINY) {
                entity.die();
                getHandler().removePaintEntity(((Spiny)entity).getPaintSpiny());
            } else if (entity.getId() == Id.PLANT) {
                entity.die();
                getHandler().removePaintEntity(((Plant)entity).getPaintPlant());
            }
        }
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public int getPixelsTravelled() {
        return pixelsTravelled;
    }

    public void setPixelsTravelled(int pixelsTravelled) {
        this.pixelsTravelled = pixelsTravelled;
    }

    public long getStartTimeAntiShock() {
        return startTimeAntiShock;
    }

    public void setStartTimeAntiShock(long startTimeAntiShock) {
        this.startTimeAntiShock = startTimeAntiShock;
    }

    public long getStartTimeFireBall() {
        return startTimeFireBall;
    }

    public void setStartTimeFireBall(long startTimeFireBall) {
        this.startTimeFireBall = startTimeFireBall;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public boolean isAntiShock() {
        return antiShock;
    }

    public void setAntiShock(boolean antiShock) {
        this.antiShock = antiShock;
    }

    public boolean isGoingDownPipe() {
        return goingDownPipe;
    }

    public void setGoingDownPipe(boolean goingDownPipe) {
        this.goingDownPipe = goingDownPipe;
    }

    public boolean isGoingUpPipe() {
        return goingUpPipe;
    }

    public void setGoingUpPipe(boolean goingUpPipe) {
        this.goingUpPipe = goingUpPipe;
    }

    public PlayerTeamGame getPlayerTeamGame() {
        return playerTeamGame;
    }

    public void setPlayerTeamGame(PlayerTeamGame playerTeamGame) {
        this.playerTeamGame = playerTeamGame;
    }


    public void nextSection() {
        int section = getSection();
        if (section == 2){
            getHandler().setTypeOfData("VICTORY");
            getHandler().setSenderName(getPlayerTeamGame().getUsername());
            getHandler().setSendData(true);
            getPlayerTeamGame().setEnd(true);
        }else {
            setSection(section+1);
            paintPlayer.setSection(section + 1);
            playerTeamGame.setSection(section+1);
            setX(64);
            paintPlayer.setX(64);
            setY(-2*64);
            paintPlayer.setY(-2*64);
            getHandler().setTypeOfData("GAME_INFO");
            getHandler().setSenderName(getPlayerTeamGame().getUsername());
            getHandler().setSendData(true);
        }
    }

    public PaintPlayer getPaintPlayer() {
        return paintPlayer;
    }

    public void setPaintPlayer(PaintPlayer paintPlayer) {
        this.paintPlayer = paintPlayer;
    }

    public void setSpeed(boolean speed) {
        this.speed = speed;
    }
}
