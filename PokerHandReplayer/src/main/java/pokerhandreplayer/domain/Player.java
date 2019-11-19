
package pokerhandreplayer.domain;

import java.util.ArrayList;

public class Player {
    private String name;
    private int stackSize; // in cents
    private ArrayList<Card> cards;
    
    // mieti kannattaako tehä pelaajakohtanen lista burnatuille/vaihetuille korteille vetopeleihi

    public Player(String name, int stackSize) {
        this.name = name;
        this.stackSize = stackSize < 0 ? 0 : stackSize;
        this.cards = new ArrayList<>();
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

    public String getName() {
        return name;
    }

    public int getStackSize() {
        return stackSize;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
    
    public void addCard(Card card) {
        this.cards.add(card);
    }

    @Override
    public String toString() {
        return this.name + " (" + this.stackSize/100 + " €)";
    }
    
    
}
