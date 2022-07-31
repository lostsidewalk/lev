package lev.gui;

import java.util.ArrayList;
import java.util.Iterator;

public class SaveStringList extends Setting<ArrayList<String>> {
    static String delimiter = "<#>";

    public SaveStringList(String title_, ArrayList<String> data_, Boolean[] extraFlags) {
        super(title_, data_, extraFlags);
    }

    public String toString() {
        String out = "";
        int num = 0;
        Iterator i$ = ((ArrayList) this.data).iterator();

        while (i$.hasNext()) {
            String f = (String) i$.next();
            if (!f.equals("")) {
                out = out + f + delimiter + "\n";
                ++num;
            }
        }

        return num + "\n" + out;
    }

    public void parse(String in) {
        String[] split = in.split(delimiter);
        this.data = new ArrayList(split.length);

        for (int i = 0; i < split.length; ++i) {
            split[i] = split[i].trim();
            if (!"".equals(split[i])) {
                this.data.add(split[i]);
            }
        }

    }

    public Setting<ArrayList<String>> copyOf() {
        SaveStringList out = new SaveStringList(this.title, this.data, this.extraFlags);
        out.data = new ArrayList(this.data);
        out.tie = this.tie;
        return out;
    }
}
