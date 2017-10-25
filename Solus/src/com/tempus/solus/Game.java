package com.tempus.solus;

import com.tempus.solus.entity.Player;

import java.awt.Font;

import java.io.InputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

public class Game extends BasicGameState implements KeyListener{
    private static final Logger logger = Logger.getLogger(Solus.class.getName());
    private Engine engine;
    private WeaponLoader weaponLoader;
    private Player player;
    private Image healthIcon;
    private Image sprintIcon;
    private Image ammoIcon;
    private SpriteSheet weaponLeftSheet;
    private SpriteSheet weaponRightSheet;
    private SpriteSheet tankLeftSheet;
    private SpriteSheet tankRightSheet;
    private Animation playerLeftAnimation;
    private Animation playerRightAnimation;
    private Animation weaponLeftAnimation;
    private Animation weaponRightAnimation;
    private Animation tankLeftAnimation;
    private Animation tankRightAnimation;
    private boolean isPaused;
    private boolean isRestartRequested;
    private boolean isFired;
    private boolean isEnemyAggro;
    private boolean enemyFacingLeft;
    public Font loadingFont;
    public Font loadingFont2;
    public UnicodeFont font;
    public UnicodeFont fpsFont;
    private float enemyPos;
    private int timeElapsed;
    private int fps;
    private int shotsFiredSA;
    private int shotsFiredA;
    public static final int STATE_ID = 1;

    public Game() {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
    }

