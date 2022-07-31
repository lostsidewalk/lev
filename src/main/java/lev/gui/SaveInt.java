package lev.gui;

class SaveInt extends Setting<Integer> {
    public SaveInt(String title_, Integer data_, Boolean[] extraFlags) {
        super(title_, data_, extraFlags);
    }

    public String toString() {
        return this.data.toString();
    }

    public void parse(String in) {
        this.data = Integer.valueOf(in);
    }

    public Setting<Integer> copyOf() {
        SaveInt out = new SaveInt(this.title, this.data, this.extraFlags);
        out.tie = this.tie;
        return out;
    }
}
