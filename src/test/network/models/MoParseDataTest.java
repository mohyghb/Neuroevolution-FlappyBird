package network.models;//import org.junit.jupiter.api.Test;

import components.network.models.MoParseData;
import constants.MoFunctions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoParseDataTest {


    MoParseData moParseData;

    @Test
    public void trainingIterationsTest(){
        this.moParseData = new MoParseData("i = 200");
        assertEquals(200, moParseData.getNumberOfTrainingIterations());
        this.moParseData = new MoParseData("i = -23");
        assertEquals(-23,moParseData.getNumberOfTrainingIterations());
        this.moParseData = new MoParseData("i = 0");
        assertEquals(0, moParseData.getNumberOfTrainingIterations());
    }


    @Test
    public void learningRateTest(){
        this.moParseData = new MoParseData("lr = 0.03");
        assertEquals(0.03f, moParseData.getLearningRate());
        this.moParseData = new MoParseData("lr = -0.2");
        assertEquals(-0.2f, moParseData.getLearningRate());
        this.moParseData = new MoParseData("lr = 0");
        assertEquals(0f, moParseData.getLearningRate());
    }

    @Test
    public void neuronLayerTest() {
        this.moParseData = new MoParseData("[2,3,4]");
        assertEquals(3,moParseData.getLayers().length);
        assertNull(moParseData.getLayers()[0]);
        assertEquals(3,moParseData.getLayers()[1].getNeurons().length);
        assertEquals(4,moParseData.getLayers()[2].getNeurons().length);

        this.moParseData = new MoParseData("[5,4,7,2]");
        assertEquals(4,moParseData.getLayers().length);
        assertNull(moParseData.getLayers()[0]);
        assertEquals(4,moParseData.getLayers()[1].getNeurons().length);
        assertEquals(7,moParseData.getLayers()[2].getNeurons().length);
        assertEquals(2,moParseData.getLayers()[3].getNeurons().length);

    }

    @Test
    public void trainingDataTest() {
        this.moParseData = new MoParseData("[2,2] => [1]\n[1,12]=>[2]");
        assertEquals(2,moParseData.getTrainingData().size());
        assertArrayEquals(new float[]{2,2},moParseData.getTrainingData().get(0).getInput());
        assertArrayEquals(new float[]{1},moParseData.getTrainingData().get(0).getOutput());
        assertArrayEquals(new float[]{1,12},moParseData.getTrainingData().get(1).getInput());
        assertArrayEquals(new float[]{2},moParseData.getTrainingData().get(1).getOutput());
    }



    @Test
    public void activationFunctionTest() {
        this.moParseData = new MoParseData("af = sigmoid");
        assertEquals(MoFunctions.SIGMOID_FUNCTION, this.moParseData.getActivationFunction());
        this.moParseData = new MoParseData("");
    }



}
