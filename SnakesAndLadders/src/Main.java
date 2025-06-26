import core.Game;
import factory.BoardEntityFactory;
import model.Board;
import model.Player;
import strategy.ExactThrowWinningStrategy;
import strategy.WinningStrategy;
import ui.ConsoleLogger;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // --- Game Configuration ---
        int boardSize = 100;
        List<Player> players = List.of(new Player("Alice"), new Player("Bob"));

        // Use a factory to create entities
        BoardEntityFactory entityFactory = new BoardEntityFactory();
        Board board = new Board(boardSize);

        // Add snakes
        board.addEntity(entityFactory.createSnake(17, 7));
        board.addEntity(entityFactory.createSnake(54, 34));
        board.addEntity(entityFactory.createSnake(62, 19));
        board.addEntity(entityFactory.createSnake(64, 60));
        board.addEntity(entityFactory.createSnake(87, 24));
        board.addEntity(entityFactory.createSnake(93, 73));
        board.addEntity(entityFactory.createSnake(95, 75));
        board.addEntity(entityFactory.createSnake(98, 79));

        // Add ladders
        board.addEntity(entityFactory.createLadder(4, 14));
        board.addEntity(entityFactory.createLadder(9, 31));
        board.addEntity(entityFactory.createLadder(20, 38));
        board.addEntity(entityFactory.createLadder(28, 84));
        board.addEntity(entityFactory.createLadder(40, 59));
        board.addEntity(entityFactory.createLadder(51, 67));
        board.addEntity(entityFactory.createLadder(63, 81));
        board.addEntity(entityFactory.createLadder(71, 91));

        // Choose a winning strategy
        WinningStrategy winningStrategy = new ExactThrowWinningStrategy();
        // WinningStrategy winningStrategy = new FirstToLandWinningStrategy();

        // --- Setup and Start Game ---
        Game game = new Game(board, players, winningStrategy);

        // Attach an observer for logging game events
        game.addObserver(new ConsoleLogger());

        System.out.println("Starting Snake and Ladders!");
        game.start();
    }
}