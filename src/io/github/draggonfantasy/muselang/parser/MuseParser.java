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

    public Music parse(List<Token> tokens) throws ParserException
    {
        this.tokens = tokens;
        this.pos = 0;
        this.phrases = new HashMap<>();

        return musicProgram();
    }

    private int duration() throws ParserException
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

    private MusicUnit scoreNote() throws ParserException
    {
        if( !(token() instanceof TokenNote) )
        {
            if( token() instanceof TokenKeyword && token().getTokenStr().equals("pause") )
            {
                next();
                int duration = duration();
                if( duration == -1 ) throw new ParserException("ScoreNote: expected duration but found " + token().getTokenId());
                next();

                return new Pause(duration);
            }
            return null;
        }
        Note note = ((TokenNote) token()).getNote();

        next();
        int duration = duration();
        next();
        if( duration == -1 ) throw new ParserException("ScoreNote: expected duration but found " + token().getTokenId());
        int velocity = integer();
        if( velocity == -1 ) throw new ParserException("ScoreNote: expected velocity but found " + token().getTokenId());
        next();

        return new ScoreNote(note, duration, velocity);
    }

    private Phrase phrase() throws ParserException
    {
        if( !(token() instanceof TokenKeyword && token().getTokenStr().equals("phrase")) ) return null;

        next();
        if( !(token() instanceof TokenIdentifier) ) throw new ParserException("Phrase: expected identifier but found " + token().getTokenId());
        String identifier = ((TokenIdentifier) token()).getIdentifier();
        next();

        if( !(token() instanceof TokenOperator && token().getTokenStr().equals("{")) ) throw new ParserException("Phrase: expected { but found " + token().getTokenId());

        next();
        List<MusicUnit> phraseUnits = musicBlock();

        if( !(token() instanceof TokenOperator && token().getTokenStr().equals("}")) ) throw new ParserException("Phrase: expected } but found " + token().getTokenId());
        next();

        Phrase phrase = new Phrase(identifier, phraseUnits);
        if(phrases.containsKey(identifier)) throw new ParserException("Phrase: overriding phrases is not allowed: " + identifier);

        phrases.put(identifier, phrase);
        return phrase;
    }

    private List<MusicUnit> musicBlock() throws ParserException
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
                if(phrase == null) throw new ParserException("Unknown phrase '" + identifier + "'");
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

    private Music musicProgram() throws ParserException
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
}
