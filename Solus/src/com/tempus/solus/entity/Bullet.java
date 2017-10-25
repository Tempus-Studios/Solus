package com.tempus.solus.entity;

import com.tempus.solus.Engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;

public class Bullet extends Entity {
    private Image bullet;
    private int damage;
    private int range;
    private float initialXPos;
    private boolean isHit;

    public Bullet(String path, int dam, int ran, int velocity) throws SlickException {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);

        isRendered = false;
        range = ran;
        bullet = new Image(path);
        damage = dam;
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
