package org.example.config_loader;

import java.io.Serializable;
public class Block implements Serializable {
    public int x;
    public int y;
    public String type;
    public String item;

    @Override
    public String toString() {
        return "BlockGame{" +
                "x=" + x +
                ", y=" + y +
                ", type='" + type + '\'' +
                ", item='" + item + '\'' +
                '}';
    }

}
