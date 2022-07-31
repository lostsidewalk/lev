package lev.gui;

import java.awt.*;

@SuppressWarnings("unused")
public class LComponent extends Container {
    public LComponent() {
    }

    protected void Add(Component c) {
        c.setVisible(true);
        this.add(c);
    }

    public Point centerIn(Component c, int y) {
        this.setLocation(c.getWidth() / 2 - this.getWidth() / 2, y);
        return this.getLocation();
    }

    public Point centerOn(Component c, int y) {
        this.setLocation(c.getX() + c.getWidth() / 2 - this.getWidth() / 2, y);
        return this.getLocation();
    }

    public Point centerOn(int x, Component c) {
        this.setLocation(x, c.getY() + c.getHeight() / 2 - this.getHeight() / 2);
        return this.getLocation();
    }

    public Point putUnder(Component c, int x, int yOffset) {
        this.setLocation(x, c.getY() + c.getHeight() + yOffset);
        return this.getLocation();
    }

    public int getBottom() {
        return this.getY() + this.getHeight();
    }

    public int getRight() {
        return this.getX() + this.getWidth();
    }

    public int getCenter() {
        return this.getX() + this.getWidth() / 2;
    }
}
