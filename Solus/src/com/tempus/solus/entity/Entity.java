package com.tempus.solus.entity;


import com.tempus.solus.Solus;
import org.newdawn.slick.geom.Rectangle;

import java.util.logging.Logger;

public abstract class Entity {
    private static final Logger logger = Logger.getLogger(Solus.class.getName());
    protected Rectangle collisionLayer;
    protected int scaleFactor = 1;
    protected boolean isAlive;
    protected boolean isRendered = false;
    protected boolean isMoving;
    protected boolean isJumping;
    protected int direction;
    protected float xPos;
    protected float yPos;
    protected float xVel;
    protected float yVel;
    protected float yAcc;
    protected float health;

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public int getDirection() {
        return direction;
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public float getXVel() {
        return xVel;
    }

    public float getYVel() {
        return yVel;
    }
    public Rectangle getCollisionLayer() {
        return collisionLayer;
    }

    public float getHealth() {
        return health;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void setDirection(int dir) {
        if (!(dir > 1) || dir < -1) {
            this.direction = dir;
        } else {
            this.direction = 1;
        }
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public void heal(float heal) {
        health += heal;
    }

    public void damage(float damage) {
        health -= damage;
    }

    public float getWidth() {
        return collisionLayer.getWidth();
    }
    public float getHeight() {
        return collisionLayer.getHeight();
    }


}
