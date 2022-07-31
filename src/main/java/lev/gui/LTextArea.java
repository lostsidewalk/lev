package lev.gui;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LTextArea extends LComponent {
    protected JScrollPane scroll;
    protected final JTextArea area;
    protected final Document doc;

    public LTextArea(Dimension size_, Color c) {
        this(c);
        this.setSize(size_);
    }

    public LTextArea(int x, int y, Color c) {
        this(new Dimension(x, y), c);
    }

    public LTextArea(Color c) {
        this.area = new JTextArea();
        this.doc = this.area.getDocument();
        this.area.setOpaque(false);
        this.setVisible(true);
        this.setForeground(c);
        this.add(this.area);
    }

    public void setSize(Dimension size) {
        this.setSize(size.width, size.height);
    }

    public void setSize(int width, int height) {
        super.setSize(width, height);
        if (this.scroll == null) {
            this.area.setSize(width, height);
        } else {
            this.scroll.setSize(width, height);
        }

    }

    public void setForeground(Color c) {
        this.area.setForeground(c);
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
        this.area.setOpaque(b);
    }

    public void setFont(Font f) {
        this.area.setFont(f);
    }

    public boolean isEmpty() {
        return this.doc.getLength() == 0;
    }

    public void setBackground(Color c) {
        this.area.setBackground(c);
    }

    public void setCaretColor(Color c) {
        this.area.setCaretColor(c);
    }

    public void setLineWrap(boolean b) {
        this.area.setLineWrap(b);
    }

    public void setWrapStyleWord(boolean b) {
        this.area.setWrapStyleWord(b);
    }

    public void addScroll() {
        this.removeAll();
        this.scroll = new JScrollPane(this.area);
        this.scroll.setSize(this.area.getSize());
        this.scroll.setOpaque(false);
        this.scroll.getViewport().setOpaque(false);
        this.scroll.setBorder(null);
        this.scroll.setVisible(true);
        this.add(this.scroll);
    }

    public void setEditable(boolean b) {
        this.area.setEditable(b);
    }

    public void setFontSize(float size) {
        this.area.setFont(this.area.getFont().deriveFont(size));
    }

    public Dimension getPreferredSize() {
        return this.area.getPreferredSize();
    }
}
