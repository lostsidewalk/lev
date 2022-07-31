package lev.gui;

import javax.swing.*;
import java.awt.*;

public class LScrollPane extends JScrollPane {
    public LScrollPane(Component c) {
        super(c);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setViewportBorder(BorderFactory.createEmptyBorder());
        this.scrollToTop();
    }

    public void setOpaque(boolean arg0) {
        super.setOpaque(false);
        this.getViewport().setOpaque(false);
    }

    public final void scrollToTop() {
        SwingUtilities.invokeLater(() -> LScrollPane.this.getVerticalScrollBar().setValue(0));
    }

    public void setVisible(boolean on) {
        super.setVisible(on);
        if (on) {
            this.scrollToTop();
        }

    }
}
