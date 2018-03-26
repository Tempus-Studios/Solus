package com.tempus.solus.map;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Tile extends Rectangle {
    private boolean isSolid;
    public static final int WIDTH = 64; //32 pixel width but scaled up by 2 -> (32 * 2)
    public static final int HEIGHT = 64; //same as above

    public Tile(int x, int y) {
        super(x, y, WIDTH, HEIGHT);
        isSolid = true;
    }

    public void setSolid(boolean solid) {
        isSolid = solid;
    }
    public boolean isSolid() {
        return isSolid;
    }
}
