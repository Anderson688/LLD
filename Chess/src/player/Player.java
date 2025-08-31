package player;

public class Player {
    private final int playerId;
    private final boolean isWhite;

    public Player(int playerId, boolean isWhite) {
        this.playerId = playerId;
        this.isWhite = isWhite;
    }

    public int getPlayerId() {
        return playerId;
    }

    public boolean getIsWhite() {
        return isWhite;
    }
}