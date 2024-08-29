package org.example.offline_game.model.entity;

import org.example.client.controller.Client;
import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.entity.enemies.Attack;
import org.example.offline_game.model.entity.enemies.Bowser;
import org.example.offline_game.model.entity.enemies.Koopa;
import org.example.offline_game.model.entity.items.Coin;
import org.example.offline_game.model.entity.items.MagicFlower;
import org.example.offline_game.model.entity.items.MagicMushroom;
import org.example.offline_game.model.entity.items.MagicStar;
import org.example.offline_game.model.enums.Id;
import org.example.offline_game.model.enums.PlayerState;

import org.example.offline_game.model.tile.blocks.*;
import org.example.offline_game.model.tile.Flag;
import org.example.offline_game.model.tile.Gate;
import org.example.offline_game.model.tile.pipes.PiranhaTelePipe;
import org.example.offline_game.model.tile.pipes.TeleSimplePipe;
import org.example.offline_game.model.tile.Tile;
import org.example.offline_game.view.CheckPointPanel;
import org.example.resources.Resources;

import java.awt.*;
public class Player extends Entity {
    private PlayerState state;
    private int pixelsTravelled = 0;
    private int frame = 0;
    private int frameDelay = 0;
    private int numberOfAD = 0;
    private long startTimeJumpAttack;
    private long startTimeSW;
    private long startTimeSword;
    private long startTimeAntiShock = 0;
    private double startTime = 0;
    private double startTimeHitBowserHead = 0;
    private boolean antiShock = false;
    private boolean goingDownPipe = false;
    private boolean goingUpPipe = false;
    private boolean grabAttack = false;
    private boolean jumpAttack = false;
    private boolean cutScene = false;
    private boolean swordW = false;
    private boolean swordS = false;
    private transient Client client;
    public Player(int x, int y, int width, int height , Id id, HandlerOfflineGame handler , PlayerState playerState , Client client) {
        super(x, y, width, height, id, handler);
        this.state = playerState;
        this.client = client;
    }
    @Override
    public void paint(Graphics g) {
        try {
            if(state == PlayerState.MINI) {
                if (getFacing() == 0) {
                    g.drawImage(Resources.getMarioPic()[frame + 5].getSheet(), getX(), getY(), getWidth(), getHeight(), null);
                } else if (getFacing() == 1) {
                    g.drawImage(Resources.getMarioPic()[frame].getSheet(), getX(), getY(), getWidth(), getHeight(), null);
                }
            }else if(state == PlayerState.MEGA) {
                if (getFacing() == 0) {
                    g.drawImage(Resources.getMegaMarioPic()[frame + 5].getSheet(), getX(), getY(), getWidth(), getHeight(), null);
                } else if (getFacing() == 1) {
                    g.drawImage(Resources.getMegaMarioPic()[frame].getSheet(), getX(), getY(), getWidth(), getHeight(), null);
                }
            }else if(state == PlayerState.FIRE) {
                if (getFacing() == 0) {
                    g.drawImage(Resources.getFireMarioPic()[frame + 5].getSheet(), getX(), getY(), getWidth(), getHeight(), null);
                } else if (getFacing() == 1) {
                    g.drawImage(Resources.getFireMarioPic()[frame].getSheet(), getX(), getY(), getWidth(), getHeight(), null);
                }
            }
            g.drawString(client.getUsername() , getX() + 5 , getY() - 10);
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
        calculationTimers();

        setX(getX() + getVelX());
        setY(getY() + getVelY());

        marioOnEarth();

        if(getY() > getHandler().getDeathY() + 64) fall();

        hitTiles();

        hitEntities();

        mangeGravity();

        mangeGoingDownPipe();

        manageGoingUpPipe();

        manageFrame();

    }
    public void calculationTimers(){
        if(System.nanoTime()/1000000000L - startTimeAntiShock >= 15 && antiShock){
            antiShock = false;
        }
        if(System.nanoTime()/1000000000L - startTimeJumpAttack >= 5 && jumpAttack){
            jumpAttack = false;
        }
        if(!(swordS && swordW)){
            startTimeSW = System.nanoTime()/1000000000L;
        }else if(System.nanoTime()/1000000000L - startTimeSW >= 1.5){
            if(System.nanoTime()/1000000000L - startTimeSword >= 5 ) {
                if (getHandler().getGame().getCoin() >= 3) {
                    getHandler().addEntity(new Sword(getX() + getWidth(), getY() + getHeight() - 64, 0, 8, Id.sword, getHandler()));
                    startTimeSword = System.nanoTime()/1000000000L;
                    getHandler().getGame().setCoin(getHandler().getGame().getCoin() - 3);
                }
            }
        }
    }
    public void marioOnEarth(){
        if((getY() + getHeight())/10 == 0){
            for(int i = 0; i < getHandler().getTiles().size(); i++){
                Tile tile = getHandler().getTiles().get(i);
                if(tile instanceof BlockGame){
                    ((BlockGame) tile).setMarioOnEarth(true);
                }
            }
        }
        else {
            for(int i = 0; i < getHandler().getTiles().size(); i++){
                Tile tile = getHandler().getTiles().get(i);
                if(tile instanceof BlockGame){
                    ((BlockGame) tile).setMarioOnEarth(false);
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
        if(tile.getId() == Id.simpleBlock){
            hitSimpleBlock((SimpleBlock) tile);
        }
        else if(tile.getId() == Id.oneCoinBlock){
            hitOneCoinBlock((OneCoinBlock) tile);
        } else if (tile.getId() == Id.multiCoinsBlock) {
            hitMultiCoinsBlock((MultiCoinsBlock) tile);
        } else if (tile.getId() == Id.questionBlock) {
            hitQuestionBlock((QuestionBlock) tile);
        } else if (tile.getId() == Id.teleSimplePipe) {
            TeleSimplePipe teleSimplePipe = (TeleSimplePipe) tile;
            if(teleSimplePipe.getFacing() == 0) {
                setGoingUpPipe(true);
            }
        }else if (tile.getId() == Id.piranhaTele) {
            PiranhaTelePipe piranhaTelePipe = (PiranhaTelePipe) tile;
            if(piranhaTelePipe.getFacing() == 0) {
                setGoingUpPipe(true);
            }
        }
    }
    public void hitTileFromBottom(Tile tile){
        if(tile instanceof BlockGame){
            BlockGame block = (BlockGame) tile;
            if(block.isTimer()){
                if(block.isFirstTime()) {
                    block.setSigmaTime(block.getTime());
                    block.setStartTime(System.nanoTime() / 1000000000L);
                    block.setFirstTime(false);
                }
                block.setMarioOnBlock(true);
            }
        }
        setVelY(0);
        if (isFalling()) {
            setFalling(false);
        }
    }
    public void hitTiles(){
        for(int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if(!isGoingDownPipe() && !isGoingUpPipe()) {
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
                }
                if (getBoundsLeft().intersects(tile.getBounds())) {
                    setVelX(0);
                    setX(tile.getX() + getWidth());
                }
                if(getBounds().intersects(tile.getBounds())){
                    if(tile.getId() == Id.flag){
                        hitFlag((Flag) tile);
                    }
                    if(tile.getId() == Id.gate){
                        hitGate((Gate) tile);
                    }
                }
            }
        }
    }
    public void hitEntities(){
        for(int i = 0 ; i < getHandler().getEntities().size() ; i ++){
            Entity entity = getHandler().getEntities().get(i);
            if(getBounds().intersects(entity.getBounds()) && entity.getId() == Id.coin){
                hitCoin((Coin) entity);
            } else if (getBounds().intersects(entity.getBounds()) && entity.getId() == Id.magicFlower) {
                hitMagicFlower((MagicFlower) entity);
            } else if( getBounds().intersects(entity.getBounds()) && entity.getId() == Id.magicMushroom){
                hitMushroom((MagicMushroom) entity);
            } else if (getBounds().intersects(entity.getBounds())&& entity.getId() == Id.magicStar) {
                hitStar((MagicStar) entity);
            } else if(entity.getId() == Id.goomba){
                if(getBoundsBottom().intersects(entity.getBoundsTop())){
                    entity.die();
                } else if (getBounds().intersects(entity.getBounds())) {
                    hitPlayer(entity);
                }
            }else if(entity.getId() == Id.koopa){
                if(getBoundsBottom().intersects(entity.getBoundsTop())){
                    hitKoopa((Koopa) entity);
                } else if (getBounds().intersects(entity.getBounds())) {
                    hitPlayer(entity);
                }
            }else if (getBounds().intersects(entity.getBounds())&& entity.getId() == Id.spiny) {
                hitPlayer(entity);
            } else if (getBounds().intersects(entity.getBounds())&& entity.getId() == Id.plant) {
                hitPlayer(entity);
            } else if (entity.getId() == Id.bowser) {
                Bowser bowser = (Bowser) entity;
                if(bowser.isHealth()) {
                    if (getBoundsBottom().intersects(bowser.head())) {
                        hitBowserHead(bowser);
                    } else if (getBounds().intersects(bowser.getBounds()) && bowser.getAttack() != Attack.grabAttack) {
                        hitPlayer(bowser);
                    }
                }
            }
        }
    }
    public void manageFrame(){
        if(getVelX() != 0){
            frameDelay++;
            if(frameDelay >= 3){
                frame++;
                if(frame >= 5){
                    frame = 0;
                }
                frameDelay = 0;
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
                if(tile.getId()  == Id.teleSimplePipe) {
                    if(getBounds().intersects(tile.getBounds())) upTeleSimplePipe((TeleSimplePipe) tile);
                }
                if(tile.getId()  == Id.piranhaTele) {
                    if(getBounds().intersects(tile.getBounds())) upPiranhaTelePipe((PiranhaTelePipe) tile);
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
                if(tile.getId()  == Id.teleSimplePipe) {
                    if(getBounds().intersects(tile.getBounds())) downTeleSimplePipe((TeleSimplePipe) tile );
                } if(tile.getId()  == Id.piranhaTele) {
                    if(getBounds().intersects(tile.getBounds())) downPiranhaTelePipe((PiranhaTelePipe) tile);
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
            }
            else {
                koopa.setX(koopa.getX() - 64);
            }
            koopa.setHit(true);
            koopa.setRun(false);
            koopa.setFistHitTime(System.nanoTime()/1000000000L);
        }
        else{
            koopa.die();
        }
    }
    public void hitStar(MagicStar magicStar) {
        getHandler().getGame().setScore(getHandler().getGame().getScore() + 40);
        if(state == PlayerState.MINI){
            state = PlayerState.MEGA;
            setY(getY() - 64);
            setHeight(128);
        } else if (state == PlayerState.MEGA) {
            state = PlayerState.FIRE;
        }
        startTimeAntiShock = System.nanoTime()/1000000000L;
        antiShock = true;
        magicStar.die();
    }
    public void hitQuestionBlock(QuestionBlock questionBlock) {
        if( !questionBlock.isPoppedUp()){
            if(questionBlock.isActivated1()){
                if(!questionBlock.isActiveScore()){
                    getHandler().getGame().setScore(getHandler().getGame().getScore() + 1);
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
                    getHandler().getGame().setCoin(getHandler().getGame().getCoin() + 1);
                    getHandler().getGame().setScore(getHandler().getGame().getScore() + 10);
                    multiCoinsBlock.setActiveScore(true);
                }
                double endTime = (double) System.nanoTime()/1000000000L;
                double timeElapsed = endTime - startTime;
                if(timeElapsed > 0.2) {
                    multiCoinsBlock.setHit(multiCoinsBlock.getHit() + 1);
                    getHandler().getGame().setCoin(getHandler().getGame().getCoin() + 1);
                    getHandler().getGame().setScore(getHandler().getGame().getScore() + 10);
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
    public void hitBowserHead(Bowser bowser){
        if(startTimeHitBowserHead != 0){
            double endTime = (double) System.nanoTime()/1000000000L;
            double timeElapsed = endTime - startTimeHitBowserHead;
            if(timeElapsed > 0.2) {
                bowser.setHp(bowser.getHp()-2);
                bowser.hurt();
            }
        }else {
            bowser.setHp(bowser.getHp()-2);
            bowser.hurt();
        }
        startTimeHitBowserHead = (double) System.nanoTime()/1000000000L;
    }
    public void hitOneCoinBlock(OneCoinBlock oneCoinBlock) {
        if(oneCoinBlock.isActivated1()){
            if(!oneCoinBlock.isActiveScore()){
                getHandler().getGame().setScore(getHandler().getGame().getScore() + 1);
                oneCoinBlock.setActiveScore(true);
            }
            double endTime = (double) System.nanoTime()/1000000000L;
            double timeElapsed = endTime - startTime;
            if(timeElapsed > 0.2) {
                getHandler().getGame().setScore(getHandler().getGame().getScore() + 1);
                oneCoinBlock.setActivated2(true);
            }
        }
        oneCoinBlock.setActivated1(true);
        startTime = (double) System.nanoTime()/1000000000L;
    }
    public void hitSimpleBlock(SimpleBlock simpleBlock) {
        if(state != PlayerState.MINI){
            simpleBlock.die();
            getHandler().getGame().setScore(getHandler().getGame().getScore() + 1);
        }
    }
    public void hitMushroom(MagicMushroom mushroom) {
        getHandler().getGame().setScore(getHandler().getGame().getScore() + 30);
        if(state == PlayerState.MINI){
            state = PlayerState.MEGA;
            setY(getY() - 64);
            setHeight(128);
        } else if (state == PlayerState.MEGA) {
            state = PlayerState.FIRE;
        }
        mushroom.die();
    }
    public void hitCoin(Coin coin){
        getHandler().getGame().setCoin(getHandler().getGame().getCoin() + 1);
        getHandler().getGame().setScore(getHandler().getGame().getScore() + 10);
        coin.die();
    }
    public void hitMagicFlower(MagicFlower magicFlower){
        getHandler().getGame().setScore(getHandler().getGame().getScore() + 20);
        if(state == PlayerState.MINI){
            state = PlayerState.MEGA;
            setY(getY() - 64);
            setHeight(128);
        } else if (state == PlayerState.MEGA) {
            state = PlayerState.FIRE;
        }
        magicFlower.die();
    }
    public void hitFlag(Flag flag){
        flag.setActive(true);
    }
    public void hitGate(Gate gate){
        if(!gate.isActive()) {
            gate.setActive(true);
            getHandler().getGame().setPaused(true);
            client.getClientFrame().setContentPane(new CheckPointPanel(getHandler() , gate));
        }
    }
    public void fall(){
        getHandler().getGame().setScore(getHandler().getGame().getScore() - 30);
        die();
    }
    public void hitPlayer(Entity entity){
        if(!antiShock) {
            if (state == PlayerState.MINI) {
                if (getHandler().getGame().getScore() - 20 >= 0) {
                    getHandler().getGame().setScore(getHandler().getGame().getScore() - 20);
                }
                die();
            } else if (state == PlayerState.MEGA) {
                state = PlayerState.MINI;
                setY(getY() + 64);
                setHeight(64);
                if (getX() < entity.getX()) {
                    setX(getX() - getWidth());
                } else if (getX() > entity.getX()) {
                    setX(getX() + getWidth());
                }
            } else if (state == PlayerState.FIRE) {
                state = PlayerState.MEGA;
            }
        } else {
            if(entity.getId() == Id.bowser){
                ((Bowser)entity).hurt();
            }
            else {
                entity.die();
            }
        }
    }
    public PlayerState getState() {
        return state;
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
    public boolean isSwordW() {
        return swordW;
    }
    public void setSwordW(boolean swordW) {
        this.swordW = swordW;
    }
    public boolean isSwordS() {
        return swordS;
    }
    public void setSwordS(boolean swordS) {
        this.swordS = swordS;
    }
    public boolean isCutScene() {
        return cutScene;
    }
    public void setCutScene(boolean cutScene) {
        this.cutScene = cutScene;
    }
    public int getNumberOfAD() {
        return numberOfAD;
    }
    public void setNumberOfAD(int numberOfAD) {
        this.numberOfAD = numberOfAD;
    }
    public boolean isGrabAttack() {
        return grabAttack;
    }
    public void setGrabAttack(boolean grabAttack) {
        this.grabAttack = grabAttack;
    }
    public boolean isJumpAttack() {
        return jumpAttack;
    }
    public void setStartTimeJumpAttack(long startTimeJumpAttack) {
        this.startTimeJumpAttack = startTimeJumpAttack;
    }
    public void setJumpAttack(boolean jumpAttack) {
        this.jumpAttack = jumpAttack;
    }
    public void setState(PlayerState state) {
        this.state = state;
    }
}
