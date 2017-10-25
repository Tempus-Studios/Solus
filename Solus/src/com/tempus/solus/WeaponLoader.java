package com.tempus.solus;

import com.tempus.solus.entity.Weapon;
import com.tempus.solus.entity.Weapon.WeaponType;

import org.newdawn.slick.SlickException;


public class WeaponLoader {
    private static Weapon[] weaponsList = new Weapon[5];
    private Weapon equippedWeapon;
    final static int MELEE_RANGE = 64;
    final static int PISTOL_RANGE = 320;
    final static int RIFLE_RANGE = 480;
    final static int SNIPER_RANGE = 640;
    public WeaponLoader() throws SlickException {
        initWeaponsList();
        equippedWeapon = weaponsList[0];
    }

    public static void initWeaponsList() throws SlickException {
        weaponsList[0] = new Weapon("infio-left.png","infio-right.png", "Infio",20, PISTOL_RANGE,100, 12,false, WeaponType.PISTOL);
        weaponsList[1] = new Weapon("asr15-left.png","asr15-right.png","ASR-15", 15, RIFLE_RANGE,100, 30,true, WeaponType.RIFLE);
    }
    public void setWeapon(Weapon w) {
        equippedWeapon = w;
    }
    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }
    public Weapon getWeaponAt(int index) {
        return weaponsList[index];
    }
    public void cycleWeaponClockwise(int index) {
    }
    public void cycleWeaponAntiClockwise(int index) {
    }
}
