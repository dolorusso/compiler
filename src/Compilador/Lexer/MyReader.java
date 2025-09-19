package Compilador.Lexer;

public interface MyReader {
    public static final int EOF = -1;
    public int read();
    public void unread(int c);
}
