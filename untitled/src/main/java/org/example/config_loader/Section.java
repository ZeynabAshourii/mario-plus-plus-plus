package org.example.config_loader;

import java.io.Serializable;
import java.util.LinkedList;
public class Section implements Serializable {
    public int length;
    public int time;
    public LinkedList<Block> blocks = new LinkedList<>();
    public LinkedList<Enemy> enemies = new LinkedList<>();
    public LinkedList<Pipe> pipes = new LinkedList<>();
    public Pipe spawnPipe;

    @Override
    public String toString() {
        return "Section{" +
                "length=" + length +
                ", time=" + time +
                ", blocks=" + blocks +
                ", enemies=" + enemies +
                ", pipes=" + pipes +
                ", spawnPipe=" + spawnPipe +
                '}';
    }
}
