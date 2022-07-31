package lev.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@SuppressWarnings("unused")
public class LImagePane extends LPanel {
    protected BufferedImage img;
    protected int IMG_WIDTH;
    protected int IMG_HEIGHT;
    protected boolean allowAlpha;
    protected float alpha;

    public LImagePane() {
        this.IMG_WIDTH = 0;
        this.IMG_HEIGHT = 0;
        this.allowAlpha = true;
        this.alpha = 1.0F;
        this.setLayout(null);
        this.setOpaque(false);
    }

    public LImagePane(File img) throws IOException {
        this();
        this.setImage(ImageIO.read(img));
    }

    public LImagePane(String img) throws IOException {
        this();
        this.setImage(ImageIO.read(new File(img)));
    }

    public LImagePane(BufferedImage img) {
        this();
        this.setImage(img);
    }

    public LImagePane(URL url) throws IOException {
        this();
        this.setImage(url);
    }

    public void setMaxSize(int x, int y) {
        this.IMG_WIDTH = x;
        this.IMG_HEIGHT = y;
        this.setImage(this.img);
    }

    Dimension calcSize(double x, double y) {
        return Lg.calcSize(x, y, this.IMG_WIDTH, this.IMG_HEIGHT);
    }

    public void paintComponent(Graphics g) {
        if (this.allowAlpha) {
            ((Graphics2D) g).setComposite(Lg.getAlphaComposite(this.alpha));
        }

        g.drawImage(this.img, 0, 0, null);
    }

    public void allowAlpha(Boolean on) {
        this.allowAlpha = on;
    }

    public void setAlpha(float value) {
        this.alpha = value;
    }

    public void removeAlpha() {
        RescaleOp op = new RescaleOp(new float[]{1.0F, 1.0F, 1.0F, 1.0F}, new float[]{0.0F, 0.0F, 0.0F, 256.0F}, null);
        this.img = op.filter(this.img, null);
        this.revalidate();
        this.repaint();
    }

    public BufferedImage getImage() {
        return this.img;
    }

    public final void setImage(BufferedImage originalImage) {
        if (originalImage == null) {
            this.img = new BufferedImage(1, 1, 1);
        } else if (this.IMG_WIDTH == 0 && this.IMG_HEIGHT == 0) {
            this.img = originalImage;
        } else {
            this.img = Lg.resizeImageWithHint(originalImage, this.calcSize(originalImage.getWidth(), originalImage.getHeight()));
        }

        Dimension size = new Dimension(this.img.getWidth(null), this.img.getHeight(null));
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.setMaximumSize(size);
        this.setSize(size);
        if (!this.allowAlpha) {
            this.removeAlpha();
        } else {
            this.revalidate();
            this.repaint();
        }

    }

    public final void setImage(File in) throws IOException {
        this.setImage(ImageIO.read(in));
    }

    public final void setImage(URL url) throws IOException {
        this.setImage(ImageIO.read(url));
    }

    public final void setImage(String path) throws IOException {
        this.setImage(ImageIO.read(new File(path)));
    }
}
