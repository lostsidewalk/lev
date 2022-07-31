package lev.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;

@SuppressWarnings("unused")
public class LButton extends LHelpComponent {
    protected final JButton button;

    public LButton(String title) {
        super(title);
        this.button = new JButton();
        this.button.setText(title);
        this.button.setMargin(new Insets(0, 2, 0, 2));
        this.button.setSize(this.button.getPreferredSize());
        this.button.setFocusable(false);
        this.button.setVisible(true);
        this.button.setLocation(0, 0);
        super.setSize(this.button.getSize());
        this.setVisible(true);
        this.add(this.button);
    }

    public LButton(String title, Dimension size) {
        this(title);
        this.setSize(size);
    }

    public LButton(String title, int x, int y) {
        this(title);
        this.setSize(x, y);
    }

    public LButton(String title, Dimension size, Point location) {
        this(title);
        this.setSize(size);
        this.setLocation(location);
    }

    public LButton(String title, Point location) {
        this(title);
        this.setLocation(location);
    }

    public final void setSize(Dimension size) {
        this.setSize(size.width, size.height);
    }

    public final void setSize(int x, int y) {
        this.button.setSize(x, y);
        super.setSize(x, y);
    }

    public void addActionListener(ActionListener l) {
        this.button.addActionListener(l);
    }

    public void setActionCommand(String s) {
        this.button.setActionCommand(s);
    }

    public JButton getSource() {
        return this.button;
    }

    public boolean requestFocusInWindow() {
        return this.button.requestFocusInWindow();
    }

    public synchronized void addMouseListener(MouseListener arg0) {
        this.button.addMouseListener(arg0);
    }

    public String getText() {
        return this.button.getText();
    }

    public void setText(String in) {
        this.button.setText(in);
    }

    protected void addHelpHandler(boolean hoverListener) {
        if (hoverListener) {
            this.button.addMouseListener(new LHelpComponent.HelpMouseHandler());
        }

        this.button.addActionListener(new LHelpComponent.HelpActionHandler());
    }

    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        this.button.setFocusable(focusable);
    }

    public void addFocusListener(FocusListener l) {
        this.button.addFocusListener(l);
    }

    public void clearActionHandlers() {
        ActionListener[] ls = this.button.getListeners(ActionListener.class);
        int len$ = ls.length;

        for (ActionListener a : ls) {
            this.button.removeActionListener(a);
        }
    }
}
