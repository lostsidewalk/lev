package lev;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class LInChannel extends LImport {
    protected FileInputStream iStream;
    protected FileChannel iChannel;
    protected long end;

    public LInChannel() {
    }

    public LInChannel(String path) {
        this.openFile(path);
    }

    public LInChannel(File f) {
        this.openFile(f);
    }

    public LInChannel(LInChannel rhs, long allocation) {
        this.slice(rhs, allocation);
    }

    protected void slice(LInChannel rhs, long allocation) {
        this.iStream = rhs.iStream;
        this.iChannel = rhs.iChannel;
        this.end = this.pos() + allocation;
    }

    public void openFile(String path) {
        try {
            this.iStream = new FileInputStream(path);
            this.iChannel = this.iStream.getChannel();
            this.end = this.iChannel.size();
        } catch (IOException var3) {
            Logger.getLogger(LInChannel.class.getName()).log(Level.SEVERE, null, var3);
        }

    }

    public void openFile(File f) {
        this.openFile(f.getPath());
    }

    public int read() {
        try {
            return this.iStream.read();
        } catch (IOException var2) {
            Logger.getLogger(LInChannel.class.getName()).log(Level.SEVERE, null, var2);
            return -1;
        }
    }

    public ByteBuffer extractByteBuffer(int skip, int read) {
        super.skip(skip);
        ByteBuffer buf = ByteBuffer.allocate(read);

        try {
            this.iChannel.read(buf);
        } catch (IOException var5) {
            Logger.getLogger(LInChannel.class.getName()).log(Level.SEVERE, null, var5);
        }

        ((Buffer) buf).flip();
        return buf;
    }

    public void pos(long pos) {
        try {
            this.iChannel.position(pos);
        } catch (IOException var4) {
            Logger.getLogger(LInChannel.class.getName()).log(Level.SEVERE, null, var4);
        }

    }

    public long pos() {
        try {
            return this.iChannel.position();
        } catch (IOException var2) {
            Logger.getLogger(LInChannel.class.getName()).log(Level.SEVERE, null, var2);
            return -1L;
        }
    }

    public final void close() {
        if (this.iStream != null) {
            try {
                this.iStream.close();
                this.iChannel.close();
            } catch (IOException var2) {
                Logger.getLogger(LInChannel.class.getName()).log(Level.SEVERE, null, var2);
            }
        }

    }

    public final int available() {
        return (int) (this.end - this.pos());
    }

    public Boolean isDone() {
        return this.pos() == this.end;
    }

    public byte[] extract(int amount) {
        ByteBuffer allocate = ByteBuffer.allocate(amount);

        try {
            this.iChannel.read(allocate);
        } catch (IOException var4) {
            Logger.getLogger(LInChannel.class.getName()).log(Level.SEVERE, null, var4);
        }

        return allocate.array();
    }

    public byte[] extractUntil(int delimiter) {
        for (int counter = 1; !this.isDone(); ++counter) {
            if (this.read() == delimiter) {
                this.jumpBack(counter);
                byte[] out = this.extract(counter - 1);
                this.skip(1);
                return out;
            }
        }

        return new byte[0];
    }

    public long transferTo(long position, long count, WritableByteChannel target) throws IOException {
        return this.iChannel.transferTo(position, count, target);
    }
}
