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
}
