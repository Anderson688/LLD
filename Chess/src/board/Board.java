package board;

import piece.*;

public class Board {
    private final Cell[][] board;

    private static Board instance;

    private Board() {
        board = new Cell[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Cell(i, j);
            }
        }

        for (int i = 0; i < 8; i++) {
            board[1][i].setPiece(PieceFactory.createPiece("pawn", true));
            board[6][i].setPiece(PieceFactory.createPiece("pawn", false));
        }

        board[0][0].setPiece(PieceFactory.createPiece("rook", true));
        board[0][1].setPiece(PieceFactory.createPiece("knight", true));
        board[0][2].setPiece(PieceFactory.createPiece("bishop", true));
        board[0][3].setPiece(PieceFactory.createPiece("queen", true));
        board[0][4].setPiece(PieceFactory.createPiece("king", true));
        board[0][5].setPiece(PieceFactory.createPiece("bishop", true));
        board[0][6].setPiece(PieceFactory.createPiece("knight", true));
        board[0][7].setPiece(PieceFactory.createPiece("rook", true));

        board[7][0].setPiece(PieceFactory.createPiece("rook", false));
        board[7][1].setPiece(PieceFactory.createPiece("knight", false));
        board[7][2].setPiece(PieceFactory.createPiece("bishop", false));
        board[7][3].setPiece(PieceFactory.createPiece("king", false));
        board[7][4].setPiece(PieceFactory.createPiece("queen", false));
        board[7][5].setPiece(PieceFactory.createPiece("bishop", false));
        board[7][6].setPiece(PieceFactory.createPiece("knight", false));
        board[7][7].setPiece(PieceFactory.createPiece("rook", false));
    }

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }
}