package lev.gui;

public class SaveEnum extends Setting<Enum> {
    Enum prototype;

    public SaveEnum(String title_, Enum data_, Boolean[] extraFlags) {
        super(title_, data_, extraFlags);
        this.prototype = data_;
    }

    public String toString() {
        return this.data.toString();
    }

    public void parse(String in) {
        Enum var10001 = this.prototype;
        this.data = Enum.valueOf(this.prototype.getClass(), in);
    }

    public Setting<Enum> copyOf() {
        SaveEnum out = new SaveEnum(this.title, this.data, this.extraFlags);
        out.tie = this.tie;
        return out;
    }
}
