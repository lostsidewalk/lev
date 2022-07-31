package lev.gui;

class SaveString extends Setting<String> {
    public SaveString(String title_, String data_, Boolean[] extraFlags) {
        super(title_, data_, extraFlags);
    }

    public String toString() {
        return this.data;
    }

    public void parse(String in) {
        this.data = in;
    }

    public Boolean isEmpty() {
        return super.isEmpty() || this.data.equals("");
    }

    public Setting<String> copyOf() {
        SaveString out = new SaveString(this.title, this.data, this.extraFlags);
        out.tie = this.tie;
        return out;
    }
}
