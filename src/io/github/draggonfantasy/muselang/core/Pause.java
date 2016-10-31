package io.github.draggonfantasy.muselang.core;

public class Pause extends MusicUnit
{
    private int duration;

    public Pause(int duration)
    {
        this.duration = duration;
    }

    public int getDuration()
    {
        return duration;
    }
}
