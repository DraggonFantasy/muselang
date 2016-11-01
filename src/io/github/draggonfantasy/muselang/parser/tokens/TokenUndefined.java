package io.github.draggonfantasy.muselang.parser.tokens;


public class TokenUndefined extends Token
{
    public static final String TOKEN_ID = "undefined";

    public TokenUndefined(String str, int line, int column)
    {
        super(str, line, column);
    }

    @Override
    public String getTokenId()
    {
        return TOKEN_ID;
    }
}
