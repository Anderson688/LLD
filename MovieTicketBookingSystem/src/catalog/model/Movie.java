package catalog.model;

import java.util.UUID;

public class Movie {
    private final String id;
    private final String title;
    private final int durationInMinutes;

    public Movie(String title, int durationInMinutes) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.durationInMinutes = durationInMinutes;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}