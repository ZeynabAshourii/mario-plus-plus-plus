package org.example.config_loader;

import java.io.Serializable;
public class Pipe implements Serializable {
    public int x;
    public int y;
    public String type;
    public Section section;
    public boolean activated;

    @Override
    public String toString() {
        return "PipeGame{" +
                "x=" + x +
                ", y=" + y +
                ", type='" + type + '\'' +
                ", section=" + section +
                ", activated=" + activated +
                '}';
    }
}
