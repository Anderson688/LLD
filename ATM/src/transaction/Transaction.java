package transaction;

import model.Card;

public interface Transaction {
    void execute(Card card, int amount);
}