package lev.gui;

import lev.Ln;

class SaveBool extends Setting<Boolean> {
    public SaveBool(String title_, Boolean data_, Boolean[] extraFlags) {
        super(title_, data_, extraFlags);
    }

    public String toString() {
        return Ln.convertBoolTo1(this.data);
    }

    public void parse(String in) {
        this.data = Ln.toBool(in);
    }

    public Setting<Boolean> copyOf() {
        SaveBool out = new SaveBool(this.title, this.data, this.extraFlags);
        out.tie = this.tie;
        return out;
    }
}
