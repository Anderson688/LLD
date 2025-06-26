package core;

import java.util.Random;

public class Dice {

    private static Dice instance;

    private final Random random;

    private final int faces;

    private Dice(int faces) {
        this.faces = faces;
        this.random = new Random();
    }

    public static synchronized Dice getInstance(int faces) {
        if (instance == null) {
            instance = new Dice(faces);
        }
        return instance;
    }

    public int roll() {
        return random.nextInt(faces) + 1;
    }
}