package com.tempus.solus;


import com.tempus.solus.entity.Entity;
import com.tempus.solus.entity.Player;

import java.awt.Font;
import java.io.InputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.tempus.solus.map.Level;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

public class Game extends BasicGameState implements KeyListener{
    private static final Logger logger = Logger.getLogger(Solus.class.getName());
    private Level currentLevel;
    private Camera camera;
    private Engine engine;
    private Player player;
    private Entity tank;
    private WeaponHandler weaponHandler;
    private Image healthIcon;
    private Image sprintIcon;
    private Image ammoIcon;
    private SpriteSheet tankLeftSheet;
    private SpriteSheet tankRightSheet;
    private Animation tankLeftAnimation;
    private Animation tankRightAnimation;
    private boolean isPaused;
    private boolean isRestartRequested;
    private boolean isFired;
    private boolean isEnemyAggro;
    private boolean enemyFacingLeft;
    private boolean kLeft;
    private boolean kRight;
    private float xDisplacement;
    public Font loadingFont;
    public Font loadingFont2;
    public UnicodeFont font;
    public UnicodeFont fpsFont;
    private float enemyPos;
    private float mapXPos;
    private int timeElapsed;
    private int fps;
    private int delta;
    public static final int STATE_ID = 1;

    public Game() {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
    }

