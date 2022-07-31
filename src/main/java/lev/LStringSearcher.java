package lev;

import java.io.BufferedInputStream;

@SuppressWarnings("unused")
public class LStringSearcher {
    final String[] targets;
    final int[] found;
    Boolean mark;

    public LStringSearcher(String... targets_) {
        this.targets = targets_;
        this.found = new int[this.targets.length];
    }

    public String next(int in) {
        return this.next((char) in);
    }

    public String next(char in) {
        for (int i = 0; i < this.found.length; ++i) {
            if (in == this.targets[i].charAt(this.found[i])) {
                @SuppressWarnings("unused") int var10002 = this.found[i]++;
                if (this.found[i] == this.targets[i].length()) {
                    return this.targets[i];
                }
            } else if (in == this.targets[i].charAt(0)) {
                this.found[i] = 1;
            } else {
                this.found[i] = 0;
            }
        }

        return "";
    }

    public String next(int in, BufferedInputStream input) {
        return this.next((char) in, input);
    }

    public String next(char in, BufferedInputStream input) {
        this.mark = true;

        for (int i = 0; i < this.found.length; ++i) {
            if (in == this.targets[i].charAt(this.found[i])) {
                int var10002 = this.found[i]++;
                if (this.found[i] == this.targets[i].length()) {
                    return this.targets[i];
                }

                this.mark = false;
            } else if (in == this.targets[i].charAt(0)) {
                this.found[i] = 1;
            } else {
                this.found[i] = 0;
            }
        }

        if (this.mark) {
            input.mark(25);
        }

        return "";
    }
}
