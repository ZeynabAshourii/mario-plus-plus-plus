package org.example.send_object.entities.items;

import org.example.marathon_mario.model.enums.Id;
import org.example.send_object.entities.PaintEntity;
import org.example.server.model.Item;

import java.awt.*;

public class PaintAdditionalItem extends PaintEntity {
    private Item.ItemType itemType;
    public PaintAdditionalItem(double x, double y, int width, int height, Id id , Item.ItemType itemType) {
        super(x, y, width, height, id);
        this.itemType = itemType;
    }

    @Override
    public void paint(Graphics g) {
        if(itemType.equals(Item.ItemType.INVISIBLE)){
            g.setColor(Color.PINK);
        } else if (itemType.equals(Item.ItemType.SPEED)) {
            g.setColor(Color.BLUE);
        } else if (itemType.equals(Item.ItemType.HEAL)) {
            g.setColor(Color.RED);
        } else if (itemType.equals(Item.ItemType.EXPLOSIVE_BOMB)) {
            g.setColor(Color.CYAN);
        } else if (itemType.equals(Item.ItemType.SPEED_BOMB)) {
            g.setColor(Color.YELLOW);
        } else if (itemType.equals(Item.ItemType.HAMMER)) {
            g.setColor(Color.ORANGE);
        } else if (itemType.equals(Item.ItemType.SWORD)) {
            g.setColor(Color.MAGENTA);
        }
        g.fillRect((int) getX(), (int) getY(), getWidth() , getHeight());
    }

    public Item.ItemType getItemType() {
        return itemType;
    }

    public void setItemType(Item.ItemType itemType) {
        this.itemType = itemType;
    }
}
