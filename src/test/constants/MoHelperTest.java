package constants;

import constants.exceptions.IllegalConversion;
import constants.exceptions.IllegalRatio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class MoHelperTest {

    @Test
    public void testGetFloatResultNoException() {
        try {
            MoHelper.getFloat("2");
        } catch (IllegalConversion illegalConversion) {
            fail();
        }
    }

    @Test
    public void testGetFloatResultException() {
        try {
            MoHelper.getFloat("s");
            fail();
        } catch (IllegalConversion illegalConversion) {

        }
    }


    @Test
    public void testChangeRatioConversionException() {
        try {
            MoHelper.changeRatio("s","1");
            fail();
        } catch (IllegalConversion illegalConversion) {

        } catch (IllegalRatio illegalRatio) {
            fail();
        }
    }


    @Test
    public void testChangeRatioRatioException() {
        try {
            MoHelper.changeRatio("-1","1");
            fail();
        } catch (IllegalConversion illegalConversion) {
            fail();
        } catch (IllegalRatio illegalRatio) {

        }
    }

    @Test
    public void testChangeRatioNoException() {
        try {
            MoHelper.changeRatio("2","1");
        } catch (IllegalConversion illegalConversion) {
            fail();
        } catch (IllegalRatio illegalRatio) {
            fail();
        }
    }


}
