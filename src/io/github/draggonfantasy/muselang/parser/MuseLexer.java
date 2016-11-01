package io.github.draggonfantasy.muselang.parser;

import io.github.draggonfantasy.muselang.parser.tokens.*;

import java.util.ArrayList;
import java.util.List;

public class MuseLexer
{
    private String singleCharTokens = "{}";
    private String[] keywords = new String[]{
            "phrase", "pause", "quarter", "half", "eighth", "sixteenth", "q", "h", "e", "st"
    };

    public List<Token> lex(String str)
    {
        List<Token> tokens = new ArrayList<>();

        StringBuilder tokenStr = new StringBuilder();
        int line = 1;
        int column = 1;
        for(int i = 0; i < str.length(); ++i, ++column)
        {
            char c = str.charAt(i);
            if( Character.isWhitespace(c) || singleCharTokens.indexOf(c) != -1 )
            {
                Token token = recognizeToken( tokenStr.toString(), line, column - tokenStr.length() );
                if(token != null) tokens.add(token);

                Token operatorToken = recognizeToken( c + "", line, column );
                if(operatorToken != null) tokens.add(operatorToken);

                tokenStr = new StringBuilder();

                if( c == '\n' )
                {
                    line++;
                    column = 0;
                }
                continue;
            }

            tokenStr.append(c);
        }
        Token token = recognizeToken( tokenStr.toString(), line, column );
        if(token != null) tokens.add(token);

        return tokens;
    }

    private Token recognizeToken(String str, int line, int column)
    {
        if( str.matches("\\s*") ) return null;

        if( str.length() == 1 && singleCharTokens.indexOf(str.charAt(0)) != -1 ) return new TokenOperator(str, line, column);
        if( find(keywords, str) ) return new TokenKeyword( str, line, column );
        if( str.matches("\\d+") ) return new TokenInteger( str, line, column );
        if( str.matches("[A-G]#?[0-9]*") && !str.matches("E#[0-9]*") ) return new TokenNote( str, line, column );
        if( str.matches("[a-z_][a-z_0-9]*") ) return new TokenIdentifier(str, line, column);

        return new TokenUndefined(str, line, column);
    }

    private <T> boolean find(T[] arr, T elem)
    {
        for(T t : arr) if( t.equals(elem) ) return true;
        return false;
    }
}
