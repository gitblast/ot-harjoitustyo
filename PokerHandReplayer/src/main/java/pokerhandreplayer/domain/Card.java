package pokerhandreplayer.domain;

public class Card {
    private int value;
    private Suit suit;

    public Card(int value, Suit suit) {
        if (value > 14 || value < 1) {
            throw new IllegalArgumentException();
        }
        
        this.value = value;
        this.suit = suit;
    }

    @Override
    public String toString() {
        if (value == 10) {
            return "T of " + suit.toString();
        }
        
        if (value == 11) {
            return "J of " + suit.toString();
        }
        
        if (value == 12) {
            return "Q of " + suit.toString();
        }
        
        if (value == 13) {
            return "K of " + suit.toString();
        }
        
        if (value == 14 || value == 1) {
            return "A of " + suit.toString();
        }
        
        return value + " of " + suit.toString();
    }
    
    
}
