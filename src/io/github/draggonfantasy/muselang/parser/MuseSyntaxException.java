package io.github.draggonfantasy.muselang.parser;

public class MuseSyntaxException extends ParserException
{
    private final int line;
    private final int column;

    public MuseSyntaxException(String msg, int line, int column)
    {
        super(msg + " at " + line + ", " + column);
        this.line = line;
        this.column = column;
    }

    public int getLine()
    {
        return line;
    }

    public int getColumn()
    {
        return column;
    }
}
