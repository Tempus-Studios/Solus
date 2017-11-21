package com.tempus.solus.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.tempus.solus.WeaponLoader;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;



public class Weapon extends Entity {
    private static final Logger logger = Logger.getLogger(Weapon.class.getName());
    private Player player;
    private Animation leftWeaponAnimation;
    private Animation rightWeaponAnimation;
    public static String WEAPON_DIRECTORY = "/res/sprite/weapons/";
    private String[] path = new String[2];
    public List<Bullet> magazine = new LinkedList<>();
    private String weaponName;
    private int weaponID;
    private int range;
    private int damage;
    private int shotsFired;
    private int magSize;
    private int ammo;
    private int totalAmmo;
    private int maxAmmo;
    private boolean automatic;
    private boolean releaseBullet;
    private float playerXPos;
    private float playerYPos;
    private boolean playerFacingLeft;
    public enum WeaponType {
        PISTOL, RIFLE, TANK, BOLT_RIFLE, SNIPER, LAUNCHER
    }
    private WeaponType weaponType;

    public Weapon(String pathLeft, String pathRight, String name, int dmg, int rng, int rof, int mag, boolean auto, WeaponType type) {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
        try {
            this.init(pathLeft, pathRight, name, dmg, rng, rof, mag, auto, type);
        } catch (SlickException ex) {
            logger.info(ex.getMessage());
        }
    }

    public void init(String pathLeft, String pathRight, String name, int dmg, int rng, int rof, int mag, boolean auto, WeaponType type) throws SlickException {
        player = new Player();
        playerXPos = 0;
        playerYPos = 0;
        playerFacingLeft = false;
        releaseBullet = false;
        shotsFired = 0;
        path[0] = WEAPON_DIRECTORY + pathLeft;
        path[1] = WEAPON_DIRECTORY + pathRight;
        leftWeaponAnimation = new Animation(new SpriteSheet(path[0], 32, 32), rof);
        rightWeaponAnimation = new Animation(new SpriteSheet(path[1], 32, 32), rof);
        leftWeaponAnimation.setAutoUpdate(false);
        rightWeaponAnimation.setAutoUpdate(false);
        range = rng;
        damage = dmg;
        weaponName = name;
        magSize = mag;
        ammo = mag;
        maxAmmo = 6 * mag;
        totalAmmo = maxAmmo;
        automatic = auto;
        weaponType = type;
        for (int i = 0; i < magSize; i++) {
            switch (weaponType) {
                case PISTOL: {
                    magazine.add(new Bullet("/res/sprite/bullets/pistol-bullet.png", damage, range, 10));
                    break;
                }
                case RIFLE: {
                    magazine.add(new Bullet("/res/sprite/bullets/rifle-bullet.png", damage, range,  15));
                    break;
                }
                case TANK: {
                    magazine.add(new Bullet("/res/sprite/bullets/tank-bullet.png", damage, range, 5));
                }
                default:
                    break;
            }
        }
    }

    public String[] getPath() {
        return path;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public int getWeaponID() {
        return weaponID;
    }

    public int getAmmo() {
        return ammo;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void decrementAmmo() {
        if (ammo > 0) {
            ammo--;
        }
    }
    public int getShots() {
        return shotsFired;
    }

    private void shoot() {
        magazine.get(0).render(playerXPos, playerYPos, playerFacingLeft);
        for (Bullet bullet : magazine) {
            bullet.render(playerXPos, playerYPos, isFacingLeft);
        }
    }

    public void reload() throws SlickException {

    }

    public void reset() {
        if (this.isAutomatic()) {
            leftWeaponAnimation.setAutoUpdate(false);
            rightWeaponAnimation.setAutoUpdate(false);
        }
        leftWeaponAnimation.setCurrentFrame(0);
        rightWeaponAnimation.setCurrentFrame(0);
    }

    public int getDamage() {
        return damage;
    }

    public void update(int delta) {
        //semi-auto
        if (!this.isAutomatic()) {
            if (this.isFacingLeft) {
                leftWeaponAnimation.restart();
                leftWeaponAnimation.setAutoUpdate(true);
                if (leftWeaponAnimation.getFrame() == 1) {
                    shoot();
                    shotsFired += 1;
                    logger.info("Fired semi-automatically: " + shotsFired);
                }
                leftWeaponAnimation.stopAt(2);
            } else {
                rightWeaponAnimation.restart();
                rightWeaponAnimation.setAutoUpdate(true);
                if (rightWeaponAnimation.getFrame() == 1) {
                    shoot();
                    shotsFired += 1;
                    logger.info("Fired semi-automatically: " + shotsFired);
                }
                rightWeaponAnimation.stopAt(2);
            }
            //automatic
        } else {
            leftWeaponAnimation.setAutoUpdate(true);
            rightWeaponAnimation.setAutoUpdate(true);
            if (leftWeaponAnimation.getFrame() == 1) {
                shotsFired += 1;
            } else if (rightWeaponAnimation.getFrame() == 1) {
                shotsFired += 1;
            }
        }
        //update bullets
        for (Bullet bullet : magazine) {
            bullet.update(delta);
        }
    }

    public void render(Graphics graphics, float playerX, float playerY, boolean isFacingLeft) {
        playerFacingLeft = isFacingLeft;
        isRendered = true;
        if (isFacingLeft) {
            leftWeaponAnimation.draw(playerX - 40, playerY - 35, 128, 128);
        } else {
            rightWeaponAnimation.draw(playerX + 40, playerY - 35, 128, 128);
        }
        //render bullets
    }
}