/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package replayertests.domain;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pokerhandreplayer.domain.Card;
import pokerhandreplayer.domain.StringHelper;
import pokerhandreplayer.domain.Suit;

public class StringHelperTest {
    
    StringHelper sh;
    
    @Test
    public void stackSizeIsConvertedCorrectly1() {
        sh = new StringHelper();
        String amount = "(1,527,889.12)";
        assertEquals(152788912, sh.getAmountInCentsFromString(amount));
    }
    
    @Test
    public void stackSizeIsConvertedCorrectly2() {
        String amount = "(1,527,889)";
        assertEquals(152788900, sh.getAmountInCentsFromString(amount));
    }
    
    @Test
    public void stackSizeIsConvertedCorrectly3() {
        String amount = "(0.00)";
        assertEquals(0, sh.getAmountInCentsFromString(amount));
    }
    
    @Test
    public void stackSizeIsConvertedCorrectly4() {
        String amount = "(0.01)";
        assertEquals(1, sh.getAmountInCentsFromString(amount));
    }
    
    @Test
    public void stackSizeIsConvertedCorrectly5() {
        String amount = "(889)";
        assertEquals(88900, sh.getAmountInCentsFromString(amount));
    }
    
    @Test
    public void stackSizeIsConvertedCorrectly6() {
        String amount = "889";
        assertEquals(88900, sh.getAmountInCentsFromString(amount));
    }
    
    @Test
    public void stackSizeFromLineReturnsCorrectly() {
        String line = "Seat 5: Hero (1,656.45)";
        assertEquals(165645, sh.getStackSizeFromLine(line));
    }
    
    @Test
    public void actionIndexReturnsCorrectly() {
        assertEquals(5, sh.getActionIndex("a string with five words"));
    }
    
    @Test
    public void getCardsStringWorks() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Card c = new Card(i, Suit.DIAMONDS);
            cards.add(c);
        }
        
        String correct = "[ A of DIAMONDS 2 of DIAMONDS 3 of DIAMONDS ]";
        assertEquals(correct, sh.getCardsString(cards));
    }
    
    @Test
    public void getCardsFromLineReturnsDepictedCards() {
        String cards = "Dealt to Hero: [X Ac As]";
        
        assertEquals("[ X A of CLUBS A of SPADES ]", sh.getCardsString(sh.getCardsFromLine(cards)));
    }
    
    @Test
    public void getNameFromLineWorksWithNamesWithSpaces() {
        String line1 = "Seat 1: Player1";
        String line2 = "Seat 1: Player Two (1,536.50)";
        String line3 = "Dealt to Player With Multiple Spaces: [X X 6c]";
        
        String names =  sh.getNameFromLine(line1) + sh.getNameFromLine(line2) + sh.getNameFromLine(line3);
        
        assertEquals("Player1Player TwoPlayer With Multiple Spaces", names);
    }
}
