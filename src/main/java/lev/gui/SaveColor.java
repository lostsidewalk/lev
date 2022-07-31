package lev.gui;

import java.awt.*;

class SaveColor extends Setting<Color> {
    public SaveColor(String title_, Color data_, Boolean[] extraFlags) {
        super(title_, extraFlags);
        Integer[] color = new Integer[]{data_.getRed(), data_.getBlue(), data_.getGreen(), data_.getAlpha()};
        this.setTo(data_);
    }

    private SaveColor(String title_, Integer[] color, Boolean[] extraFlags) {
        super(title_, extraFlags);
        Color c = new Color(color[0], color[1], color[2], color[3]);
        this.setTo(c);
    }

    public String toString() {
        String out = "";
        out = out + this.data.getRed() + "-";
        out = out + this.data.getGreen() + "-";
        out = out + this.data.getBlue() + "-";
        out = out + this.data.getAlpha();
        return out;
    }

    public void parse(String in) {
        String[] split = in.split("-");
        Integer[] ints = new Integer[4];

        for (int i = 0; i < 4; ++i) {
            ints[i] = Integer.parseInt(split[i]);
        }

        this.data = new Color(ints[0], ints[1], ints[2], ints[3]);
    }

    public Setting<Color> copyOf() {
        SaveColor out = new SaveColor(this.title, this.data, this.extraFlags);
        out.tie = this.tie;
        return out;
    }
}
