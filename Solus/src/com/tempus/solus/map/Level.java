package com.tempus.solus.map;

import com.tempus.solus.Engine;
import com.tempus.solus.entity.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Level {
    private static final Logger logger = Logger.getLogger(Engine.class.getName());
    public static String levelName;
    public String levelPath;
    public int levelID;
    private boolean movingLeft;
    private boolean movingRight;
    private int xPos;
    private int xVel;
    private TiledMap map;
    Player player;





    public Level(String levelName, String mapPath) {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
        player = null;

        movingLeft = false;
        movingRight = false;
        try {
            xPos = (int) player.getXPos() - 128;
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
        xVel = 0;
        this.levelName = levelName;
        try {
            map = new TiledMap(mapPath);
        } catch (SlickException ex) {
            logger.severe(ex.getMessage());
            logger.info("INVALID MAP PATH");
        }
    }
    public void setMovingLeft(boolean isLeft) {
        movingLeft = isLeft;
    }
    public void setMovingRight(boolean isRight) {
        movingRight = isRight;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void render(Graphics graphics) {
        graphics.scale(2,2);
        map.render(xPos,-320);
        graphics.scale(.5f,.5f);
    }
    public void update(int delta) {
        if(movingLeft) {
            xVel = 3;
        } else if(movingRight) {
            xVel = -3;
        } else {
            xVel = 0;
        }

        if (xVel < 0 && !movingRight) {
            xVel += 0.01;
        }
        if (xVel > 0 && !movingLeft) {
            xVel -= 0.01;
        }
        if (xVel == 0 || player.getYPos() < Engine.GAME_HEIGHT - 160) {
            player.setAnimations(false);
            player.resetAnimation();
        } else {
            player.setAnimations(true);
        }


        xPos += xVel;

    }

}