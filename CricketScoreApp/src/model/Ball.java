package model;

import types.BallType;

public class Ball {
    private final int runs;
    private final BallType ballType;
    private final Dismissal dismissal; // Can be null if no wicket

    public Ball(int runs, BallType ballType, Dismissal dismissal) {
        this.runs = runs;
        this.ballType = ballType;
        this.dismissal = dismissal;
    }

    public int getRuns() { return runs; }
    public BallType getBallType() { return ballType; }
    public Dismissal getDismissal() { return dismissal; }
}