package io.github.draggonfantasy.muselang.core;

import java.util.ArrayList;
import java.util.List;

public class Phrase extends MusicUnit
{
    private final String name;
    private List<MusicUnit> notes = new ArrayList<>();

    public Phrase(String name, List<MusicUnit> notes)
    {
        this.name = name;
        this.notes = notes;
    }

    public List<MusicUnit> getNotes()
    {
        return notes;
    }

    public List<MusicUnit> getNotesCopy()
    {
        return new ArrayList<>(notes);
    }
}
