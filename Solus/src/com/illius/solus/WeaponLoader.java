package com.illius.solus;

import com.illius.solus.entity.Weapon;
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

    public void setWeapon(Weapon w) {
        equippedWeapon = w;
    }


    public static void initWeaponsList() throws SlickException {
        weaponsList[0] = new Weapon("/res/sprite/weapon/InfioLeft.png","/res/sprite/weapon/InfioRight.png", "Infio",20, PISTOL_RANGE, 12,false, false);
        weaponsList[1] = new Weapon("/res/sprite/weapon/ASR15-left.png","/res/sprite/weapon/ASR15-right.png","ASR-15", 15, RIFLE_RANGE, 30,true, true);
    }
    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }
    public Weapon getWeaponAt(int index) {
        return weaponsList[index];
    }
}
