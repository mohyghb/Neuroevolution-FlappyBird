package components.network.models;


import constants.MoConstants;
import constants.MoFunctions;

public class MoLayer {


    public static final String SEP_KEY = "\nlayer";
    public static final String LAYER = "layer";


    /**
     * a layer inside a neural network which contains:
     * - neurons
     */

    private MoNeuron[] neurons;

    /**
     * constructor for hidden/output layers
     * @param prevNeurons
     * number of neurons in the previous layer
     * @param numNeurons
     * number of neurons in this layer
     *
     */
     // MODIFIES: this
     // EFFECTS: construct a hidden/output layer for neural network
    public MoLayer(int prevNeurons, int numNeurons) {
        this.neurons = this.getNeurons(prevNeurons,numNeurons);
    }


    /**
     * constructor for input neurons of this layer
     * these inputs are converted into neurons for this layer
     * @param inputs
     * inputs given by user or other classes to be set
     * as an input neuron
     */
     // MODIFIES: this
     // EFFECTS: construct an input layer for neural network
    public MoLayer(float[] inputs) {
        this.neurons = this.getNeurons(inputs);
    }



    // for combination
    public MoLayer() {

    }

    // for when reading files
    public MoLayer(String data) {
        this.readLayer(data);
    }


    /**
     *
     * @param prevNumber
     * length of the previous layer
     * @param numNeurons
     * length of the current layer
     * @return
     * an array of MoNeurons in which all the neurons in the next layer
     * will have same amount of weights as there are neurons in the current layer
     */
    // EFFECTS: returns an array of MoNeurons in which all the neurons in the next layer
    // will have same amount of weights as there are neurons in the current layer
    private MoNeuron[] getNeurons(int prevNumber, int numNeurons) {
        MoNeuron[] moNeurons = new MoNeuron[numNeurons];

        for (int i = 0;i < moNeurons.length; i++) {
            moNeurons[i] = new MoNeuron(MoNeuron.getRandomWeights(prevNumber,
                    MoConstants.MAX_WEIGHT,
                    MoConstants.MIN_WEIGHT),
                    MoFunctions.getRandom(1f,0f));
        }

        return moNeurons;
    }


    /**
     *
     * @param inputs
     * inputs of the neurons
     * @return
     * returns a list of MoNeurons that have the corresponding
     * inputs
     */
    // EFFECTS: returns a list of MoNeurons that have the corresponding inputs
    private MoNeuron[] getNeurons(float[] inputs) {
        MoNeuron[] moNeurons = new MoNeuron[inputs.length];
        for (int i = 0; i < moNeurons.length; i++) {
            moNeurons[i] = new MoNeuron(inputs[i]);
        }
        return moNeurons;
    }

    /**
     *
     * @return the current neurons of this layer
     */
    // EFFECTS: returns the neurons inside this layer
    public MoNeuron[] getNeurons() {
        return this.neurons;
    }



    public static MoLayer getCombination(MoLayer l1,MoLayer l2) {
        MoLayer moLayer = new MoLayer();
        MoNeuron[] combine = new MoNeuron[l1.neurons.length];
        for (int i = 0; i < combine.length; i++) {
            if (i % 2 == 0) {
                combine[i] = l1.neurons[i];
            } else {
                combine[i] = l2.neurons[i];
            }
        }
        moLayer.neurons = combine;
        return moLayer;
    }



    public String downloadLayer() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.neurons.length; i++) {
            stringBuilder.append(this.neurons[i].downloadNeuron());
            if (i < this.neurons.length - 1) {
                stringBuilder.append("\n");
            }
        }
        stringBuilder.append(SEP_KEY);
        return stringBuilder.toString();
    }


    public void readLayer(String data) {
        if (data.startsWith("\n")) {
            data = data.substring(1);
        }
        String[] split = data.split("\n");
        this.neurons = new MoNeuron[split.length];
        for (int i = 0; i < split.length; i++) {
            this.neurons[i] = new MoNeuron(split[i]);
        }
    }


}
