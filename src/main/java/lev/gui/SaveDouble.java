package lev.gui;

public class SaveDouble extends Setting<Double> {
    public SaveDouble(String title_, Double data_, Boolean[] extraFlags) {
        super(title_, data_, extraFlags);
    }

    public String toString() {
        return this.data.toString();
    }

    public void parse(String in) {
        this.data = Double.valueOf(in);
    }

    public Setting<Double> copyOf() {
        SaveDouble out = new SaveDouble(this.title, this.data, this.extraFlags);
        out.tie = this.tie;
        return out;
    }
}
