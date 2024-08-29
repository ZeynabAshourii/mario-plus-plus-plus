package org.example.team_game.send_object.paint_tile.blocks;

import org.example.resources.Resources;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_tile.PaintTile;
import java.awt.*;

public class PaintQuestionBlock extends PaintTile {
    private Id itemId;
    private double spriteY1 = getY();
    private boolean poppedUp = false;
    private boolean activated1 = false;
    public PaintQuestionBlock(double x, double y, int width, int height, Id id, int section , Id itemId) {
        super(x, y, width, height, id, section);
        this.itemId = itemId;
    }

    @Override
    public void paint(Graphics g) {
        try {
            if (!poppedUp) {
                paintItem(g);
            }
            if(activated1){
                g.drawImage(Resources.getEmptyBlock().getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
            }
            else {
                g.drawImage(Resources.getPowerUp().getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
            }
        }
        catch (Exception e){}
    }
    public void paintItem(Graphics g){
        if(itemId == Id.COIN){
            g.drawImage(Resources.getCoinPic().getSheet() , (int) getX(), (int) spriteY1, getWidth() , getHeight() , null);
        } else if (itemId == Id.MAGIC_FLOWER) {
            g.drawImage(Resources.getFlowerPic().getSheet() , (int) getX(), (int) spriteY1, getWidth() , getHeight() , null);
        } else if (itemId == Id.MAGIC_MUSHROOM) {
            g.drawImage(Resources.getMushroomPic().getSheet() , (int) getX(), (int) spriteY1, getWidth() , getHeight() , null);
        } else if (itemId == Id.MAGIC_STAR) {
            g.drawImage(Resources.getStarPic().getSheet() , (int) getX(), (int) spriteY1, getWidth() , getHeight() , null);
        }
    }

    public void setSpriteY1(double spriteY1) {
        this.spriteY1 = spriteY1;
    }

    public void setPoppedUp(boolean poppedUp) {
        this.poppedUp = poppedUp;
    }

    public void setActivated1(boolean activated1) {
        this.activated1 = activated1;
    }
}
