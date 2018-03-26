package com.tempus.solus.map;


import com.tempus.solus.Engine;
import com.tempus.solus.entity.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Level {
    private static final Logger logger = Logger.getLogger(Engine.class.getName());
    private static List<Image> background = new ArrayList<>();
    private BufferedWriter writer;
    private static BufferedReader reader;
    private static TiledMap map;
    private static String levelID;
    private static String levelPath;
    private float yAcc;
    public Tile[][] tiles;

    public Level() {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
        this.init();
    }

    private void init() {
        yAcc = 0.4f;
        try {
            int backgroundSpace = 0;

            while (backgroundSpace < 96 * 32 * 2) {
                background.add(new Image("/res/level/earth-background.png"));
                backgroundSpace += background.get(0).getWidth();
            }
        } catch(SlickException ex) {
            logger.severe(ex.getMessage());
        }
        try {
            reader = new BufferedReader(new FileReader("res/level/level-info.txt"));
        } catch (IOException ex) {
            logger.severe(ex.getMessage());
        }
        this.loadMap();
    }

    private void loadMap() {
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
        this.levelPath = "res/level/level-" + levelID + ".tmx";
        try {
            this.map = new TiledMap(levelPath);
            tiles = new Tile[96][10];
            int tileId;
            for(int x = 0; x < tiles.length; x++) {
                for(int y = 0; y < tiles[x].length; y++) {
                    tiles[x][y] = new Tile(x * Tile.WIDTH, y * Tile.HEIGHT);
                    tileId = map.getTileId(x, y, 0);
                    if(map.getTileProperty(tileId, "blocked", "false").equalsIgnoreCase("true")) {
                        tiles[x][y].setSolid(true);
                    } else {
                        tiles[x][y].setSolid(false);
                    }
                }
            }
        } catch (SlickException ex) {
            logger.severe("Failed to load map\n" + ex.getMessage());
        }
    }

    public void update(int delta) {
    }
    public void checkCollision(Rectangle foo) {
        //3 x 4 tile - player occupancy rectangles

    }
    public void render(Graphics graphics) {
        //parallax scrolling background
        for(int i = 0; i < background.size(); i ++) {
            background.get(i).draw(i * background.get(i).getWidth(), -(background.get(i).getHeight() / 5), 2);
        }
        //scrolling map
        graphics.scale(2,2);
        map.render(0,-32);
        graphics.scale(.5f,.5f);
    }

    public void setCurrentLevel(String nextLevelID, boolean doLoadNextMap) {
        if (nextLevelID != null) {
            this.levelID = nextLevelID;
        }
    }
    public float getYAcc() {
        return yAcc;
    }

    public int getID() {
        return Integer.valueOf(levelID);
    }


}