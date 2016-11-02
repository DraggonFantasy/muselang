package io.github.draggonfantasy.muselang.interpreter;

import io.github.draggonfantasy.muselang.core.*;

import javax.sound.midi.*;
import java.util.List;

public class MuseInterpreter
{
    private static final int CHANNEL = 10;
    private static final int RESOLUTION = 60;

    public Sequence toMidiSequence(Music music) throws InvalidMidiDataException, MuseRuntimeException
    {
        Sequence seq = new Sequence(Sequence.PPQ, RESOLUTION);
        Track track = seq.createTrack();

        List<MusicUnit> musicUnits = music.getUnits();
        long tick = 0;
        for (MusicUnit unit : musicUnits)
        {
            tick = addUnitToTrack(music, track, tick, unit);
        }

        return seq;
    }

    private long addUnitToTrack(Music music, Track track, long tick, MusicUnit unit) throws InvalidMidiDataException, MuseRuntimeException
    {
        if (unit instanceof ScoreNote)
        {
            tick = addNoteToTrack(track, tick, (ScoreNote) unit);
        } else if (unit instanceof PhraseCall)
        {
            PhraseCall  phraseCall = (PhraseCall) unit;
            PhraseProto phrase = music.getPhrase(phraseCall.getPhraseName());
            if(phrase == null)
            {
                throw new MuseRuntimeException("No prototype found for phrase " + phraseCall.getPhraseName());
            }
            for(MusicUnit phraseUnit : phrase.getNotes())
            {
                tick = addUnitToTrack(music, track, tick, phraseUnit);
            }
        } else if(unit instanceof Pause)
        {
            tick += RESOLUTION / (((Pause)unit).getDuration() / 4f);
        }
        return tick;
    }

    private long addNoteToTrack(Track track, long tick, ScoreNote note) throws InvalidMidiDataException
    {
        ShortMessage midiMessageOn  = new ShortMessage(ShortMessage.NOTE_ON,  CHANNEL, note.getNote().noteId(), note.getVelocity());
        ShortMessage midiMessageOff = new ShortMessage(ShortMessage.NOTE_OFF, CHANNEL, note.getNote().noteId(), note.getVelocity());
        track.add(new MidiEvent(midiMessageOn, tick));

        tick += RESOLUTION / (note.getDuration() / 4f); // Resolution is how many ticks is done per quarter note
        // So if the note's duration is quarter, we need to increase tick in value of resolution
        // If note's duration is eighth, we need to increase tick in value of resolution/2 and so on
        track.add(new MidiEvent(midiMessageOff, tick));
        return tick;
    }
}
