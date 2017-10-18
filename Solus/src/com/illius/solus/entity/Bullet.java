package com.illius.solus.entity;

import com.illius.solus.Engine;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet extends Image implements Entity {
    private int damage;
    public int x;
    public int y;
    public Bullet(String path, int d) throws SlickException {
        super(path);
        damage = d;
        x = 64;
        y = Engine.GAME_HEIGHT - 190;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void update(int delta) {

    }
}
