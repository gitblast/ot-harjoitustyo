package pokerhandreplayer.domain;

public class Card {
    private int value;
    private Suit suit;
    
    public Card() { // constructor for unknown card
        this.value = -1;
        this.suit = null;
    }

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    
    
    /**
     * Converts a string representing the value of a card to integer, and returns it.
     * 
     * @param value
     * @return integer value of the parameter string
     */
    public static int intValue(String value) {
        if (value.equals("A")) {
            return 14;
        } else if (value.equals("K")) {
            return 13;
        } else if (value.equals("Q")) {
            return 12;
        } else if (value.equals("J")) {
            return 11;
        } else if (value.equals("T")) {
            return 10;
        } else {
            return Integer.parseInt(value);
        }
    }
    
    public static Suit stringToSuit(String suit) {
        if (suit.equals("s")) {
            return Suit.SPADES;
        }
        
        if (suit.equals("h")) {
            return Suit.HEARTS;
        }
        
        if (suit.equals("d")) {
            return Suit.DIAMONDS;
        }
        
        if (suit.equals("c")) {
            return Suit.CLUBS;
        }
        
        return null;
    }

    @Override
    public String toString() {
        if (value == -1) {
            return "X";
        } else if (value == 10) {
            return "T of " + suit.toString();
        } else if (value == 11) {
            return "J of " + suit.toString();
        } else if (value == 12) {
            return "Q of " + suit.toString();
        } else if (value == 13) {
            return "K of " + suit.toString();
        } else if (value == 14 || value == 1) {
            return "A of " + suit.toString();
        }
        
        return value + " of " + suit.toString();
    }
    
    
}
