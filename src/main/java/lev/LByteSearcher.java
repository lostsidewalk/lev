package lev;

import java.io.BufferedInputStream;

public class LByteSearcher {
    byte[][] targets;
    int[] found;
    Boolean mark;

    public LByteSearcher(byte[]... targets_) {
        this.init(targets_);
    }

    public LByteSearcher(int target) {
        byte[] targ = new byte[]{(byte) target};
        this.init(targ);
    }

    final void init(byte[]... targets_) {
        this.targets = targets_;
        this.found = new int[this.targets.length];
    }

    public byte[] next(int in) {
        return this.next((char) in);
    }

    public byte[] next(char in) {
        for (int i = 0; i < this.found.length; ++i) {
            if (in == this.targets[i][this.found[i]]) {
                int var10002 = this.found[i]++;
                if (this.found[i] == this.targets[i].length) {
                    return this.targets[i];
                }
            } else if (in == this.targets[i][0]) {
                this.found[i] = 1;
            } else {
                this.found[i] = 0;
            }
        }

        return new byte[0];
    }

    public byte[] next(int in, BufferedInputStream input) {
        return this.next((char) in, input);
    }

    public byte[] next(char in, BufferedInputStream input) {
        this.mark = true;

        for (int i = 0; i < this.found.length; ++i) {
            if (in == this.targets[i][this.found[i]]) {
                int var10002 = this.found[i]++;
                if (this.found[i] == this.targets[i].length) {
                    return this.targets[i];
                }

                this.mark = false;
            } else if (in == this.targets[i][0]) {
                this.found[i] = 1;
            } else {
                this.found[i] = 0;
            }
        }

        if (this.mark) {
            input.mark(25);
        }

        return new byte[0];
    }
}
