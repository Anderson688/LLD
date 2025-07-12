package model;

public class User {
    private final String userId;
    private final String location;

    public User(String userId, String location) {
        this.userId = userId;
        this.location = location;
    }

    public String getUserId() { return userId; }
    public String getLocation() { return location; }
}