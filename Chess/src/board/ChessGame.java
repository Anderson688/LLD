package board;

import enums.Status;
import move.Move;
import piece.King;
import piece.Piece;
import player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChessGame {
    private final Board board;

    private final Player player1;
    private final Player player2;

    boolean isWhiteTurn;
    private final List<Move> moves;
    private Status status;

    public ChessGame(Player player1, Player player2) {
        board = Board.getInstance();

        this.player1 = player1;
        this.player2 = player2;

        isWhiteTurn = true;
        moves = new ArrayList<>();
        status = Status.ACTIVE;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (this.status == Status.ACTIVE) {
            Player currentPlayer = isWhiteTurn ? player1 : player2;
            System.out.println(currentPlayer.getPlayerId() + "'s turn (" + (currentPlayer.getIsWhite() ? "White" : "Black") + ")");

            // Ask for source coordinates
            System.out.print("Enter source row and column (e.g., 6 4): ");
            int startRow = scanner.nextInt();
            int startCol = scanner.nextInt();

            // Ask for destination coordinates
            System.out.print("Enter destination row and column (e.g., 4 4): ");
            int endRow = scanner.nextInt();
            int endCol = scanner.nextInt();

            // Get the start and end cells from the board
            Cell startCell = board.getCell(startRow, startCol);
            Cell endCell = board.getCell(endRow, endCol);

            if (startCell == null || startCell.getPiece() == null) {
                System.out.println("Invalid move: No piece at source cell.");
                continue;
            }

            // Make the move
            makeMove(new Move(startCell, endCell));
        }

        System.out.println("Game Over! Status: " + this.status);
    }

    // Make a move in the game
    private void makeMove(Move move) {
        // Initial check for a valid move
        // To check if source and destination don't contain the same color pieces
        if (move.isValid(isWhiteTurn)) {
            Piece destinationPiece = move.getEnd().getPiece();
            // Check if the destination cell contains some piece
            if (destinationPiece != null) {
                // If the destination cell contains King and currently white is
                // playing --> White wins
                if (destinationPiece instanceof King) {
                    this.status = isWhiteTurn ? Status.WHITE_WIN : Status.BLACK_WIN;
                    return;
                }
                // Set the destination piece as killed
                destinationPiece.setKilled(true);
            }
            // Adding the valid move to the game logs
            moves.add(move);
            // Moving the source piece to the destination cell
            move.getEnd().setPiece(move.getStart().getPiece());
            // Setting the source cell to null (means it doesn't have any piece)
            move.getStart().setPiece(null);
            // Toggling the turn
            this.isWhiteTurn = !isWhiteTurn;
            System.out.println(isWhiteTurn);
        }
    }
}