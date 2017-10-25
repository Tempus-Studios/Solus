package com.tempus.solus.entity;


import com.tempus.solus.Solus;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.logging.Logger;

public abstract class Entity {
    static final Logger logger = Logger.getLogger(Solus.class.getName());
    public boolean isFacingLeft;
    public boolean isMovingLeft;
    public boolean isMovingRight;
    public boolean isJumping;
    protected boolean isAlive;
    protected boolean isRendered = false;
    protected float xPos;
    protected float yPos;
    protected float xVel;
    protected float yVel;
    protected float health;
    protected float yAcc;

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

    public float getYAcc() {
        return yAcc;
    }
    public float getHealth() {
        return health;
    }
    public boolean isAlive() {
        return isAlive;
    }

    public boolean isMovingLeft() {
        return isMovingLeft;
    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public boolean isFacingLeft() {
        return isFacingLeft;
    }
    public void heal(float heal) {
        health += heal;
    }
    public void damage(float damage) {
        health -= damage;
    }

    public void setMovingLeft(boolean movingLeft) {
        isMovingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        isMovingRight = movingRight;
    }

    public void setFacingLeft(boolean facingLeft) {
        isFacingLeft = facingLeft;
    }
    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }
}
