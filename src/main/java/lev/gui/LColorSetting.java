package lev.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("unused")
public class LColorSetting extends LUserSetting<Color> {
    protected final Rectangle box = new Rectangle(0, 0, 12, 12);
    protected final Rectangle boxOutline;
    protected Color color;
    protected final ArrayList<Runnable> actionListeners = new ArrayList<>(0);

    public LColorSetting(String text, Font font, Color fontColor, Color pickerStart) {
        super(text, font, fontColor);
        this.color = pickerStart;
        int spacing = 5;
        int outline = 2;
        this.boxOutline = new Rectangle(0, 0, this.box.width + 2 * outline, this.box.height + 2 * outline);
        this.boxOutline.x = this.titleLabel.getWidth() + spacing;
        this.box.x = this.boxOutline.x + outline;
        this.boxOutline.y = this.titleLabel.getHeight() / 2 - this.boxOutline.height / 2;
        this.box.y = this.boxOutline.y + outline;
        int y = this.titleLabel.getHeight();
        if (y < this.boxOutline.height) {
            y = this.boxOutline.height;
        }

        this.setSize(this.boxOutline.x + this.boxOutline.width, y);
        this.setOutline(true);
    }

    protected void addUpdateHandlers() {
        this.addMouseListener(new ColorPickHandler());
    }

    public boolean revertTo(Map<Enum<?>, Setting<?>> m) {
        if (this.isTied()) {
            Color cur = this.color;
            this.color = m.get(this.saveTie).getColor();
            this.repaint();
            return cur == this.color;
        }

        return true;
    }

    public Color getValue() {
        return this.color;
    }

    public void highlightChanged() {
    }

    public void clearHighlight() {
    }

    final void setOutline(boolean small) {
        byte size;
        if (small) {
            size = 1;
        } else {
            size = 2;
        }

        this.boxOutline.x = this.box.x - size;
        this.boxOutline.y = this.box.y - size;
        this.boxOutline.width = this.box.width + size * 2;
        //noinspection SuspiciousNameCombination
        this.boxOutline.height = this.boxOutline.width;
    }

    public int getCenter() {
        return this.titleLabel.getRight() + 3;
    }

    protected void addHelpHandler(boolean hoverListener) {
        this.addMouseListener(new LHelpComponent.HelpMouseHandler());
    }

    void pickNewColor() {
        Color newColor = JColorChooser.showDialog(this, "Choose New Color", this.getBackground());
        if (newColor != null) {
            this.color = newColor;
            this.update();
        }

    }

    public void addActionListener(Runnable r) {
        this.actionListeners.add(r);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(Color.BLACK);
        g2.fill(this.boxOutline);
        ++this.boxOutline.x;
        ++this.boxOutline.y;
        g2.fill(this.boxOutline);
        --this.boxOutline.x;
        --this.boxOutline.y;
        g2.setPaint(this.color);
        g2.fill(this.box);
        super.paint(g2);
    }

    class ColorPickHandler implements MouseListener {
        ColorPickHandler() {
        }

        public void mouseClicked(MouseEvent e) {
            LColorSetting.this.pickNewColor();

            for (Runnable r : LColorSetting.this.actionListeners) {
                r.run();
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
            LColorSetting.this.setOutline(false);
            LColorSetting.this.repaint();
        }

        public void mouseExited(MouseEvent e) {
            LColorSetting.this.setOutline(true);
            LColorSetting.this.repaint();
        }
    }
}
