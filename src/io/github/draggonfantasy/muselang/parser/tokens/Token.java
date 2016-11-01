package io.github.draggonfantasy.muselang.parser.tokens;

import java.util.Objects;

public abstract class Token
{
    private final String tokenStr;
    private final int line, column;

    public Token(String tokenStr, int line, int column)
    {
        Objects.requireNonNull(tokenStr);
        this.line = line;
        this.column = column;
        this.tokenStr = tokenStr;
    }

    public abstract String getTokenId();

    public String getTokenStr()
    {
        return tokenStr;
    }

    @Override
    public String toString()
    {
        return getTokenStr();
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
