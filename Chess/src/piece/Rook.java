package piece;

import move.RookMovementStrategy;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite, new RookMovementStrategy());
    }
}