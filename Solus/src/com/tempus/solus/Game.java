package com.tempus.solus;

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
    private SpriteSheet playerLeftSheet;
    private SpriteSheet playerRightSheet;
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
    private boolean isAlive;
    private boolean isRestartRequested;
    private boolean isMovingLeft;
    private boolean isMovingRight;
    private boolean isFacingLeft;
    private boolean isFacingRight;
    private boolean isSprinting;
    private boolean isJumping;
    private boolean isSprintRequested;
    private boolean isFired;
    private boolean isEnemyAggro;
    private boolean enemyFacingLeft;
    public Font loadingFont;
    public Font loadingFont2;
    public UnicodeFont font;
    public UnicodeFont fpsFont;
    public static float aGravity;
    public float xPos;
    public float yPos;
    public float xVel;
    public float yVel;
    public float playerHealth;
    private float sprintEnergy;
    private float sprintMultiplier;
    private float enemyPos;
    private int timeElapsed;
    private int fps;
    private int shotsFired;
    private boolean foo1;
    private boolean foo2;
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
        isPaused = false;
        isAlive = true;
        isRestartRequested = false;
        isMovingLeft = false;
        isMovingRight = false;
        isFacingLeft = false;
        isFacingRight = true;
        isSprinting = false;
        isJumping = false;
        isSprintRequested = false;
        isFired = false;
        isEnemyAggro = false;
        enemyFacingLeft = true;
        aGravity = 0.4f;
        xPos = 32;
        yPos = Engine.GAME_HEIGHT - 160;
        xVel = 0;
        yVel = 0;
        playerHealth = 100;
        sprintEnergy = 100;
        sprintMultiplier = 1;
        enemyPos = Engine.GAME_WIDTH - 192;
        engine = new Engine();
        loadingFont = null;
        loadingFont2 = null;
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
        playerLeftSheet = new SpriteSheet("/res/sprite/player-left.png", 32, 32);
        playerRightSheet = new SpriteSheet("/res/sprite/player-right.png", 32, 32);
        playerLeftAnimation = new Animation(playerLeftSheet, 150);
        playerRightAnimation = new Animation(playerRightSheet, 150);
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
        if (isAlive && !isPaused) {
            if (xPos < 32) {
                xPos = 32;
            }
            if (xPos > Engine.GAME_WIDTH - 160) {
                xPos = Engine.GAME_WIDTH - 160;
            }
            if (yPos > Engine.GAME_HEIGHT - 160) {
                yPos = Engine.GAME_HEIGHT - 160;
            }
            //Moving left
            if (isMovingLeft) {
                isFacingLeft = true;
                xVel = -3;
            }
            //Moving right
            if (isMovingRight) {
                isFacingRight = true;
                xVel = 3;
            }
            //Standing still
            if (isMovingLeft && isMovingRight) {
                xVel = 0;
            }
            if (!isMovingLeft && !isMovingRight) {
                xVel = 0;
            }
            if (xVel == 0 || yPos < Engine.GAME_HEIGHT - 160) {
                playerLeftAnimation.setAutoUpdate(false);
                playerRightAnimation.setAutoUpdate(false);
                playerLeftAnimation.setCurrentFrame(0);
                playerRightAnimation.setCurrentFrame(0);
            } else {
                playerLeftAnimation.setAutoUpdate(true);
                playerRightAnimation.setAutoUpdate(true);
            }
            if (yPos < Engine.GAME_HEIGHT - 160) {
                yVel += aGravity;
            }

            if (isFacingLeft) {
                if (!isMovingLeft) {
                    if (xVel < 0) {
                        xVel += 0.01;
                    }
                }
            }
            if (isFacingRight) {
                if (!isMovingRight) {
                    if (xVel > 0) {
                        xVel -= 0.01;
                    }
                }
            }
            //Jumping
            if (isJumping) {
                if (yPos == Engine.GAME_HEIGHT - 160) {
                    yVel = -9;
                }
                isJumping = false;
            }
            //Sprinting
            if (sprintEnergy > 100) {
                sprintEnergy = 100;
            }
            if (sprintEnergy < 0) {
                sprintEnergy = 0;
            }
            if (sprintEnergy > 0) {
                if (!(!isMovingLeft && !isMovingRight)) {
                    if (isSprintRequested) {
                        isSprinting = true;
                    } else {
                        isSprinting = false;
                    }
                }
            }
            if (isSprinting) {
                if (yPos == Engine.GAME_HEIGHT - 160) {
                    playerLeftAnimation.setDuration(1, 125);
                    playerRightAnimation.setDuration(1, 125);
                    sprintEnergy -= -0.1;
                    sprintMultiplier = 2;
                }
            } else {
                playerLeftAnimation.setDuration(1, 175);
                playerRightAnimation.setDuration(1, 175);
                sprintMultiplier = 1;
            }
            /*if (isFired) {
                logger.info(   "Fired weapon");
                if (isFacingLeft) {
                    infioLeftAnimation.restart();
                    infioLeftAnimation.setAutoUpdate(isFired);
                    infioLeftAnimation.stopAt(2);
                } else {
                    infioRightAnimation.restart();
                    infioRightAnimation.setAutoUpdate(isFired);
                    infioRightAnimation.stopAt(2);
                }
                isFired = false;
            }*/
            if(isFired) {
                //semi-auto
                if (!weaponLoader.getEquippedWeapon().isAutomatic()) {
                    if (isFacingLeft) {
                        weaponLeftAnimation.restart();
                        weaponLeftAnimation.setAutoUpdate(isFired);
                        weaponLeftAnimation.stopAt(2);
                        shotsFired += 1;
                        logger.info("Fired semi-automatically: " + shotsFired);
                        isFired = false;
                    } else {
                        weaponRightAnimation.restart();
                        weaponRightAnimation.setAutoUpdate(isFired);
                        weaponRightAnimation.stopAt(2);
                        shotsFired += 1;
                        logger.info("Fired semi-automatically: " + shotsFired);
                        isFired = false;
                    }
                    //automatic
                } else {
                    if (isFacingLeft) {
                        weaponLeftAnimation.setAutoUpdate(isFired);
                        if (weaponLeftAnimation.getFrame() == 1) {
                            weaponLoader.getEquippedWeapon().decrementAmmo();
                            shotsFired += 1;
                        }
                    } else {
                        weaponRightAnimation.setAutoUpdate(isFired);
                        if(weaponRightAnimation.getFrame() == 1) {
                            weaponLoader.getEquippedWeapon().decrementAmmo();
                            shotsFired += 1;
                        }
                    }
                }
            }
            //Player motion
            xVel *= sprintMultiplier;
            xPos += xVel;
            yPos += yVel;
            if (!isSprinting) {
                sprintEnergy++;
            }
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
            if (Math.abs((enemyPos - xPos)) <= 448) {
                isEnemyAggro = true;
            } else {
                isEnemyAggro = false;
            }
            playerHealth += 0.025;
            if (isEnemyAggro) {
                if (timeElapsed >= 1000) {
                    playerHealth -= 0.2;
                    //timeElapsed = 0;
                }
            }
            if (playerHealth < 0) {
                playerHealth = 0;
            }
            if (playerHealth > 100) {
                playerHealth = 100;
            }
            if (playerHealth == 0) {
                isAlive = false;
            }
            timeElapsed += delta;
            fps = engine.getFPS();
        } else {
            if (isPaused) {
                //TODO: pause menu
            }
            if (!isAlive) {
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
        if (isAlive && !isPaused) {
            //Draw background
            graphics.setColor(Color.white);
            graphics.fillRect(0, 0, Engine.GAME_WIDTH, Engine.GAME_HEIGHT);
            graphics.setColor(Color.gray);
            graphics.fillRoundRect(64, 28, 320, 32, 8, 100);
            //TODO: make color transitions
            if (playerHealth >= 50) {
                graphics.setColor(Color.green);
            } else {
                if (playerHealth >= 20) {
                    graphics.setColor(Color.yellow);
                } else {
                    graphics.setColor(Color.red);
                }
            }
            if (playerHealth > 1.5 ) {
                for (int i = 0; i < 6; i++) {
                    graphics.fillRoundRect(64, 28, (float) ((playerHealth * 3.2)), 32, 8, 100);
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
            if (isFacingLeft) {
                weaponLeftAnimation.draw(xPos - 40, yPos - 35, 128, 128);
                playerLeftAnimation.draw(xPos, yPos, 128, 128);
            } else {
                if (isFacingRight) {
                    weaponRightAnimation.draw(xPos + 40, yPos - 35, 128, 128);
                    playerRightAnimation.draw(xPos, yPos, 128, 128);
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
            if (!isAlive) {
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
                foo1 = false;
                isFacingLeft = false;
                isFacingRight = false;
                isMovingLeft = true;
                isMovingRight = false;
                break;
            }
            case Input.KEY_RIGHT: {
                foo2 = false;
                isFacingLeft = false;
                isFacingRight = true;
                isMovingLeft = false;
                isMovingRight = true;
                break;
            }
            case Input.KEY_UP: {
                isJumping = true;
                break;
            }
            case Input.KEY_LSHIFT: {
                isSprintRequested = true;
                break;
            }
            case Input.KEY_SPACE: {
                isSprintRequested = false;
                isFired = true;
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
                if (!isAlive) {
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
                isMovingLeft = false;
                break;
            }
            case Input.KEY_RIGHT: {
                isMovingRight = false;
                break;
            }
            case Input.KEY_LSHIFT: {
                isSprintRequested = false;
                break;
            }
            default: {
                break;
            }
        }
    }
}