package piece;

import move.QueenMovementStrategy;

public class Queen extends Piece {
    public Queen(boolean isWhite) {
        super(isWhite, new QueenMovementStrategy());
    }
}