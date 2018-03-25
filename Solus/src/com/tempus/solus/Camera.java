package com.tempus.solus;

public class Camera {
    private float camX;
    private float camY;
    private float worldWidth;
    private float worldHeight;
    private float maxOffsetX;
    private float maxOffsetY;
    private float minOffsetX;
    private float minOffsetY;

    public Camera(float levelWidth, float levelHeight) {
        camX = 0;
        camY = 0;
        worldWidth = levelWidth;
        worldHeight = levelHeight;
        maxOffsetX = levelWidth - (Engine.GAME_WIDTH);
        maxOffsetY = levelHeight - (Engine.GAME_HEIGHT);
        minOffsetX = 0;
        minOffsetY = 0;
    }

    public void setX(float x) {
        camX = x;
    }

    public float getX() {
        return camX;
    }

    public void setY(float y) {
        camY = y;
    }

    public float getY() {
        return camY;
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public void setWorldWidth(float width) {
        worldWidth = width;
    }

    public float getWorldHeight() {
        return worldHeight;
    }

    public void setWorldHeight(float height) {
        worldHeight = height;
    }

    public float getMaxOffsetX() {
        return maxOffsetX;
    }

    public float getMaxOffsetY() {
        return maxOffsetY;
    }
    public float getMinOffsetX() {
        return minOffsetX;
    }

    public float getMinOffsetY() {
        return minOffsetY;
    }
}
