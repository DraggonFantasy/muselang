package io.github.draggonfantasy.muselang.parser.tokens;

public class TokenOperator extends Token
{
    public static final String TOKEN_ID = "operator";

    public TokenOperator(String tokenStr, int line, int column)
    {
        super(tokenStr, line, column);
    }

    @Override
    public String getTokenId()
    {
        return TOKEN_ID;
    }
}
