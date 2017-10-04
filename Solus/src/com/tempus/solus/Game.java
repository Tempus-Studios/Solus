package com.tempus.solus;

import java.awt.Font;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.lwjgl.Sys;
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

public class Game extends BasicGameState implements KeyListener{
    private static final Logger logger = Logger.getLogger(Solus.class.getName());
    private Engine engine;
    private Input input;
    private WeaponLoader weaponLoader;
    private SpriteSheet playerLeftSheet;
    private SpriteSheet playerRightSheet;
    private SpriteSheet infioLeftSheet;
    private SpriteSheet infioRightSheet;
    private SpriteSheet tankLeftSheet;
    private SpriteSheet tankRightSheet;
    private Animation playerLeftAnimation;
    private Animation playerRightAnimation;
    private Animation infioLeftAnimation;
    private Animation infioRightAnimation;
    private Animation tankLeftAnimation;
    private Animation tankRightAnimation;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private boolean isFacingLeft = false;
    private boolean isFacingRight = true;
    private boolean isSprinting = false;
    private boolean isJumpRequested = false;
    private boolean isSprintRequested = false;
    private boolean isFired = false;
    private boolean isEnemyAggro = false;
    public Font loadingFont;
    public UnicodeFont font;
    public static float aGravity = 0.4f;
    public float xPos = 32;
    public float yPos = Engine.GAME_HEIGHT - 160;
    public float xVel = 0;
    public float yVel = 0;
    private float enemyPos = Engine.GAME_WIDTH - 160;
    public float playerHealth = 100;
    private float sprintEnergy = 100;
    private float sprintMultiplier = 1;
    private int fps;
    public static final int STATE_ID = 1;

    public Game() {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
    }

    public int getID() {
        return STATE_ID;
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        engine = new Engine();
        input = new Input(0);
        loadingFont = new Font("Arial", Font.BOLD, 20);
        font = new UnicodeFont(loadingFont);
        font.addAsciiGlyphs();
        font.getEffects().add(new ColorEffect(java.awt.Color.BLACK));
        font.loadGlyphs();
        playerLeftSheet = new SpriteSheet("/res/sprite/player-left.png", 32, 32);
        playerRightSheet = new SpriteSheet("/res/sprite/player-right.png", 32, 32);
        playerLeftAnimation = new Animation(playerLeftSheet, 150);
        playerRightAnimation = new Animation(playerRightSheet, 150);
        infioLeftSheet = new SpriteSheet("/res/sprite/weapon-infio-left.png", 32,32);
        infioRightSheet = new SpriteSheet("/res/sprite/weapon-infio-right.png", 32,32);
        infioLeftAnimation = new Animation(infioLeftSheet, 100);
        infioRightAnimation = new Animation(infioRightSheet, 100);
        infioLeftAnimation.setAutoUpdate(false);
        infioRightAnimation.setAutoUpdate(false);
        tankLeftSheet = new SpriteSheet("/res/sprite/tank-left.png", 32, 32);
        tankRightSheet = new SpriteSheet("/res/sprite/tank-right.png", 32, 32);
        tankLeftAnimation = new Animation(tankLeftSheet, 200);
        tankRightAnimation = new Animation(tankRightSheet, 200);
        tankLeftAnimation.setAutoUpdate(true);
        tankRightAnimation.setAutoUpdate(true);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
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
        if (isMovingRight){
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

        //Jumping
        if (isJumpRequested) {
            if (yPos == Engine.GAME_HEIGHT - 160) {
                yVel = -9;
            }
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
            playerLeftAnimation.setDuration(1,125);
            playerRightAnimation.setDuration(1,125);
            sprintEnergy -= -0.1;
            sprintMultiplier = 2;
        } else {
            playerLeftAnimation.setDuration(1,175);
            playerRightAnimation.setDuration(1,175);
            sprintMultiplier = 1;
        }

        if (isFired) {
            logger.info("Fired weapon");
            if(isFacingLeft) {
                infioLeftAnimation.restart();
                infioLeftAnimation.setAutoUpdate(isFired);
                infioLeftAnimation.stopAt(2);
            } else {
                infioRightAnimation.restart();
                infioRightAnimation.setAutoUpdate(isFired);
                infioRightAnimation.stopAt(2);
            }
            isFired = false;
        }
        //Player motion
        xVel *= sprintMultiplier;
        xPos += xVel;
        yPos += yVel;
        if (!isSprinting) {
            sprintEnergy += 0.01;
        }
        enemyPos--;
        if ((enemyPos - xPos) <= 320) {
            isEnemyAggro = true;
        } else {
            isEnemyAggro = false;
        }
        if (isEnemyAggro) {
            playerHealth--;
        }
        fps = engine.getFPS();
    }

    public void fireWeapon() {
        logger.info("Fired weapon");
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        //Set font
        graphics.setFont(font);
        //Draw background
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, Engine.GAME_WIDTH, Engine.GAME_HEIGHT);
        //FPS counter
        graphics.drawString("FPS: " + fps, 8, 8);
        graphics.drawString(Math.round(playerHealth) + "/100", Engine.GAME_WIDTH - 86, Engine.GAME_HEIGHT - 44);
        //Render sprites
        tankLeftAnimation.draw(enemyPos, Engine.GAME_HEIGHT - 224, 192, 192);
        if (isFacingLeft) {
            infioLeftAnimation.draw(xPos - 40,yPos - 35, 128,128);
            playerLeftAnimation.draw(xPos, yPos, 128, 128);
        } else {
            if (isFacingRight) {
                infioRightAnimation.draw(xPos + 40, yPos - 35, 128, 128);
                playerRightAnimation.draw(xPos, yPos, 128, 128);
            }
        }
    }

    @Override
    public void keyPressed(int code, char c) {
        switch (code) {
            case Input.KEY_LEFT: {
                isFacingLeft = true;
                isFacingRight = false;
                isMovingLeft = true;
                isMovingRight = false;
                break;
            }
            case Input.KEY_RIGHT: {
                isFacingLeft = false;
                isFacingRight = true;
                isMovingLeft = false;
                isMovingRight = true;
                break;
            }
            case Input.KEY_UP: {
                isJumpRequested = true;
                break;
            }
            case Input.KEY_LSHIFT: {
                isSprintRequested = true;
                break;
            }
            case Input.KEY_SPACE: {
                fireWeapon();
                isFired = true;
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
            case Input.KEY_UP: {
                isJumpRequested = false;
                break;
            }
            case Input.KEY_LSHIFT: {
                isSprintRequested = false;
                break;
            }
            case Input.KEY_SPACE: {
                isFired = false;
            }
            default: {
                break;
            }
        }
    }
}