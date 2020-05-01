package components.network.models;

import constants.MoConstants;
import constants.MoFunctions;
import constants.MoHelper;
import constants.exceptions.IllegalConversion;
import constants.exceptions.IllegalRatio;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MoParseData {


    private static final String NEURON_PARSE_REGEX = "\\[.*\\]";
    private static final String TRAINING_DATA_REGEX = "\\[.*\\].*=>.*\\[.*\\]";
    private static final String ACTIVATION_FUNCTION_REGEX = "af";
    private static final String LEARNING_RATE_REGEX = "lr";
    private static final String LEARNING_ITERATIONS_REGEX = "i";

    private static final int NEURON = 0;
    private static final int TRAINING_DATA = 1;
    private static final int ACTIVATION_DATA = 2;
    private static final int LEARNING_RATE = 3;
    private static final int LEARNING_ITERATIONS = 4;
    private static final int ERROR = -1;


    private String data;
    private MoLayer[] layers;
    private ArrayList<MoTrainingData> trainingData;
    private int activationFunction;
    private int numberOfTrainingIterations;
    private float learningRate;


    /**
     * this class parses the data that is provided
     * to neural network
     * and puts it into layers and neurons accordingly
     */
    // REQUIRES: !data.isEmpty()
    // MODIFIES: this
    // EFFECTS: constructor
    public MoParseData(String data) {
        this.data = data;
        this.trainingData = new ArrayList<>();
        this.numberOfTrainingIterations = MoConstants.LEARNING_ITERATIONS;
        this.learningRate = MoConstants.LEARNING_RATE;
        this.start();
    }


    /**
     * starts parsing out the data
     */
    // MODIFIES: this
    // EFFECTS: parses each line of the data
    private void start() {
        // we first separate by each line
        String[] lines = this.data.split("\\n");
        // then for each line
        // we parse it
        for (int i = 0; i < lines.length; i++) {
            this.parseLine(this.classify(lines[i]),lines[i]);
        }
    }

    /**
     * parses the line
     * @param line
     * one line from the data provided to us
     * @return a number which classifies which function
     * needs to parse this line
     */
    // EFFECTS: returns an int which classifies
    // what type of function needs to be run on
    // this function
    private static int classify(String line) {
        if (Pattern.matches(TRAINING_DATA_REGEX,line)) {
            // line is an training data
            return TRAINING_DATA;
        } else if (Pattern.matches(NEURON_PARSE_REGEX,line)) {
            // then this is describing the neuron layout and length in
            // each layout
            return NEURON;
        }  else if (line.contains(ACTIVATION_FUNCTION_REGEX)) {
            // line specifies the activation function
            return ACTIVATION_DATA;
        } else if (line.contains(LEARNING_ITERATIONS_REGEX)) {
            return LEARNING_ITERATIONS;
        } else if (line.contains(LEARNING_RATE_REGEX)) {
            return LEARNING_RATE;
        }
        return ERROR;
    }


    /**
     * @param function
     * type of function that needs to be done on this line
     * @param line
     * the line of code which will be parsed
     */
    // REQUIRES: function!=null && !line.isEmpty()
    // MODIFIES: this
    // EFFECTS: performs function on line
    private void parseLine(int function, String line) {
        switch (function) {
            case NEURON:
                this.parseNeuron(line);
                break;
            case TRAINING_DATA:
                this.parseTraining(line);
                break;
            case ACTIVATION_DATA:
                this.parseActivationFunction(line);
                break;
            case LEARNING_RATE:
                this.learningRate = Float.parseFloat(MoFunctions.parseAfter(line,MoConstants.EQUAL));
                break;
            case LEARNING_ITERATIONS:
                this.numberOfTrainingIterations = Integer.parseInt(MoFunctions.parseAfter(line,MoConstants.EQUAL));
                break;
            default:
                break; }
    }



    // REQUIRES: !line.isEmpty()
    // MODIFIES: this
    // EFFECTS: parses out number of layers, and
    // number of neurons in each layer
    // and constructs them
    private void parseNeuron(String line) {
        // line should be in the format of
        // [2,3,4]
        int[] extractedNumbers = MoFunctions.getIntegerVersion(line);
        // we are going to have this much layers;
        // in = 2;
        // hid = 3;
        // out = 4;
        layers = new MoLayer[extractedNumbers.length];
        for (int i = 0; i < layers.length; i++) {
            if (i == 0) {
                // if i = 0;
                // then we pass null as the layer
                // since there is nothing before the input neurons
                // so its null to 2
                layers[0] = null;
            } else {
                // else we pass the neurons in previous layer
                // and in the current layer to MoLayer
                layers[i] = new MoLayer(extractedNumbers[i - 1],extractedNumbers[i]);
            }
        }

    }


    // REQUIRES: !line.isEmpty()
    // MODIFIES: this
    // EFFECTS: parses out the training data
    // that is provided by the user
    // and add it to the trainingData ArrayList
    private void parseTraining(String line) {
        String[] split = line.split("=>");

        if (split.length == 2) {
            // then we can add it
            // otherwise their code is wrong
            // split [0] is the inputs
            // split [1] is the expected output
            float[] inputs = MoFunctions.getFloatVersion(split[0]);
            float[] outputs = MoFunctions.getFloatVersion(split[1]);
            MoTrainingData moTrainingData = new MoTrainingData(inputs,outputs);
            // we add this training data to the rest of them
            this.trainingData.add(moTrainingData);
        }

    }

    // REQUIRES: !line.isEmpty()
    // MODIFIES: this
    // EFFECTS: parse an activation function
    // and set it to its field accordingly
    private void parseActivationFunction(String line) {
        String[] split = line.split("=");
        // first element should be af
        // split[1] = the name of the activation function
        if (split.length == 2) {
            // then we recognise which one of the function it is
            // but for now we only use sigmoid
            this.activationFunction = MoFunctions.SIGMOID_FUNCTION;
        }
    }







    // EFFECTS: returns neural network's layers
    public MoLayer[] getLayers() {
        return layers;
    }

    // MODIFIES: this
    // EFFECTS: sets this.layers to layers
//    public void setLayers(MoLayer[] layers) {
//        this.layers = layers;
//    }

    // EFFECTS: returns neural network's training data
    public ArrayList<MoTrainingData> getTrainingData() {
        return trainingData;
    }

    // MODIFIES: this
    // EFFECTS: sets this.trainingData to trainingData
//    public void setTrainingData(ArrayList<MoTrainingData> trainingData) {
//        this.trainingData = trainingData;
//    }

    // EFFECTS: returns neural network's activation function
    public int getActivationFunction() {
        return activationFunction;
    }

    // MODIFIES: this
    // EFFECTS: sets this.activationFunction to activationFunction
//    public void setActivationFunction(int activationFunction) {
//        this.activationFunction = activationFunction;
//    }

    // EFFECTS: returns neural network's training iterations
    public int getNumberOfTrainingIterations() {
        return numberOfTrainingIterations;
    }

    // MODIFIES: this
    // EFFECTS: sets this.numberOfTrainingIterations to numberOfTrainingIterations
//    public void setNumberOfTrainingIterations(int numberOfTrainingIterations) {
//        this.numberOfTrainingIterations = numberOfTrainingIterations;
//    }

    // EFFECTS: returns neural network's learning rate
    public float getLearningRate() {
        return learningRate;
    }

    // MODIFIES: this
    // EFFECTS: sets this.learningRate to learningRate
//    public void setLearningRate(float learningRate) {
//        this.learningRate = learningRate;
//    }

    private void setMaxMin(String text) {
        String[] split = text.split(",");
        try {
            MoHelper.changeRatio(split[0],split[1]);
        } catch (IllegalConversion illegalConversion) {
            illegalConversion.printStackTrace();
        } catch (IllegalRatio illegalRatio) {
            illegalRatio.printStackTrace();
        }
    }

}
