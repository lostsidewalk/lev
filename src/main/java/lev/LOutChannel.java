package lev;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Stack;

@SuppressWarnings("unused")
public class LOutChannel extends LExport {
    FileOutputStream out;
    FileChannel channel;
    Stack<LengthPair> lengthStackTracker = new Stack<>();
    ArrayList<LengthPair> posQueueTracker = new ArrayList<>();

    public LOutChannel(String path) throws FileNotFoundException {
        super(path);
    }

    public LOutChannel(File f) throws FileNotFoundException {
        super(f);
    }

    public void openOutput(String path) throws FileNotFoundException {
        this.out = new FileOutputStream(path);
        this.channel = this.out.getChannel();
    }

    public void write(byte[] array) throws IOException {
        this.out.write(array);
    }

    public void write(byte b) throws IOException {
        this.out.write(b);
    }

    public void close() throws IOException {
        this.out.close();
        this.channel.close();
    }

    public void markLength(int size) throws IOException {
        this.lengthStackTracker.push(new LengthPair(this.pos(), size));
        this.writeZeros(size);
    }

    public void closeLength() throws IOException {
        if (!this.lengthStackTracker.isEmpty()) {
            LengthPair last = this.lengthStackTracker.pop();
            long curPos = this.pos();
            this.pos(last.pos);
            this.write((int) (curPos - last.pos) - last.size, last.size);
            this.pos(curPos);
        }

    }

    public void setPosMarker(int size) throws IOException {
        this.posQueueTracker.add(new LengthPair(this.pos(), size));
        this.writeZeros(size);
    }

    public void fillPosMarker() throws IOException {
        if (!this.posQueueTracker.isEmpty()) {
            LengthPair last = this.posQueueTracker.remove(0);
            long curPos = this.pos();
            this.pos(last.pos);
            this.write((int) curPos, last.size);
            this.pos(curPos);
        }

    }

    public void pos(long set) throws IOException {
        this.channel.position(set);
    }

    public long pos() throws IOException {
        return this.channel.position();
    }

    static class LengthPair {
        long pos;
        int size;

        LengthPair(long posi, int sizei) {
            this.pos = posi;
            this.size = sizei;
        }
    }
}
