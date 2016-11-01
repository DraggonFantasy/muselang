package io.github.draggonfantasy.muselang.parser.tokens;

public class TokenIdentifier extends Token
{
    public static final String TOKEN_ID = "identifier";

    private final String identifier;

    public TokenIdentifier(String str, int line, int column)
    {
        super(str, line, column);
        identifier = str;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    @Override
    public String getTokenId()
    {
        return TOKEN_ID;
    }
}
