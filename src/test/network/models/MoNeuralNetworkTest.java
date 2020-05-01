package network.models;

import components.network.MoNeuralNetwork;
import constants.MoFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoNeuralNetworkTest {


    MoNeuralNetwork moNeuralNetwork;

    @BeforeEach
    public void init() {
        moNeuralNetwork = new MoNeuralNetwork("[2,5,1]\n" +
                "[1,0] => [0]\n" +
                "[1,1] => [1]\n" +
                "[0,1] => [0]\n" +
                "[0,0] => [0]");
    }

    @Test
    public void calculateTest() {
//        // training neural network to recognize AND operation

        moNeuralNetwork.calculate(new float[]{0,0});
        float[] outputs = moNeuralNetwork.getOutputs();
        assertTrue(outputs[0]*100<50);

        moNeuralNetwork.calculate(new float[]{1,0});
        outputs = moNeuralNetwork.getOutputs();
        assertTrue(outputs[0]*100<50);

        moNeuralNetwork.calculate(new float[]{0,1});
        outputs = moNeuralNetwork.getOutputs();
        assertTrue(outputs[0]*100<50);

        moNeuralNetwork.calculate(new float[]{1,1});
        outputs = moNeuralNetwork.getOutputs();
        assertTrue(outputs[0]*100>50);

    }


    @Test
    public void mutateTest() {
        this.moNeuralNetwork.mutate(0.1f);
        this.moNeuralNetwork.mutate(1f);
    }

    @Test
    public void saveUploadNNTest() throws IOException {
        this.moNeuralNetwork.save("./data/nnTest.txt");
        this.moNeuralNetwork.upload("./data/nnTest.txt");
    }

    @Test
    public void crossOverTest() {
        MoNeuralNetwork neuralNetwork = new MoNeuralNetwork("[2,5,1]");
        MoNeuralNetwork.crossOver(neuralNetwork,this.moNeuralNetwork);
    }

    @Test
    public void constructorTest() {
        this.moNeuralNetwork = new MoNeuralNetwork();
    }




}
