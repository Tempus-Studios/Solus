package com.tempus.solus.entity;

import com.tempus.solus.Engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet extends Image implements Entity {
    private int damage;
    private int xPos;
    private int yPos;
    private int xVel;

    public Bullet(String path, int damage) throws SlickException {
        super(path);
        damage = damage;
        xPos = 64;
        yPos = Engine.GAME_HEIGHT - 190;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void render(Graphics graphics) {

    }

    @Override
    public void update(int delta) {

    }
}
