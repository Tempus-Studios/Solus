package com.tempus.solus.util.io;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class InputHandler extends BasicGameState implements KeyListener {
    public Input input;
    public static final int STATE_ID = 3;

    public int getID() {
        return STATE_ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        input = new Input(0);
        input.addKeyListener(this);


    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        stateBasedGame.getCurrentState();


    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    }



    @Override
    public void keyPressed(int code, char c) {
        switch (code) {

            case 1: {

            }
        }
    }
    @Override
    public void keyReleased(int code, char c) {

    }
}
