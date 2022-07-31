package lev.gui;

class SaveFloat extends Setting<Float> {
    public SaveFloat(String title_, Float data_, Boolean[] extraFlags) {
        super(title_, data_, extraFlags);
    }

    public String toString() {
        return this.data.toString();
    }

    public void parse(String in) {
        this.data = Float.valueOf(in);
    }

    public Setting<Float> copyOf() {
        SaveFloat out = new SaveFloat(this.title, this.data, this.extraFlags);
        out.tie = this.tie;
        return out;
    }
}
