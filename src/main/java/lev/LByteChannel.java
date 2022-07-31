package lev;

@SuppressWarnings("unused")
public class LByteChannel extends LImport {
    byte[] input;
    int pos;
    int end;

    public LByteChannel() {
    }

    public LByteChannel(byte[] input) {
        this.openStream(input);
    }

    public final void openStream(byte[] input) {
        this.input = input;
        this.pos = 0;
        this.end = input.length;
    }

    public final void openStream(LShrinkArray in) {
        this.openStream(in.extractAllBytes());
    }

    public int read() {
        return Ln.bToUInt(this.input[this.pos++]);
    }

    public void pos(long pos) {
        this.pos = (int) pos;
    }

    public long pos() {
        return this.pos;
    }

    public void close() {
        this.input = null;
    }

    public int available() {
        return this.end - this.pos;
    }

    public Boolean isDone() {
        return this.pos == this.end;
    }

    public void skip(int skip) {
        this.pos += skip;
    }

    public void jumpBack(int amount) {
        this.skip(-amount);
    }

    public byte[] extract(int read) {
        byte[] out = new byte[read];

        System.arraycopy(this.input, this.pos, out, 0, read);

        this.skip(read);
        return out;
    }
}
