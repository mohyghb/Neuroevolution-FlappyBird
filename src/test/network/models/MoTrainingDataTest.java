package network.models;

import components.network.models.MoTrainingData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoTrainingDataTest {

    MoTrainingData moTrainingData;

    @Test
    public void constructorTest() {
        moTrainingData = new MoTrainingData(new float[]{3,4,4},new float[]{2,1});
        assertEquals(3,moTrainingData.getInput()[0]);
        assertEquals(4,moTrainingData.getInput()[1]);
        assertEquals(4,moTrainingData.getInput()[2]);
        assertEquals(2,moTrainingData.getOutput()[0]);
        assertEquals(1,moTrainingData.getOutput()[1]);
    }


}
