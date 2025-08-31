package piece;

import move.KnightMovementStrategy;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite, new KnightMovementStrategy());
    }
}