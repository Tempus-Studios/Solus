package com.tempus.solus.map;

import com.tempus.solus.Engine;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Level {
    private static final Logger logger = Logger.getLogger(Engine.class.getName());
    public String levelName;
    public String levelPath;
    private TiledMap map;





    public Level(String levelName, String mapPath) {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);

        this.levelName = levelName;
        try {
            map = new TiledMap(mapPath);
        } catch (SlickException ex) {
            logger.severe(ex.getMessage());
            logger.info("INVALID MAP PATH");
        }
    }

    public void render(Graphics graphics , int x, int y) {
        graphics.scale(2,2);
        map.render(x,y);
        graphics.scale(.5f,.5f);
    }

}