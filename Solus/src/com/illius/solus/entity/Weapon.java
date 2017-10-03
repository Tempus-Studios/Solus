package com.illius.solus.entity;

public class Weapon {
    private String pathL, pathR;
    private int gun_id;
    private int range;
    private int damage;

    public Weapon(String pL, String pR, int id, int d, int r) {
        pathL = pL;
        pathR = pR;
        gun_id = id;
        range = r;
        damage = d;
    }

}
