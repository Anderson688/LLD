import board.ChessGame;
import player.Player;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Player player1 = new Player(1, true);
        Player player2 = new Player(2, false);
        // Initialize game
        ChessGame chessGame = new ChessGame(player1, player2);
        chessGame.start();
    }
}