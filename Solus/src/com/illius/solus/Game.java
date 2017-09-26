package com.illius.solus;

import java.awt.Font;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState implements MusicListener {
    private Engine engine;
    private Input input;
    private SpriteSheet playerRightSheet;
    private SpriteSheet playerLeftSheet;
    private Animation playerLeftAnimation;
    private Animation playerRightAnimation;
    private Music menuMusic;
    protected boolean movingLeft = false;
    protected boolean movingRight = false;
    protected boolean sprinting = false;
    protected boolean jumped = false;
    private boolean allowJump = true;
    public Font loadingFont;
    public UnicodeFont font;
    public float xPos = 32;
    public float yPos = Engine.GAME_HEIGHT - 128;
    public float xVel = 0;
    public float yVel = 0;
    public float playerHealth = 100;
    public float sprintEnergy = 100;
    public float sprintMulitplier = 1;
    public int fps;
    public static final int STATE_ID = 1;
    public int getID() {
        return STATE_ID;
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        input = new Input(0);
        loadingFont = new Font("Arial", Font.BOLD, 20);
        font = new UnicodeFont(loadingFont);
        font.addAsciiGlyphs();
        font.getEffects().add(new ColorEffect(java.awt.Color.BLACK));
        font.loadGlyphs();
        playerLeftSheet = new SpriteSheet("/res/sprite/player-left.png", 32, 32);
        playerRightSheet = new SpriteSheet("/res/sprite/player-right.png", 32, 32);
        playerLeftAnimation = new Animation(playerLeftSheet, 150);
        playerRightAnimation = new Animation(playerRightSheet, 150);
        menuMusic = new Music("res/audio/Locus.ogg");
        menuMusic.addListener(this);
        menuMusic.setPosition(0);
        menuMusic.loop(1, 1);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        if (xPos < 32) {
            xPos = 32;
        }
        if (xPos > Engine.GAME_WIDTH - 128) {
            xPos = Engine.GAME_WIDTH - 128;
        }
        if (yPos > Engine.GAME_HEIGHT - 128) {
            yPos = Engine.GAME_HEIGHT - 128;
        }
        if (yPos < Engine.GAME_HEIGHT - 224) {
            yPos = Engine.GAME_HEIGHT - 224;
        }
        //Moving left
        if (input.isKeyDown(Input.KEY_LEFT)) {
            xVel = -3;
            movingLeft = true;
        } else {
            movingLeft = false;
        }
        //Moving right
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            xVel = 3;
            movingRight = true;
        } else {
            movingRight = false;
        }
        //Standing still
        if (movingLeft && movingRight) {
            xVel = 0;
            movingLeft = false;
            movingRight = false;
        }
        if (!movingLeft && !movingRight) {
            xVel = 0;
        }
        if (xVel == 0 || yPos < Engine.GAME_HEIGHT - 128) {
            playerLeftAnimation.setAutoUpdate(false);
            playerRightAnimation.setAutoUpdate(false);
            playerLeftAnimation.setCurrentFrame(0);
            playerRightAnimation.setCurrentFrame(0);
        } else {
            playerLeftAnimation.setAutoUpdate(true);
            playerRightAnimation.setAutoUpdate(true);
        }
        if (xPos <= Engine.GAME_HEIGHT - 192) {
            yVel = 4;
        }
        if (input.isKeyDown(Input.KEY_UP)) {
            jumped = true;
            jump();
        }
        if (sprintEnergy > 100) {
            sprintEnergy = 100;
        }
        if (sprintEnergy < 0) {
            sprintEnergy = 0;
        }
        if (sprintEnergy > 0) {
            if (!(!movingLeft && !movingRight)) {
                if (input.isKeyDown(Input.KEY_LSHIFT)) {
                    sprinting = true;
                } else {
                    sprinting = false;
                }
            }
        }
        if (sprinting) {
            playerLeftAnimation.setDuration(1,125);
            playerRightAnimation.setDuration(1,125);
            sprintEnergy -= -0.1;
            sprintMulitplier = 2;
        } else {
            playerLeftAnimation.setDuration(1,175);
            playerRightAnimation.setDuration(1,175);
            sprintMulitplier = 1;
        }
        if (input.isKeyDown(Input.KEY_Z)) {
            if (!sprinting) {
                fireWeapon();
            }
        }
        xVel *= sprintMulitplier;
        xPos += xVel;
        yPos += yVel;
        if (!sprinting) {
            sprintEnergy += 0.01;
        }
        engine = new Engine();
        fps = engine.getFPS();
    }

    private void jump() {
        //TODO fix jump
        jumped = true;
        if (yPos == Engine.GAME_HEIGHT - 128) {
            allowJump = true;
        } else {
            allowJump = false;
        }
        if (allowJump) {
            if (yPos > Engine.GAME_HEIGHT - 224) {
                yVel = -6;
            }
            if (yPos == Engine.GAME_HEIGHT - 224) {
                yVel = -6;
            }
        }
        jumped = false;
    }

    public void fireWeapon() {
        System.out.println("Fired weapon");
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        //Set font
        graphics.setFont(font);
        //Draw background
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, Engine.GAME_WIDTH, Engine.GAME_HEIGHT);
        //FPS counter
        graphics.drawString("FPS: " + fps, 8, 8);
        //Render player animation
        if (movingRight || (!movingLeft && !movingRight)) {
            playerRightAnimation.draw(xPos, yPos, 128, 128);
        } else {
            playerLeftAnimation.draw(xPos, yPos, 128, 128);
        }
    }

    public void musicSwapped(Music music1, Music music2) {

    }

    public void musicEnded(Music music) {

    }
}