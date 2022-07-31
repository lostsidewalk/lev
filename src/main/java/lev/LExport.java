package lev;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

@SuppressWarnings("unused")
public abstract class LExport {
    public LExport(File file) throws FileNotFoundException {
        this(file.getPath());
    }

    public LExport(String path) throws FileNotFoundException {
        Ln.makeDirs(path);
        this.openOutput(path);
    }

    public abstract void openOutput(String var1) throws FileNotFoundException;

    public abstract void write(byte[] var1) throws IOException;

    public void write(byte[] array, int size) throws IOException {
        this.write(array);
        if (size - array.length > 0) {
            this.writeZeros(size - array.length);
        }

    }

    public void writeZeros(int size) throws IOException {
        this.write(new byte[size]);
    }

    public void write(int input, int size) throws IOException {
        this.write(Ln.toByteArray(input, size, size));
    }

    public void write(int input) throws IOException {
        this.write(Ln.toByteArray(input));
    }

    public abstract void write(byte var1) throws IOException;

    public void write(String input) throws IOException {
        this.write(input, 0);
    }

    public void write(String input, int size) throws IOException {
        this.write(Ln.toByteArray(input), size);
    }

    public void write(boolean input, int size) throws IOException {
        if (input) {
            this.write(1, size);
        } else {
            this.write(0, size);
        }

    }

    public void write(float input) throws IOException {
        ByteBuffer out = ByteBuffer.allocate(4);
        out.putInt(Integer.reverseBytes(Float.floatToIntBits(input)));
        this.write(out.array(), 4);
    }

    public abstract void close() throws IOException;
}
