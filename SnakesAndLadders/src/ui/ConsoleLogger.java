package ui;

import core.GameObserver;
import model.Player;

public class ConsoleLogger implements GameObserver {
    @Override
    public void onPlayerMove(Player player, int roll, int startPos, int endPos) {
        System.out.printf("%s rolled a %d and moved from %d to %d%n",
                player.getName(), roll, startPos, endPos);
    }

    @Override
    public void onPlayerWin(Player player) {
        System.out.println("====================================");
        System.out.printf("!!! %s has won the game !!!%n", player.getName());
        System.out.println("====================================");
    }

    @Override
    public void onBoardEntityEncounter(Player player, String entityName, int startPos, int endPos) {
        System.out.printf("%s encountered a %s at position %d and moved to %d%n",
                player.getName(), entityName, startPos, endPos);
    }
}