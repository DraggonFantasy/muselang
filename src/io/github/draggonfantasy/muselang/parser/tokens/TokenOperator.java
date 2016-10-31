package io.github.draggonfantasy.muselang.parser.tokens;

public class TokenOperator extends Token
{
    public static final String TOKEN_ID = "operator";

    public TokenOperator(String tokenStr)
    {
        super(tokenStr);
    }

    @Override
    public String getTokenId()
    {
        return TOKEN_ID;
    }
}
