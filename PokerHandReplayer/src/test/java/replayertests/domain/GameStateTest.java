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
import pokerhandreplayer.domain.GameState;
import pokerhandreplayer.domain.Player;

/**
 *
 * @author jmammela
 */
public class GameStateTest {
    
    GameState gs;
    
    @Before
    public void setUp() {
        String[] names = {"FirstPlayer", "Second Player", "Third test player"};
        ArrayList<Player> players = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            players.add(new Player(names[i], i));
        }
        
        gs = new GameState(players, 999);
    }
    
    @Test
    public void potReturnsCorretly() {
        assertEquals(999, gs.getPot());
    }
    
    @Test
    public void getPlayersReturnsCorrectAmountOfPlayers() {
        assertEquals(3, gs.getPlayers().size());
        
    }
    
    @Test
    public void toStringWorksCorrectly() {        
        gs.getPlayers().get(0).setHasTurn(true);
        gs.getPlayers().get(0).setBet(1);
        System.out.println(gs);
        assertEquals("FirstPlayer (0.0 cent) [ ]  1 *<\n" +
            "Second Player (1.0 cent) [ ]  \n" +
            "Third test player (2.0 cent) [ ]  \n" +
            "\n" +
            "POT: 999\n", gs.toString());
        
    }
}
