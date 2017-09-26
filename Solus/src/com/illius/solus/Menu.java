package com.illius.solus;

import java.util.Random;

import java.awt.Font;

import java.io.InputStream;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.Color;

import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;


public class Menu extends BasicGameState implements KeyListener {
    private UnicodeFont titleFont, optionsFont;
    private Random rng;
    private Input input;
    private int menuBarY = Engine.GAME_HEIGHT / 2 - 50;
    private boolean switchToGame = false;
    private boolean switchToSettings = false;
    final int PLAY = Engine.GAME_HEIGHT / 2 -50;
    final int HELP = PLAY + 100;
    final int QUIT = HELP + 100;
    private int stars[][] = new int[25][2];
    public static final int STATE_ID = 0;

    public int getID() {
        return STATE_ID;
    }
    public Menu() {
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        input = new Input(0);
        rng = new Random();
        for(int i=0; i<stars.length; i++) {
            stars[i][0] = rng.nextInt(Engine.GAME_WIDTH);
            stars[i][1] = rng.nextInt(Engine.GAME_HEIGHT);
        }
        Font loadingFont = null;
        Font loadingFont2 = null;
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("/res/PressStart2P.ttf");
            loadingFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            loadingFont2 = loadingFont;
            loadingFont = loadingFont.deriveFont(72f);
            loadingFont2 = loadingFont2.deriveFont(36f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        titleFont = new UnicodeFont(loadingFont);
        titleFont.getEffects().add(new ColorEffect(java.awt.Color.white));
        titleFont.addAsciiGlyphs();
        try {
            titleFont.loadGlyphs();
        } catch(SlickException e) {
            e.printStackTrace();
        }

        optionsFont = new UnicodeFont(loadingFont2);
        optionsFont.getEffects().add(new ColorEffect(java.awt.Color.white));
        optionsFont.addAsciiGlyphs();
        try {
            optionsFont.loadGlyphs();
        } catch(SlickException e) {
            e.printStackTrace();
        }
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setFont(titleFont);
        graphics.setColor(Color.white);
        for(int i = 0; i <stars.length; i++) {
            int size = rng.nextInt(4);
            graphics.fillOval(stars[i][0], stars[i][1], size, size);
        }
        graphics.drawString("SOLUS", Engine.GAME_WIDTH / 4 + 64, Engine.GAME_HEIGHT / 8);
        graphics.fillRoundRect(Engine.GAME_WIDTH/2 - 162.5f, menuBarY, 325,75,5);
        graphics.setFont(optionsFont);
        if(menuBarY == PLAY) {
            graphics.setColor(Color.black);
        } else {
            graphics.setColor(Color.white);
        }
        graphics.drawString("PLAY", Engine.GAME_WIDTH / 2 - 75, PLAY + 20);
        if (menuBarY == HELP) {
            graphics.setColor(Color.black);
        } else {
            graphics.setColor(Color.white);
        }
        graphics.drawString("SETTINGS", Engine.GAME_WIDTH / 2 - 140, HELP + 20);
        if(menuBarY ==QUIT) {
            graphics.setColor(Color.black);
        } else {
            graphics.setColor(Color.white);
        }
        graphics.drawString("QUIT", Engine.GAME_WIDTH / 2 - 75, QUIT + 20);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(switchToGame)
            stateBasedGame.enterState(Game.STATE_ID);
        for(int x = 0; x < stars.length; x++) {
            if(stars[x][0] >= 0)
                stars[x][0]--;
            else
                stars[x][0] = Engine.GAME_WIDTH-1;
        }

    }
    @Override
    public void keyPressed(int code, char c) {
        switch(code) {
            case Input.KEY_UP:
                if (menuBarY == PLAY)
                    menuBarY = QUIT;
                else
                    menuBarY -=100;
                break;
            case Input.KEY_DOWN:
                if(menuBarY == QUIT)
                    menuBarY = PLAY;
                else
                    menuBarY+=100;
                break;
            case Input.KEY_ENTER:
                if(menuBarY == PLAY)
                    switchToGame = true;
                else if(menuBarY == HELP)
                    switchToSettings = true;
                else if (menuBarY == QUIT)
                    //maybe prompt an 'are you sure you wanna quit' dialog
                    System.exit(0);
                break;
            default: break;
        }
    }
    @Override
    public void keyReleased(int code, char c) {

    }
}
