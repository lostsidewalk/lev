package lev.gui;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent.EventType;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Lg {
    public Lg() {
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, Dimension size) {
        int type = originalImage.getType() == 0 ? 2 : originalImage.getType();
        BufferedImage resizedImage = new BufferedImage(size.width, size.height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, size.width, size.height, null);
        g.dispose();
        return resizedImage;
    }

    public static BufferedImage resizeImageWithHint(BufferedImage originalImage, Dimension size) {
        if (originalImage.getWidth() == size.width && originalImage.getHeight() == size.height) {
            return originalImage;
        } else {
            int type = originalImage.getType() == 0 ? 2 : originalImage.getType();
            BufferedImage resizedImage = new BufferedImage(size.width, size.height, type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, size.width, size.height, null);
            g.dispose();
            g.setComposite(AlphaComposite.Src);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            return resizedImage;
        }
    }

    public static Dimension calcSize(double x, double y, int maxX, int maxY) {
        if (maxX == 0) {
            maxX = Integer.MAX_VALUE;
        }

        if (maxY == 0) {
            maxY = Integer.MAX_VALUE;
        }

        double xMod = (double) maxX / x;
        double yMod = (double) maxY / y;
        double mod = Math.min(xMod, yMod);
        if (mod < 1.0) {
            x *= mod;
            y *= mod;
        }

        return new Dimension((int) x, (int) y);
    }

    public static int taller(Component a, Component b) {
        return Math.max(a.getHeight(), b.getHeight());
    }

    public static Composite getAlphaComposite(float trans) {
        return AlphaComposite.getInstance(3, trans);
    }

    public static int getSpacing(boolean horiz, int allocated, Component... cs) {
        int len$ = cs.length;

        for (Component c : cs) {
            if (horiz) {
                allocated -= c.getWidth();
            } else {
                allocated -= c.getHeight();
            }
        }

        return allocated / (cs.length + 1);
    }

    public static JEditorPane getQuickHTMLPane(String str) {
        JEditorPane ep = new JEditorPane("text/html", str);
        ep.addHyperlinkListener(e -> {
            if (e.getEventType().equals(EventType.ACTIVATED)) {
                try {
                    Desktop.getDesktop().browse(URI.create(e.getURL().toString()));
                } catch (IOException var3) {
                    Logger.getLogger(Lg.class.getName()).log(Level.SEVERE, null, var3);
                }
            }

        });
        ep.setEditable(false);
        return ep;
    }
}
