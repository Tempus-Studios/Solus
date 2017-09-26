package com.illius.solus.util;

import com.illius.solus.sound.BackgroundMusic;
import com.illius.solus.util.thread.ThreadPool;
import org.lwjgl.opengl.Display;

import com.illius.solus.Game_OLD;

public class GameStateManager {
    public enum GameState {
        LAUNCH, MENU, GAME, PAUSED, DEAD
    }

    //placeholder values
    public GameState currentState = GameState.GAME;
    public GameState newState = GameState.GAME;

    public void setGameState(GameState currentState, GameState newState) {
        this.currentState = currentState;
        this.newState = newState;
        handleGameState();
    }

    public void handleGameState() {
        ThreadPool threadPool = new ThreadPool(2);
        switch (newState) {
            case MENU: {
                if (currentState.equals(GameState.PAUSED)) {
                    //save game
                    //threadPool.runTask(new Menu());
                } else {
                    if (currentState.equals(GameState.LAUNCH)) {
                        //threadPool.runTask(new Menu());
                    }
                }
            }
            break;
            case GAME: {
                if (currentState.equals(GameState.MENU)) {
                    threadPool.runTask(new Game_OLD());
                    threadPool.runTask(new BackgroundMusic());
                } else {
                    if (currentState.equals(GameState.LAUNCH)) {
                        //for testing purposes
                        threadPool.runTask(new Game_OLD());
                        threadPool.runTask(new BackgroundMusic());
                    } else {
                        if (currentState.equals(GameState.DEAD)) {
                            //TODO
                        } else {
                            if (currentState.equals(GameState.PAUSED)) {
                                //TODO
                            }
                        }
                    }
                }
            }
            break;
            case PAUSED: {
                if (currentState.equals(GameState.GAME)) {
                    //TODO
                }
            }
            break;
            case DEAD: {
                if (currentState.equals(GameState.GAME)) {
                    Display.destroy();
                    System.exit(0);
                }
            }
            break;
            default: {
                //threadPool.runTask(new Menu());
            }
        }
    }
}
