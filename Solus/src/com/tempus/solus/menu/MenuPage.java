package com.tempus.solus.menu;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;

abstract public class MenuPage {
    protected UnicodeFont titleFont;
    protected UnicodeFont optionsFont;


    public MenuPage(UnicodeFont titleFont, UnicodeFont optionsFont) {
        this.titleFont = titleFont;
        this.optionsFont = optionsFont;
    }

    abstract protected void init();
    abstract public void render(Graphics graphics);
    abstract public void update(int keyCode, int newMouseX, int newMouseY, int clickedX, int clickedY, int delta);


}
