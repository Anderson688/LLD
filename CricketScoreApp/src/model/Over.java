package model;

import types.BallType;

import java.util.ArrayList;
import java.util.List;

public class Over {
    private final int overNumber;
    private final List<Ball> balls = new ArrayList<>();

    public Over(int overNumber) {
        this.overNumber = overNumber;
    }

    public void addBall(Ball ball) {
        this.balls.add(ball);
    }

    public int getLegalBallCount() {
        return Math.toIntExact(balls.stream().filter(ball -> ball.getBallType() == BallType.NORMAL).count());
    }

    public int getOverNumber() { return overNumber; }
    public List<Ball> getBalls() { return balls; }
}