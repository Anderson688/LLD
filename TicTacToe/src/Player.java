public class Player {
    private final int id;

    private final PlayingPiece playingPiece;

    public Player(int id, PlayingPiece playingPiece) {
        this.id = id;
        this.playingPiece = playingPiece;
    }

    public PlayingPiece getPlayingPiece() {
        return this.playingPiece;
    }
}