package factory;

import model.entities.BoardEntity;
import model.entities.Ladder;
import model.entities.Snake;

public class BoardEntityFactory {

    public BoardEntity createSnake(int start, int end) {
        return new Snake(start, end);
    }

    public BoardEntity createLadder(int start, int end) {
        return new Ladder(start, end);
    }
}