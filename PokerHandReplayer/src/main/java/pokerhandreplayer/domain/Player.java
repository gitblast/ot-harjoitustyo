
package pokerhandreplayer.domain;

import java.util.ArrayList;

public class Player {
    private String name;
    private int stackSize; // in cents
    private ArrayList<Card> cards;
    private String label; // bets, calls, raises, etc
    private int bet; // in cents
    private boolean hasTurn;
    private boolean hasFolded;

    public Player(String name, int stackSize) {
        this.name = name;
        this.stackSize = stackSize < 0 ? 0 : stackSize;
        this.cards = new ArrayList<>();
        this.bet = 0;
        this.label = "";
        this.hasTurn = false;
        this.hasFolded = false;
    }
    
    public Player(String name, int stackSize, ArrayList<Card> cards, int bet, String label) {
        this.name = name;
        this.stackSize = stackSize < 0 ? 0 : stackSize;
        this.cards = cards;
        this.bet = bet;
        this.label = label;
        this.hasTurn = false;
    }

    public void setHasFolded(boolean hasFolded) {
        this.hasFolded = hasFolded;
    }
    
    public boolean hasFolded() {
        return hasFolded;
    }
    
    public Boolean reduceStack(int amount) {
        /**
         * Reduce player's stack size by the amount given as a parameter. If amount is greater than current stackSize, do nothing and return false.
         * @param amount the amount to be reduced from player's stack
         * @return false if amount is greater than current stack size, otherwise true
         */
        if (this.stackSize < amount) {
            return false;
        }
        
        this.stackSize -= amount;
        return true;
    }

    public void setHasTurn(boolean hasTurn) {
        this.hasTurn = hasTurn;
    }

    public boolean hasTurn() {
        return hasTurn;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
    
    
    
    public ArrayList<Card> getCardsDeepCopy() {
        ArrayList<Card> newCards = new ArrayList<>();
        for (Card c : cards) {
            newCards.add(new Card(c.getValue(), c.getSuit()));
        }
        return newCards;
    }

    public String getName() {
        return name;
    }

    public int getStackSize() {
        return stackSize;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getBet() {
        return bet;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
    
    public void addCard(Card card) {
        this.cards.add(card);
    }

    @Override
    public String toString() {
        return this.name + " (" + Double.valueOf(this.stackSize) + " cent)";
    }
    
    
}
