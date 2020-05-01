package constants;

import constants.exceptions.IllegalRatio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MoConstantsTest {



    @Test
    public void testMaxMin() {
        try {
            MoConstants.changeMaxMin(54,23);
        } catch (IllegalRatio illegalRatio) {
            fail();
        }
        assertEquals(54,MoConstants.MAX_WEIGHT);
        assertEquals(23,MoConstants.MIN_WEIGHT);
        try {
            MoConstants.changeMaxMin(1,-1);
        } catch (IllegalRatio illegalRatio) {
            fail();
        }
        assertEquals(1,MoConstants.MAX_WEIGHT);
        assertEquals(-1,MoConstants.MIN_WEIGHT);

        try {
            MoConstants.changeMaxMin(1,2);
            fail();
        } catch (IllegalRatio illegalRatio) {
        }
    }

    @Test
    public void testExtractNumbers() {
        String[] numbers = MoConstants.extractNumbers("[4,5,2]");
        assertEquals("4",numbers[0]);
        assertEquals("5",numbers[1]);
        assertEquals("2",numbers[2]);
        String[] numbers1 = MoConstants.extractNumbers("[-1]");
        assertEquals("-1",numbers1[0]);
        String[] numbers2 = MoConstants.extractNumbers("");
        assertEquals(1, numbers2.length);
        assertEquals("", numbers2[0]);
    }



}
