package io.github.draggonfantasy.muselang.parser.tokens;

public class TokenKeyword extends Token
{
    public static final String TOKEN_ID = "keyword";

    public TokenKeyword(String tokenStr, int line, int column)
    {
        super(tokenStr, line, column);
    }

    @Override
    public String getTokenId()
    {
        return TOKEN_ID;
    }
}
