package lev.gui;

import lev.gui.resources.LImages;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

@SuppressWarnings("unused")
public class LProgressBarFrame extends JFrame implements LProgressBarInterface {
    protected final LProgressBar bar;
    protected LLabel title;
    protected Dimension correctLocation = new Dimension(0, 0);
    protected final Dimension GUIsize = new Dimension(250, 100);
    protected LImagePane backgroundPanel;
    protected JFrame guiRef;

    public LProgressBarFrame(Font header, Color headerC, Font footer, Color footerC) {
        this.bar = new LProgressBar(150, 15, footer, footerC);
        this.addComponents(header, headerC);
        this.moveToCorrectLocation();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    final void addComponents(Font header, Color headerC) {
        this.backgroundPanel = new LImagePane(LImages.multipurpose());
        this.backgroundPanel.setVisible(true);
        super.add(this.backgroundPanel);
        this.setSize(this.GUIsize);
        this.setResizable(false);
        this.setLayout(null);
        this.title = new LLabel("Please wait...", header, headerC);
        this.setSize(250, 72);
        this.bar.setLocation(this.getWidth() / 2 - this.bar.getWidth() / 2, this.getHeight() / 2 - this.bar.getHeight() / 2 + 10);
        this.title.setLocation(this.getWidth() / 2 - this.title.getWidth() / 2, 2);
        this.setSize(250, 100);
        this.backgroundPanel.add(this.bar);
        this.backgroundPanel.add(this.title);
        this.setResizable(false);
    }

    public void setExitOnClose() {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void setMax(int max, String reason) {
        this.bar.setMax(max, reason);
    }

    public void incrementBar() {
        this.bar.incrementBar();
    }

    public void setCorrectLocation(int x, int y) {
        this.correctLocation = new Dimension(x, y);
    }

    public final void moveToCorrectLocation() {
        if (this.guiRef != null) {
            Rectangle r = this.guiRef.getBounds();
            this.setLocation(r.x + this.correctLocation.width, r.y + this.correctLocation.height);
        } else {
            this.setLocation(this.correctLocation.width, this.correctLocation.height);
        }

    }

    public void setStatus(String input_) {
        this.bar.setStatus(input_);
    }

    public void open() {
        SwingUtilities.invokeLater(() -> {
            LProgressBarFrame.this.moveToCorrectLocation();
            LProgressBarFrame.this.setVisible(true);
        });
    }

    public void open(ChangeListener c) {
        this.open();
        this.setDoneListener(c);
    }

    void setDoneListener(ChangeListener c) {
        this.bar.setDoneListener(c);
    }

    public void close() {
        this.setVisible(false);
    }

    public void setGUIref(JFrame ref) {
        this.guiRef = ref;
    }

    public void reset() {
        this.bar.reset();
    }

    public int getBar() {
        return this.bar.getBar();
    }

    public void setBar(int in) {
        this.bar.setBar(in);
    }

    public int getMax() {
        return this.bar.getMax();
    }

    public void setMax(int in) {
        this.bar.setMax(in);
    }

    public void setStatusNumbered(int min, int max, String status) {
        this.bar.setStatusNumbered(min, max, status);
    }

    public void setStatusNumbered(String status) {
        this.bar.setStatusNumbered(status);
    }

    public void pause(boolean on) {
        this.bar.pause(on);
    }

    public boolean paused() {
        return this.bar.paused();
    }

    public void done() {
        this.bar.done();
    }

    public Component add(Component c) {
        this.backgroundPanel.add(c);
        return c;
    }
}
