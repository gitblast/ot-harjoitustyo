package replayertests.domain;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pokerhandreplayer.domain.Card;
import pokerhandreplayer.domain.HandCreator;
import pokerhandreplayer.domain.Player;
import pokerhandreplayer.domain.Suit;

/**
 *
 * @author jmammela
 */
public class HandCreatorTest {
    HandCreator hc;
    
    @Before
    public void setUp() {
        hc = new HandCreator();
        ArrayList<String> lines = new ArrayList<>();
        lines.add("Seat 1: Player1 (666)");
        lines.add("Seat 2: Player2 (777.00)");
        lines.add("Seat 3: Player3 (999)");
        hc.buildInitialState(lines);
    }
    
    @Test
    public void initialStateIsBuiltWithCorrectAmountOfPlayers() {
        ArrayList<String> lines = new ArrayList<>();
        int playersPlusStates = 3 + 1;
        assertEquals(playersPlusStates, hc.getPlayerNames().size() + hc.getStates().size());
    }
    
    @Test
    public void initialStateOnlyAddsPlayersIfLineStartsWithSeat() {
        HandCreator hc2 = new HandCreator();
        ArrayList<String> lines = new ArrayList<>();
        lines.add("1: Player1 (666)");
        lines.add("2: Player2 (777.00)");
        lines.add("3: Player3 (999)");
        
        hc2.buildInitialState(lines);
        
        assertEquals(0, hc2.getStates().get(0).getPlayers().size());
    }
    
    @Test
    public void initialStateHasCorrectPlayers() {
        ArrayList<Player> players = hc.getStates().get(0).getPlayers();
        String names = players.get(0).getName() + players.get(1).getName() + players.get(2).getName();
        assertEquals("Player1Player2Player3", names);
    }
    
    @Test
    public void createStateAddsGameStateWithCorrectPot() {
        hc.createState("Player1", "test", 20, 20, new ArrayList<Card>());
        assertEquals(20, hc.getStates().get(0).getPot() + 20);
    }
    
    @Test
    public void initialDoesntAddDuplicates() {
        HandCreator hc2 = new HandCreator();
        ArrayList<String> lines = new ArrayList<>();
        lines.add("Seat 1: Player1 (666)");
        lines.add("Seat 2: Player1 (777.00)");
        hc2.buildInitialState(lines);
        
        assertEquals(1, hc2.getStates().get(0).getPlayers().size());
    }
    
    @Test
    public void createStateDecreasesPlayerStackSizeWithAmountOfBet() {
        hc.createState("Player1", "test", 20, 20, new ArrayList<Card>());
        assertEquals(66580, hc.getStates().get(1).getPlayers().get(0).getStackSize());
    }
    
    @Test
    public void createStateAddsCardsToCorrectPlayer() {
        ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(new Card(1, Suit.DIAMONDS)));
        hc.createState("Player1", "test", 20, 20, cards);
        int initialSize = hc.getStates().get(0).getPlayers().get(0).getCards().size();
        assertEquals(initialSize + 1, hc.getStates().get(1).getPlayers().get(0).getCards().size());
    }
    
    @Test
    public void calculateProfitCalculatesCorrectly() {
        hc.createState("Player1", "test", 20, 0, new ArrayList<Card>());
        int profit = hc.calculateProfit("Player1", 0);
        assertEquals(-20, profit);
    }
    
    @Test
    public void setNewCardsAddsCorrectCards() {
        hc.setNewCards("Dealt to Player1: [9c]");
        Card c = hc.getStates().get(1).getPlayers().get(0).getCards().get(0);
        assertEquals("9 of CLUBS", c.toString());
    }
    
    @Test
    public void setNewCardsAddsCorrectCardsWhenMultiple() {
        hc.setNewCards("Dealt to Player1: [9c Th]");
        Card c = hc.getStates().get(1).getPlayers().get(0).getCards().get(1);
        assertEquals("T of HEARTS", c.toString());
    }
    
    @Test
    public void setNewPotCalculatesNewPotCorrectly() {
        hc.createState("Player1", "test", 20, 0, new ArrayList<Card>());
        hc.createState("Player2", "fold", 60, 0, new ArrayList<Card>());
        hc.createState("Player3", "folded", 90, 0, new ArrayList<Card>());
        hc.setNewPot();
        
        assertEquals(20 + 60 + 90, hc.getStates().get(4).getPot());
    }
    
    @Test
    public void addPlayerActionCreatesCorrectState1() {
        int initialStack = hc.getStates().get(0).getPlayers().get(0).getStackSize();
        hc.addPlayerAction("Player1: posts ante 8", "Player1");
        int after = hc.getStates().get(1).getPlayers().get(0).getStackSize();
        assertEquals(initialStack - 800, after);
    }
    
    @Test
    public void addPlayerActionCreatesCorrectState2() {
        int initialStack = hc.getStates().get(0).getPlayers().get(0).getStackSize();
        hc.addPlayerAction("Player1: brings in for 10", "Player1");
        int after = hc.getStates().get(1).getPlayers().get(0).getStackSize();
        assertEquals(initialStack - 1000, after);
    }
//    
//    @Test
//    public void addPlayerActionCreatesCorrectState3() {
//        int initialStack = hc.getStates().get(0).getPlayers().get(0).getStackSize();
//        hc.addPlayerAction("Player1: posts small blind 2", "Player1");
//        int after = hc.getStates().get(1).getPlayers().get(0).getStackSize();
//        assertEquals(initialStack - 200, after);
//    }
//    
//    @Test
//    public void addPlayerActionCreatesCorrectState4() {
//        int initialStack = hc.getStates().get(0).getPlayers().get(0).getStackSize();
//        hc.addPlayerAction("Player1: posts big blind 4", "Player1");
//        int after = hc.getStates().get(1).getPlayers().get(0).getStackSize();
//        assertEquals(initialStack - 400, after);
//    }
}