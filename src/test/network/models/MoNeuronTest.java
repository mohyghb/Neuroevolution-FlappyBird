package network.models;

import components.network.models.MoNeuron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MoNeuronTest {



    MoNeuron moNeuron;

    @BeforeEach
    public void init() {
        moNeuron = new MoNeuron(new float[]{11,22,33},3);
    }


    @Test
    public void hiddenOrOutputNeuronsTest() {
        moNeuron = new MoNeuron(new float[]{23,45,12},3);
        assertEquals(23, moNeuron.getWeights()[0]);
        assertEquals(45, moNeuron.getWeights()[1]);
        assertEquals(12, moNeuron.getWeights()[2]);
        assertEquals(moNeuron.getSavedWeights(), moNeuron.getWeights());
        assertEquals(3, moNeuron.getBias());
        assertEquals(0, moNeuron.getValue());
        assertEquals(1, moNeuron.getGradient());
    }


    @Test
    public void inputNeuronsTest() {
        moNeuron = new MoNeuron(3);
        assertEquals(3,moNeuron.getValue());
        assertEquals(-1,moNeuron.getBias());
        assertEquals(-1,moNeuron.getGradient());
        assertNull(moNeuron.getWeights());
        assertNull(moNeuron.getSavedWeights());
    }


    @Test
    public void updateWeightsTest() {
        moNeuron = new MoNeuron(new float[]{2,3,4},7);

        assertEquals(2,moNeuron.getWeights()[0]);
        assertEquals(3,moNeuron.getWeights()[1]);
        assertEquals(4,moNeuron.getWeights()[2]);

        moNeuron.setSavedWeights(new float[]{4,5,6});
        moNeuron.updateWeights();

        assertEquals(4,moNeuron.getWeights()[0]);
        assertEquals(5,moNeuron.getWeights()[1]);
        assertEquals(6,moNeuron.getWeights()[2]);
    }


    @Test
    public void mutateTest() {
        for(int i = 0; i < 2000; i++){
            this.moNeuron.mutateWeights();
        }
    }

    @Test
    public void downloadNeuronTest() {
        assertEquals("[0.0,1.0,3.0,11.0,22.0,33.0]",this.moNeuron.downloadNeuron());
    }

    @Test
    public void readNeuronTest() {
        this.moNeuron.readNeuron("[2,3,4,1,76]");
        assertEquals(2,this.moNeuron.getValue());
        assertEquals(3,this.moNeuron.getGradient());
        assertEquals(4,this.moNeuron.getBias());
        assertArrayEquals(new float[]{1,76},this.moNeuron.getWeights());
        this.moNeuron = new MoNeuron("[2,3,4,1,76]");
        this.moNeuron.setGradient(23f);
        this.moNeuron.setValue(2f);
    }

    @Test
    public void randomWeightsTest() {
        MoNeuron.getRandomWeights(2,5f,4f);
    }

}
