package com.tempus.solus.entity;

import com.tempus.solus.Engine;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Player extends Entity {
    private static final Logger logger = Logger.getLogger(Player.class.getName());
    private SpriteSheet playerLeftSheet;
    private SpriteSheet playerRightSheet;
    private Animation playerLeftAnimation;
    private Animation playerRightAnimation;
    private boolean isSprinting;
    private boolean isSprintRequested;
    private float sprintEnergy;
    private float sprintMultiplier;

    public Player() {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
        try {
            init();
        } catch (SlickException ex) {
            logger.severe("Failed to initialize player");
        }
    }

    public float getSprintEnergy() {
        return sprintEnergy;
    }

    public void setSprintRequested(boolean sprintRequested) {
        isSprintRequested = sprintRequested;
    }


    public void init() throws SlickException {
        isAlive = true;
        isMovingLeft = false;
        isMovingRight = false;
        isFacingLeft = false;
        isSprinting = false;
        isJumping = false;
        isSprintRequested = false;
        xPos = 32;
        yPos = Engine.GAME_HEIGHT - 160;
        xVel = 0;
        yVel = 0;
        yAcc = 0.4f;
        health = 100;
        sprintEnergy = 100;
        sprintMultiplier = 1;
        playerLeftSheet = new SpriteSheet("/res/sprite/player-left.png", 32, 32);
        playerRightSheet = new SpriteSheet("/res/sprite/player-right.png", 32, 32);
        playerLeftAnimation = new Animation(playerLeftSheet, 150);
        playerRightAnimation = new Animation(playerRightSheet, 150);
        playerLeftAnimation.setAutoUpdate(false);
        playerRightAnimation.setAutoUpdate(false);
    }

    public void update(int delta) throws SlickException {
        if (xPos < 32) {
            xPos = 32;
        }
        if (xPos > Engine.GAME_WIDTH - 160) {
            xPos = Engine.GAME_WIDTH - 160;
        }
        if (yPos > ((float) (Engine.GAME_HEIGHT - 160))) {
            yPos = Engine.GAME_HEIGHT - 160;
        }
        if (yPos < ((float) (Engine.GAME_HEIGHT - 160))) {
            yVel += yAcc;
        }
        //Moving left
        if (isMovingLeft) {
            isFacingLeft = true;
            xVel = -3;
        }
        //Moving right
        if (isMovingRight) {
            isFacingLeft = false;
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
        if (isFacingLeft) {
            if (!isMovingLeft) {
                if (xVel < 0) {
                    xVel += 0.01;
                }
            }
        }
        if (!isFacingLeft) {
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
        //Player motion
        xVel *= sprintMultiplier;
        xPos += xVel;
        yPos += yVel;
        if (!isSprinting) {
            sprintEnergy++;
        }
        if (health < 0) {
            health = 0;
        }
        if (health > 100) {
            health = 100;
        }
        if (health == 0) {
            isAlive = false;
        } else {
            isAlive = true;
        }
    }
    public void render(Graphics graphics) throws SlickException {
        if (isFacingLeft) {
            playerLeftAnimation.draw(xPos, yPos, 128, 128);
        } else if (!isFacingLeft) {
            playerRightAnimation.draw(xPos, yPos, 128, 128);
        }
    }
}

