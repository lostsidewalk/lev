package lev.gui;

import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.FocusListener;
import java.util.Map;

@SuppressWarnings("unused")
public class LNumericSetting extends LUserSetting<Integer> {
    protected final LSpinner setting;

    public LNumericSetting(String text, Font font, Color c, int min, Integer max, int step) {
        super(text, font, c);
        this.setting = new LSpinner(text, min, min, max, step, Integer.toString(max).length());
        int spacing = 10;
        this.titleLabel.addShadow();
        if (this.titleLabel.getHeight() < this.setting.getHeight()) {
            this.titleLabel.setLocation(this.titleLabel.getX(), this.setting.getHeight() / 2 - this.titleLabel.getHeight() / 2);
            this.setSize(1, this.setting.getHeight());
        } else {
            this.setting.setLocation(this.setting.getX(), this.titleLabel.getHeight() / 2 - this.setting.getHeight() / 2);
            this.setSize(1, this.titleLabel.getHeight());
        }

        this.setting.setLocation(this.titleLabel.getWidth() + spacing, this.setting.getY());
        this.Add(this.setting);
        this.Add(this.titleLabel);
        this.setting.titleLabel = this.titleLabel;
        this.setSize(this.titleLabel.getWidth() + this.setting.getWidth() + spacing, this.getHeight());
        this.setLocation(this.getX() - this.getWidth(), this.getY());
        this.setVisible(true);
    }

    public final void tie(Enum<?> s, LSaveFile save_, LHelpPanel help_, boolean hoverHandler) {
        this.setting.tie(s, save_, help_, hoverHandler);
    }

    public void linkTo(Enum<?> s, LSaveFile save, LHelpPanel help_, boolean hoverListener) {
        this.setting.linkTo(s, save, help_, hoverListener);
    }

    public void tie(Enum<?> s, LSaveFile save_) {
        this.setting.tie(s, save_);
    }

    public Integer getValue() {
        return this.setting.getValue();
    }

    public void setValue(String s) {
        this.setValue(Integer.parseInt(s));
    }

    public void setValue(int i) {
        this.setting.setValue(i);
    }

    public void setValue(double d) {
        this.setValue(Double.toString(d));
    }

    public synchronized void addFocusListener(FocusListener arg0) {
        this.setting.addFocusListener(arg0);
    }

    public void addChangeListener(ChangeListener c) {
        this.setting.addChangeListener(c);
    }

    public void setColor(Color c) {
        this.titleLabel.setForeground(c);
    }

    public void highlightChanged() {
        this.setting.highlightChanged();
    }

    public void clearHighlight() {
        this.setting.clearHighlight();
    }

    protected void addUpdateHandlers() {
        this.setting.addUpdateHandlers();
    }

    public boolean revertTo(Map<Enum<?>, Setting<?>> m) {
        return this.setting.revertTo(m);
    }

    protected void addHelpHandler(boolean hoverListener) {
        this.setting.addHelpHandler(hoverListener);
    }

    public final void setLocation(int x, int y) {
        super.setLocation(x, y);
        this.setting.helpYoffset = y;
    }

    public final void setLocation(Point p) {
        super.setLocation(p);
        this.setting.helpYoffset = p.y;
    }
}
