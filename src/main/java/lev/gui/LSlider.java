package lev.gui;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Map;

public class LSlider extends LUserSetting<Integer> {
    protected JSlider slider;

    public LSlider(String title, Font font, Color c, int min, int max, int cur) {
        super(title, font, c);
        this.slider = new JSlider(0, min, max, cur);
        this.slider.setSize(100, 16);
        this.slider.setLocation(this.titleLabel.getRight() + 5, 0);
        this.slider.setVisible(true);
        this.add(this.slider);
        this.setSize(this.titleLabel.getWidth() + 100, Lg.taller(this.slider, this.titleLabel));
    }

    public void setSize(int x, int y) {
        this.slider.setSize(x - this.titleLabel.getWidth() - 5, this.slider.getHeight());
        this.titleLabel.setLocation(this.titleLabel.getX(), y / 2 - this.titleLabel.getHeight() / 2);
        this.slider.setLocation(this.slider.getX(), y / 2 - this.slider.getHeight() / 2);
        super.setSize(x, y);
    }

    protected void addUpdateHandlers() {
        this.slider.addChangeListener(new LUserSetting.UpdateChangeHandler());
    }

    public boolean revertTo(Map<Enum<?>, Setting<?>> m) {
        if (this.isTied()) {
            int cur = this.slider.getValue();
            this.slider.setValue(m.get(this.saveTie).getInt());
            return cur == this.slider.getValue();
        }

        return true;
    }

    public Integer getValue() {
        return this.slider.getValue();
    }

    public void highlightChanged() {
    }

    public void clearHighlight() {
    }

    public int getCenter() {
        return this.titleLabel.getRight() + 3;
    }

    protected void addHelpHandler(boolean hoverListener) {
        this.slider.addMouseListener(new LHelpComponent.HelpMouseHandler());
    }

    public void addChangeListener(ChangeListener c) {
        this.slider.addChangeListener(c);
    }
}
