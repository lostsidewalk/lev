package lev.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class LTextField extends LUserSetting<String> {
    protected JTextField field;
    protected LButton enterButton;

    public LTextField(String title_, Font font, Color shade) {
        super(title_, font, shade);
        this.init();
    }

    public LTextField(String title_) {
        super(title_);
        this.init();
    }

    final void init() {
        this.field = new JTextField();
        this.add(this.field);
        if (this.titleLabel != null) {
            this.titleLabel.addShadow();
            this.field.setLocation(0, this.titleLabel.getHeight() + 5);
            this.setSize(275, 50);
        } else {
            this.setSize(275, 22);
        }

        this.field.setVisible(true);
        this.setVisible(true);
    }

    public final void setSize(int x, int y) {
        super.setSize(x, y);
        if (this.titleLabel != null) {
            this.field.setSize(x, y - this.titleLabel.getHeight() - 5);
        } else {
            this.field.setSize(x, y);
        }

        if (this.enterButton != null) {
            this.field.setSize(x - this.enterButton.getWidth() - 10, this.field.getHeight());
            this.enterButton.setLocation(this.field.getX() + this.field.getWidth() + 10, this.field.getY());
            this.enterButton.setSize(this.enterButton.getWidth(), this.field.getHeight());
        }

    }

    public void addActionListener(ActionListener a) {
        this.field.addActionListener(a);
    }

    protected void addUpdateHandlers() {
        this.field.addActionListener(new LUserSetting.UpdateHandler());
    }

    public String getText() {
        return this.field.getText();
    }

    public void setText(String s) {
        this.field.setText(s);
    }

    public boolean revertTo(Map<Enum<?>, Setting<?>> m) {
        if (this.isTied()) {
            String cur = this.field.getText();
            this.field.setText(m.get(this.saveTie).getStr());
            return cur.equals(this.field.getText());
        }

        return true;
    }

    public String getValue() {
        return this.field.getText();
    }

    public void highlightChanged() {
        this.field.setBackground(new Color(224, 121, 147));
    }

    public void clearHighlight() {
        this.field.setBackground(Color.white);
    }

    public void addHelpHandler(boolean hoverListener) {
        this.field.addFocusListener(new LHelpComponent.HelpFocusHandler());
        if (hoverListener) {
            this.field.addMouseListener(new LHelpComponent.HelpMouseHandler());
        }

    }

    public void addEnterButton(String label, ActionListener done) {
        this.enterButton = new LButton(label);
        this.enterButton.addActionListener(done);
        this.add(this.enterButton);
        this.setSize(this.getSize().width, this.getSize().height);
    }
}
