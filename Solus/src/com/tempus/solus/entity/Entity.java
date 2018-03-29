package com.tempus.solus.entity;


import com.tempus.solus.Solus;
import org.newdawn.slick.geom.Rectangle;

import java.util.logging.Logger;

public abstract class Entity {
    private static final Logger logger = Logger.getLogger(Solus.class.getName());
<<<<<<< HEAD
    protected Rectangle collisionLayer;
    protected Rectangle leftBounds, rightBounds, topBounds, bottomBounds;
=======
    protected Rectangle hitbox;
>>>>>>> 74989b8e7495f6159ffe7784236bf009cce1efff
    protected int scaleFactor = 1;
    protected boolean isAlive;
    protected boolean isRendered = false;
    protected boolean isMoving;
    protected boolean onGround;
    protected int direction;
    protected float xPos;
    protected float yPos;
    protected float xVel;
    protected float yVel;
    protected float yAcc;
    protected float health;
    protected int groundLevel;


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

    public void setXPos(float newX) {
        xPos = newX;
    }
    public void setYPos(float newY) {
        yPos = newY;
    }

    public void setYVel(float newYVel) {
        yVel = newYVel;
    }
    public void setXVel(float newXVel) {
        xVel = newXVel;
    }

    public void setYAcc(float value) {
        yAcc = value;
    }

    public Rectangle getHitbox() {
        return hitbox;
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

    public void heal(float heal) {
        health += heal;
    }

    public void damage(float damage) {
        health -= damage;
    }
    public boolean isOnGround() {
        return onGround;
    }

<<<<<<< HEAD
    public int getLtBounds() {
        return (int) collisionLayer.getX();
    }
    public int getRtBounds() {
        return (int) (collisionLayer.getX() + collisionLayer.getWidth());
    }
    public int getBtBounds() {
        return (int) (collisionLayer.getY() + collisionLayer.getHeight());
=======
    public float getWidth() {
        return hitbox.getWidth();
    }
    public float getHeight() {
        return hitbox.getHeight();
>>>>>>> 74989b8e7495f6159ffe7784236bf009cce1efff
    }
    public void jump() {
        if (onGround) {
            yVel = -9;
        }
    }
    public void setGroundLevel(int groundLevel) {
        this.groundLevel = groundLevel;
    }

    public int getGroundLevel() {
        return groundLevel;
    }
<<<<<<< HEAD

    public Rectangle getTopBounds() {
        return topBounds;
    }
    public Rectangle getBottomBounds() {
        return bottomBounds;
    }
    public Rectangle getLeftBounds() {
        return leftBounds;
    }
    public Rectangle getRightBounds() {
        return rightBounds;
    }
=======
>>>>>>> 74989b8e7495f6159ffe7784236bf009cce1efff
}
