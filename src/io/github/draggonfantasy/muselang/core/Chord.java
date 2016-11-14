package io.github.draggonfantasy.muselang.core;

import java.util.List;

public class Chord extends MusicUnit
{
    private List<ScoreNote> units;

    public Chord(List<ScoreNote> units)
    {
        this.units = units;
    }

    public List<ScoreNote> getUnits()
    {
        return units;
    }
}
