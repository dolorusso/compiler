package Compilador.Lexer;

import java.io.*;

public class PushbackMyReader implements MyReader {
    private final PushbackReader reader;

    public PushbackMyReader(String filename) throws IOException {
        this.reader = new PushbackReader(new FileReader(filename), 2);
    }

    @Override
    public int read() {
        try {
            return reader.read();
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public void unread(int c) {
        try {
            if (c != -1) reader.unread(c);
        } catch (IOException e) {
            throw new RuntimeException("Error en unread()", e);
        }
    }
}
