package io.github.draggonfantasy.muselang.parser;

import io.github.draggonfantasy.muselang.core.*;
import io.github.draggonfantasy.muselang.parser.tokens.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MuseParser
{
    public static final int DEFAULT_VELOCITY = 127;
    public static final int DEFAULT_DURATION = 4;
    private List<Token> tokens;
    private int pos;

    public Music parse(List<Token> tokens) throws MuseSyntaxException
    {
        this.tokens = tokens;
        this.pos = 0;

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
        if(duration == -1) return new ScoreNote(note, DEFAULT_DURATION, DEFAULT_VELOCITY);

        next();
        int velocity = integer();
        syntaxErrorIf(velocity == -1, "ScoreNote: expected velocity but found " + token().getTokenId(), token().getLine(), token().getColumn());
        next();

        return new ScoreNote(note, duration, velocity);
    }

    private PhraseProto phrase() throws MuseSyntaxException
    {
        if( !(token() instanceof TokenKeyword && token().getTokenStr().equals("phrase")) ) return null;

        next();
        syntaxErrorIf(!(token() instanceof TokenIdentifier),
                      "PhraseProto: expected identifier but found " + token().getTokenId(), token().getLine(), token().getColumn());
        String identifier = ((TokenIdentifier) token()).getIdentifier();
        next();

        syntaxErrorIf(!(token() instanceof TokenOperator && token().getTokenStr().equals("{")),
                      "PhraseProto: expected { but found " + token().getTokenId(), token().getLine(), token().getColumn());

        next();
        List<MusicUnit> phraseUnits = musicBlock();

        syntaxErrorIf(!(token() instanceof TokenOperator && token().getTokenStr().equals("}")),
                      "PhraseProto: expected } but found " + token().getTokenId(), token().getLine(), token().getColumn());
        next();

        return new PhraseProto(identifier, phraseUnits);
    }

    private Repeat repeat() throws MuseSyntaxException
    {
        if( !(token() instanceof TokenKeyword && token().getTokenStr().equals("repeat")) ) return null;

        next();

        int times = integer();
        syntaxErrorIf(times == -1,
                "Repeat: expected times integer but found " + token().getTokenId(), token().getLine(), token().getColumn());
        next();

        syntaxErrorIf(!(token() instanceof TokenOperator && token().getTokenStr().equals("{")),
                "Repeat: expected { but found " + token().getTokenId(), token().getLine(), token().getColumn());


        next();
        List<MusicUnit> units = musicBlock();

        syntaxErrorIf(!(token() instanceof TokenOperator && token().getTokenStr().equals("}")),
                "PhraseProto: expected } but found " + token().getTokenId(), token().getLine(), token().getColumn());
        next();

        return new Repeat(units, times);
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
                next();
                unit = new PhraseCall(identifier);
                units.add(unit);
            } else if(token() instanceof TokenKeyword)
            {
                Repeat repeat = repeat();
                if(repeat == null) throw new MuseSyntaxException("Illegal keyword " + token().getTokenId(), token().getLine(), token().getColumn());

                units.add(repeat);
            } else
            {
                break;
            }
        }
        return units;
    }

    private Music musicProgram() throws MuseSyntaxException
    {
        Map<String, PhraseProto> phrases = new HashMap<>();
        PhraseProto phrase;
        while( (phrase = phrase()) != null )
        {
            phrases.put(phrase.getName(), phrase);
        }

        List<MusicUnit> units = musicBlock();

        return new Music(phrases, units);
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
