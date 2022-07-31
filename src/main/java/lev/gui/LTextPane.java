package lev.gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LTextPane extends LComponent {
    protected JScrollPane scroll;
    protected final JTextPane pane;
    protected final Document doc;

    public LTextPane(Color c) {
        this();
        this.setForeground(c);
    }

    public LTextPane() {
        this.pane = new JTextPane();
        this.doc = this.pane.getDocument();
        this.pane.setOpaque(false);
        this.setVisible(true);
        this.add(this.pane);
    }

    public LTextPane(int x, int y, Color c) {
        this(c);
        this.setSize(x, y);
    }

    public LTextPane(Dimension size, Color c) {
        this(c);
        this.setSize(size);
    }

    public void setSize(Dimension size) {
        this.setSize(size.width, size.height);
    }

    public void setSize(int width, int height) {
        super.setSize(width, height);
        if (this.scroll == null) {
            this.pane.setSize(width, height);
        } else {
            this.scroll.setSize(width, height);
        }

    }

    public void setSize(int x) {
        this.setSize(x, this.getHeight(x));
    }

    public int getHeight(int width) {
        View v = this.pane.getUI().getRootView(this.pane);
        v.setSize((float) width, 2.14748365E9F);
        return (int) v.getPreferredSpan(1);
    }

    public void setForeground(Color c) {
        this.pane.setForeground(c);
    }

    public String getText() {
        try {
            return this.doc.getText(0, this.doc.getLength());
        } catch (BadLocationException var2) {
            return "ERROR";
        }
    }

    public void setText(String in) {
        this.clearText();

        try {
            this.doc.insertString(0, in, null);
        } catch (BadLocationException var3) {
            this.badText();
        }

    }

    public void badText() {
        try {
            this.doc.insertString(0, "Bad Error", null);
        } catch (BadLocationException var2) {
            Logger.getLogger(LTextPane.class.getName()).log(Level.SEVERE, null, var2);
        }

    }

    public void clearText() {
        try {
            this.doc.remove(0, this.doc.getLength());
        } catch (BadLocationException var2) {
        }

    }

    public void append(String in) {
        try {
            this.doc.insertString(this.doc.getLength(), in, null);
        } catch (BadLocationException var3) {
            this.badText();
        }

    }

    public void setOpaque(Boolean b) {
        this.pane.setOpaque(b);
    }

    public void setCentered() {
        StyledDocument doc = this.pane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, 1);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    public void setFont(Font f) {
        this.pane.setFont(f);
    }

    public boolean isEmpty() {
        return this.doc.getLength() == 0;
    }

    public void setBackground(Color c) {
        this.pane.setBackground(c);
    }

    public void setCaretColor(Color c) {
        this.pane.setCaretColor(c);
    }

    public void centerText() {
        StyledDocument doc = this.pane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, 1);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    public void setContentType(String s) {
        this.pane.setContentType(s);
    }

    public void addScroll() {
        this.removeAll();
        this.scroll = new JScrollPane(this.pane);
        this.scroll.setSize(this.pane.getSize());
        this.scroll.setOpaque(false);
        this.scroll.getViewport().setOpaque(false);
        this.scroll.setBorder(null);
        this.scroll.setVisible(true);
        this.add(this.scroll);
    }

    public void setEditable(boolean b) {
        this.pane.setEditable(b);
    }

    public void setFontSize(float size) {
        this.pane.setFont(this.pane.getFont().deriveFont(size));
    }

    public Dimension getPreferredSize() {
        return this.pane.getPreferredSize();
    }
}
