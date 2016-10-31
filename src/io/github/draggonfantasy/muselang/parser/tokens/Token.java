package io.github.draggonfantasy.muselang.parser.tokens;

import java.util.Objects;

public abstract class Token
{
    private final String tokenStr;

    public Token(String tokenStr)
    {
        Objects.requireNonNull(tokenStr);
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
}
