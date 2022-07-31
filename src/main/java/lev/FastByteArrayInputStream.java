package lev;

import java.io.InputStream;

public class FastByteArrayInputStream extends InputStream {
    protected byte[] buf = null;
    protected int count = 0;
    protected int pos = 0;

    public FastByteArrayInputStream(byte[] buf, int count) {
        this.buf = buf;
        this.count = count;
    }

    public final int available() {
        return this.count - this.pos;
    }

    public final int read() {
        return this.pos < this.count ? this.buf[this.pos++] & 255 : -1;
    }

    public final int read(byte[] b, int off, int len) {
        if (this.pos >= this.count) {
            return -1;
        } else {
            if (this.pos + len > this.count) {
                len = this.count - this.pos;
            }

            System.arraycopy(this.buf, this.pos, b, off, len);
            this.pos += len;
            return len;
        }
    }

    public final long skip(long n) {
        if ((long) this.pos + n > (long) this.count) {
            n = this.count - this.pos;
        }

        if (n < 0L) {
            return 0L;
        } else {
            this.pos = (int) ((long) this.pos + n);
            return n;
        }
    }
}
