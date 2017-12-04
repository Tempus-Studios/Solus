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


    public void update (int delta) {


    }

    public void render(Graphics graphics) {
        float offsetX = this.getOffsetX();
        float offsetY = this.getOffsetY();

        graphics.scale(2,2);
        map.render((int)(offsetX%32),-320);
        graphics.scale(.5f,.5f);
    }

    public void setCurrentLevel(String nextLevelID, boolean doLoadNextMap) {
        if (nextLevelID != null) {
            this.levelID = nextLevelID;
        }
    }
    public float getMapWidth() {
        return map.getWidth()*2;
    }
    public float getMapHeight() {
        return map.getHeight()*2;
    }

    public float getOffsetX() {
        int offset_x = 0;

        //the first thing we are going to need is the half-width of the screen, to calculate if the player is in the middle of our screen
        int half_width = (int) (Engine.GAME_WIDTH/1/2);

        //next up is the maximum offset, this is the most right side of the map, minus half of the screen offcourse
        int xMax = (int) (map.getWidth()*32*2)-half_width;

        //now we have 3 cases here
        if(player.getXPos() < half_width){
            //the player is between the most left side of the map, which is zero and half a screen size which is 0+half_screen
            offset_x = 0;
        }else if(player.getXPos() > xMax){
            //the player is between the maximum point of scrolling and the maximum width of the map
            //the reason why we substract half the screen again is because we need to set our offset to the topleft position of our screen
            offset_x = xMax-half_width;
        }else{
            //the player is in between the 2 spots, so we set the offset to the player, minus the half-width of the screen
            offset_x = (int) (player.getXPos()-half_width);
        }

        return offset_x;
    }
    public float getOffsetY(){
        int offset_y = 0;

        int half_height = (int) (Engine.GAME_HEIGHT/1/2);

        int yMax = (int) (map.getHeight()*32*2)-half_height;

        if(player.getYPos() < half_height){
            offset_y = 0;
        }else if(player.getYPos() > yMax){
            offset_y = yMax-half_height;
        }else{
            offset_y = (int) (player.getYPos()-half_height);
        }

        return offset_y;
    }



}