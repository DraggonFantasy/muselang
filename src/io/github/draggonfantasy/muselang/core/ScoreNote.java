package io.github.draggonfantasy.muselang.core;

public class ScoreNote extends MusicUnit
{
    private final Note note;
    private final int duration;
    private final int velocity;

    public ScoreNote(Note note, int duration, int velocity)
    {
        this.note = note;
        this.duration = duration;
        this.velocity = velocity;
    }

    public int getVelocity()
    {
        return velocity;
    }

    public Note getNote()
    {
        return note;
    }

    public int getDuration()
    {
        return duration;
    }
}
