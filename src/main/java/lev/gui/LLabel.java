package lev.gui;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("unused")
public class LLabel extends LComponent {
    protected final JLabel mainText;
    protected JLabel shadow;
    protected JLabel shadow2;
    protected JLabel shadow3;
    protected final int shadowSpacing = 2;

    public LLabel(String text, Font font, Color c) {
        this.mainText = new JLabel(text);
        this.mainText.setFont(font);
        this.mainText.setForeground(c);
        this.mainText.setSize(this.mainText.getPreferredSize());
        this.mainText.setFocusable(false);
        this.mainText.setVisible(true);
        this.add(this.mainText);
        this.setSize(this.mainText.getPreferredSize());
    }

    public void addShadow() {
        this.shadow = this.copyLabel(this.mainText);
        this.shadow.setForeground(Color.BLACK);
        this.shadow2 = this.copyLabel(this.shadow);
        this.shadow3 = this.copyLabel(this.shadow);
        this.shadow.setLocation(this.shadowSpacing, this.shadowSpacing);
        this.shadow2.setLocation(0, this.shadowSpacing);
        this.shadow3.setLocation(this.shadowSpacing, 0);
        this.add(this.shadow);
        this.add(this.shadow2);
        this.add(this.shadow3);
        this.setSize(this.getWidth() + this.shadowSpacing, this.getHeight() + this.shadowSpacing);
    }

    private JLabel copyLabel(JLabel input) {
        JLabel output = new JLabel(input.getText());
        output.setFont(input.getFont());
        output.setForeground(input.getForeground());
        output.setSize(output.getPreferredSize());
        output.setVisible(true);
        return output;
    }

    public String getText() {
        return this.mainText.getText();
    }

    public void setText(String input) {
        this.mainText.setText(input);
        this.mainText.setSize(this.mainText.getPreferredSize());
        if (this.shadow != null) {
            this.shadow.setText(input);
            this.shadow2.setText(input);
            this.shadow3.setText(input);
            this.shadow.setSize(this.shadow.getPreferredSize());
            this.shadow2.setSize(this.shadow2.getPreferredSize());
            this.shadow3.setSize(this.shadow3.getPreferredSize());
        }

        this.setSize(this.getPreferredSize());
    }

    public void setFontColor(Color c) {
        this.mainText.setForeground(c);
    }

    public Dimension getPreferredSize() {
        return this.shadow == null ? this.mainText.getSize() : new Dimension(this.mainText.getWidth() + this.shadowSpacing, this.mainText.getHeight() + this.shadowSpacing);
    }

    public void setForeground(Color c) {
        this.mainText.setForeground(c);
        this.mainText.repaint();
    }

    public Font getFont() {
        return this.mainText.getFont();
    }

    public void setFont(Font font) {
        this.mainText.setFont(font);
        if (this.shadow != null) {
            this.shadow.setFont(font);
            this.shadow2.setFont(font);
            this.shadow3.setFont(font);
        }
    }
}