    @Override
    public int getID() {
        return STATE_ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        engine = new Engine();
        player = new Player();
        currentLevel = new Level();
        player.setYAcc(currentLevel.getYAcc());
        camera = new Camera(96 * 32 * 2, 10 * 32 * 2);
        xDisplacement = Engine.GAME_WIDTH / 2 - player.getWidth() + (14 * 4);
        loadingFont = null;
        loadingFont2 = null;
        isPaused = false;
        isRestartRequested = false;
        isFired = false;
        isEnemyAggro = false;
        enemyFacingLeft = true;
        kLeft = false;
        kRight = false;

        enemyPos = Engine.GAME_WIDTH - 192;
        weaponHandler = new WeaponHandler();
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("/res/PressStart2P.ttf");
            loadingFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            loadingFont2 = loadingFont;
            loadingFont = loadingFont.deriveFont(28f);
            loadingFont2 = loadingFont2.deriveFont(12f);
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
        font = new UnicodeFont(loadingFont);
        font.getEffects().add(new ColorEffect(null));
        font.addAsciiGlyphs();
        try {
            font.loadGlyphs();
        } catch(SlickException ex) {
            logger.severe(ex.getMessage());
        }
        fpsFont = new UnicodeFont(loadingFont2);
        fpsFont.getEffects().add(new ColorEffect(null));
        fpsFont.addAsciiGlyphs();
        try {
            fpsFont.loadGlyphs();
        } catch(SlickException ex) {
            logger.severe(ex.getMessage());
        }
        healthIcon = new Image("/res/sprite/icons/HealthIcon.png");
        sprintIcon = new Image("/res/sprite/icons/SprintEnergyIcon.png");
        ammoIcon = new Image("/res/sprite/icons/AmmoIcon.png");
        tankLeftSheet = new SpriteSheet("/res/sprite/enemies/tank-left.png", 32, 32);
        tankRightSheet = new SpriteSheet("/res/sprite/enemies/tank-right.png", 32, 32);
        tankLeftAnimation = new Animation(tankLeftSheet, 200);
        tankRightAnimation = new Animation(tankRightSheet, 200);
        tankLeftAnimation.setAutoUpdate(true);
        tankRightAnimation.setAutoUpdate(true);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        this.delta = delta;
        if (player.isAlive() && !isPaused) {
            player.update(delta);
            //camera
            camera.setX(player.getXPos() - (Engine.GAME_WIDTH / 2));
            camera.setY(player.getYPos() - (Engine.GAME_HEIGHT / 2));
            if (camera.getX() > camera.getMaxOffsetX()) {
                camera.setX(camera.getMaxOffsetX());
            }
            if (camera.getX() < camera.getMinOffsetX()) {
                camera.setX(camera.getMinOffsetX());
            }
            if (camera.getY() > camera.getMaxOffsetY()) {
                camera.setY(camera.getMaxOffsetY());
            }
            if (camera.getY() < camera.getMinOffsetY()) {
                camera.setY(camera.getMinOffsetY());
            }
            //collision
            //checkCollision();
            //bullet movement
            //weaponHandler.getEquippedWeapon().magazine.get(0).update(delta);
            /*if (enemyPos <= 32) {
                enemyFacingLeft = false;
            }
            if (enemyPos >= Engine.GAME_WIDTH - 192) {
                enemyFacingLeft = true;
            }
            if (enemyFacingLeft) {
                enemyPos--;
            } else {
                enemyPos++;
            }
            if (Math.abs((enemyPos - player.getXPos())) <= 448) {
                isEnemyAggro = true;
            } else {
                isEnemyAggro = false;
            }
            if (isEnemyAggro) {
                if (timeElapsed >= 1000) {
                    player.damage(0.2f);
                }
            }*/
            player.heal(0.025f);
            timeElapsed += delta;
            fps = engine.getFPS();
        } else if (!player.isAlive()) {
            if (isRestartRequested) {
                stateBasedGame.getState(Game.STATE_ID).init(gameContainer, stateBasedGame);
                stateBasedGame.enterState(Game.STATE_ID);
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        //Set font
        if (player.isAlive() && !isPaused) {
            graphics.translate(-camera.getX(), -camera.getY());
            currentLevel.render(graphics);
            graphics.setColor(Color.gray);
            graphics.fillRoundRect(camera.getX() + 64f, camera.getY() + 28, 320, 32, 8, 100);
            graphics.fillRoundRect(camera.getX() + 64f, camera.getY() + 76, 240, 24, 8, 100);
            if (player.getHealth() >= 50) {
                graphics.setColor(Color.green);
            } else {
                if (player.getHealth() >= 20) {
                    graphics.setColor(Color.yellow);
                } else {
                    graphics.setColor(Color.red);
                }
            }
            if (player.getHealth() > 1.5) {
                for (int i = 0; i < 5; i++) {
                    graphics.fillRoundRect(camera.getX() + 64f, camera.getY() + 28, (float) ((player.getHealth() * 3.2)), 32, 8, 100);
                }
            }
            if (player.getSprintEnergy() >= 15) {
                graphics.setColor(Color.blue);
            } else {
                graphics.setColor(Color.orange);
            }
            if (player.getSprintEnergy() > 1.5) {
                for (int i = 0; i < 5; i++) {
                    graphics.fillRoundRect(camera.getX() + 64f, camera.getY() + 76, (player.getSprintEnergy() * 2.4f), 24, 8, 100);
                }
            }
            graphics.setColor(Color.red);
            graphics.setFont(font);
            healthIcon.draw(camera.getX() + 16f, camera.getY() + 28);
            sprintIcon.draw(camera.getX() + 16f, camera.getY() + 76, .8f);
            graphics.setColor(Color.black);
            graphics.setFont(fpsFont);
            graphics.drawString("FPS:" + fps, camera.getX() + Engine.GAME_WIDTH - 80, camera.getY() + Engine.GAME_HEIGHT - 16);
            //graphics.drawString("Time: " + timeElapsed / 1000, Engine.GAME_WIDTH / 2, Engine.GAME_HEIGHT / 2);
            weaponHandler.getEquippedWeapon().render(graphics, (player.getXPos() - player.getWidth() + (14 * 4)), player.getYPos(), player.getDirection());
            player.render(graphics);
        } else {
            if (isPaused) {
                graphics.setColor(Color.black);
                graphics.fillRect(0, 0, Engine.GAME_WIDTH, Engine.GAME_HEIGHT);
                graphics.setColor(Color.white);
                graphics.setFont(font);
                graphics.drawString("PAUSED", Engine.GAME_WIDTH / 2 - 72, Engine.GAME_HEIGHT / 2 - 96);
            }
            if (!player.isAlive()) {
                graphics.setColor(Color.black);
                graphics.fillRect(0, 0, Engine.GAME_WIDTH, Engine.GAME_HEIGHT);
                graphics.setColor(Color.white);
                graphics.setFont(font);
                graphics.drawString("YOU DIED!", Engine.GAME_WIDTH / 2 - 106, Engine.GAME_HEIGHT / 2 - 96);
            }
        }
    }

    @Override
    public void keyPressed(int code, char c) {
        switch (code) {
            case Input.KEY_LEFT: {
                weaponHandler.getEquippedWeapon().setDirection(-1);
                player.setMoving(true);
                kLeft = true;
                player.setDirection(-1);
            }
            break;
            case Input.KEY_RIGHT: {
                weaponHandler.getEquippedWeapon().setDirection(1);
                player.setMoving(true);
                kRight = true;
                player.setDirection(1);
            }
            break;
            case Input.KEY_UP: {
                player.jump();
            }
            break;
            case Input.KEY_LSHIFT: {
                if (player.getSprintEnergy() >= 15 && player.isOnGround()) {
                    player.sprint();
                }
            }
            break;
            case Input.KEY_SPACE: {
                player.setSprintRequested(false);
                weaponHandler.getEquippedWeapon().update(delta);
            }
            break;
            case Input.KEY_ESCAPE: {
                isPaused = !isPaused;
            }
            break;
            case Input.KEY_ENTER: {
                if (!player.isAlive()) {
                    isRestartRequested = true;
                }
            }
            break;
            case Input.KEY_1: {
                weaponHandler.setWeapon(weaponHandler.getWeaponAt(0));
            }
            break;
            case Input.KEY_2: {
                weaponHandler.setWeapon(weaponHandler.getWeaponAt(1));
            }
            break;
            case Input.KEY_Q: {
                weaponHandler.cycleWeapon(-1, weaponHandler.getCurrentWeaponIndex());
            }
            break;
            case Input.KEY_E: {
                weaponHandler.cycleWeapon(1, weaponHandler.getCurrentWeaponIndex());
            }
            break;
        }
    }

    @Override
    public void keyReleased(int code, char c) {
        switch (code) {
            case Input.KEY_LEFT: {
                if(!kRight) {
                    player.setMoving(false);
                } else {
                    player.setDirection(1);
                }
                kLeft = false;
            }
            break;
            case Input.KEY_RIGHT: {
                if(!kLeft) {
                    player.setMoving(false);
                } else {
                    player.setDirection(-1);
                }
                kRight = false;
            }
            break;
            case Input.KEY_SPACE: {
                isFired = false;
                weaponHandler.getEquippedWeapon().reset();

            }
            break;
            case Input.KEY_LSHIFT: {
                player.stopSprint();


            }
            break;
        default: break;
        }
    }

    public void checkCollision() {
        for(int x = 0; x < 96; x++) {
            for(int y = 0; y < 10; y++) {
                if(player.getHitbox().intersects(currentLevel.tiles[x][y])) {
                    if(currentLevel.blocked[x][y]) {
                        player.setGroundLevel((int) currentLevel.tiles[x][y].getY());
                    }
                }
            }
        }
    }
}
