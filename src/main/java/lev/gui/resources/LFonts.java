package lev.gui.resources;

import java.awt.*;
import java.io.IOException;

@SuppressWarnings("unused")
public class LFonts {
    public LFonts() {
    }

    public static Font Typo3(float size) {
        return getFont("Typo3-Medium.ttf", size);
    }

    public static Font Neuropol(float size) {
        return getFont("NEUROPOL.ttf", size);
    }

    public static Font OptimusPrinceps(float size) {
        return getFont("OptimusPrincepsSemiBold.ttf", size);
    }

    public static Font Oleo(float size) {
        return getFont("OLEO.ttf", size);
    }

    public static Font MyriadPro(float size) {
        return getFont("MyriadPro-Regular.ttf", size);
    }

    public static Font MyriadProBold(float size) {
        return getFont("myriadwebpro-bold.ttf", size);
    }

    public static Font ReasonSystem(float size) {
        return getFont("reasonSystem-Regular.ttf", size);
    }

    static Font getFont(String path, float size) {
        try {
            Font font = Font.createFont(0, LFonts.class.getClassLoader().getResourceAsStream(path));
            return font.deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException var3) {
            return new Font("Serif", Font.PLAIN, 3);
        }
    }
}
