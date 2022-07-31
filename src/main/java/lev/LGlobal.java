package lev;

import java.util.zip.Inflater;

public class LGlobal {
    static Inflater inflater;

    public LGlobal() {
    }

    public static Inflater getInflater() {
        if (inflater == null) {
            inflater = new Inflater();
        }

        return inflater;
    }
}
