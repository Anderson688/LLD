package model;

import stats.CareerStats;

public class Player {
    private final String name;
    private final CareerStats careerStats;

    public Player(String name) {
        this.name = name;
        this.careerStats = new CareerStats();
    }

    public String getName() {
        return name;
    }

    public CareerStats getCareerStats() {
        return careerStats;
    }
}