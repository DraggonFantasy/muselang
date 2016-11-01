package io.github.draggonfantasy.muselang.parser;

import io.github.draggonfantasy.muselang.core.*;
import io.github.draggonfantasy.muselang.parser.tokens.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 30.10.16.
 */
public class MuseParser
{
    private List<Token> tokens;
    private int pos;

    private Map<String, Phrase> phrases;

    public Music parse(List<Token> tokens) throws MuseSyntaxException
    {
        this.tokens = tokens;
        this.pos = 0;
        this.phrases = new HashMap<>();

        return musicProgram();
    }

    private int duration() throws MuseSyntaxException
    {
        if(token() instanceof TokenInteger)
        {
            return ((TokenInteger)token()).getValue();
        } else if(token() instanceof TokenKeyword)
        {
            switch (token().getTokenStr())
            {
                case "h" :case "half":      return 2;
                case "q" :case "quarter":   return 4;
                case "e" :case "eighth":    return 8;
                case "st":case "sixteenth": return 16;
            }
        }
        return -1;
    }

    private MusicUnit scoreNote() throws MuseSyntaxException
    {
        if( !(token() instanceof TokenNote) )
        {
            if( token() instanceof TokenKeyword && token().getTokenStr().equals("pause") )
            {
                next();
                int duration = duration();
                syntaxErrorIf(duration == -1, "ScoreNote: expected duration but found " + token().getTokenId(), token().getLine(), token().getColumn());
                next();

                return new Pause(duration);
            }
            return null;
        }
        Note note = ((TokenNote) token()).getNote();

        next();
        int duration = duration();
        next();
        syntaxErrorIf(duration == -1, "ScoreNote: expected duration but found " + token().getTokenId(), token().getLine(), token().getColumn());
        int velocity = integer();
        syntaxErrorIf(velocity == -1, "ScoreNote: expected velocity but found " + token().getTokenId(), token().getLine(), token().getColumn());
        next();

        return new ScoreNote(note, duration, velocity);
    }

    private Phrase phrase() throws MuseSyntaxException
    {
        if( !(token() instanceof TokenKeyword && token().getTokenStr().equals("phrase")) ) return null;

        next();
        syntaxErrorIf(!(token() instanceof TokenIdentifier),
                      "Phrase: expected identifier but found " + token().getTokenId(), token().getLine(), token().getColumn());
        String identifier = ((TokenIdentifier) token()).getIdentifier();
        next();

        syntaxErrorIf(!(token() instanceof TokenOperator && token().getTokenStr().equals("{")),
                      "Phrase: expected { but found " + token().getTokenId(), token().getLine(), token().getColumn());

        next();
        List<MusicUnit> phraseUnits = musicBlock();

        syntaxErrorIf(!(token() instanceof TokenOperator && token().getTokenStr().equals("}")),
                      "Phrase: expected } but found " + token().getTokenId(), token().getLine(), token().getColumn());
        next();

        Phrase phrase = new Phrase(identifier, phraseUnits);
        syntaxErrorIf(phrases.containsKey(identifier), "Phrase: overriding phrases is not allowed: " + identifier, token().getLine(), token().getColumn());

        phrases.put(identifier, phrase);
        return phrase;
    }

    private List<MusicUnit> musicBlock() throws MuseSyntaxException
    {
        List<MusicUnit> units = new ArrayList<>();
        while(true)
        {
            MusicUnit unit = scoreNote();
            if(unit != null)
            {
                units.add(unit);
            } else if (token() instanceof TokenIdentifier)
            {
                String identifier = ((TokenIdentifier) token()).getIdentifier();
                Phrase phrase = phrases.get(identifier);
                syntaxErrorIf(phrase == null, "Unknown phrase '" + identifier + "'", token().getLine(), token().getColumn());
                next();
                unit = phrase;
                units.add(unit);
            } else
            {
                break;
            }
        }
        return units;
    }

    private Music musicProgram() throws MuseSyntaxException
    {
        //noinspection StatementWithEmptyBody
        while( phrase() != null ) {}
        List<MusicUnit> units = musicBlock();

        return new Music(units);
    }

    private int integer()
    {
        if(token() instanceof TokenInteger)
        {
            return ((TokenInteger)token()).getValue();
        }
        return -1;
    }

    private Token next()
    {
        if( tokens.size() <= ++pos ) return null;

        return tokens.get( pos );
    }

    private Token token()
    {
        if( tokens.size() <= pos ) return null;

        return tokens.get(pos);
    }

    private void syntaxErrorIf(boolean condition, String msg, int line, int column) throws MuseSyntaxException
    {
        if( condition ) throw new MuseSyntaxException(msg, line, column);
    }
}
