package piece;

public class PieceFactory {
    public static Piece createPiece(String type, boolean isWhite) {
        return switch (type.toLowerCase()) {
            case "pawn" -> new Pawn(isWhite);
            case "rook" -> new Rook(isWhite);
            case "knight" -> new Knight(isWhite);
            case "bishop" -> new Bishop(isWhite);
            case "queen" -> new Queen(isWhite);
            case "king" -> new King(isWhite);
            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        };
    }
}