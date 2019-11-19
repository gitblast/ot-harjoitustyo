package replayertests.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import pokerhandreplayer.domain.Player;

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
}
