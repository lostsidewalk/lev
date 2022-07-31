package lev.gui;

import lev.gui.resources.LFonts;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

@SuppressWarnings("unused")
public class LProgressBar extends LComponent implements LProgressBarInterface {
    protected final JProgressBar bar;
    protected boolean pause;
    protected LLabel status;
    protected boolean centered = true;
    LCheckBox done;

    public LProgressBar(int width, int height, Font footerF, Color footerC) {
        this.done = new LCheckBox("", LFonts.Typo3(1.0F), Color.BLACK);
        this.bar = new JProgressBar(0, 100);
        this.bar.setSize(width, height);
        this.bar.setLocation(this.bar.getWidth() / 2, 0);
        this.bar.setStringPainted(true);
        this.bar.setVisible(true);
        this.status = new LLabel(". . .", footerF, footerC);
        this.status.setLocation(this.bar.getX() + this.bar.getWidth() / 2 - this.status.getWidth() / 2, this.bar.getY() + this.bar.getHeight() + 10);
        this.setSize(this.bar.getWidth() * 2, this.status.getY() + this.status.getHeight());
        this.add(this.bar);
        this.add(this.status);
        this.setVisible(true);
    }

    public void setSize(int x, int y) {
        super.setSize(x, y);
        this.bar.setLocation(this.bar.getWidth() / 2, 0);
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
        this.status.setLocation(this.bar.getX(), this.status.getY());
    }

    public void setMax(int max, String reason) {
        this.setMax(max);
        this.setStatus(reason);
    }

    public void addShadow() {
        this.status.addShadow();
    }

    public void incrementBar() {
        if (!this.pause) {
            SwingUtilities.invokeLater(() -> LProgressBar.this.bar.setValue(LProgressBar.this.bar.getValue() + 1));
        }

    }

    public void setStatusOffset(int y) {
        this.status.setLocation(this.status.getX(), this.status.getY() + y);
    }

    public void setStatus(final String input_) {
        if (!this.pause) {
            SwingUtilities.invokeLater(() -> {
                int x = LProgressBar.this.status.getX() + LProgressBar.this.status.getWidth() / 2;
                LProgressBar.this.status.setText(input_);
                if (LProgressBar.this.centered) {
                    LProgressBar.this.status.setLocation(x - LProgressBar.this.status.getWidth() / 2, LProgressBar.this.status.getY());
                }

            });
        }

    }

    public void setDoneListener(ChangeListener c) {
        this.done = new LCheckBox("", this.done.getFont(), Color.BLACK);
        this.done.addChangeListener(c);
    }

    public void reset() {
        if (!this.pause) {
            SwingUtilities.invokeLater(() -> LProgressBar.this.bar.setValue(0));
        }

    }

    public int getBar() {
        return this.bar.getValue();
    }

    public void setBar(final int in) {
        if (!this.pause) {
            SwingUtilities.invokeLater(() -> LProgressBar.this.bar.setValue(in));
        }

    }

    public int getMax() {
        return this.bar.getMaximum();
    }

    public void setMax(final int in) {
        if (!this.pause) {
            SwingUtilities.invokeLater(() -> {
                LProgressBar.this.bar.setValue(0);
                LProgressBar.this.bar.setMaximum(in);
            });
        }

    }

    public void setStatusNumbered(int min, int max, String status) {
        if (!this.pause) {
            this.setStatus("(" + min + "/" + max + ") " + status);
        }

    }

    public void setStatusNumbered(final String status) {
        if (!this.pause) {
            SwingUtilities.invokeLater(() -> LProgressBar.this.setStatus("(" + (LProgressBar.this.getBar() + 1) + "/" + LProgressBar.this.getMax() + ") " + status));
        }

    }

    public void setStatusLabel(LLabel label) {
        this.status.setVisible(false);
        this.status = label;
    }

    public void pause(boolean on) {
        this.pause = on;
    }

    public boolean paused() {
        return this.pause;
    }

    public void done() {
        this.setBar(this.getMax());
        this.done.setSelected(true);
    }
}
