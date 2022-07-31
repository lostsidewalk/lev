package lev.gui;

import java.util.ArrayList;

public class SaveStringList extends Setting<ArrayList<String>> {
    static final String delimiter = "<#>";

    public SaveStringList(String title_, ArrayList<String> data_, Boolean[] extraFlags) {
        super(title_, data_, extraFlags);
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        int num = 0;

        for (String f : this.data) {
            if (!f.equals("")) {
                out.append(f).append(delimiter).append("\n");
                ++num;
            }
        }

        return num + "\n" + out;
    }

    public void parse(String in) {
        String[] split = in.split(delimiter);
        this.data = new ArrayList<>(split.length);

        for (int i = 0; i < split.length; ++i) {
            split[i] = split[i].trim();
            if (!"".equals(split[i])) {
                this.data.add(split[i]);
            }
        }

    }

    public Setting<ArrayList<String>> copyOf() {
        SaveStringList out = new SaveStringList(this.title, this.data, this.extraFlags);
        out.data = new ArrayList<>(this.data);
        out.tie = this.tie;
        return out;
    }
}
