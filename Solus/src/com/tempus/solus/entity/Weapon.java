package com.tempus.solus.entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

public class Weapon implements Entity{
    //saves some typing lel
    public static String LOCATION = "/res/sprite/weapons/";
    private String[] path = new String[2];
    private int gun_id;
    private int range;
    private int damage;
    private String name;
    public List<Bullet> clip = new ArrayList<Bullet>();
    private int clip_size;
    private int ammo;
    private int total_ammo;
    private int max_ammo;
    public enum GunType {
        PISTOL, RIFLE, TANK, ALIENRIFLE, SNIPER, LAUNCHER
    }
    private GunType gunType;
    protected boolean automatic;



    public Weapon(String pL, String pR, String n, int d, int r, int cs, boolean a, GunType gt) throws SlickException {
        path[0] = LOCATION + pL;
        path[1] = LOCATION + pR;
        range = r;
        damage = d;
        name = n;
        clip_size = cs;
        ammo = cs;
        max_ammo = 6*cs;
        total_ammo = max_ammo;
        automatic = a;
        gunType = gt;
        for (int i = 0; i < clip_size; i++) {
            switch(gunType) {
                case PISTOL: {
                    clip.add(new Bullet("/res/sprite/bullets/pistol-bullet.png", damage, 10));
                    break;
                }
                case RIFLE: {
                    clip.add(new Bullet("/res/sprite/bullets/rifle-bullet.png", damage, 15));
                    break;
                }
                case TANK: {
                    clip.add(new Bullet("/res/sprite/bullets/tank-bullet.png", damage, 5));
                }
                default: break;
            }
        }
    }

    public String getName() {
        return name;
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
    public void render(Graphics g, float playerX, float playerY) {

    }

    @Override
    public void update(int delta) {

    }
}
