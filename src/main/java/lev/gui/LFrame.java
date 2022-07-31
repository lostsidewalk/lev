package lev.gui;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("unused")
public class LFrame extends JFrame {
    private static final int marginX = 16;
    private static final int marginY = 38;
    protected final LImagePane background;
    private boolean init = false;

    public LFrame(String title) {
        super(title);
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.background = new LImagePane();
        this.getContentPane().add(this.background);
    }

    protected void init() {
    }

    public void open() {
        if (!this.init) {
            this.init();
            this.init = true;
        }

        this.setVisible(true);
    }

    public void setBackground(Color c) {
        this.getContentPane().setBackground(c);
    }

    public Dimension getRealSize() {
        return new Dimension(this.getRealWidth(), this.getRealHeight());
    }

    public void setRealSize(Dimension size) {
        this.setRealSize(size.width, size.height);
    }

    public void setRealSize(int x, int y) {
        this.setSize(x + marginX, y + marginY);
    }

    public int getRealWidth() {
        return this.getWidth() - marginX;
    }

    public int getRealHeight() {
        return this.getHeight() - marginY;
    }

    public Point defaultLocation() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int y = 5;
        int x = screen.width / 2 - this.getWidth() / 2;
        if (x < 0) {
            x = 0;
        }

        return new Point(x, y);
    }

    public Point centerScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int y = screen.height / 2 - this.getHeight() / 2;
        int x = screen.width / 2 - this.getWidth() / 2;
        if (x < 0) {
            x = 0;
        }

        if (y < 0) {
            y = 0;
        }

        return new Point(x, y);
    }

    public void remeasure() {
        this.background.setMaxSize(this.getRealWidth(), 0);
    }
}
