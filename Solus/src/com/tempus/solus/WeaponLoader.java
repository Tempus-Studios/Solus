package com.tempus.solus;

import com.tempus.solus.entity.Weapon;
import com.tempus.solus.entity.Weapon.WeaponType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


import org.newdawn.slick.SlickException;

public class WeaponLoader {
    private static final Logger logger = Logger.getLogger(WeaponLoader.class.getName());
    public static List<Weapon> weaponsList = new ArrayList<>();
    private Weapon equippedWeapon;
    final static int MELEE_RANGE = 64;
    final static int PISTOL_RANGE = 320;
    final static int RIFLE_RANGE = 480;
    final static int SNIPER_RANGE = 640;

    public WeaponLoader() throws SlickException {
        this.initWeaponsList();
        equippedWeapon = weaponsList.get(0);
    }

    public static void initWeaponsList() throws SlickException {
        weaponsList.add(new Weapon("infio-left.png", "infio-right.png", "Infio", 20, PISTOL_RANGE, 100, 12, false, WeaponType.PISTOL));
        weaponsList.add(new Weapon("asr15-left.png", "asr15-right.png", "ASR-15", 15, RIFLE_RANGE, 100, 30, true, WeaponType.RIFLE));
        //weaponsList.add(new Weapon("infio-left.png", "infio-right.png", "Test", 20, PISTOL_RANGE, 100, 12, false, WeaponType.PISTOL));
    }

    public Weapon getWeaponAt(int index) {
        return weaponsList.get(index);
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public int getCurrentWeaponIndex() {
        return weaponsList.indexOf(equippedWeapon);
    }

    public void setWeapon(Weapon weapon) {
        equippedWeapon = weapon;
    }

    public void cycleWeapon(int direction, int currentIndex) {
        int targetIndex = currentIndex + direction;
        if (targetIndex > (weaponsList.size() - 1)) {
            targetIndex = 0;
        } else if (targetIndex < 0) {
            targetIndex = weaponsList.size() - 1;
        }
        this.setWeapon(weaponsList.get(targetIndex));
    }
}