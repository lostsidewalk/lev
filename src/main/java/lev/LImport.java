package lev;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@SuppressWarnings("unused")
public abstract class LImport {
    public LImport() {
    }

    public int read() {
        return this.extractInt(1);
    }

    public final long extractLong(int skip, int read) {
        return Ln.arrayToLong(this.extract(skip, read));
    }

    public final String extractString(int skip, int read) {
        this.skip(skip);
        return this.extractString(read);
    }

    public String extractString(int amount) {
        return Ln.arrayToString(this.extract(amount));
    }

    public final int extractInt(int skip, int read) {
        return Ln.arrayToInt(this.extract(skip, read));
    }

    public final int extractInt(int read) {
        return this.extractInt(0, read);
    }

    public final int[] extractInts(int skip, int read) {
        return Ln.toIntArray(this.extract(skip, read));
    }

    public int[] extractInts(int amount) {
        return Ln.toIntArray(this.extract(amount));
    }

    public final int[] getInts(int skip, int read) {
        int[] out = this.extractInts(skip, read);
        this.jumpBack(skip + read);
        return out;
    }

    public float extractFloat() {
        return Float.intBitsToFloat(this.extractInt(0, 4));
    }

    public boolean extractBool(int amount) {
        return this.extract(amount)[0] != 0;
    }

    public byte[] extractAllBytes() {
        return this.extract(this.available());
    }

    public String extractString() {
        return Ln.arrayToString(this.extractUntil(0));
    }

    public String extractAllString() {
        return Ln.arrayToString(this.extract(0, this.available()));
    }

    public String extractLine() {
        byte[] read1 = new byte[]{10};
        byte[] read2 = new byte[]{13, 10};
        return Ln.arrayToString(this.extractUntil(read2, read1));
    }

    public byte[] extractUntil(char delimiter, int bufsize) {
        return this.extractUntil((int) delimiter, bufsize);
    }

    public byte[] extractUntil(char delimiter) {
        return this.extractUntil((int) delimiter);
    }

    public byte[] extractUntil(int delimiter) {
        byte[] delimiterB = new byte[]{(byte) delimiter};
        return this.extractUntil(delimiterB);
    }

    public byte[] extract(int skip, int read) {
        this.skip(skip);
        return this.extract(read);
    }

    public abstract byte[] extract(int var1);

    public String getString(int amount) {
        String out = this.extractString(amount);
        this.jumpBack(amount);
        return out;
    }

    public String getString(int skip, int amount) {
        return Ln.arrayToString(this.getInts(skip, amount));
    }

    public byte[] extractUntil(int delimiter, int bufsize) {
        byte[] buffer = new byte[bufsize];

        int counter;
        int in;
        //noinspection StatementWithEmptyBody
        for (counter = 0; this.available() > 0 && (in = this.read()) != delimiter; buffer[counter++] = (byte) in) {
        }

        byte[] out = new byte[counter];
        System.arraycopy(buffer, 0, out, 0, counter);
        return out;
    }

    public byte[] extractUntil(byte[]... delimiters) {
        ArrayList<Byte> buffer = new ArrayList<>(50);
        LByteSearcher search = new LByteSearcher(delimiters);
        byte[] stop = new byte[0];

        while (this.available() > 0 && stop.length == 0) {
            int in = this.read();
            stop = search.next(in);
            if (stop.length == 0) {
                buffer.add((byte) in);
            }
        }

        byte[] out = new byte[buffer.size() - (stop.length - 1)];

        for (int i = 0; i < buffer.size() - (stop.length - 1) && i < buffer.size(); ++i) {
            out[i] = buffer.get(i);
        }

        return out;
    }

    public byte[] getBytes(int skip, int amount) {
        byte[] out = this.extract(skip, amount);
        this.jumpBack(skip + amount);
        return out;
    }

    public byte[] getAllBytes() {
        return this.getBytes(0, this.available());
    }

    public void skip(int offset) {
        if (offset != 0) {
            this.pos(this.pos() + (long) offset);
        }

    }

    public void jumpBack(int amount) {
        this.skip(-amount);
    }

    public abstract void pos(long var1);

    public abstract long pos();

    public final String scanTo(String... targets) {
        LStringSearcher search = new LStringSearcher(targets);

        for (int inputInt = this.read(); inputInt != -1; inputInt = this.read()) {
            String result;
            if (!(result = search.next(inputInt)).equals("")) {
                return result;
            }
        }

        return "";
    }

    public abstract void close();

    public abstract int available();

    public Boolean isDone() {
        return this.available() == 0;
    }

    public LShrinkArray correctForCompression() throws DataFormatException {
        int uncompressedSize = Ln.arrayToInt(this.extractInts(4));
        byte[] compressedByteData = this.getAllBytes();
        Inflater decompresser = LGlobal.getInflater();
        decompresser.setInput(compressedByteData, 0, this.available());
        byte[] uncompressedByteData = new byte[uncompressedSize];
        decompresser.inflate(uncompressedByteData);
        decompresser.reset();
        return new LShrinkArray(ByteBuffer.wrap(uncompressedByteData));
    }
}
