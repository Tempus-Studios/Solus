package com.tempus.solus.menu;

import com.tempus.solus.Engine;

import com.tempus.solus.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainMenu extends MenuPage {
    private Music menuMusic;

    private float menuBarY;
    private static final float PLAY = Engine.GAME_HEIGHT / (35f / 11f);
    private static final float SETTINGS = PLAY + (Engine.GAME_HEIGHT / (540f / 100f));
    private static final float QUIT = SETTINGS + (Engine.GAME_HEIGHT / (540f / 100f));
    private static final RoundedRectangle PLAY_RECT = new RoundedRectangle(Engine.GAME_WIDTH / (1920f / 635f), PLAY, (Engine.GAME_WIDTH / (192f / 65f)), Engine.GAME_HEIGHT / (36f / 5f), 5);
    private static final RoundedRectangle SETTINGS_RECT = new RoundedRectangle(Engine.GAME_WIDTH / (1920f / 635f), SETTINGS, (Engine.GAME_WIDTH / (192f / 65f)), Engine.GAME_HEIGHT / (36f / 5f), 5);
    private static final RoundedRectangle QUIT_RECT = new RoundedRectangle(Engine.GAME_WIDTH / (1920f / 635f), QUIT, (Engine.GAME_WIDTH / (192f / 65f)), Engine.GAME_HEIGHT / (36f / 5f), 5);


    private enum MenuState {
        MAIN, SETTINGS
    }
    private MenuState state;
    private StateBasedGame stateBasedGame;
    private Settings settingsPage;

    public MainMenu(StateBasedGame gameThatIsStateBased, UnicodeFont title, UnicodeFont options) {
        super(title, options);
        this.init();
        //TODO: rename the sbg param
        stateBasedGame = gameThatIsStateBased;
    }
    private void reset() {
        state = MenuState.MAIN;
        menuBarY = Engine.GAME_HEIGHT / (35f / 11f);
    }

    protected void init() {
        menuBarY = Engine.GAME_HEIGHT / (35f / 11f);
        settingsPage = new Settings(titleFont, optionsFont);
        state = MenuState.MAIN;
    }

    public void update(int keyCode, int newMouseX, int newMouseY, int clickedX, int clickedY, int delta) {
        switch(state) {
            case MAIN: {
                this.handleKeyboardInput(keyCode);
                this.handleMouseInput(newMouseX, newMouseY, clickedX, clickedY);
            }
            break;
            case SETTINGS: {
                settingsPage.update(keyCode, newMouseX, newMouseY, clickedX, clickedY, delta);
            }
            break;
            default:
                break;
        }
    }

    public void render(Graphics graphics) {
        if(state.equals(MenuState.MAIN) || settingsPage.getCurrentState().equals(Settings.SettingsState.BACK)) {
            if(settingsPage.getCurrentState().equals(Settings.SettingsState.BACK)) {
                settingsPage.reset();
                this.reset();
            }
            graphics.setFont(titleFont);
            graphics.drawString("SOLUS", Engine.GAME_WIDTH / (60f / 19f), Engine.GAME_HEIGHT / 8f);
            if((int) menuBarY == (int) PLAY_RECT.getY()) {
                graphics.fill(PLAY_RECT);
            } else if ((int) menuBarY == (int) SETTINGS_RECT.getY()) {
                graphics.fill(SETTINGS_RECT);
            } else {
                graphics.fill(QUIT_RECT);
            }
            graphics.setFont(optionsFont);

            if ((int) menuBarY == (int) PLAY) {
                graphics.setColor(Color.black);
            } else {
                graphics.setColor(Color.white);
            }
            graphics.drawString("PLAY", Engine.GAME_WIDTH / (960f / 405f), PLAY + (Engine.GAME_HEIGHT / 27));
            if ((int) menuBarY == (int) SETTINGS) {
                graphics.setColor(Color.black);
            } else {
                graphics.setColor(Color.white);
            }
            graphics.drawString("SETTINGS", Engine.GAME_WIDTH / (960f / 340f), SETTINGS + (Engine.GAME_HEIGHT / 27));
            if ((int) menuBarY == (int) QUIT) {
                graphics.setColor(Color.black);
            } else {
                graphics.setColor(Color.white);
            }
            graphics.drawString("QUIT", Engine.GAME_WIDTH / (960f / 405f), QUIT + (Engine.GAME_HEIGHT / 27));
        } else if(state.equals(MenuState.SETTINGS) && !(settingsPage.getCurrentState().equals(Settings.SettingsState.BACK))) {
            settingsPage.render(graphics);
        }
    }
    private void handleKeyboardInput(int keyCode) {
        switch (keyCode) {
            case Input.KEY_UP: {
                if ((int) menuBarY != (int) PLAY) {
                    menuBarY -= Engine.GAME_HEIGHT / (540f / 100f);
                } else {
                    menuBarY = QUIT;
                }

            }
            break;
            case Input.KEY_DOWN: {
                if ((int) menuBarY != (int) QUIT) {
                    menuBarY += Engine.GAME_HEIGHT / (540f / 100f);
                } else {
                    menuBarY = PLAY;
                }

            }
            break;
            case Input.KEY_ENTER: {
                if ((int) menuBarY == (int) PLAY) {
                    stateBasedGame.enterState(Game.STATE_ID,new FadeOutTransition(), new FadeInTransition());
                } else if ((int) menuBarY == (int) SETTINGS) {
                    state = MenuState.SETTINGS;
                } else if ((int) menuBarY == (int) QUIT) {
                    //TODO: Do da ting with system.exit(zero);
                }
            }
            break;
            default:
                break;
        }
    }
    private void handleMouseInput(int newX, int newY, int clickedX, int clickedY) {
        if(PLAY_RECT.contains(newX, newY)) {
            menuBarY = PLAY;
        } else if(PLAY_RECT.contains(clickedX, clickedY)) {
            stateBasedGame.enterState(Game.STATE_ID);
        } else if(SETTINGS_RECT.contains(newX, newY)) {
            menuBarY = SETTINGS;
        } else if(SETTINGS_RECT.contains(clickedX, clickedY)) {
            state = MenuState.SETTINGS;
        } else if (QUIT_RECT.contains(newX, newY)) {
            menuBarY = QUIT;
        } else if (QUIT_RECT.contains(clickedX, clickedY)) {
            //TODO: exit solus
        }
    }
}
