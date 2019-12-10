package replayertests.domain;

import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import pokerhandreplayer.domain.Card;
import pokerhandreplayer.domain.Player;
import pokerhandreplayer.domain.Suit;

public class PlayerTest {
    
    Player player;
    
    @Before
    public void setUp() {
        player = new Player("TestPlayer", 555);
    }
    
    @Test
    public void negativeStackSizeIsTurnedToZero() {
        Player p = new Player("Test", -666);
        assertEquals(0, p.getStackSize());
    }
    
    @Test
    public void playerStackCanBeReduced() {
        player.reduceStack(100);
        assertEquals(555-100, player.getStackSize());
    }
    
    @Test
    public void validReductionReturnsTrue() {
        assertEquals(true, player.reduceStack((100)));
    }
    
    @Test
    public void invalidReductionReturnsFalse() {
        assertEquals(false, player.reduceStack(666));
    }
    
    @Test
    public void newPlayerHasNoCards() {
        assertEquals(0, player.getCards().size());
    }
    
    @Test
    public void cardsCanBeAdded() {
        Card c = new Card(10, Suit.DIAMONDS);
        
        player.addCard(c);
        assertEquals(1, player.getCards().size());
    }
    
    @Test
    public void getNameReturnsName() {
        assertEquals("TestPlayer", player.getName());
    }
    
    @Test
    public void toStringWorksCorrectly() {
        assertEquals("TestPlayer (555.0 cent)", player.toString());
    }
    
    @Test
    public void broaderConstructorWorks() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Card c = new Card(i, Suit.DIAMONDS);
            cards.add(c);
        }
        
        Player p = new Player("Test", 50, cards, 10, "testing");
        Player d = new Player("Test", -50, cards, 10, "testing");
        assertEquals("Test (50.0 cent)Test (0.0 cent)", p.toString() + d.toString());
    }
    
    @Test
    public void setCardsWorks() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Card c = new Card(i, Suit.DIAMONDS);
            cards.add(c);
        }
        
        player.setCards(cards);
        
        assertEquals(cards, player.getCards());
    }
    
    @Test
    public void setLabelWorks() {
        player.setLabel("LABEL");
        assertEquals("LABEL", player.getLabel());
    }
    
    @Test
    public void deepcopyingReturnsDeepcopy() {
        for (int i = 1; i < 4; i++) {
            Card c = new Card(i, Suit.DIAMONDS);
            player.addCard(c);
        }
        
        ArrayList<Card> cards = player.getCards();
        ArrayList<Card> deepCopy = player.getCardsDeepCopy();
        
        assertThat(deepCopy, not(cards));
    }
}
