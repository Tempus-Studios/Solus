package com.tempus.solus.map.tile;

import com.tempus.solus.Engine;
import org.newdawn.slick.geom.Rectangle;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Tile extends Rectangle {
    private static final Logger logger = Logger.getLogger(Engine.class.getName());
    public static final float TILE_WIDTH = 32;
    public static final float TILE_HEIGHT = 32;
    public static final float SCALE = 2;

    public Tile(float x, float y) {
        super(x, y, TILE_WIDTH * SCALE, TILE_HEIGHT * SCALE);
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
    }

    public float getX() {
        return super.getX();
    }

    public void setX(float x) {
        super.setX(x);
    }

    public float getY() {
        return super.getY();
    }

    public void setY(float y) {
        super.setY(y);
    }


}
