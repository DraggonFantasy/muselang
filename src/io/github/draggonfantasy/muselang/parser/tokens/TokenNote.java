package io.github.draggonfantasy.muselang.parser.tokens;

import io.github.draggonfantasy.muselang.core.Note;

public class TokenNote extends Token
{
    public static final String TOKEN_ID = "note";

    private final Note note;

    public TokenNote(String tokenStr)
    {
        super(tokenStr);
        String fullNoteStr = tokenStr;
        if( !Character.isDigit(tokenStr.charAt(tokenStr.length()-1)) )
        {
            fullNoteStr = tokenStr + 5; // If used shorthand note notation (like C#), add default octave to end
        }

        this.note = Note.fromString(fullNoteStr);
    }

    @Override
    public String getTokenId()
    {
        return TOKEN_ID;
    }

    public Note getNote()
    {
        return note;
    }

    @Override
    public String toString()
    {
        return getTokenStr() + " (" + note.name() + ")";
    }
}
