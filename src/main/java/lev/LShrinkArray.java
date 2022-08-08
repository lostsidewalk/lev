package lev;

import java.io.File;
import java.nio.Buffer;
import java.nio.ByteBuffer;

@SuppressWarnings("unused")
public class LShrinkArray extends LImport {
    private final ByteBuffer buffer;

    public LShrinkArray(byte[] in) {
        this.buffer = ByteBuffer.wrap(in);
    }

    public LShrinkArray(ByteBuffer array_) {
        this.buffer = array_.duplicate();
    }

    public LShrinkArray(LImport rhs, int high) {
        if (rhs.getClass() == this.getClass()) {
            LShrinkArray rhss = (LShrinkArray) rhs;
            this.buffer = rhss.buffer.slice();
        } else {
            this.buffer = ByteBuffer.wrap(rhs.getBytes(0, high));
        }

        ((Buffer) this.buffer).limit(high);
    }

    public LShrinkArray(LShrinkArray rhs) {
        this(rhs, rhs.available());
    }

    public LShrinkArray(File f) {
        LInChannel in = new LInChannel(f);
        this.buffer = ByteBuffer.wrap(in.extract(0, in.available()));
        in.close();
    }

    public Boolean isDone() {
        return !this.buffer.hasRemaining();
    }

    public final int available() {
        return this.buffer.remaining();
    }

    public final void skip(int skip) {
        ((Buffer) this.buffer).position(((Buffer) this.buffer).position() + skip);
    }

    public void jumpBack(int amount) {
        this.skip(-amount);
    }

    public byte[] extractUntil(int delimiter) {
        int counter = 1;
        byte[] array = this.buffer.array();

        for (int i = this.buffer.arrayOffset() + ((Buffer) this.buffer).position(); i < ((Buffer) this.buffer).limit() + this.buffer.arrayOffset(); ++i) {
            if (array[i] == delimiter) {
                byte[] out = this.extract(counter - 1);
                this.skip(1);
                return out;
            }

            ++counter;
        }

        return new byte[0];
    }

    public byte[] extractUntil(String delimiter) {
        return this.extractUntil(new byte[][]{Ln.toByteArray(delimiter)});
    }

    public byte[] extract(int amount) {
        byte[] bytes = new byte[amount];
        this.buffer.get(bytes);
        return bytes;
    }

    public void pos(long pos) {
        ((Buffer) this.buffer).position((int) pos);
    }

    public long pos() {
        return ((Buffer) this.buffer).position();
    }

    public void close() {
        this.buffer.clear();
    }

    public String toString() {
        long pos = this.pos();
        ((Buffer) this.buffer).position(0);
        int[] bytes = Ln.toIntArray(this.getAllBytes());
        ((Buffer) this.buffer).position((int) pos);
        int count = 0;
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < bytes.length && i < 1000; ++i) {
            out.append(Ln.printHex(bytes[i]));
            if ((long) (i + 1) == pos) {
                out.append(">");
            } else if ((long) i == pos) {
                out.append("<");
            } else {
                out.append(" ");
            }

            ++count;
            if (count == 16) {
                count = 0;
                out.append("\n");
            }
        }

        return out.toString();
    }
}
