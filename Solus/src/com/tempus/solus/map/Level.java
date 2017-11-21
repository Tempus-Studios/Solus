package com.tempus.solus.map;

import com.tempus.solus.Engine;
import com.tempus.solus.entity.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Level {
    private static final Logger logger = Logger.getLogger(Engine.class.getName());
    private BufferedWriter writer;
    private BufferedReader reader;
    private boolean movingLeft;
    private boolean movingRight;
    private int xPos;
    private int xVel;
    private float sprintMultiplier;
    private TiledMap map;
    private String levelID;
    private String levelPath;
    Player player;





    public Level() {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
        this.init();

    }

    private void init() {
        player = null;
        movingLeft = false;
        movingRight = false;
        sprintMultiplier = 1;

        try {
            xPos = (int) player.getXPos() - 128;
        } catch (NullPointerException ex) {
            logger.severe(ex.getMessage());
        }
        xVel = 0;
        try {
            reader = new BufferedReader(new FileReader("res/level/level-info.txt"));
        } catch (IOException ex) {
            logger.severe(ex.getMessage());
        }
        try {
            xPos = (int) (player.getXPos() - 128);
        } catch(NullPointerException ex) {
            logger.severe(ex.getMessage());
        }

        xVel = 0;
    }

    public void loadMap() {
        try {
            this.levelID = reader.readLine();
            logger.info(levelID);
        } catch (IOException ex) {
            logger.severe(ex.getMessage());
        } catch (NullPointerException ex) {
            //TODO: Default to level 1
            this.levelID = "test";
            logger.severe("error");
        }
        this.levelPath = "res/level/lvl-" + levelID + "-map.tmx";
        try {
            this.map = new TiledMap(levelPath);
        } catch (SlickException ex) {
            logger.severe("Failed to load map\n" + ex.getMessage());
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

        if (player.isSprinting()) {
            player.decrementSprintEnergy(0.3f);
            if (player.getYPos() >= Engine.GAME_HEIGHT - 160) {
                player.setDuration(1, 125);
                sprintMultiplier = 2;
            }
        } else {
            player.setDuration(1, 175);
            sprintMultiplier = 1;
        }

        xVel *= sprintMultiplier;
        xPos += xVel;

    }
    public void setCurrentLevel(String nextLevelID, boolean doLoadNextMap) {
        if (nextLevelID != null) {
            this.levelID = nextLevelID;
        }
    }
}