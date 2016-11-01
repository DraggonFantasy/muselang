package io.github.draggonfantasy.muselang;

import io.github.draggonfantasy.muselang.core.Music;
import io.github.draggonfantasy.muselang.interpreter.MuseInterpreter;
import io.github.draggonfantasy.muselang.parser.MuseLexer;
import io.github.draggonfantasy.muselang.parser.MuseParser;
import io.github.draggonfantasy.muselang.parser.MuseSyntaxException;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Muselang
{
    public static void main(String[] args) throws InvalidMidiDataException, MidiUnavailableException, IOException
    {
        if(args.length < 2)
        {
            showHelp();
            return;
        }

        MuseInterpreter interpreter = new MuseInterpreter();

        List<String> lines = Files.readAllLines(Paths.get(args[1]));
        String code = lines.stream().collect(Collectors.joining("\n"));
        Music music;
        Sequence seq;
        try
        {
            music = new MuseParser().parse(new MuseLexer().lex(code));
            seq = interpreter.toMidiSequence(music);
        } catch (MuseSyntaxException e)
        {
            System.err.println("Syntax error:\n" + e.getMessage());
            System.err.println(lines.get(e.getLine() - 1));
            for(int i = 0; i < e.getColumn() - 1; ++i)
            {
                if( lines.get(e.getLine() - 1).charAt(i) == '\t' ) System.err.print("\t");
                System.err.print(" ");
            }
            System.err.println("^");
            return;
        }

        switch (args[0])
        {
            case "play":
                Sequencer player = MidiSystem.getSequencer();
                player.open();
                player.setSequence(seq);
                player.start();
                break;
            case "save":
                if(args.length < 3)
                {
                    showHelp();
                    return;
                }
                MidiSystem.write(seq, 1, new File(args[2]));
                break;
            default:
                showHelp();
                break;
        }
    }

    private static void showHelp()
    {
        System.out.println("Welcome to Muselang!\nUsage:\n\tplay {sourcefile.mus}\n\tsave {sourcefile.mus} {destfile.mid}");
    }
}
