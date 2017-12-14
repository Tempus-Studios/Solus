package com.tempus.solus.menu;

import com.tempus.solus.Engine;
import com.tempus.solus.Solus;

import org.lwjgl.input.Mouse;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;


import java.awt.Font;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Menu extends BasicGameState implements KeyListener, MouseListener {
    private static final Logger logger = Logger.getLogger(Solus.class.getName());
    private int keyCode;
    private int newMouseX;
    private int newMouseY;
    private int clickedX;
    private int clickedY;
    private UnicodeFont titleFont;
    private UnicodeFont optionsFont;
    private Random starLocationGenerator;

    private int stars[][] = new int[25][2];
    private Input input;

    private MenuPage mainMenu;
    public static final int STATE_ID = 0;


    public Menu() {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        input = gameContainer.getInput();
        keyCode = -1;
        newMouseX = -1;
        newMouseX = -1;
        clickedX = -1;
        clickedY = -1;
        starLocationGenerator = new Random();
        for (int i=0; i<stars.length; i++) {
            stars[i][0] = starLocationGenerator.nextInt(Engine.GAME_WIDTH);
            stars[i][1] = starLocationGenerator.nextInt(Engine.GAME_HEIGHT);
        }
        Font loadingFont = null;
        Font loadingFont2 = null;
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("/res/PressStart2P.ttf");
            loadingFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            loadingFont2 = loadingFont;
            loadingFont = loadingFont.deriveFont((float)(Engine.GAME_WIDTH / (40 / 3)));
            loadingFont2 = loadingFont2.deriveFont((float) (Engine.GAME_WIDTH / (80 / 3)));
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
        titleFont = new UnicodeFont(loadingFont);
        titleFont.getEffects().add(new ColorEffect(java.awt.Color.white));
        titleFont.addAsciiGlyphs();
        try {
            titleFont.loadGlyphs();
        } catch(SlickException ex) {
            logger.severe(ex.getMessage());
        }
        optionsFont = new UnicodeFont(loadingFont2);
        optionsFont.getEffects().add(new ColorEffect(java.awt.Color.white));
        optionsFont.addAsciiGlyphs();
        try {
            optionsFont.loadGlyphs();
        } catch(SlickException ex) {
            logger.severe(ex.getMessage());
        }
        mainMenu = new MainMenu(stateBasedGame, titleFont, optionsFont);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        mainMenu.render(graphics);
        graphics.setColor(org.newdawn.slick.Color.white);
        for (int i = 0; i <stars.length; i++) {
            int size = starLocationGenerator.nextInt(Engine.GAME_WIDTH / 240);
            graphics.fillOval(stars[i][0], stars[i][1], size, size);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        if(input.isMousePressed(0)) {
            clickedX = input.getMouseX();
            clickedY = input.getMouseY();
        }
        mainMenu.update(keyCode, newMouseX, newMouseY, clickedX, clickedY, delta);
        keyCode = -1;
        for (int x = 0; x < stars.length; x++) {
            if (stars[x][0] >= 0) {
                stars[x][0]--;
            } else {
                stars[x][0] = Engine.GAME_WIDTH - 1;
            }
        }

    }
    //keyboard stuff
    @Override
    public void keyPressed(int code, char c) {
        keyCode = code;
        logger.info("" + code);
    }
    @Override
    public void keyReleased(int code, char c) {
        //TODO: TODO: TODO: TODO: TODO: TODO: TODO: Do key releasement of keys from being pressed to not being pressed therefore being released implementation
    }
    //mouse stuff
    @Override
    public void mouseMoved(int oldX, int oldY, int newX, int newY) {
        super.mouseMoved(oldX, oldY, newX, newY);
        newMouseX = newX;
        newMouseY = newY;

    }


}
