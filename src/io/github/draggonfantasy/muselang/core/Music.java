package io.github.draggonfantasy.muselang.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Music
{
    private Map<String, PhraseProto> phrases;
    private List<MusicUnit> units = new ArrayList<>();

    public Music(Map<String, PhraseProto> phrases, List<MusicUnit> units)
    {
        this.phrases = phrases;
        this.units = units;
    }

    public List<MusicUnit> getUnits()
    {
        return units;
    }

    public Map<String, PhraseProto> getPhrases()
    {
        return phrases;
    }

    public PhraseProto getPhrase(String key)
    {
        return phrases.get(key);
    }
}
