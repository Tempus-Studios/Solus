package com.tempus.solus.entity;

import com.tempus.solus.Engine;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.Rectangle;


import java.nio.file.WatchEvent;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Player extends Entity {
    private static final Logger logger = Logger.getLogger(Player.class.getName());
    private SpriteSheet playerLeftSheet;
    private SpriteSheet playerRightSheet;
    private Animation playerLeftAnimation;
    private Animation playerRightAnimation;
    private Weapon weapon1;
    private Weapon weapon2;
    private boolean isSprinting;
    private boolean isSprintRequested;
    private float sprintEnergy;
    private float sprintEnergyVel;
    private float sprintMultiplier;

    public Player() {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
        try {
            this.init();
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
    public boolean getSprintRequested() {
        return isSprintRequested;
    }
    public boolean isSprinting() {
        return isSprinting;
    }
    public void setDuration(int index, int duration) {
        playerLeftAnimation.setDuration(index, duration);
        playerRightAnimation.setDuration(index, duration);
    }

    public void setAnimations(boolean isAutoUpdate) {
        playerLeftAnimation.setAutoUpdate(isAutoUpdate);
        playerRightAnimation.setAutoUpdate(isAutoUpdate);
    }
    public void resetAnimations() {
        playerRightAnimation.setCurrentFrame(0);
        playerLeftAnimation.setCurrentFrame(0);
    }
    public void decrementSprintEnergy(float decrement) {
        sprintEnergy -= decrement;
    }

    public void init() throws SlickException {
        isAlive = true;
        isMoving = false;
        direction = 1;
        scaleFactor = 4;
        isSprinting = false;
        isSprintRequested = false;
        onGround = false;
        xPos = 128;
        sprintEnergyVel = 0;
        yPos = Engine.GAME_HEIGHT - 320;
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
        collisionLayer = new Rectangle(128 + (14 * scaleFactor), yPos, 8 * scaleFactor, this.getHeight());
    }

    public void update(int delta) throws SlickException {
        //collision debugging
        collisionLayer.setX(128 + (14 * scaleFactor));
        collisionLayer.setY(yPos);
        onGround = ((int) yPos >= (int)(Engine.GAME_HEIGHT - 160f));
        //left side bound
        if (xPos < 128) {
            xPos = 128;
        }
        /*if (xPos > Engine.GAME_WIDTH - 160) {
            xPos = Engine.GAME_WIDTH - 160;
        }*/
        if (yPos > ((float) (Engine.GAME_HEIGHT - 160))) {
            yPos = (int)(Engine.GAME_HEIGHT - 160f);
        }
        if (yPos < ((Engine.GAME_HEIGHT - 160f))) {
            yVel += yAcc;
        }
        if (isMoving) {
            if (direction == 1 && xVel < 3) {
                xVel+= 0.1f;
            } else if (direction == -1 && xVel > -3) {
                xVel -= 0.1f;
            }
        } else {
            if(xVel > 0f) {
                xVel -= 0.1f;
                if(xVel < 0f) {
                    xVel = 0;
                }
            } else if(xVel < 0f) {
                xVel += 0.1f;
                if(xVel > 0f) {
                    xVel = 0;
                }
            }
        }
        if (xVel == 0 || yPos < Engine.GAME_HEIGHT - 160) {
           this.setAnimations(false);
           this.resetAnimations();
        } else {
            playerLeftAnimation.setAutoUpdate(true);
            playerRightAnimation.setAutoUpdate(true);
        }
        //Sprinting
        if (sprintEnergy > 100) {
            sprintEnergy = 100f;
        }
        if (sprintEnergy < 0) {
            sprintEnergy = 0;
        }
        if (sprintEnergy > 2) {
            if(isMoving && isSprintRequested) {
                sprintEnergy -= 0.3f;

                    sprintMultiplier = 2;

                logger.info(sprintMultiplier + "shift pressed");
                logger.info(isMoving + "" + isSprintRequested + sprintMultiplier + "spr");
            } else {

                //logger.info(sprintMultiplier + "shift not pressed");
            }
        }
        //Player motion
        xPos += (xVel * sprintMultiplier);
        yPos += yVel;
        sprintEnergy += sprintEnergyVel;
        if (!isSprinting) {
            sprintEnergy += 0.2f;
        }
        if (health < 0) {
            health = 0;
        }
        if (yVel!=0) {
            isSprintRequested = false;
        }
        if (health > 100) {
            health = 100;
        }
        if (health == 0) {
            //isAlive = false;
        } else {
            isAlive = true;
        }
    }
    public void render(Graphics graphics) throws SlickException {
        if (direction == -1) {
            playerLeftAnimation.draw(128, yPos, 128, 128);
        } else if (direction == 1) {
            playerRightAnimation.draw(128, yPos, 128, 128);
        }
        //collision debug
        graphics.setColor(Color.red);
        graphics.fill(collisionLayer);
    }
    public float getWidth() {
        return playerLeftAnimation.getWidth() * scaleFactor;

    }

    public float getHeight() {
        return playerLeftAnimation.getHeight() * scaleFactor;
    }
    public void sprint() {
        if (yPos == Engine.GAME_HEIGHT - 160) {
            playerLeftAnimation.setDuration(1, 125);
            playerRightAnimation.setDuration(1, 125);
        }
        sprintMultiplier = 2;
        sprintEnergyVel = -0.5f;

    }
    public void stopSprint() {
        playerLeftAnimation.setDuration(1, 175);
        playerRightAnimation.setDuration(1, 175);
        sprintMultiplier = 1;
        sprintEnergyVel = 0.05f;
    }
    public void jump() {
        logger.info("y: " + yPos +" onground level: " + (Engine.GAME_HEIGHT - 160));
        super.jump();
        if(onGround) {
            sprintEnergy -= 15;
        }
    }

}
