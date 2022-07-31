package lev.gui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

@SuppressWarnings("unused")
public class LMenuItem extends LHelpComponent {
    protected final JMenuItem menuItem;

    public LMenuItem(String title_) {
        super(title_);
        this.menuItem = new JMenuItem(title_);
    }

    protected void addHelpHandler(boolean hoverListener) {
        this.menuItem.addMouseListener(new LHelpComponent.HelpMouseHandler());
    }

    public JMenuItem getItem() {
        return this.menuItem;
    }

    public void addActionListener(ActionListener a) {
        this.menuItem.addActionListener(a);
    }

    public void addMouseListener(MouseListener m) {
        this.menuItem.addMouseListener(m);
    }

    public boolean isVisible() {
        return this.menuItem.isVisible();
    }

    public void setVisible(boolean b) {
        this.menuItem.setVisible(b);
    }
}
