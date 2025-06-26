package core;

import model.Board;
import model.Player;
import strategy.WinningStrategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Game {
    private final Board board;
    private final Queue<Player> players;
    private final Dice dice;
    private final WinningStrategy winningStrategy;
    private final List<GameObserver> observers = new ArrayList<>();

    public Game(Board board, List<Player> playerList, WinningStrategy winningStrategy) {
        this.board = board;
        this.players = new LinkedList<>(playerList);
        this.dice = Dice.getInstance(6); // Standard 6-sided die
        this.winningStrategy = winningStrategy;
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void start() {
        while (true) {
            Player currentPlayer = players.poll();
            int roll = dice.roll();

            int startPos = currentPlayer.getPosition();
            int nextPos = winningStrategy.getNextPosition(currentPlayer, roll, board.getSize());

            notifyPlayerMove(currentPlayer, roll, startPos, nextPos);

            // Check for snake or ladder
            int finalPos = board.getEntityAt(nextPos)
                    .map(entity -> {
                        notifyBoardEntityEncounter(currentPlayer, entity.getClass().getSimpleName(), nextPos, entity.getEnd());
                        return entity.getEnd();
                    })
                    .orElse(nextPos);

            currentPlayer.setPosition(finalPos);

            if (winningStrategy.hasWon(currentPlayer, board.getSize())) {
                notifyPlayerWin(currentPlayer);
                break;
            }

            players.add(currentPlayer); // Add player back to the end of the queue
        }
    }

    private void notifyPlayerMove(Player player, int roll, int startPos, int endPos) {
        for (GameObserver observer : observers) {
            observer.onPlayerMove(player, roll, startPos, endPos);
        }
    }

    private void notifyPlayerWin(Player player) {
        for (GameObserver observer : observers) {
            observer.onPlayerWin(player);
        }
    }

    private void notifyBoardEntityEncounter(Player player, String entityName, int startPos, int endPos) {
        for (GameObserver observer : observers) {
            observer.onBoardEntityEncounter(player, entityName, startPos, endPos);
        }
    }
}