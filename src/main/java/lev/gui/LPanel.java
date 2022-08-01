package lev.gui;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("unused")
public class LPanel extends JPanel implements Scrollable {
    protected Point last;
    protected int spacing;
    protected Align align;

    public LPanel() {
        this.spacing = 12;
        this.align = LPanel.Align.Left;
        this.setLayout(null);
        super.setSize(1, 1);
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder());
        super.setVisible(true);
        this.last = new Point(this.spacing, 0);
    }

    public LPanel(Rectangle r) {
        this();
        this.setBounds(r);
    }

    public LPanel(int x, int y) {
        this();
        this.setLocation(x, y);
    }

    protected static AlphaComposite makeAlphaComposite(float alpha) {
        return AlphaComposite.getInstance(3, alpha);
    }

    public void Add(Component input) {
        input.setVisible(true);
        this.add(input);
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 20;
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 100;
    }

    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    public Dimension getPreferredScrollableViewportSize() {
        return new Dimension(300, 300);
    }

    public void remeasure(Dimension size) {
        this.setSize(size);
    }

    @SuppressWarnings("UnusedReturnValue")
    public Point setPlacement(Component c) {
        return this.setPlacement(c, this.last.x, this.last.y);
    }

    public Point setPlacement(Component c, int x, int y) {
        switch (this.align) {
            case Right:
                c.setLocation(x - c.getWidth(), y + this.spacing);
                break;
            case Left:
                c.setLocation(x, y + this.spacing);
                break;
            case Center:
                if (c instanceof LComponent) {
                    LComponent lc = (LComponent)c;
                    c.setLocation(lc.getCenter(), y + this.spacing);
                } else {
                    c.setLocation(x - c.getWidth() / 2, y + this.spacing);
                }
        }

        this.updateLast(c);
        return this.last;
    }

    public void placeAdd(Component c) {
        this.setPlacement(c);
        this.Add(c);
    }

    public void align(Align align) {
        this.align = align;
    }

    public void updateLast(Component c) {
        this.last = new Point(this.last.x, c.getY() + c.getHeight());
    }

    public enum Align {
        Left,
        Center,
        Right;

        Align() {
        }
    }
}
