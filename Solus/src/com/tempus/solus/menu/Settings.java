package com.tempus.solus.menu;


import com.tempus.solus.Engine;
import com.tempus.solus.Solus;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.RoundedRectangle;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Settings extends MenuPage {
    private static final Logger logger = Logger.getLogger(Solus.class.getName());
    protected enum SettingsState {
        SETTINGS_MAIN, ABOUT, CONTROLS, AUDIO_VIDEO, BACK
    }
    private SettingsState settingsState = SettingsState.SETTINGS_MAIN;
    private float menuBarY;
    private MenuPage avPage;
    private MenuPage aboutPage;
    private MenuPage controlsPage;
    public static float CONTROLS = Engine.GAME_HEIGHT / (35f / 11f);;
    public static float AUDIO_VIDEO = CONTROLS + Engine.GAME_HEIGHT / (540f / 75f);
    public static float ABOUT = AUDIO_VIDEO + Engine.GAME_HEIGHT / (540f / 75f);
    public static float BACK = ABOUT + Engine.GAME_HEIGHT / (540f / 75f);

    public Settings( UnicodeFont title, UnicodeFont options) {
        super(title, options);
        this.init();
    }

    @Override
    protected void init() {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
        menuBarY = Engine.GAME_HEIGHT / (35f / 11f);
        //TODO: init about, controls, and audio/video pages
    }
    protected SettingsState getCurrentState() {
        return settingsState;
    }
    protected void reset() {
        settingsState = SettingsState.SETTINGS_MAIN;
        menuBarY = Engine.GAME_HEIGHT / (35f / 11f);
    }
    @Override
    public void render(Graphics graphics) {
        if(settingsState.equals(SettingsState.SETTINGS_MAIN)) {
            graphics.setFont(titleFont);
            graphics.drawString("SETTINGS", Engine.GAME_WIDTH / (960f / 200f), Engine.GAME_HEIGHT / 8f);
            graphics.fillRoundRect(Engine.GAME_WIDTH / (960f / 260f), menuBarY, Engine.GAME_WIDTH / (960f / 440f), Engine.GAME_HEIGHT / (36f / 5f), 5);
            graphics.setFont(optionsFont);
            if ((int) menuBarY == (int) CONTROLS) {
                graphics.setColor(Color.black);
            } else {
                graphics.setColor(Color.white);
            }
            graphics.drawString("CONTROLS", Engine.GAME_WIDTH / (960f / 340f), CONTROLS + (Engine.GAME_HEIGHT / 27));
            if ((int) menuBarY == (int) AUDIO_VIDEO) {
                graphics.setColor(Color.black);
            } else {
                graphics.setColor(Color.white);
            }
            graphics.drawString("AUDIO/VIDEO", Engine.GAME_WIDTH / (960f / 285f), AUDIO_VIDEO + (Engine.GAME_HEIGHT / 27));
            if ((int) menuBarY == (int) ABOUT) {
                graphics.setColor(Color.black);
            } else {
                graphics.setColor(Color.white);
            }
            graphics.drawString("ABOUT", Engine.GAME_WIDTH / (960f / 390f), ABOUT + (Engine.GAME_HEIGHT / 27));
            if ((int) menuBarY == (int) BACK) {
                graphics.setColor(Color.black);
            } else {
                graphics.setColor(Color.white);
            }
            graphics.drawString("BACK", Engine.GAME_WIDTH / (960f / 405f), BACK + (Engine.GAME_HEIGHT / 27));
        } else if(settingsState.equals(SettingsState.CONTROLS)) {
            //controlsPage.render(graphics);
        } else if(settingsState.equals(SettingsState.AUDIO_VIDEO)) {
            //avPage.render(graphics);
        } else if(settingsState.equals(SettingsState.ABOUT)) {
            //aboutPage.render(graphics);
        }

    }

    //@Override
    public void update(int keyCode, int newMouseX, int newMouseY, int clickedX, int clickedY, int delta) {
        switch(settingsState) {
            case SETTINGS_MAIN: {
                this.handleKeyboardInput(keyCode);
                this.handleMouseInput(newMouseX, newMouseY, clickedX, clickedY);
            }
            break;
            case ABOUT: {
                //TODO: update about page
            }
            break;
            case CONTROLS: {
                //TODO: update controls page
            }
            break;
            case AUDIO_VIDEO: {
                //TODO: update a/v page
            }
            break;
            default:
                break;
        }
    }
    private void handleKeyboardInput(int keyCode) {
        switch(keyCode) {
            case Input.KEY_UP: {
                if((int) menuBarY != (int) CONTROLS) {
                    menuBarY -= Engine.GAME_HEIGHT / (540f / 75f);
                } else {
                    menuBarY = BACK;
                }
            }
            break;
            case Input.KEY_DOWN: {
                if((int) menuBarY != (int) BACK) {
                    menuBarY += Engine.GAME_HEIGHT / (540f / 75f);
                } else {
                    menuBarY = CONTROLS;
                }
            }
            break;
            case Input.KEY_ENTER: {
                if((int) menuBarY == (int) CONTROLS) {
                    //TODO: switch to controls page
                } else if((int) menuBarY == (int) AUDIO_VIDEO) {
                    //TODO: switch to a/v page
                } else if((int) menuBarY == (int) ABOUT) {
                    //TODO: switch to about page
                } else if((int) menuBarY == (int) BACK) {
                    settingsState = SettingsState.BACK;
                }
            }
            break;
            default:
                break;
        }
    }
    private void handleMouseInput(int newX, int newY, int clickedX, int clickedY) {
        if(new RoundedRectangle(Engine.GAME_WIDTH / (960f / 260f), CONTROLS, Engine.GAME_WIDTH / (960f / 440f), Engine.GAME_HEIGHT / (36f / 5f), 5).contains(newX, newY)) {
            menuBarY = CONTROLS;
        } else if(new RoundedRectangle(Engine.GAME_WIDTH / (960f / 260f), AUDIO_VIDEO, Engine.GAME_WIDTH / (960f / 440f), Engine.GAME_HEIGHT / (36f / 5f), 5).contains(newX, newY)) {
            menuBarY = AUDIO_VIDEO;
        } else if(new RoundedRectangle(Engine.GAME_WIDTH / (960f / 260f), ABOUT, Engine.GAME_WIDTH / (960f / 440f), Engine.GAME_HEIGHT / (36f / 5f), 5).contains(newX, newY)) {
            menuBarY = ABOUT;
        } else if(new RoundedRectangle(Engine.GAME_WIDTH / (960f / 260f), BACK, Engine.GAME_WIDTH / (960f / 440f), Engine.GAME_HEIGHT / (36f / 5f), 5).contains(newX, newY)) {
            menuBarY = BACK;
        }
    }
}
