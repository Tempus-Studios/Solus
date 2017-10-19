package com.tempus.solus.entity;

import com.tempus.solus.Engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet extends Image implements Entity {
    private int damage;
    private int xPos;
    private static int xVel;
    private boolean isHit;

    public Bullet(String path, int d, int velocity) throws SlickException {
        super(path);
        damage = d;
        xPos = 0;
        xVel = velocity;
        isHit = false;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void render(Graphics graphics, float playerX, float playerY) {
        if(!isHit) {
            graphics.drawImage(this, playerX + xPos, playerY + 30);
        }
    }

    @Override
    public void update(int delta) {
        xPos += xVel;
    }
}
