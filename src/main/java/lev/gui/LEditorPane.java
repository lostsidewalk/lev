package lev.gui;

import javax.swing.*;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.View;
import java.awt.*;

@SuppressWarnings("unused")
public class LEditorPane extends LComponent {
    protected final JEditorPane pane = new JEditorPane();

    public LEditorPane() {
        this.pane.setEditable(false);
        this.pane.setVisible(true);
        this.setVisible(true);
        this.add(this.pane);
    }

    public void addHyperLinkListener(HyperlinkListener h) {
        this.pane.addHyperlinkListener(h);
    }

    public void setContentType(String s) {
        this.pane.setContentType(s);
    }

    public String getText() {
        return this.pane.getText();
    }

    public void setText(String s) {
        this.pane.setText(s);
    }

    public void setSize(Dimension size) {
        this.setSize(size.width, size.height);
    }

    public void setSize(int x, int y) {
        super.setSize(x, y);
        this.pane.setSize(x, y);
    }

    public void setSize(int x) {
        this.setSize(x, this.getHeight(x));
    }

    public void setFont(Font f) {
        this.pane.setFont(f);
    }

    public void honorDisplayProperties() {
        this.pane.putClientProperty("JEditorPane.honorDisplayProperties", Boolean.TRUE);
    }

    public Dimension getPreferredSize() {
        return this.pane.getPreferredSize();
    }

    public void compactContent(int maxWidth) {
        this.setSize(maxWidth);
        if (this.getPreferredSize().width < this.getWidth()) {
            this.setSize(this.getPreferredSize().width, this.getHeight());
        }

    }

    public int getHeight(int width) {
        View v = this.pane.getUI().getRootView(this.pane);
        v.setSize((float) width, 2.14748365E9F);
        return (int) v.getPreferredSpan(1);
    }

    public int getWidth(int height) {
        View v = this.pane.getUI().getRootView(this.pane);
        v.setSize(2.14748365E9F, (float) height);
        return (int) v.getPreferredSpan(0);
    }

    public void setOpaque(boolean on) {
        this.pane.setOpaque(on);
    }

    public void setEnabled(boolean on) {
        this.pane.setEnabled(on);
    }
}
