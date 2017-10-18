package com.illius.solus;

import com.illius.solus.entity.Bullet;

import java.awt.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState implements MusicListener, KeyListener {
    private static final Logger logger = Logger.getLogger(Solus.class.getName());
    private Engine engine;
    private WeaponLoader weaponLoader;
    private Input input;

    private Image pistolBulletImage;
    private Image rifleBulletImage;

    private SpriteSheet playerRightSheet;
    private SpriteSheet playerLeftSheet;
    private SpriteSheet weaponLeftSheet;
    private SpriteSheet weaponRightSheet;
    private Animation playerLeftAnimation;
    private Animation playerRightAnimation;
    private Animation weaponLeftAnimation;
    private Animation weaponRightAnimation;
    private Music menuMusic;
    protected boolean movingLeft = false;
    protected boolean movingRight = false;
    protected boolean facingLeft =false;
    protected boolean sprinting = false;
    protected boolean isFired = false;
    protected boolean jumping = false;
    protected boolean isWeaponSwapped = false;
    public Font loadingFont;
    public UnicodeFont font;
    public static float aGravity = .4f;
    public float xPos = 32;
    public float yPos = Engine.GAME_HEIGHT - 160;
    public float xVel = 0f;
    public float yVel = 0.00000f;
    public float playerHealth = 100;
    public float sprintEnergy = 100;
    public float sprintMultiplier = 1;
    public int fps;
    public int shotsFired = 0;
    public static final int STATE_ID = 1;

    public Game() {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
    }

    public int getID() {
        return STATE_ID;
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        input = new Input(0);
        weaponLoader = new WeaponLoader();
        loadingFont = new Font("Arial", Font.BOLD, 20);
        font = new UnicodeFont(loadingFont);
        font.addAsciiGlyphs();
        font.getEffects().add(new ColorEffect(java.awt.Color.BLACK));
        font.loadGlyphs();

        pistolBulletImage = new Image("/res/sprite/pistol-bullet.png");
        rifleBulletImage = new Image("/res/sprite/rifle-bullet.png");

        playerLeftSheet = new SpriteSheet("/res/sprite/player-left.png", 32, 32);
        playerRightSheet = new SpriteSheet("/res/sprite/player-right.png", 32, 32);
        playerLeftAnimation = new Animation(playerLeftSheet, 150);
        playerRightAnimation = new Animation(playerRightSheet, 150);

        weaponLeftSheet = new SpriteSheet(weaponLoader.getEquippedWeapon().getPath()[0], 32,32);
        weaponRightSheet = new SpriteSheet(weaponLoader.getEquippedWeapon().getPath()[1], 32,32);
        weaponLeftAnimation = new Animation(weaponLeftSheet, 100);
        weaponRightAnimation = new Animation(weaponRightSheet, 100);
        weaponLeftAnimation.setAutoUpdate(false);
        weaponRightAnimation.setAutoUpdate(false);


        menuMusic = new Music("res/audio/Locus.ogg");
        menuMusic.addListener(this);
        menuMusic.setPosition(0);
        menuMusic.loop(1, 1);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {


        if (xPos < 32) {
            xPos = 32;
        }
        if (xPos > Engine.GAME_WIDTH - 160) {
            xPos = Engine.GAME_WIDTH - 160;
        }
        if (yPos > Engine.GAME_HEIGHT - 160) {
            yPos = Engine.GAME_HEIGHT - 160;
        }
        //Moving left
        if(facingLeft && movingLeft) {
            xVel = -3;
        }
        //Moving right
        if(!facingLeft && movingRight) {
            xVel = 3;
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
        if (xVel == 0 || yPos < Engine.GAME_HEIGHT - 160) {
            playerLeftAnimation.setAutoUpdate(false);
            playerRightAnimation.setAutoUpdate(false);
            playerLeftAnimation.setCurrentFrame(0);
            playerRightAnimation.setCurrentFrame(0);
        } else {
            playerLeftAnimation.setAutoUpdate(true);
            playerRightAnimation.setAutoUpdate(true);
        }
        if (yPos < Engine.GAME_HEIGHT - 160) {
            yVel += aGravity;
        }

        //jump
        if (jumping) {
            if (yPos == Engine.GAME_HEIGHT - 160) {
                yVel = -9;
            }
            jumping = false;
        }

        if(isFired) {
            //semi-auto
            if(!weaponLoader.getEquippedWeapon().isAutomatic()) {
                if (facingLeft) {
                    weaponLeftAnimation.restart();
                    weaponLeftAnimation.setAutoUpdate(isFired);
                    weaponLeftAnimation.stopAt(2);
                    shotsFired += 1;
                    logger.info("Fired semi-automatically: " + shotsFired);
                    isFired = false;
                } else {
                    weaponRightAnimation.restart();
                    weaponRightAnimation.setAutoUpdate(isFired);
                    weaponRightAnimation.stopAt(2);
                    shotsFired += 1;
                    logger.info("Fired semi-automatically: " + shotsFired);
                    isFired = false;
                }
            //automatic
            } else {
                if(facingLeft) {
                    weaponLeftAnimation.setAutoUpdate(isFired);
                    if(weaponLeftAnimation.getFrame() == 1) {
                        weaponLoader.getEquippedWeapon().decrementAmmo();
                        shotsFired += 1;
                    }
                } else {
                    weaponRightAnimation.setAutoUpdate(isFired);
                    if(weaponRightAnimation.getFrame() == 1) {
                        weaponLoader.getEquippedWeapon().decrementAmmo();
                        shotsFired += 1;
                    }
                }
            }
        }


        if (sprintEnergy > 100) {
            sprintEnergy = 100;
        }
        if (sprintEnergy < 0) {
            sprintEnergy = 0;
        }


            if (sprinting) {
                playerLeftAnimation.setDuration(1, 125);
                playerRightAnimation.setDuration(1, 125);
                sprintEnergy -= -0.1;
                sprintMultiplier = 2;
            } else {
                playerLeftAnimation.setDuration(1, 175);
                playerRightAnimation.setDuration(1, 175);
                sprintMultiplier = 1;
            }




        if(isWeaponSwapped) {
            weaponLoader.setWeapon(weaponLoader.getWeaponAt(1));
            weaponLeftSheet = new SpriteSheet(weaponLoader.getEquippedWeapon().getPath()[0], 32,32);
            weaponRightSheet = new SpriteSheet(weaponLoader.getEquippedWeapon().getPath()[1], 32,32);
            weaponLeftAnimation = new Animation(weaponLeftSheet, 100);
            weaponRightAnimation = new Animation(weaponRightSheet, 100);
            weaponLeftAnimation.setAutoUpdate(false);
            weaponRightAnimation.setAutoUpdate(false);
            isWeaponSwapped = false;
        }

        xVel *= sprintMultiplier;
        xPos += xVel;
        yPos += yVel;
        if (!sprinting) {
            sprintEnergy += 0.01;
        }
        engine = new Engine();
        fps = engine.getFPS();
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        //Set font
        graphics.setFont(font);
        //Draw background
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, Engine.GAME_WIDTH, Engine.GAME_HEIGHT);
        //FPS counter
        graphics.drawString("FPS: " + fps, 8, 8);

        //Render player animation + guns
        if (!facingLeft) {
            weaponRightAnimation.draw(xPos + 40,yPos - 35, 128,128);
            playerRightAnimation.draw(xPos, yPos, 128, 128);
        } else if (facingLeft) {
            weaponLeftAnimation.draw(xPos - 40,yPos - 35, 128,128);
            playerLeftAnimation.draw(xPos, yPos, 128, 128);
        }


    }
    @Override
    public void keyPressed(int code, char c) {
        switch (code) {
            case Input.KEY_LEFT: {
                facingLeft = true;
                movingLeft = true;
                movingRight = false;
                break;
            }
            case Input.KEY_RIGHT: {
                facingLeft = false;
                movingLeft = false;
                movingRight = true;
                break;
            }
            case Input.KEY_UP: {
                jumping = true;
                break;
            }
            case Input.KEY_LSHIFT: {
                sprinting = true;
                break;
            }
            case Input.KEY_SPACE: {
                isFired = true;
                sprinting = false;
                break;
            }
            case Input.KEY_E: {
                isWeaponSwapped = true;
            }

            default: {
                break;
            }
        }
    }
    @Override
    public void keyReleased(int code, char c) {
        switch(code) {
            case Input.KEY_LEFT: {
                movingLeft = false;
                break;
            }
            case Input.KEY_RIGHT: {
                movingRight = false;
                break;
            }
            case Input.KEY_LSHIFT: {
                sprinting = false;
                break;
            }
            case Input.KEY_SPACE: {
                isFired = false;
                if(weaponLoader.getEquippedWeapon().isAutomatic()) {
                    weaponLeftAnimation.setAutoUpdate(isFired);
                    weaponRightAnimation.setAutoUpdate(isFired);
                }
                weaponLeftAnimation.setCurrentFrame(0);
                weaponRightAnimation.setCurrentFrame(0);
                break;
            }
            default: {
                break;
            }

        }
    }
    public void musicSwapped(Music music1, Music music2) {

    }

    public void musicEnded(Music music) {

    }
}
