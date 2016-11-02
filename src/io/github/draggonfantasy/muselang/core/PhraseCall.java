package io.github.draggonfantasy.muselang.core;

public class PhraseCall extends MusicUnit
{
    private final String phraseName;

    public PhraseCall(String phraseName)
    {
        this.phraseName = phraseName;
    }

    public String getPhraseName()
    {
        return phraseName;
    }
}
