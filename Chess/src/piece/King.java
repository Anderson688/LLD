package piece;

import move.KingMovementStrategy;

public class King extends Piece {
    public King(boolean isWhite) {
        super(isWhite, new KingMovementStrategy());
    }
}