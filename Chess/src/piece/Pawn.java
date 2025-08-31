package piece;

import move.PawnMovementStrategy;

public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite, new PawnMovementStrategy());
    }
}