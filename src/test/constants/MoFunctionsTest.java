package constants;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MoFunctionsTest {


    @Test
    public void randomFloatTest(){
        for(int i = 0; i < 2000; i++){
            float random = MoFunctions.getRandom(153f,2f);
            assertTrue(random<=153f && random>= 2f);
        }
    }


    @Test
    public void randomDoubleTest(){
        for(int i = 0; i < 2000; i++){
            double random = MoFunctions.getRandom(153.0,2.0);
            assertTrue(random<=153 && random>= 2);
        }
    }


    @Test
    public void randomIntTest(){
        for(int i = 0; i < 2000; i++){
            int random = MoFunctions.getRandom(153,2);
            assertTrue(random<=153 && random>= 2);
        }
    }



    @Test
    public void getIntegerVersionTest() {
        int[] numbers = MoFunctions.getIntegerVersion("[2,3,14]");
        assertEquals(2,numbers[0]);
        assertEquals(3,numbers[1]);
        assertEquals(14,numbers[2]);

        numbers = MoFunctions.getIntegerVersion("[652]");
        assertEquals(652,numbers[0]);

        numbers = MoFunctions.getIntegerVersion("");
        assertEquals(0,numbers.length);
    }

    @Test
    public void getFloatVersionTest() {
        float[] numbers = MoFunctions.getFloatVersion("[2,3,14]");
        assertEquals(2,numbers[0]);
        assertEquals(3,numbers[1]);
        assertEquals(14,numbers[2]);

        numbers = MoFunctions.getFloatVersion("[652]");
        assertEquals(652,numbers[0]);

        numbers = MoFunctions.getFloatVersion("");
        assertEquals(0,numbers.length);
    }

    @Test
    public void sigmoidFunctionTest() {
        assertEquals((float)(1 / (1 + Math.pow(Math.E, -2))), MoFunctions.sigmoidFunction(2));
    }

    @Test
    public void activationFunctionTest() {
        assertEquals((float)(1 / (1 + Math.pow(Math.E, -2))), MoFunctions.activationFunction(MoFunctions.SIGMOID_FUNCTION,2));
        assertEquals(5f, MoFunctions.activationFunction(234,5));
    }

    @Test
    public void desktopPathTest() {
        assertEquals(System.getProperty("user.home") + "/Desktop/" + "hello" + ".txt", MoFunctions.desktopPath("hello"));
    }


    @Test
    public void saveReadFileTest() throws IOException {
        MoFunctions.saveFile("random content",MoFunctions.desktopPath("readWriteTest"));
        assertEquals("random content\n",MoFunctions.readFile(MoFunctions.desktopPath("readWriteTest")));
    }

    @Test
    public void parseAfterTest() {
        assertEquals("hello",MoFunctions.parseAfter("qowe =hello","="));
        assertEquals("",MoFunctions.parseAfter("qowe hello","="));
    }




}
