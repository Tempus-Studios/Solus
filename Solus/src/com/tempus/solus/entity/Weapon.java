package com.tempus.solus.entity;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Weapon {
    public static final String INFIO =  "/res/sprite/weapons/infio-";
    public static final String ASR15 = "/res/sprite/weapons/asr15-";
    public static final String PLASMA_CANNON = "/res/sprite/weapons/tank-gun-";
    public static final String BOLT_RIFLE = "/res/sprite/weapons/bolt-rifle-";

    public static final int PISTOL_DAMAGE = 15;
    public static final int RIFLE_DAMAGE = 20;
    public static final int REVOLVER_DAMAGE = 50;
    public static final int CANNON_DAMAGE = 100;


    private SpriteSheet sheetLeft, sheetRight;
    private Animation animationLeft, animationRight;
    private int damage;
    private boolean isAutomatic;

    public Weapon(String path, int fireRate, int damage, boolean isAutomatic) throws SlickException {
        sheetLeft = new SpriteSheet(path + "left.png", 32, 32);
        sheetRight = new SpriteSheet(path + "right.png", 32, 32);
        animationLeft = new Animation(sheetLeft, fireRate);
        animationRight = new Animation(sheetRight, fireRate);
        this.damage = damage;
        this.isAutomatic = isAutomatic;
        init();
    }

    private void init() {
        animationLeft.setAutoUpdate(false);
        animationRight.setAutoUpdate(false);
    }
    public void render(Graphics graphics, int playerX, int playerY, int direction) {
        if(direction == Player.LEFT) {
            animationLeft.draw(playerX - 110, playerY - 30, 128, 128);
        } else if(direction == Player.RIGHT) {
            animationRight.draw(playerX - 30, playerY - 30, 128, 128);
        }

    }
    public void update(int delta) {
        if(Keyboard.isKeyDown(Input.KEY_SPACE)) {
            if(isAutomatic) {
                animationRight.update(delta);//(true);
                animationLeft.update(delta);
            } else /* if not automatic */ {
                animationLeft.update(delta);
                animationRight.update(delta);
                animationRight.stopAt(2);
                animationLeft.stopAt(2);
            }
        } else {
            animationLeft.setAutoUpdate(false);
            animationRight.setAutoUpdate(false);
            animationLeft.start();
            animationRight.start();
            animationRight.setCurrentFrame(0);
            animationLeft.setCurrentFrame(0);
        }
    }
}
