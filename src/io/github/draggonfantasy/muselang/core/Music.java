package io.github.draggonfantasy.muselang.core;

import java.util.ArrayList;
import java.util.List;

public class Music
{
    private List<MusicUnit> units = new ArrayList<>();

    public Music(List<MusicUnit> units)
    {
        this.units = units;
    }

    public List<MusicUnit> getUnits()
    {
        return units;
    }

}
