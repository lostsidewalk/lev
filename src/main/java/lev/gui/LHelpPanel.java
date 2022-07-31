package lev.gui;

import java.awt.*;

@SuppressWarnings("unused")
public class LHelpPanel extends LPanel {
    protected final LLabel setting;
    protected final LTextPane help;
    protected final Image arrow;
    protected final int arrowX;
    protected int y = -100;
    protected int textOffset = 0;
    protected final LPanel bottomArea;
    protected boolean textVisible = true;
    protected boolean hideArrow = false;
    protected final boolean following = true;

    public LHelpPanel(Rectangle bounds, Font titleFont, Color titleC, Color contentC, Image arrow, int arrowX) {
        this.arrowX = arrowX;
        this.arrow = arrow;
        this.setBounds(bounds);
        this.setting = new LLabel("  ", titleFont, titleC);
        this.setting.setLocation(17, 0);
        this.help = new LTextPane(new Dimension(this.getWidth() - 35, this.getHeight()), contentC);
        this.help.addScroll();
        this.help.setEditable(false);
        this.help.setLocation(35, 0);
        this.add(this.setting);
        this.add(this.help);
        this.setting.setVisible(this.textVisible);
        this.help.setVisible(this.textVisible);
        this.bottomArea = new LPanel();
        this.bottomArea.setSize((int) bounds.getWidth(), 190);
        this.bottomArea.setLocation(0, (int) bounds.getHeight() - this.bottomArea.getHeight());
        this.bottomArea.setVisible(false);
        this.add(this.bottomArea);
        this.setVisible(true);
    }

    public void revalidate() {
        super.revalidate();
        if (this.bottomArea != null) {
            this.bottomArea.revalidate();
        }

    }

    public void repaint() {
        super.repaint();
        if (this.bottomArea != null) {
            this.bottomArea.repaint();
        }

    }

    public void paintComponent(Graphics g) {
        if (this.textVisible && !this.hideArrow && this.following && this.arrow != null) {
            g.drawImage(this.arrow, this.arrowX, this.y - this.arrow.getHeight(null) / 2, null);
        }

    }

    public void setHeaderColor(Color c) {
        this.setting.setForeground(c);
    }

    public void setHeaderFont(Font f) {
        this.setting.setFont(f);
    }

    public void setDefaultY(int y) {
        this.spacing = y;
    }

    public void setTitle(String title_) {
        this.setting.setText("   " + title_);
        this.setting.setSize(this.setting.getPreferredSize());
    }

    public void setTitleOffset(int y) {
        this.textOffset = y;
    }

    public void setXOffsets(int title, int helpText) {
        this.setting.setLocation(title, this.setting.getY());
        this.help.setLocation(helpText, this.help.getY());
    }

    public void setY(int y_) {
        if (y_ != -1 && this.following) {
            this.y = y_;
        } else {
            this.y = this.spacing;
        }

        this.setting.setLocation(this.setting.getX(), this.y - this.setting.getHeight() / 2 + this.textOffset);
        this.help.setLocation(this.help.getX(), this.y + this.setting.getHeight() / 2 + this.textOffset);
        this.evalPositioning();
        this.repaint();
    }

    public void focusOn(Component c, int offset) {
        this.setY(c.getY() + c.getHeight() / 2 + offset);
    }

    public void setDefaultPos() {
        this.setY(-1);
    }

    public void setContent(String text) {
        this.hideArrow = false;
        this.help.setText(text);
        this.evalPositioning();
    }

    private void evalPositioning() {
        int min = this.getLimit() - this.spacing - this.setting.getHeight() / 2;
        if (min > this.help.getPreferredSize().height) {
            min = this.help.getPreferredSize().height;
        }

        this.help.setSize(this.help.getWidth(), min);
        int helpReach = this.setting.getY() + this.setting.getHeight() + this.help.getHeight();
        if (helpReach > this.getLimit()) {
            int move = helpReach - this.getLimit();
            this.help.setLocation(this.help.getX(), this.help.getY() - move);
            this.setting.setLocation(this.setting.getX(), this.setting.getY() - move);
        }

    }

    private int getLimit() {
        return this.bottomArea != null && this.bottomArea.isVisible() ? this.getHeight() - this.bottomArea.getHeight() : this.getHeight() - 25;
    }

    public void addContent(String text) {
        this.help.append(text);
        this.evalPositioning();
    }

    public void clearBottomArea() {
        this.bottomArea.removeAll();
        this.bottomArea.setVisible(false);
    }

    public void addToBottomArea(Component c) {
        this.bottomArea.Add(c);
        this.bottomArea.setVisible(true);
    }

    public void setBottomAreaHeight(int y) {
        int limit = this.bottomArea.getY() + this.bottomArea.getHeight();
        this.bottomArea.setSize(this.bottomArea.getWidth(), y);
        this.bottomArea.setLocation(0, limit - this.bottomArea.getHeight());
    }

    public void setBottomAreaVisible(boolean on) {
        this.bottomArea.setVisible(on);
    }

    public void textVisible(Boolean b) {
        this.textVisible = b;
        this.setting.setVisible(b);
        this.help.setVisible(b);
        this.repaint();
    }

    public void hideArrow() {
        this.hideArrow = true;
    }

    public Dimension getBottomSize() {
        return this.bottomArea.getSize();
    }

    public void reset() {
        this.clearBottomArea();
        this.setTitle("");
        this.setContent("");
        this.hideArrow();
        this.repaint();
    }
}
