package catalog.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cinema {
    private final String id;
    private final String name;
    private final String city;
    private final List<Screen> screens;

    public Cinema(String name, String city) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.city = city;
        this.screens = new ArrayList<>();
    }

    public void addScreen(Screen screen) {
        this.screens.add(screen);
    }

    public List<Screen> getScreens() {
        return screens;
    }
}