package model.entities;

public class Ladder implements BoardEntity {
    private final int start;
    private final int end;

    public Ladder(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getEnd() {
        return end;
    }
}