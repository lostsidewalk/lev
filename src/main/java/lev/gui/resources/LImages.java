package lev.gui.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@SuppressWarnings("unused")
public class LImages {
    public LImages() {
    }

    public static BufferedImage multipurpose() {
        return create(LImages.class.getClassLoader().getResource("multipurpose.png"));
    }

    public static BufferedImage arrow(boolean leftArrow, boolean dark) {
        if (leftArrow) {
            return dark ? create(LImages.class.getClassLoader().getResource("ArrowLeftDark.png")) : create(LImages.class.getClassLoader().getResource("ArrowLeft.png"));
        } else {
            return dark ? create(LImages.class.getClassLoader().getResource("ArrowRightDark.png")) : create(LImages.class.getClassLoader().getResource("ArrowRight.png"));
        }
    }

    static BufferedImage create(URL image) {
        try {
            return ImageIO.read(image);
        } catch (IOException var2) {
            return null;
        }
    }
}
