package io.github.draggonfantasy.muselang.parser.tokens;

public class TokenInteger extends Token
{
    public static final String TOKEN_ID = "integer";

    private final int value;

    public TokenInteger(String tokenStr)
    {
        super(tokenStr);
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
