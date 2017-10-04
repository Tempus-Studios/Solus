package com.tempus.solus;

import com.tempus.solus.entity.Weapon;

public class WeaponLoader {
    private Weapon infio = new Weapon("/res/sprite/weapon-infio-lefteft.png","/res/sprite/infio-right.png", 0, 10, 50);
    private Weapon weaponEquipped;
    public void setWeapon(Weapon w) {
        weaponEquipped = w;
    }
}
