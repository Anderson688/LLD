package core;

import model.Player;

public interface GameObserver {
    void onPlayerMove(Player player, int roll, int startPos, int endPos);
    void onPlayerWin(Player player);
    void onBoardEntityEncounter(Player player, String entityName, int startPos, int endPos);
}