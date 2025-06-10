import java.util.Scanner;

public class TicTacToeGame {
    private final Player[] players;
    private final Board board;
    private final int winningCondition;
    private final Scanner inputScanner = new Scanner(System.in);

    public TicTacToeGame() {
        int playerCount = 0;
        while (true) {
            System.out.print("Enter number of players (min 2, max 9): ");
            try {
                playerCount = Integer.parseInt(inputScanner.nextLine());
            } catch (Exception _) {}
            if (playerCount < 2 || playerCount > 9) System.out.print("Re");
            else break;
        }
        this.players = new Player[playerCount];
        int boardSize = 0;
        while (true) {
            System.out.print("Enter board size (min 3, max 10): ");
            try {
                boardSize = Integer.parseInt(inputScanner.nextLine());
            } catch (Exception _) {}
            if (boardSize < 3 || boardSize > 10) System.out.print("Re");
            else break;
        }
        this.board = new Board(boardSize);
        int winningCondition = 0;
        while (true) {
            System.out.print("Enter win size (min 3, max " + boardSize + "): ");
            try {
                winningCondition = Integer.parseInt(inputScanner.nextLine());
            } catch (Exception _) {}
            if (winningCondition < 3 || winningCondition > boardSize) System.out.print("Re");
            else break;
        }
        this.winningCondition = winningCondition;
        initializePlayers(playerCount);
    }

    private void initializePlayers(int playerCount) {
        int i = 0;
        while (i < playerCount) {
            System.out.print("Player " + i + " enter your playing piece: ");
            String playerInput = inputScanner.nextLine();
            if (playerInput.isBlank() || playerInput.length() > 1) {
                System.out.println("Invalid playing piece for player " + i);
            } else {
                players[i] = new Player(i, new PlayingPiece(playerInput.charAt(0)));
                i++;
            }
        }
    }

    public void startGame() {
        System.out.println("Starting Game ...");
        int playerTurn = 0;
        int emptyCells = board.getBoardSize()* board.getBoardSize();
        boolean foundWinner = false;
        while (emptyCells > 0) {
            System.out.print("Player " + playerTurn + " enter row,col to place piece: ");
            String playerInput = inputScanner.nextLine();
            String[] cell = playerInput.split(",");
            boolean error = false;
            int row = -1, col = -1;
            try {
                row = Integer.parseInt(cell[0]);
                col = Integer.parseInt(cell[1]);
            } catch (Exception ex) {
                error = true;
            }
            if (error || !board.placePiece(row, col, players[playerTurn].getPlayingPiece())) {
                System.out.println("Invalid row,col for player " + playerTurn);
                continue;
            }
            board.printBoard();
            if (isWinnerFound(row, col)) {
                foundWinner = true;
                break;
            }
            playerTurn++;
            playerTurn %= players.length;
            emptyCells--;
        }
        if (foundWinner) System.out.println("Player " + playerTurn + " wins!!");
        else System.out.println("Game is tied..");
    }

    private boolean isWinnerFound(int row, int col) {
        PlayingPiece playingPiece = board.getCell(row, col);

        int rowCount = 1;
        for (int i = col-1; i >= 0 && board.getCell(row, i) == playingPiece; i--) {
            rowCount++;
        }
        for (int i = col+1; i < board.getBoardSize() && board.getCell(row, i) == playingPiece; i++) {
            rowCount++;
        }
        if (rowCount == winningCondition) return true;

        int colCount = 1;
        for (int i = row-1; i >= 0 && board.getCell(i, col) == playingPiece; i--) {
            colCount++;
        }
        for (int i = row+1; i < board.getBoardSize() && board.getCell(i, col) == playingPiece; i++) {
            colCount++;
        }
        if (colCount == winningCondition) return true;

        int diagCount1 = 1;
        for (int i = row-1, j = col-1; i >= 0 && j >= 0 && board.getCell(i, j) == playingPiece; i--, j--) {
            diagCount1++;
        }
        for (int i = row+1, j = col+1; i < board.getBoardSize() && j < board.getBoardSize() && board.getCell(i, j) == playingPiece; i++, j++) {
            diagCount1++;
        }
        if (diagCount1 == winningCondition) return true;

        int diagCount2 = 1;
        for (int i = row+1, j = col-1; i < board.getBoardSize() && j >= 0 && board.getCell(i, j) == playingPiece; i++, j--) {
            diagCount2++;
        }
        for (int i = row-1, j = col+1; i >= 0 && j < board.getBoardSize() && board.getCell(i, j) == playingPiece; i--, j++) {
            diagCount2++;
        }
        return diagCount2 == winningCondition;
    }
}