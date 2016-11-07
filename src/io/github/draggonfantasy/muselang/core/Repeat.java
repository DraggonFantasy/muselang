package io.github.draggonfantasy.muselang.core;

import java.util.ArrayList;
import java.util.List;

public class Repeat extends MusicUnit
{
    private final int times;
    private List<MusicUnit> units = new ArrayList<>();

    public Repeat(List<MusicUnit> units, int times)
    {
        this.units = units;
        this.times = times;
    }

    public int getTimes()
    {
        return times;
    }

    public List<MusicUnit> getUnits()
    {
        return units;
    }
}
