package lev.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("unused")
public class LCheckBox extends LUserSetting<Boolean> {
    protected JCheckBox cbox;
    protected ArrayList<LComponent> underlings = new ArrayList<>(0);

    public LCheckBox(String text, Font font, Color shade) {
        super(text, font, shade);
        int spacing = 5;
        this.titleLabel.addMouseListener(new ClickText());
        this.cbox = new JCheckBox();
        this.cbox.setSize(this.cbox.getPreferredSize());
        this.cbox.setOpaque(false);
        this.cbox.setLocation(this.titleLabel.getWidth() + spacing, 0);
        this.cbox.addChangeListener(new underlingChangeHandler());
        this.cbox.addActionListener(new underlingActionHandler());
        this.setSize(this.titleLabel.getWidth() + this.cbox.getWidth() + spacing, this.titleLabel.getHeight());
        this.setVisible(true);
        this.add(this.cbox);
    }

    public int getCenter() {
        return this.titleLabel.getWidth() + 3;
    }

    public void addShadow() {
        this.titleLabel.addShadow();
    }

    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        this.cbox.setFocusable(focusable);
    }

    public void setOffset(int offset) {
        this.cbox.setLocation(this.cbox.getX(), offset);
    }

    public void setSelected(Boolean b) {
        this.cbox.setSelected(b);
    }

    public Boolean isSelected() {
        return this.cbox.isSelected();
    }

    public boolean revertTo(Map<Enum<?>, Setting<?>> m) {
        if (this.isTied()) {
            boolean cur = this.cbox.isSelected();
            this.cbox.setSelected(m.get(this.saveTie).getBool());
            return cur == this.cbox.isSelected();
        }

        return true;
    }

    public void addActionListener(ActionListener l) {
        this.cbox.addActionListener(l);
    }

    public void addChangeListener(ChangeListener l) {
        this.cbox.addChangeListener(l);
    }

    public void addMouseListener(MouseListener l) {
        this.titleLabel.addMouseListener(l);
        this.cbox.addMouseListener(l);
    }

    public Boolean getValue() {
        return this.cbox.isSelected();
    }

    public void addAsUnderling(LComponent c) {
        this.underlings.add(c);
        this.visibilityUpdate();
    }

    private void visibilityUpdate() {
        SwingUtilities.invokeLater(() -> {
            for (LComponent c : LCheckBox.this.underlings) {
                c.setVisible(LCheckBox.this.isSelected());
            }
        });
    }

    public void highlightChanged() {
    }

    public void clearHighlight() {
    }

    public void addHelpHandler(boolean hoverListener) {
        this.cbox.addFocusListener(new LHelpComponent.HelpFocusHandler());
        if (hoverListener) {
            this.cbox.addMouseListener(new LHelpComponent.HelpMouseHandler());
            this.titleLabel.addMouseListener(new LHelpComponent.HelpMouseHandler());
        }
    }

    protected final void addUpdateHandlers() {
        this.cbox.addActionListener(new LUserSetting<?>.UpdateHandler());
        this.cbox.addChangeListener(new LUserSetting<?>.UpdateChangeHandler());
    }

    public void removeChangeListener(ChangeListener c) {
        this.cbox.removeChangeListener(c);
    }

    public void setColor(Color c) {
        this.titleLabel.setForeground(c);
    }

    class ClickText implements MouseListener {
        ClickText() {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
            LCheckBox.this.cbox.requestFocusInWindow();
            LCheckBox.this.cbox.setSelected(!LCheckBox.this.cbox.isSelected());

        }
    }

    private class underlingActionHandler implements ActionListener {
        private underlingActionHandler() {
        }

        public void actionPerformed(ActionEvent event) {
            LCheckBox.this.visibilityUpdate();
        }
    }

    private class underlingChangeHandler implements ChangeListener {
        private underlingChangeHandler() {
        }

        public void stateChanged(ChangeEvent event) {
            LCheckBox.this.visibilityUpdate();
        }
    }
}
