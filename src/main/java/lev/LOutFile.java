package lev;

import java.io.*;

@SuppressWarnings("unused")
public class LOutFile extends LExport {
    private BufferedOutputStream output;

    public LOutFile(File file) throws FileNotFoundException {
        super(file);
    }

    public LOutFile(String path) throws FileNotFoundException {
        super(path);
    }

    public void write(byte b) throws IOException {
        this.output.write(b);
    }

    public void write(byte[] array) throws IOException {
        this.output.write(array);
    }

    public void openOutput(String path) throws FileNotFoundException {
        Ln.makeDirs(path);
        this.output = new BufferedOutputStream(new FileOutputStream(path));
    }

    public void close() throws IOException {
        this.output.close();
    }
}
