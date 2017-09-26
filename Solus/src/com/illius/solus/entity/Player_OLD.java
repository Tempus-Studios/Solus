package com.illius.solus.entity;

import com.illius.solus.Game_OLD;
import com.illius.solus.Engine;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Player_OLD {
    public Texture playerFacingLeftTexture;
    public Texture playerFacingRightTexture;
    public String playerName;
    public static boolean isDead = false;
    public boolean sprinting = false;
    public boolean sprintAvailable = true;
    public boolean allowSprintRecharge = false;
    public boolean facingLeft = false;
    public boolean facingRight = true;
    public boolean movingLeft = false;
    public boolean movingRight = false;
    public boolean jumped = false;
    public float xPos = 32;
    public float yPos = 32;
    public float xVel = 0;
    public float yVel = 0;
    public float health = 100;
    public final float maxHealth = 100;
    public float sprintEnergy = 100;

    public Player_OLD(String name) {
        this.playerName = name;
    }

    public void render() {
        try {
            playerFacingLeftTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/playerFacingLeft.png"));
            playerFacingRightTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/playerFacingRight.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (facingLeft) {
            playerFacingLeftTexture.bind();
        } else {
            playerFacingLeftTexture.release();
        }
        if (facingRight) {
            playerFacingRightTexture.bind();
        } else {
            playerFacingRightTexture.release();
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(xPos, -yPos, 0);
        GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0.0f, 1.0f);
            GL11.glVertex2f(0.0f, 0.0f);
            GL11.glTexCoord2f(1.0f, 1.0f);
            GL11.glVertex2f(160.0f, 0.0f);
            GL11.glTexCoord2f(1.0f, 0.0f);
            GL11.glVertex2f(160.0f, 160.0f);
            GL11.glTexCoord2f(0.0f, 0.0f);
            GL11.glVertex2f(0.0f, 160.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public void update() {
        if (xPos < -32) {
            xPos = -32;
        }
        if (xPos > Engine.GAME_WIDTH - 96) {
            xPos = Engine.GAME_WIDTH - 96;
        }
        if (yPos < 32) {
            yPos = 32;
        }
        if (health < maxHealth) {
            health += 0.0001;
        }
        if (health < 0) {
            health = 0;
        }
        if (health == 0) {
            isDead = true;
        } else {
            isDead = false;
        }
        if (sprintEnergy < 0) {
            sprintEnergy = 0;
            sprintAvailable = false;
        }
        if (sprintEnergy > 100) {
            sprintEnergy = 100;
            sprintAvailable = true;
        }
        if (sprintEnergy == 0) {
            sprintAvailable = false;
            sprinting = false;
            allowSprintRecharge = true;
            sprintEnergy += 0.0001;
        }
        if (sprintAvailable) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                sprinting = true;
            } else {
                sprinting = false;
            }
        } else {
            sprinting = false;
        }
        if (sprinting) {
            sprintEnergy -= 0.1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            movingLeft = true;
            movingRight = false;
            facingLeft = true;
            facingRight = false;
            if (sprinting) {
                xVel = -6;
            } else {
                if (yPos == 32) {
                    xVel = -4;
                } else {
                    xVel = -3;
                }
            }
        } else {
            if (movingLeft) {
                if (xVel < 0 ) {
                    if (sprinting) {
                        xVel += 0.5;
                    } else {
                        xVel += 0.3;
                    }
                } else {
                    if (xVel > 0) {
                        xVel = 0;
                        movingLeft = false;
                        movingRight = false;
                    }
                }
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            movingLeft = false;
            movingRight = true;
            facingLeft = false;
            facingRight = true;
            if (sprinting) {
                xVel = 6;
            } else {
                if (yPos == 32) {
                    xVel = 4;
                } else {
                    xVel = 3;
                }
            }
        } else {
            if (movingRight) {
                if (xVel > 0 ) {
                    if (sprinting) {
                        xVel -= 0.5;
                    } else {
                        xVel -= 0.3;
                    }
                } else {
                    if (xVel < 0) {
                        xVel = 0;
                        movingLeft = false;
                        movingRight = false;
                    }
                }
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_D)) {
            facingLeft = false;
            facingRight = true;
            xVel = 0;
        }
        if (movingLeft) {
            facingRight = false;
            facingLeft = true;
        }
        if (movingRight) {
            facingRight = true;
            facingLeft = false;
        }
        if (facingLeft && facingRight) {
            facingRight = true;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            jumped = true;
            if (yPos == 32) {
                yVel = 6.5f;
            } else {
                if (yPos > Engine.GAME_HEIGHT - 160 ) {
                    yPos = Engine.GAME_HEIGHT - 160;
                }
                if (yPos == Engine.GAME_HEIGHT - 160) {
                    yVel = -6.5f;
                }
            }
        } else {
            if (jumped) {
                if (yPos <= 32) {
                    yVel = 0;
                } else {
                    yVel = -5;
                }
                jumped = false;
            }
        }
        xPos += xVel;
        yPos += yVel;
        if (Mouse.isButtonDown(0)) {
            //fireWeapon();
        }
    }

    public void fireWeapon(String weaponType, int currentAmmo, int magSize, int reserves) {
        //TODO
    }

    public void takeDamage(float baseDamage, boolean dotActive, float dotDamage, int dotNumTicks, float tickRate) {
        Game_OLD game = new Game_OLD();
        health -= baseDamage;
        if (dotActive) {
            for (int i = 0; i < (dotNumTicks * game.getFps()); i++) {
                if (game.hasSecondPassed) {
                    health -= dotDamage;
                }
            }
        }
    }
}
