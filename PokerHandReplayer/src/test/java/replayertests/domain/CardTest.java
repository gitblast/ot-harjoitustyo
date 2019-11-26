/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package replayertests.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pokerhandreplayer.domain.Card;
import pokerhandreplayer.domain.Suit;

public class CardTest {
    
    @Before
    public void setUp() {
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void cantCreateHigherThan14() {
        Card c = new Card(15, Suit.DIAMONDS);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void cantCreateLowerThanOne() {
        Card c = new Card(0, Suit.DIAMONDS);
    }
    
    // stringToSuit -testing:
    
    @Test
    public void stringToSuitWorksWithDiamonds() {
        assertEquals(Suit.DIAMONDS, Card.stringToSuit("d"));
    }
    
    @Test
    public void stringToSuitWorksWithSpades() {
        assertEquals(Suit.SPADES, Card.stringToSuit("s"));
    }
    
    @Test
    public void stringToSuitWorksWithHearts() {
        assertEquals(Suit.HEARTS, Card.stringToSuit("h"));
    }
    
    @Test
    public void stringToSuitWorksWithClubs() {
        assertEquals(Suit.CLUBS, Card.stringToSuit("c"));
    }
    
    @Test
    public void invalidParameterReturnsNull() {
        assertEquals(null, Card.stringToSuit("p"));
    }
    
    // intValue -testing:
    
    @Test
    public void intValueReturnsNumericalValue() {
        int sum = Card.intValue("A")
                + Card.intValue("K")
                + Card.intValue("Q")
                + Card.intValue("J")
                + Card.intValue("T")
                + Card.intValue("9");
        
        assertEquals(14 + 13 + 12 + 11 + 10 + 9, sum);
    }
    
    // toString -testing:
    
    @Test
    public void valueOfOneToStringReturnsA() {
        Card smallAce = new Card(1, Suit.DIAMONDS);
        assertEquals("A of DIAMONDS", smallAce.toString());
    }
    
    @Test
    public void valueOf14ToStringReturnsA() {
        Card smallAce = new Card(14, Suit.DIAMONDS);
        assertEquals("A of DIAMONDS", smallAce.toString());
    }
    
    @Test
    public void valueOf13ToStringReturnsK() {
        Card smallAce = new Card(13, Suit.DIAMONDS);
        assertEquals("K of DIAMONDS", smallAce.toString());
    }
    
    @Test
    public void valueOf12ToStringReturnsQ() {
        Card smallAce = new Card(12, Suit.DIAMONDS);
        assertEquals("Q of DIAMONDS", smallAce.toString());
    }
    
    @Test
    public void valueOf11ToStringReturnsJ() {
        Card smallAce = new Card(11, Suit.DIAMONDS);
        assertEquals("J of DIAMONDS", smallAce.toString());
    }
    
    @Test
    public void valueOf10ToStringReturnsT() {
        Card tenner = new Card(10, Suit.DIAMONDS);
        assertEquals("T of DIAMONDS", tenner.toString());
    }
    
    @Test
    public void cardCreatedWithEmptyConstructorToStringReturnsX() {
        Card c = new Card();
        assertEquals("X", c.toString());
    }
    
    @Test
    public void cardToStringReturnsValueAndSuit() {
        Card c = new Card(9, Suit.DIAMONDS);
        assertEquals("9 of DIAMONDS", c.toString());
    }
}
