package io.github.draggonfantasy.muselang.parser.tokens;

public class TokenInteger extends Token
{
    public static final String TOKEN_ID = "integer";

    private final int value;

    public TokenInteger(String tokenStr, int line, int column)
    {
        super(tokenStr, line, column);
        this.value = Integer.parseUnsignedInt(tokenStr);
    }

    @Override
    public String getTokenId()
    {
        return TOKEN_ID;
    }

    public int getValue()
    {
        return value;
    }
}
