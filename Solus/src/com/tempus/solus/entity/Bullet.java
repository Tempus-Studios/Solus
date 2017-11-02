package com.tempus.solus.entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Bullet extends Entity {
    private static final Logger logger = Logger.getLogger(Bullet.class.getName());
    private Image bullet;
    private int damage;
    private int range;
    private float initialXPos;
    private boolean isHit;

    public Bullet(String path, int dmg, int rng, int velocity) throws SlickException {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);

        isRendered = false;
        range = rng;
        bullet = new Image(path);
        damage = dmg;
        initialXPos = 0;
        xPos = 0;
        xVel = velocity;
        isHit = false;
        isFacingLeft = false;
    }

    public int getDamage() {
        return damage;
    }

    //@Override
    public void render(float playerX, float playerY, boolean isFacingLeft) {
        isRendered = true;
        if(isFacingLeft) {
            xVel = -xVel;
        }
        if(!isHit && isRendered) {
            xPos = playerX;
            initialXPos = playerX;
            bullet.draw(xPos, playerY + 30, 4);

        }
    }

    //@Override
    public void update(int delta) {
        if(isRendered) {
            xPos += xVel;
        }
        if((xPos > (initialXPos + range)) || (xPos < (initialXPos - range)) || isHit) {
            isRendered = false;
        }
        //add collision here
        // ...
    }


}
