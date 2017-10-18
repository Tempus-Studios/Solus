package com.tempus.solus.entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

public class Weapon implements Entity{
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
    protected boolean automatic;
    protected boolean isRifle;


    public Weapon(String pL, String pR, String n, int d, int r, int cs, boolean a, boolean rifle) throws SlickException {
        path[0] = pL;
        path[1] = pR;
        range = r;
        damage = d;
        name = n;
        clip_size = cs;
        ammo = cs;
        max_ammo = 6*cs;
        total_ammo = max_ammo;
        automatic = a;
        isRifle = rifle;
        for (int i = 0; i < clip_size; i++) {
            if (!isRifle) {
                clip.add(new Bullet("/res/sprite/pistol-bullet.png", damage));
            } else {
                clip.add(new Bullet("/res/sprite/rifle-bullet.png", damage));
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
        if(total_ammo >= clip_size) {
            for(int i = 0; i < clip_size; i++) {
                clip.add(new Bullet("/res/sprite/pistol-bullet.png", damage));
            }
        }
    }
    public boolean isARifle() {
        return isRifle;
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
