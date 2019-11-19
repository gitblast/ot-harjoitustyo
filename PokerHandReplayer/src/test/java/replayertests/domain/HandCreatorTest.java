package replayertests.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pokerhandreplayer.domain.HandCreator;

/**
 *
 * @author jmammela
 */
public class HandCreatorTest {
    HandCreator hc;
    
    @Before
    public void setUp() {
        hc = new HandCreator();
    }

    @Test
    public void stackSizeIsConvertedCorrectly1() {
        String amount = "(1,527,889.12)";
        assertEquals(152788912, hc.getStackSizeFromString(amount));
    }
    
    @Test
    public void stackSizeIsConvertedCorrectly2() {
        String amount = "(1,527,889)";
        assertEquals(152788900, hc.getStackSizeFromString(amount));
    }
    
    @Test
    public void stackSizeIsConvertedCorrectly3() {
        String amount = "(0.00)";
        assertEquals(0, hc.getStackSizeFromString(amount));
    }
    
    @Test
    public void stackSizeIsConvertedCorrectly4() {
        String amount = "(0.01)";
        assertEquals(1, hc.getStackSizeFromString(amount));
    }
    
    @Test
    public void stackSizeIsConvertedCorrectly5() {
        String amount = "(889)";
        assertEquals(88900, hc.getStackSizeFromString(amount));
    }
}
