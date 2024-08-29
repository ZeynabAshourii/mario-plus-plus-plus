package org.example.offline_game.model.tile.blocks;


import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.enums.Id;
import org.example.resources.Resources;
import java.awt.*;
public class SimpleBlock extends BlockGame {
    public SimpleBlock(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
    }

    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getWallPic().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
        if(isTimer()) {
            if (isMarioOnBlock()) {
                setTime(System.nanoTime() / 1000000000L - getStartTime() + getSigmaTime() );
                setMarioOnBlock(false);
            } else if(isMarioOnEarth()) {
                setFirstTime(true);
                setTime(0);
            } else {
                setFirstTime(true);
            }
            if(getTime() > 2){
                die();
            }
        }
    }
}
