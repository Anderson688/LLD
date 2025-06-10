import java.util.Scanner;

public class Board {

    private final int boardSize;
    private final PlayingPiece[][] playingBoard;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.playingBoard = new PlayingPiece[boardSize][boardSize];
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    public boolean placePiece(int row, int col, PlayingPiece playingPiece) {
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize || playingBoard[row][col] != null) return false;
        playingBoard[row][col] = playingPiece;
        return true;
    }

    public PlayingPiece getCell(int row, int col) {
        return this.playingBoard[row][col];
    }

    public void printBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (playingBoard[i][j] != null) {
                    System.out.print(playingBoard[i][j].getPiece() + "   ");
                } else {
                    System.out.print("    ");
                }
                System.out.print(" | ");
            }
            System.out.println();
        }
    }
}