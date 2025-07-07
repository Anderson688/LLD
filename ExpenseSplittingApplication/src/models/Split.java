package models;

public class Split {
    private final User user;
    private double amount;
    private double percentage; // Only used for PERCENT split

    public Split(User user) {
        this.user = user;
    }

    // Getters
    public User getUser() {
        return user;
    }

    public double getAmount() {
        return amount;
    }

    public double getPercentage() {
        return percentage;
    }

    // Setters
    public void setAmount(double amount) { this.amount = amount; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
}