    @Override
    public int getID() {
        return STATE_ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        engine = new Engine();
        player = new Player();
        weaponLoader = new WeaponLoader();
        loadingFont = null;
        loadingFont2 = null;
        isPaused = false;
        isRestartRequested = false;
        isFired = false;
        isEnemyAggro = false;
        enemyFacingLeft = true;
        enemyPos = Engine.GAME_WIDTH - 192;
        shotsFiredA = 0;
        shotsFiredSA = 0;
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("/res/PressStart2P.ttf");
            loadingFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            loadingFont2 = loadingFont;
            loadingFont = loadingFont.deriveFont(28f);
            loadingFont2 = loadingFont2.deriveFont(12f);
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
        font = new UnicodeFont(loadingFont);
        font.getEffects().add(new ColorEffect(null));
        font.addAsciiGlyphs();
        try {
            font.loadGlyphs();
        } catch(SlickException ex) {
            logger.severe(ex.getMessage());
        }
        fpsFont = new UnicodeFont(loadingFont2);
        fpsFont.getEffects().add(new ColorEffect(null));
        fpsFont.addAsciiGlyphs();
        try {
            fpsFont.loadGlyphs();
        } catch(SlickException ex) {
            logger.severe(ex.getMessage());
        }
        healthIcon = new Image("/res/sprite/icons/HealthIcon.png");
        sprintIcon = new Image("/res/sprite/icons/SprintEnergyIcon.png");
        ammoIcon = new Image("/res/sprite/icons/AmmoIcon.png");
        weaponLeftSheet = new SpriteSheet(weaponLoader.getEquippedWeapon().getPath()[0], 32,32);
        weaponRightSheet = new SpriteSheet(weaponLoader.getEquippedWeapon().getPath()[1], 32,32);
        weaponLeftAnimation = new Animation(weaponLeftSheet, 100);
        weaponRightAnimation = new Animation(weaponRightSheet, 100);
        weaponLeftAnimation.setAutoUpdate(false);
        weaponRightAnimation.setAutoUpdate(false);
        tankLeftSheet = new SpriteSheet("/res/sprite/tank-left.png", 32, 32);
        tankRightSheet = new SpriteSheet("/res/sprite/tank-right.png", 32, 32);
        tankLeftAnimation = new Animation(tankLeftSheet, 200);
        tankRightAnimation = new Animation(tankRightSheet, 200);
        tankLeftAnimation.setAutoUpdate(true);
        tankRightAnimation.setAutoUpdate(true);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        if (player.isAlive() && !isPaused) {
            player.update(stateBasedGame, delta);
            if (isFired) {
                //semi-auto
                if (!weaponLoader.getEquippedWeapon().isAutomatic()) {
                    if (player.isFacingLeft()) {
                        weaponLeftAnimation.restart();
                        weaponLeftAnimation.setAutoUpdate(isFired);
                        weaponLeftAnimation.stopAt(2);
                        shotsFiredSA++;
                        logger.info("Fired semi-automatically: " + shotsFiredSA);
                        isFired = false;
                    } else {
                        weaponRightAnimation.restart();
                        weaponRightAnimation.setAutoUpdate(isFired);
                        weaponRightAnimation.stopAt(2);
                        shotsFiredSA++;
                        logger.info("Fired semi-automatically: " + shotsFiredSA);
                        isFired = false;
                    }
                    //automatic
                } else {
                    if (player.isFacingLeft()) {
                        weaponLeftAnimation.setAutoUpdate(isFired);
                        if (weaponLeftAnimation.getFrame() == 1) {
                            weaponLoader.getEquippedWeapon().decrementAmmo();
                            logger.info("Fired automatically: " + shotsFiredA);
                            shotsFiredA++;
                        }
                    } else {
                        weaponRightAnimation.setAutoUpdate(isFired);
                        if (weaponRightAnimation.getFrame() == 1) {
                            weaponLoader.getEquippedWeapon().decrementAmmo();
                            logger.info("Fired automatically: " + shotsFiredA);
                            shotsFiredA++;
                        }
                    }
                }
            }
            //Player motion
            if (enemyPos <= 32) {
                enemyFacingLeft = false;
            }
            if (enemyPos >= Engine.GAME_WIDTH - 192) {
                enemyFacingLeft = true;
            }
            if (enemyFacingLeft) {
                enemyPos--;
            } else {
                enemyPos++;
            }
            if (Math.abs((enemyPos - player.getXPos())) <= 448) {
                isEnemyAggro = true;
            } else {
                isEnemyAggro = false;
            }
            player.healPlayer(0.025f);
            if (isEnemyAggro) {
                if (timeElapsed >= 1000) {
                    player.damagePlayer(0.2f);
                }
            }
            timeElapsed += delta;
            fps = engine.getFPS();

        } else {
            if (!player.isAlive()) {
                if (isRestartRequested) {
                    stateBasedGame.getState(Game.STATE_ID).init(gameContainer, stateBasedGame);
                    stateBasedGame.enterState(Game.STATE_ID);
                }
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        //Set font
        if (player.isAlive() && !isPaused) {
            //Draw background
            graphics.setColor(Color.white);
            graphics.fillRect(0, 0, Engine.GAME_WIDTH, Engine.GAME_HEIGHT);
            graphics.setColor(Color.gray);
            graphics.fillRoundRect(64, 28, 320, 32, 8, 100);
            if (player.getPlayerHealth() >= 50) {
                graphics.setColor(Color.green);
            } else {
                if (player.getPlayerHealth() >= 20) {
                    graphics.setColor(Color.yellow);
                } else {
                    graphics.setColor(Color.red);
                }
            }
            if (player.getPlayerHealth() > 1.5 ) {
                for (int i = 0; i < 6; i++) {
                    graphics.fillRoundRect(64, 28, (float) ((player.getPlayerHealth() * 3.2)), 32, 8, 100);
                }
            }
            graphics.setColor(Color.red);
            graphics.setFont(font);
            graphics.drawString("+", 16, 32);
            graphics.setColor(Color.black);
            graphics.setFont(fpsFont);
            graphics.drawString("FPS:" + fps, Engine.GAME_WIDTH - 80, Engine.GAME_HEIGHT - 16);
            //graphics.drawString("Time: " + timeElapsed / 1000, Engine.GAME_WIDTH / 2, Engine.GAME_HEIGHT / 2);
            //Render sprites
            if (enemyFacingLeft) {
                tankLeftAnimation.draw(enemyPos, Engine.GAME_HEIGHT - 224, 192, 192);
            } else {
                tankRightAnimation.draw(enemyPos, Engine.GAME_HEIGHT - 224, 192, 192);
            }
            player.render(graphics);
            if (player.isFacingLeft()) {
                weaponLeftAnimation.draw(player.getXPos() - 40, player.getYPos() - 35, 128, 128);
            } else {
                if (player.isFacingRight()) {
                    weaponRightAnimation.draw(player.getXPos() + 40, player.getYPos() - 35, 128, 128);
                }
            }
        } else {
            if (isPaused) {
                graphics.setColor(Color.black);
                graphics.fillRect(0, 0, Engine.GAME_WIDTH, Engine.GAME_HEIGHT);
                graphics.setColor(Color.white);
                graphics.setFont(font);
                graphics.drawString("PAUSED", Engine.GAME_WIDTH / 2 - 72, Engine.GAME_HEIGHT / 2 - 96);
            }
            if (!player.isAlive()) {
                graphics.setColor(Color.black);
                graphics.fillRect(0, 0, Engine.GAME_WIDTH, Engine.GAME_HEIGHT);
                graphics.setColor(Color.white);
                graphics.setFont(font);
                graphics.drawString("YOU DIED!", Engine.GAME_WIDTH / 2 - 106, Engine.GAME_HEIGHT / 2 - 96);
            }
        }
    }

    @Override
    public void keyPressed(int code, char c) {
        switch (code) {
            case Input.KEY_LEFT: {
                player.setFacingLeft(true);
                player.setFacingRight(false);
                player.setMovingLeft(true);
                player.setMovingRight(false);
                break;
            }
            case Input.KEY_RIGHT: {
                player.setFacingLeft(false);
                player.setFacingRight(true);
                player.setMovingLeft(false);
                player.setMovingRight(true);
                break;
            }
            case Input.KEY_UP: {
                player.setJumping(true);
                break;
            }
            case Input.KEY_LSHIFT: {
                player.setSprintRequested(true);
                break;
            }
            case Input.KEY_SPACE: {
                player.setSprintRequested(false);
                isFired = true;
                break;
            }
            case Input.KEY_1: {
                weaponLoader.setWeapon(weaponLoader.getWeaponAt(0));
                try {
                    weaponLeftSheet = new SpriteSheet(weaponLoader.getEquippedWeapon().getPath()[0], 32, 32);
                    weaponRightSheet = new SpriteSheet(weaponLoader.getEquippedWeapon().getPath()[1], 32, 32);
                } catch (SlickException ex) {
                    logger.severe(ex.getMessage());
                }
                weaponLeftAnimation = new Animation(weaponLeftSheet, 100);
                weaponRightAnimation = new Animation(weaponRightSheet, 100);
                weaponLeftAnimation.setAutoUpdate(false);
                weaponRightAnimation.setAutoUpdate(false);
                break;
            }
            case Input.KEY_2: {
                weaponLoader.setWeapon(weaponLoader.getWeaponAt(1));
                try {
                    weaponLeftSheet = new SpriteSheet(weaponLoader.getEquippedWeapon().getPath()[0], 32, 32);
                    weaponRightSheet = new SpriteSheet(weaponLoader.getEquippedWeapon().getPath()[1], 32, 32);
                } catch (SlickException ex) {
                    logger.severe(ex.getMessage());
                }
                weaponLeftAnimation = new Animation(weaponLeftSheet, 100);
                weaponRightAnimation = new Animation(weaponRightSheet, 100);
                weaponLeftAnimation.setAutoUpdate(false);
                weaponRightAnimation.setAutoUpdate(false);
                break;
            }
            case Input.KEY_ESCAPE: {
                if (!isPaused) {
                    isPaused = true;
                } else {
                    isPaused = false;
                }
                break;
            }
            case Input.KEY_ENTER: {
                if (!player.isAlive()) {
                    isRestartRequested = true;
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    @Override
    public void keyReleased(int code, char c) {
        switch (code) {
            case Input.KEY_LEFT: {
                player.setMovingLeft(false);
                break;
            }
            case Input.KEY_RIGHT: {
                player.setMovingRight(false);
                break;
            }
            case Input.KEY_SPACE: {
                isFired = false;
                if (weaponLoader.getEquippedWeapon().isAutomatic()) {
                    weaponLeftAnimation.setAutoUpdate(isFired);
                    weaponRightAnimation.setAutoUpdate(isFired);
                }
                weaponLeftAnimation.setCurrentFrame(0);
                weaponRightAnimation.setCurrentFrame(0);
                break;
            }
            case Input.KEY_LSHIFT: {
                player.setSprintRequested(false);
                break;
            }
            default: {
                break;
            }
        }
    }
}