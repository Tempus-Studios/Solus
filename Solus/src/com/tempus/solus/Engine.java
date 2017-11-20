package com.tempus.solus;

import java.awt.Dimension;
import java.awt.Toolkit;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class Engine extends StateBasedGame implements KeyListener, Runnable {
    private static final Logger logger = Logger.getLogger(Engine.class.getName());
    private static AppGameContainer container;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SCREEN_WIDTH = (int) SCREEN_SIZE.getWidth();
    private static final int SCREEN_HEIGHT = (int) SCREEN_SIZE.getHeight();
    public static final int GAME_WIDTH = SCREEN_WIDTH / 2;
    public static final int GAME_HEIGHT = SCREEN_HEIGHT / 2 ;
    public static float MUSIC_VOLUME = 50;
    public static String GAME_STATE = "STATE_MENU";

    public Engine() {
        super("Solus");
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
    }

    public float getMusicVolume() {
        return MUSIC_VOLUME;
    }

    public int getFPS() {
        return container.getFPS();
    }

    @Override
    public void run() {
        try {
            container = new AppGameContainer(new Engine());
            container.setDisplayMode(GAME_WIDTH, GAME_HEIGHT,false);
            //TODO
            //container.setIcon();
            //container.setMouseCursor();
            container.setShowFPS(false);
            container.setVSync(true);
            container.setUpdateOnlyWhenVisible(false);
            container.start();
        } catch (SlickException ex) {
            logger.severe(ex.getMessage());
            container.exit();
            System.exit(0);
        }
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        //addState(new Menu());
        addState(new Game());
        //addState(new DeathScreen());
    }
}
