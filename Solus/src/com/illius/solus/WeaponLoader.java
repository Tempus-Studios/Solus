package com.illius.solus;

import com.illius.solus.entity.Weapon;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class WeaponLoader extends BasicGameState {
    private Weapon infio = new Weapon("/res/sprite/InfioLeft.png","/res/sprite/InfioRight.png", 0, 10, 50);
    private Weapon weaponEquipped;
    @Override
    public int getID() {
        return 10;
    }
    public void setWeapon(Weapon w) {
        weaponEquipped = w;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }
}
