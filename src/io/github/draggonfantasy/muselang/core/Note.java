package io.github.draggonfantasy.muselang.core;

public enum Note
{
    C0(0),
    C_SHARP0(1),
    D0(2),
    D_SHARP0(3),
    E0(4),
    F0(5),
    F_SHARP0(6),
    G0(7),
    G_SHARP0(8),
    A0(9),
    A_SHARP0(10),
    B0(11),
    C1(12),
    C_SHARP1(13),
    D1(14),
    D_SHARP1(15),
    E1(16),
    F1(17),
    F_SHARP1(18),
    G1(19),
    G_SHARP1(20),
    A1(21),
    A_SHARP1(22),
    B1(23),
    C2(24),
    C_SHARP2(25),
    D2(26),
    D_SHARP2(27),
    E2(28),
    F2(29),
    F_SHARP2(30),
    G2(31),
    G_SHARP2(32),
    A2(33),
    A_SHARP2(34),
    B2(35),
    C3(36),
    C_SHARP3(37),
    D3(38),
    D_SHARP3(39),
    E3(40),
    F3(41),
    F_SHARP3(42),
    G3(43),
    G_SHARP3(44),
    A3(45),
    A_SHARP3(46),
    B3(47),
    C4(48),
    C_SHARP4(49),
    D4(50),
    D_SHARP4(51),
    E4(52),
    F4(53),
    F_SHARP4(54),
    G4(55),
    G_SHARP4(56),
    A4(57),
    A_SHARP4(58),
    B4(59),
    C5(60),
    C_SHARP5(61),
    D5(62),
    D_SHARP5(63),
    E5(64),
    F5(65),
    F_SHARP5(66),
    G5(67),
    G_SHARP5(68),
    A5(69),
    A_SHARP5(70),
    B5(71),
    C6(72),
    C_SHARP6(73),
    D6(74),
    D_SHARP6(75),
    E6(76),
    F6(77),
    F_SHARP6(78),
    G6(79),
    G_SHARP6(80),
    A6(81),
    A_SHARP6(82),
    B6(83),
    C7(84),
    C_SHARP7(85),
    D7(86),
    D_SHARP7(87),
    E7(88),
    F7(89),
    F_SHARP7(90),
    G7(91),
    G_SHARP7(92),
    A7(93),
    A_SHARP7(94),
    B7(95),
    C8(96),
    C_SHARP8(97),
    D8(98),
    D_SHARP8(99),
    E8(100),
    F8(101),
    F_SHARP8(102),
    G8(103),
    G_SHARP8(104),
    A8(105),
    A_SHARP8(106),
    B8(107),
    C9(108),
    C_SHARP9(109),
    D9(110),
    D_SHARP9(111),
    E9(112),
    F9(113),
    F_SHARP9(114),
    G9(115),
    G_SHARP9(116),
    A9(117),
    A_SHARP9(118),
    B9(119),
    C10(120),
    C_SHARP10(121),
    D10(122),
    D_SHARP10(123),
    E10(124),
    F10(125),
    F_SHARP10(126),
    G10(127),
    ;

    private int noteId;

    Note(int noteId)
    {
        this.noteId = noteId;
    }

    public int noteId()
    {
        return noteId;
    }

    public static Note fromString(String noteStr)
    {
        noteStr = noteStr.replace("#", "_SHARP");
        for(Note note : values())
        {
            if(note.name().equals(noteStr)) return note;
        }
        return null;
    }
}
