package org.example.team_game.send_object.paint_tile.blocks;

import org.example.resources.Resources;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_tile.PaintTile;
import java.awt.*;

public class PaintOneCoinBlock extends PaintTile {
    private boolean poppedUp = false;
    private boolean activated1 = false;
    private double spriteY1 = getY();
    public PaintOneCoinBlock(double x, double y, int width, int height, Id id, int section) {
        super(x, y, width, height, id, section);
    }

    @Override
    public void paint(Graphics g) {
        try {
            if (!poppedUp) {
                g.drawImage(Resources.getCoinPic().getSheet(), (int) getX(), (int) spriteY1, getWidth(), getHeight(), null);
            }
            if(activated1){
                g.drawImage(Resources.getWallPic().getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
            }
            else {
                g.drawImage(Resources.getOneCoinBlock().getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
            }
        }
        catch (Exception e){}
    }

    public void setPoppedUp(boolean poppedUp) {
        this.poppedUp = poppedUp;
    }

    public void setActivated1(boolean activated1) {
        this.activated1 = activated1;
    }

    public void setSpriteY1(double spriteY1) {
        this.spriteY1 = spriteY1;
    }
}
