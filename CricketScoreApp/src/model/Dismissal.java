package model;

import types.DismissalType;

public class Dismissal {
    private final Player batsman;
    private final Player bowler;
    private final DismissalType type;

    public Dismissal(Player batsman, Player bowler, DismissalType type) {
        this.batsman = batsman;
        this.bowler = bowler;
        this.type = type;
    }

    public Player getBatsman() { return batsman; }
    public Player getBowler() { return bowler; }
    public DismissalType getType() { return type; }
}