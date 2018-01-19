package com.tempus.solus.map;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Tile {
    private Rectangle tile;
    private boolean isSolid;
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public Tile(int x, int y) {
        tile = new Rectangle(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
        isSolid = true;
    }

    public int getX() {
        return (int) tile.getX();
    }
    public int getY() {
        return (int)  tile.getY();
    }
    public void setSolid(boolean solid) {
        isSolid = solid;
    }
    public boolean isSolid() {
        return isSolid;
    }
    public boolean intersects(Shape shape) {
        return tile.intersects(shape);
    }
}
