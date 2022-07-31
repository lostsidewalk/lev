package lev;

import java.io.InputStream;
import java.io.OutputStream;

public class FastByteArrayOutputStream extends OutputStream {
    protected byte[] buf;
    protected int size;

    public FastByteArrayOutputStream() {
        this(5120);
    }

    public FastByteArrayOutputStream(int initSize) {
        this.buf = null;
        this.size = 0;
        this.size = 0;
        this.buf = new byte[initSize];
    }

    private void verifyBufferSize(int sz) {
        if (sz > this.buf.length) {
            byte[] old = this.buf;
            this.buf = new byte[Math.max(sz, 2 * this.buf.length)];
            System.arraycopy(old, 0, this.buf, 0, old.length);
            Object var3 = null;
        }

    }

    public int getSize() {
        return this.size;
    }

    public byte[] getByteArray() {
        return this.buf;
    }

    public final void write(byte[] b) {
        this.verifyBufferSize(this.size + b.length);
        System.arraycopy(b, 0, this.buf, this.size, b.length);
        this.size += b.length;
    }

    public final void write(byte[] b, int off, int len) {
        this.verifyBufferSize(this.size + len);
        System.arraycopy(b, off, this.buf, this.size, len);
        this.size += len;
    }

    public final void write(int b) {
        this.verifyBufferSize(this.size + 1);
        this.buf[this.size++] = (byte) b;
    }

    public void reset() {
        this.size = 0;
    }

    public InputStream getInputStream() {
        return new FastByteArrayInputStream(this.buf, this.size);
    }
}
