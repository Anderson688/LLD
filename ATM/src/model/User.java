package model;

public class User {
    private final String username;
    private Card card;

    public User(String username, Card card) {
        this.username = username;
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

}