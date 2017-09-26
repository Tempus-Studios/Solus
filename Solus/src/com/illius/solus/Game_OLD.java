package com.illius.solus;

import com.illius.solus.entity.Player_OLD;
import com.illius.solus.util.GameStateManager;

import java.awt.Toolkit;
import java.awt.Dimension;

import org.lwjgl.Sys;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Game_OLD implements Runnable {
    public GameStateManager gameStateManager = new GameStateManager();
    public Player_OLD player = new Player_OLD("Bob");
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREEN_WIDTH = (int) SCREEN_SIZE.getWidth();
    public static final int SCREEN_HEIGHT = (int) SCREEN_SIZE.getHeight();
    public static final int GAME_WIDTH = SCREEN_WIDTH / 2;
    public static final int GAME_HEIGHT = SCREEN_HEIGHT / 2 ;
    public boolean hasSecondPassed;
    public boolean isPaused = false;
    public int fps;
    public int delta;
    public long lastFrame;
    public long lastFPS;

    public void run() {
        initGL();
        lastFPS = getTime();
        while (!Display.isCloseRequested()) {
            if (!player.isDead) {
                if (!isPaused) {
                    render();
                    update();
                    updateFPS();
                    Display.update();
                    Display.sync(60);
                } else {
                    gameStateManager.setGameState(GameStateManager.GameState.GAME, GameStateManager.GameState.PAUSED);
                }
            } else {
                gameStateManager.setGameState(GameStateManager.GameState.GAME, GameStateManager.GameState.DEAD);
            }
        }
        Display.destroy();
        System.exit(0);
    }

    public void initGL() {
        try {
            System.out.println("Loading LWJGL " + Sys.getVersion());
        } catch (Exception ex) {
            System.out.println("Unable to load LWJGL. This is a fatal error-- please provide the following error report to the developers:\n");
            ex.printStackTrace();
            System.exit(0);
        }
        try {
            Display.setDisplayMode(new DisplayMode(GAME_WIDTH, GAME_HEIGHT));
            Display.setInitialBackground(1, 1, 1);
            Display.create();
            Display.setVSyncEnabled(true);
            Display.setTitle("The Adventures of Bob");
            System.out.println("Loading OpenGL " + GL11.glGetString(GL11.GL_VERSION));
        } catch (LWJGLException ex) {
            System.out.println("Unable to load OpenGL. This is a fatal error-- please provide the following error report to the developers:\n");
            ex.printStackTrace();
            System.exit(0);
        }
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glViewport(0, 0, GAME_WIDTH, GAME_HEIGHT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, GAME_WIDTH, 0, GAME_HEIGHT, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    public void update() {
        if (!player.isDead) {
            player.update();
        }
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        if (!player.isDead) {
            player.render();
        } else {
            //call death screen
        }
    }

    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            System.out.println("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
            hasSecondPassed = true;
        } else {
            hasSecondPassed = false;
        }
        fps++;
    }

    public int getFps() {
        return fps;
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    //may or may not use this
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
        return delta;
    }
}
