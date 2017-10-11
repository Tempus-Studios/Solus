package com.illius.solus.entity;

public abstract class Enemy {
    public String enemyType;
    public String texturePath;
    public float xPos;
    public float yPos;
    public float xVel;
    public float yVel;
    public float health;
    public float maxHealth;

    public Enemy(String enemyType) {
        this.enemyType = enemyType;
    }

    public Enemy(String enemyType, String texturePath, float xPos, float yPos, float xVel, float yVel, float health, float maxHealth) {
        this.enemyType = enemyType;
        this.texturePath = texturePath;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = xVel;
        this.yVel = yVel;
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public abstract void render();

    public abstract void update();
}
