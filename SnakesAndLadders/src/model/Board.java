package model;

import model.entities.BoardEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Board {
    private final int size;
    private final Map<Integer, BoardEntity> entities;

    public Board(int size) {
        this.size = size;
        this.entities = new HashMap<>();
    }

    public void addEntity(BoardEntity entity) {
        entities.put(entity.getStart(), entity);
    }

    public int getSize() {
        return size;
    }

    public Optional<BoardEntity> getEntityAt(int position) {
        return Optional.ofNullable(entities.get(position));
    }
}