package org.example.offline_game.model.tile.blocks;


import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.enums.Id;
import org.example.offline_game.model.tile.Tile;

import java.awt.*;
public class BlockGame extends Tile {
    private boolean timer;
    private long time = 0;
    private long startTime;
    private boolean marioOnBlock = false;
    private long sigmaTime;
    private boolean firstTime = true;
    private boolean marioOnEarth = false;
    public BlockGame(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
        if(handler.getGame().isBossFight()){
            if((x != 64*handler.getLength()+64) && (x != -64) && (y != 0) ){
                timer = true;
            }
        }
    }
    @Override
    public void paint(Graphics g) {

    }
    @Override
    public void update() {

    }
    public boolean isTimer() {
        return timer;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public boolean isMarioOnBlock() {
        return marioOnBlock;
    }
    public void setMarioOnBlock(boolean marioOnBlock) {
        this.marioOnBlock = marioOnBlock;
    }
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public long getSigmaTime() {
        return sigmaTime;
    }
    public void setSigmaTime(long sigmaTime) {
        this.sigmaTime = sigmaTime;
    }
    public boolean isFirstTime() {
        return firstTime;
    }
    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }
    public boolean isMarioOnEarth() {
        return marioOnEarth;
    }
    public void setMarioOnEarth(boolean marioOnEarth) {
        this.marioOnEarth = marioOnEarth;
    }
}
