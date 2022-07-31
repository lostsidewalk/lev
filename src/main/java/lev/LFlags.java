package lev;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("unused")
public class LFlags implements Serializable {
    final byte[] flags;

    public LFlags(int size) {
        this.flags = new byte[size];
    }

    public LFlags(byte[] inFlags) {
        this.flags = new byte[inFlags.length];
        this.set(inFlags);
    }

    public LFlags(LFlags rhs) {
        this.flags = new byte[rhs.flags.length];
        System.arraycopy(rhs.flags, 0, this.flags, 0, rhs.flags.length);
    }

    public final void set(byte[] inFlags) {
        System.arraycopy(inFlags, 0, this.flags, 0, this.flags.length);
    }

    public final boolean get(int bit) {
        byte byt = this.flags[bit / 8];
        return (byt >>> bit % 8 & 1) != 0;
    }

    public final void set(int bit, boolean on) {
        int index = bit / 8;
        this.flags[index] = (byte) (on ? this.flags[index] | 1 << bit % 8 : this.flags[index] & ~(1 << bit % 8));
    }

    public final int getFirstTrue() {
        for (int i = 0; i < this.length() * 8; ++i) {
            if (this.get(i)) {
                return i;
            }
        }

        return -1;
    }

    public final byte[] export() {
        return this.flags;
    }

    public final int length() {
        return this.flags.length;
    }

    public final void clear() {
        Arrays.fill(this.flags, (byte) 0);

    }

    public boolean isZeros() {
        byte[] arr$ = this.flags;
        int len$ = arr$.length;

        for (byte b : arr$) {
            if (b != 0) {
                return false;
            }
        }

        return true;
    }

    public final String toString() {
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < this.length() * 8; ++i) {
            if (this.get(i)) {
                out.append("1");
            } else {
                out.append("0");
            }
        }

        return out.toString();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            LFlags other = (LFlags) obj;
            return Arrays.equals(this.flags, other.flags);
        }
    }

    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Arrays.hashCode(this.flags);
        return hash;
    }
}
