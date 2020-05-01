package constants;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoConverterTest {


    @Test
    public void convertToIntegerTest() {
        int[] result = MoConverter.convertToIntegers(new ArrayList<Integer>(){{add(2);add(3);add(-1);}});
        assertEquals(2,result[0]);
        assertEquals(3,result[1]);
        assertEquals(-1,result[2]);
        result = MoConverter.convertToIntegers(new ArrayList<>());
        assertEquals(0,result.length);
        result = MoConverter.convertToIntegers(new ArrayList<Integer>(){{add(45);}});
        assertEquals(45,result[0]);
    }

    @Test
    public void convertToFloatTest() {
        float[] result = MoConverter.convertToFloats(new ArrayList<Float>(){{add(2f);add(3f);add(-1f);}});
        assertEquals(2f,result[0]);
        assertEquals(3f,result[1]);
        assertEquals(-1f,result[2]);
        result = MoConverter.convertToFloats(new ArrayList<>());
        assertEquals(0,result.length);
        result = MoConverter.convertToFloats(new ArrayList<Float>(){{add(45f);}});
        assertEquals(45f,result[0]);
    }






}
