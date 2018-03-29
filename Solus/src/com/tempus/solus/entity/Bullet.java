package com.tempus.solus.entity;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

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
        direction = 1;
    }

    public int getDamage() {
        return damage;
    }

    public void update(int delta) {
        if (isRendered) {
            xPos += xVel;
        }
        if ((xPos > (initialXPos + range)) || (xPos < (initialXPos - range)) || isHit) {
            isRendered = false;
        }
        //add collision here
        // ...
    }

    public void render(float playerX, float playerY, int direction) {
        logger.info("made it to bullet render");
        isRendered = true;
        if (direction == -1) {
            this.xVel = -xVel;
        }
        if (!isHit && isRendered) {
            this.xPos = playerX;
            this.initialXPos = playerX;
            bullet.draw(xPos, playerY + 30, 4);
        }
    }
}
