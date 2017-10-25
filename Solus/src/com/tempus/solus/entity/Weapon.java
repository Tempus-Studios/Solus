package com.tempus.solus.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;

public class Weapon extends Entity {
    //saves some typing lel
    public static String WEAPON_DIRECTORY = "/res/sprite/weapons/";
    private Animation leftWeaponAnimation;
    private Animation rightWeaponAnimation;
    private String[] path = new String[2];
    private int range;
    private int damage;
    private int shotsFired;
    private String weaponName;
    public List<Bullet> magazine = new ArrayList<Bullet>();
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
        PISTOL, RIFLE, TANK, ALIENRIFLE, SNIPER, LAUNCHER
    }
    private WeaponType weaponType;


    public Weapon(String pathLeft, String pathRight, String name, int dmg, int ran, int rate, int mag, boolean auto, WeaponType type) throws SlickException {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
        playerXPos = 0;
        playerYPos = 0;
        playerFacingLeft = false;
        releaseBullet = false;
        shotsFired = 0;
        path[0] = WEAPON_DIRECTORY + pathLeft;
        path[1] = WEAPON_DIRECTORY + pathRight;
        leftWeaponAnimation = new Animation(new SpriteSheet(path[0], 32, 32), rate);
        rightWeaponAnimation = new Animation(new SpriteSheet(path[1], 32, 32), rate);
        leftWeaponAnimation.setAutoUpdate(false);
        rightWeaponAnimation.setAutoUpdate(false);
        range = ran;
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
    private void shoot() {
        magazine.get(0).render(playerXPos, playerYPos, playerFacingLeft);
    }

    public String getName() {
        return weaponName;
    }

    public String[] getPath() {
        return path;
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
    public void update() {
            //semi-auto
            if (!this.isAutomatic()) {
                if (this.isFacingLeft) {
                    leftWeaponAnimation.restart();
                    leftWeaponAnimation.setAutoUpdate(true);
                    if(leftWeaponAnimation.getFrame() == 1) {
                        shoot();
                        shotsFired += 1;
                        logger.info("Fired semi-automatically: " + shotsFired);
                    }
                    leftWeaponAnimation.stopAt(2);
                    return;
                } else {
                    rightWeaponAnimation.restart();
                    rightWeaponAnimation.setAutoUpdate(true);
                    if(rightWeaponAnimation.getFrame() == 1) {
                        shoot();
                        shotsFired += 1;
                        logger.info("Fired semi-automatically: " + shotsFired);
                    }
                    rightWeaponAnimation.stopAt(2);
                    return;
                }
                //automatic
            } else {
                if (isFacingLeft) {
                    leftWeaponAnimation.setAutoUpdate(true);
                    if (leftWeaponAnimation.getFrame() == 1) {
                           shotsFired += 1;
                    }
                } else {
                    rightWeaponAnimation.setAutoUpdate(true);
                    if (rightWeaponAnimation.getFrame() == 1) {
                          shotsFired += 1;
                    }
                }
            }

    }

    public void reload() throws SlickException {

    }
    public void reset() {
        if(this.isAutomatic()) {
            leftWeaponAnimation.setAutoUpdate(false);
            rightWeaponAnimation.setAutoUpdate(false);
        }
        leftWeaponAnimation.setCurrentFrame(0);
        rightWeaponAnimation.setCurrentFrame(0);
    }
    public int getDamage() {
        return damage;
    }


    //@Override
    public void render(Graphics graphics, float playerX, float playerY, boolean isFacingLeft) {
        playerFacingLeft = isFacingLeft;
        isRendered = true;
        if (isFacingLeft) {
            leftWeaponAnimation.draw(playerX - 40, playerY - 35, 128, 128);
        } else {
            rightWeaponAnimation.draw(playerX + 40, playerY - 35, 128, 128);
        }
    }
}