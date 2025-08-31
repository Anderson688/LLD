package move;

import board.Cell;

public class Move {
    private final Cell start;
    private final Cell end;

    public Move(Cell start, Cell end) {
        this.start = start;
        this.end = end;
    }

    public Cell getStart() {
        return start;
    }

    public Cell getEnd() {
        return end;
    }

    public boolean isValid(boolean isWhiteTurn) {
        if (start.getPiece() == null) return false;
        if (start.getPiece().getIsWhite() != isWhiteTurn) return false;
        if (end.getPiece() != null && end.getPiece().getIsWhite() == isWhiteTurn) return false;
        return start.getPiece().isValidMove(this);
    }
}