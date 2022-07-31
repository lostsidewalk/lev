package lev.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.util.Map;

@SuppressWarnings("unused")
public class LComboBox<T> extends LUserSetting<Integer> {
    protected JComboBox<T> box;
    protected T previous;
    protected LButton enterButton;

    public LComboBox(String title_) {
        super(title_);
        this.init();
    }

    public LComboBox(String title_, Font font, Color shade) {
        super(title_, font, shade);
        this.titleLabel.addShadow();
        this.init();
        this.box.setLocation(this.box.getX(), this.titleLabel.getY() + this.titleLabel.getHeight() + 10);
    }

    final void init() {
        this.box = new JComboBox<>();
        this.add(this.box);
        this.box.setVisible(true);
        this.setVisible(true);
    }

    public void setSize(int x, int y) {
        super.setSize(x, y);
        if (this.titleLabel == null) {
            this.box.setSize(x, y);
        } else {
            this.box.setSize(x, y - this.titleLabel.getY() - this.titleLabel.getHeight() - 10);
        }

        if (this.enterButton != null) {
            this.enterButton.setLocation(x - this.enterButton.getWidth(), this.box.getY());
            this.box.setSize(x - this.enterButton.getWidth() - 5, this.box.getHeight());
            this.enterButton.setSize(this.enterButton.getWidth(), this.box.getHeight());
        }

    }

    public void addActionListener(ActionListener a) {
        this.box.addActionListener(a);
    }

    public T getSelectedItem() {
        return (T) this.box.getSelectedItem();
    }

    public void reset() {
        this.box.setSelectedIndex(-1);
    }

    public void removeAllItems() {
        this.box.removeAllItems();
    }

    public void addItem(T o) {
        this.box.addItem(o);
    }

    public void setSelectedIndex(int in) {
        if (this.box.getItemCount() <= in) {
            this.box.setSelectedIndex(this.box.getItemCount() - 1);
        } else this.box.setSelectedIndex(Math.max(in, -1));
    }

    public void switchTo(T o) {
        for (int i = 0; i < this.box.getItemCount(); ++i) {
            if (this.box.getItemAt(i).equals(o)) {
                this.setSelectedIndex(i);
            }
        }

    }

    public void savePrevious() {
        if (this.box.getSelectedItem() != null) {
            this.previous = this.getSelectedItem();
        } else {
            this.previous = null;
        }

    }

    public void switchToPrevious() {
        if (this.previous != null) {
            this.switchTo(this.previous);
        }

    }

    public void addHelpHandler(boolean mouseListener) {
        this.addFocusListener(new LHelpComponent.HelpFocusHandler());
        if (mouseListener) {
            this.addMouseListener(new LHelpComponent.HelpMouseHandler());
        }

    }

    public void addFocusListener(FocusListener f) {
        super.addFocusListener(f);
        Component[] arr$ = this.box.getComponents();
        int len$ = arr$.length;

        for (Component c : arr$) {
            c.addFocusListener(f);
        }

        if (this.enterButton != null) {
            this.enterButton.addFocusListener(f);
        }

    }

    public void addMouseListener(MouseListener m) {
        super.addMouseListener(m);
        this.box.addMouseListener(m);
        Component[] arr$ = this.box.getComponents();
        int len$ = arr$.length;

        for (Component c : arr$) {
            c.addMouseListener(m);
        }

        if (this.titleLabel != null) {
            this.titleLabel.addMouseListener(m);
        }

        if (this.enterButton != null) {
            this.enterButton.addMouseListener(m);
        }

    }

    protected void addUpdateHandlers() {
        this.box.addActionListener(new LUserSetting.UpdateHandler());
    }

    public void addEnterButton(String label, ActionListener done) {
        this.enterButton = new LButton(label);
        this.enterButton.addActionListener(done);
        this.add(this.enterButton);
        this.setSize(this.getSize().width, this.getSize().height);
    }

    public boolean isEmpty() {
        return this.box.getModel().getSize() == 0;
    }

    public boolean revertTo(Map<Enum<?>, Setting<?>> m) {
        if (this.isTied()) {
            int cur = this.box.getSelectedIndex();
            this.box.setSelectedIndex(m.get(this.saveTie).getInt());
            return cur == this.box.getSelectedIndex();
        }

        return true;
    }

    public Integer getValue() {
        return this.box.getSelectedIndex();
    }

    public void highlightChanged() {
        this.box.setBackground(new Color(224, 121, 147));
    }

    public void clearHighlight() {
        this.box.setBackground(null);
    }
}
