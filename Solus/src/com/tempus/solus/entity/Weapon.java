package com.tempus.solus.entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

public class Weapon implements Entity{
    //TODO: append from root dir
    public enum WeaponType {
        PISTOL, RIFLE, TANK, ALIENRIFLE, SNIPER, LAUNCHER
    }
    private WeaponType weaponType;
    private static String WEAPON_DIRECTORY = "/res/sprite/weapons/";
    private String[] path = new String[2];
    private int range;
    private int damage;
    private String weaponName;
    public List<Bullet> magazine = new ArrayList<Bullet>();
    private int magSize;
    private int ammo;
    private int totalAmmo;
    private int maxAmmo;

    private boolean automatic;

    public Weapon(String pathLeft, String pathRight, String name, int dmg, int rng, int mag, boolean a, WeaponType type) throws SlickException {
        path[0] = WEAPON_DIRECTORY + pathLeft;
        path[1] = WEAPON_DIRECTORY + pathRight;
        weaponName = name;
        range = rng;
        damage = dmg;
        magSize = mag;
        ammo = mag;
        maxAmmo = 6 * mag;
        totalAmmo = maxAmmo;
        automatic = a;
        weaponType = type;
        for (int i = 0; i < magSize; i++) {
            switch (weaponType) {
                case PISTOL: {
                    magazine.add(new Bullet("/res/sprite/bullets/pistol-bullet.png", damage, 10));
                    break;
                }
                case RIFLE: {
                    magazine.add(new Bullet("/res/sprite/bullets/rifle-bullet.png", damage, 15));
                    break;
                }
                case TANK: {
                    magazine.add(new Bullet("/res/sprite/bullets/tank-bullet.png", damage, 5));
                }
                default: break;
            }
        }
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
    public void reload() throws SlickException {

    }
    public int getDamage() {
        return damage;
    }


    @Override
    public void render(Graphics graphics, float playerX, float playerY) {

    }

    @Override
    public void update(int delta) {

    }
}
