package com.illius.solus.entity;


import org.newdawn.slick.Graphics;

public interface Entity {
    void render(Graphics g);
    void update(int delta);
}
