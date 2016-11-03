<img src="https://raw.githubusercontent.com/DraggonFantasy/muselang/master/icon.png" width="64" height="64">
# Muselang
Muselang is a music programming language. It's designed to be simple, concise and powerful enough to make music with it :)  
How does it look? Here's simple melody written in Muselang:  
```
C5 4 100
D5 4 100
E5 4 100
F5 4 100
G5 4 100
A5 quarter 100
B5 q 100
```
As you may already guessed - this program plays an octave. `C5 4 100` means "play quarter note C of octave 5 with velocity 100". Also you can omit octave number for 5th octave. So `C5` and `C` is a same note in Muselang.  
Muselang also support *phrases*:
```
phrase hello {
    C eighth 100
    D eighth 100
    E e 100
    D e 50
}
phrase world {
    F quarter 50
    G quarter 50
}
phrase main {
    hello
    world
    A 4 100
    B 4 100
}

main
```
You can play melodies written in Muselang using Muselang interpreter or you can export a melody as a MIDI file.

## Why use Muselang?
Why make music with Muselang when there exist lots of WYSIWYG programs for music creation? I can give you an answer :)  
* Muselang can be used for procedure generation of music. You may say that MIDI can also be easily used for procedure generation of music. But! Muselang is human-readable, has concept of phrases etc
* Muselang is VCS-friendly. You can collaborate with other music developers, creating the most awesome music people ever made! This is also useful for open-source games!
* Muselang can be used as a backend for WYSIWYG programs
* Writing text can be faster than moving around notes in GUI progams (that is true if you don't have a MIDI keyboard, like me :) )
* Muselang is free and open-source!

## What is current state of Muselang?
Current (as for 31.10.2016) version of Muselang is 0.1.0 and it's still in early development. Up until 1.0.0, new versions *may* (or may not) be non backwards-compatible.
