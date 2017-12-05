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
    private Player player;
    private BufferedWriter writer;
    private BufferedReader reader;
    private boolean isMoving;
    //private boolean movingLeft;
    //private boolean movingRight;
    private int direction;
    private float xPos;
    private float xVel;
    private float sprintMultiplier;
    private TiledMap map;
    private String levelID;
    private String levelPath;

    public Level(Player player) {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
        this.player = player;
        this.init();
    }

    private void init() {
        isMoving = false;
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

    public void setMoving(boolean moving) {
        this.isMoving = moving;
    }


    public void render(Graphics graphics) {
        graphics.scale(2,2);
        map.render(-(int) player.getXPos() + 128,-320);
        graphics.scale(.5f,.5f);
    }

    public void setCurrentLevel(String nextLevelID, boolean doLoadNextMap) {
        if (nextLevelID != null) {
            this.levelID = nextLevelID;
        }
    }
}