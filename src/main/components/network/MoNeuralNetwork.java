package components.network;

import components.network.models.MoLayer;
import components.network.models.MoNeuron;
import components.network.models.MoParseData;
import components.network.models.MoTrainingData;
import constants.MoFunctions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * https://www.youtube.com/watch?v=1DIu7D98dGo
 * this video was used heavily to help me create the back propagation part.
 * Other parts were inspired by it as well. NO COPY AND PASTE WERE DONE! All of
 * the classes and methods were written by me
 * only the backProp Method in this class were inspired by this video
 * also my code is MUCH more organized, better written, and faster than the one in the video
 *
 *
 */

public class MoNeuralNetwork {






    private MoLayer[] layers;
    private ArrayList<MoTrainingData> trainingData;
    private MoParseData moParseData;
    private float learningRate;
    private int numberOfTrainingIterations;
    private int activationFunction;

    /**
     * data is an String containing all we need to create a neural network:
     *
     * [2 , 3, 3, 1]
     * [2,2] => [0]
     * [2,1] => [1]
     * [3,1] => [0]
     * af = sigmoid
     * lr = 0.05
     * i = 102000
     *
     *
     * the above neural network would have:
     *  2 input neurons
     *  2 layers of hidden neurons (3 neurons and 2 neurons accordingly in each one)
     *  1 output neuron
     *  3 training data
     *  activation function = sigmoid
     *  learning rate  = 0.05
     *  training iterations = 102000
     */

    public MoNeuralNetwork(String data) {
        this.moParseData = new MoParseData(data);
        this.start();
    }

    public MoNeuralNetwork(float lr, int af, int ti) {
        this.learningRate = lr;
        this.activationFunction = af;
        this.numberOfTrainingIterations = ti;
    }

    public MoNeuralNetwork() {
        this.moParseData = new MoParseData("");
        this.start();
    }



    // MODIFIES: this
    // EFFECTS: starts training the data for the neural network
    private void start() {
        this.setParsedValues();
        //System.out.print("Outputs before training:\n");
        for (MoTrainingData moTrainingData: this.trainingData) {
            this.calculate(moTrainingData.getInput());
        }

        this.trainData();
       // System.out.print("Outputs after training:\n");
        for (MoTrainingData moTrainingData: this.trainingData) {
            this.calculate(moTrainingData.getInput());
        }
        //done
    }


    // MODIFIES: this
    // EFFECTS: sets the parsed values from this.data
    // into their respective places
    private void setParsedValues() {
        this.layers = this.moParseData.getLayers();
        this.trainingData = this.moParseData.getTrainingData();
        this.activationFunction = this.moParseData.getActivationFunction();
        this.learningRate = this.moParseData.getLearningRate();
        this.numberOfTrainingIterations = this.moParseData.getNumberOfTrainingIterations();
    }



    /**
     * instead of using matrix multiplication for this
     * I use 3 different for loops to achieve the same goal
     * also the runtime of this is the same as using
     * matrices
     */
    // REQUIRES: inputs != null
    // MODIFIES: this
    // EFFECTS: forward through the neural network in order to get a result
    private void forwardNeuralNetwork(float[] inputs) {
        this.setInputNeurons(inputs);
        for (int i = 1;i < this.layers.length; i++) {
            MoNeuron[] previousLayerNeurons = this.layers[i - 1].getNeurons();
            MoNeuron[] currentLayerMoNeurons = this.layers[i].getNeurons();
            for (int j = 0; j < currentLayerMoNeurons.length; j++) {
                MoNeuron currentNeuron = currentLayerMoNeurons[j];
                float sumOfWeights = 0;
                for (int k = 0; k < previousLayerNeurons.length; k++) {
                    sumOfWeights += currentNeuron.getWeights()[k] * previousLayerNeurons[k].getValue();
                }
                // @TODO we need to add the bias to the sumOfWeights
                currentNeuron.setValue(MoFunctions.activationFunction(activationFunction,sumOfWeights));
            }
        }
    }



    // REQUIRES: inputs != null
    // MODIFIES: this
    // EFFECTS: sets the input layer to inputs
    private void setInputNeurons(float[] inputs) {
        this.layers[0] = new MoLayer(inputs);
    }



    // REQUIRES: moTrainingData != null
    // MODIFIES: this
    // EFFECTS: back propagation of the neural network, in order to learn from
    // the error
    private void backPropNeuralNetwork(MoTrainingData moTrainingData) {
        this.backPropOutputNeurons(moTrainingData);
        this.backPropHiddenNeurons();
        this.updateAllWeights();
    }

    // MODIFIES: this
    // EFFECTS: performs mathematical operations in order to find the
    // error and reduce it for the future events through adjusting weights
    // of the output layer
    private void backPropOutputNeurons(MoTrainingData moTrainingData) {

        MoNeuron[] moNeurons = this.layers[this.layers.length - 1].getNeurons();
        for (int i = 0; i < moNeurons.length; i++) {
            MoNeuron currentNeuron = moNeurons[i];
            float expectedOutput = moTrainingData.getOutput()[i];
            float actualOutput = currentNeuron.getValue();

            float derivative = actualOutput - expectedOutput;
            float delta = derivative * (actualOutput * (1 - actualOutput));
            // delta is going to be the gradient of the currentNeuron
            currentNeuron.setGradient(delta);

            float[] weights = currentNeuron.getSavedWeights();
            for (int j = 0; j < weights.length;j++) {
                float previousOutput = layers[this.layers.length - 2].getNeurons()[j].getValue();
                float error = delta * previousOutput;
                weights[j] = currentNeuron.getWeights()[j] - learningRate * error;
            }
        }

    }


    // MODIFIES: this
    // EFFECTS: performs mathematical operations in order to find the
    // error and reduce it for the future events through adjusting weights
    // of the hidden layer
    private void backPropHiddenNeurons() {
        for (int i = this.layers.length - 2; i > 0; i--) {
            MoNeuron[] moNeurons = this.layers[i].getNeurons();
            for (int j = 0; j < moNeurons.length; j++) {
                MoNeuron currentNeuron = moNeurons[j];
                float actualOutput = currentNeuron.getValue();
                float gradientSum = sumGradient(j, i + 1);
                float delta = (gradientSum) * (actualOutput * (1 - actualOutput));
                currentNeuron.setGradient(delta);
                // And for all their weights
                float[] savedWeights = currentNeuron.getSavedWeights();
                for (int k = 0; k < savedWeights.length; k++) {
                    float previousOutput = layers[i - 1].getNeurons()[k].getValue();
                    float error = delta * previousOutput;
                    savedWeights[k] = currentNeuron.getWeights()[k] - this.learningRate * error;
                }
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: sets the new trained weights into their
    // respective classes
    private void updateAllWeights() {
        for (int i = 0; i < this.layers.length; i++) {
            MoNeuron[] allNeuronsInLayer = this.layers[i].getNeurons();
            for (int j = 0; j < allNeuronsInLayer.length; j++) {
                allNeuronsInLayer[j].updateWeights();
            }
        }
    }

    // REQUIRES: neuronIndex != null && layerIndex != null
    // EFFECTS: returns a gradient sum from all the neurons in
    // layerIndex at a specific weight index (neuronIndex)
    private float sumGradient(int neuronIndex,int layerIndex) {
        float gradientSum = 0;
        MoLayer currentLayer = this.layers[layerIndex];
        MoNeuron[] moNeurons = currentLayer.getNeurons();
        for (int i = 0; i < moNeurons.length; i++) {
            MoNeuron currentNeuron = moNeurons[i];
            gradientSum += currentNeuron.getWeights()[neuronIndex] * currentNeuron.getGradient();
        }
        return gradientSum;
    }



    // MODIFIES: this
    // EFFECTS: trains every MoTrainingData for numberOfTrainIterations
    private void trainData() {
        for (int i = 0; i < this.numberOfTrainingIterations; i++) {
            for (MoTrainingData moTrainingData: this.trainingData) {
                forwardNeuralNetwork(moTrainingData.getInput());
                backPropNeuralNetwork(moTrainingData);
            }
        }
    }



//    // MODIFIES: this
//    // EFFECTS: trains the data
//    public void trainData(ArrayList<MoTrainingData> td) {
//        this.trainingData = td;
//        this.trainData();
//    }





    // REQUIRES: inputs != null
    // MODIFIES: this.MoLayers
    // EFFECTS: it forwards propagates the inputs through the
    // trained neural network to get a result (by changing the values of the output neurons)
    public void calculate(float[] inputs) {
        this.forwardNeuralNetwork(inputs);
        MoNeuron[] outputs = this.layers[this.layers.length - 1 ].getNeurons();
//        for (int i = 0;i < outputs.length; i++) {
//            System.out.print(outputs[i].getValue() + " , ");
//        }
       // System.out.println();
    }


    // EFFECTS: returns every value of neurons in
    // the last layer as an array of floats
    public float[] getOutputs() {
        MoNeuron[] outputNeurons = this.layers[this.layers.length - 1 ].getNeurons();
        float[] outputs = new float[outputNeurons.length];
        for (int i = 0;i < outputs.length; i++) {
            outputs[i] = outputNeurons[i].getValue();
        }
        return outputs;
    }


    // takes
    public static MoNeuralNetwork crossOver(MoNeuralNetwork n1, MoNeuralNetwork n2) {
        MoNeuralNetwork crossed = new MoNeuralNetwork(
                n1.learningRate,
                n1.activationFunction,
                n1.numberOfTrainingIterations);
        int hiddenLayerEnd = n1.layers.length;
        MoLayer[] newMoLayers = new MoLayer[hiddenLayerEnd];
        newMoLayers[0] = n1.layers[0];
        for (int i = 1; i < n1.layers.length; i++) {
            newMoLayers[i] = MoLayer.getCombination(n1.layers[i],n2.layers[i]);
        }
        crossed.layers = newMoLayers;
        return crossed;
    }


    public void mutate(float chance) {
        // randomly mutate the weights
        float random = MoFunctions.getRandom(1f, 0f);
        if (random <= chance) {
            for (MoLayer moLayer : this.layers) {
                if (moLayer != null) {
                    for (MoNeuron moNeuron : moLayer.getNeurons()) {
                        moNeuron.mutateWeights();
                    }
                }
            }
        }
    }


    private String downloadANN() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < this.layers.length; i++) {
            stringBuilder.append(this.layers[i].downloadLayer());
//            if (this.layers[i] != null) {
//
//            }
            if (i != this.layers.length - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    private void readANN(String lines) {
        String[] split = lines.split(MoLayer.LAYER);
        this.layers = new MoLayer[split.length];
        this.layers[0] = null;

        for (int index = 0;index < this.layers.length - 1; index++) {
            MoLayer moLayer = new MoLayer(split[index]);
            this.layers[index + 1] = moLayer;
            // pass it ot the molayer class
        }
    }


    public void save(String path) throws IOException {
        MoFunctions.saveFile(this.downloadANN(),path);
    }

    public void upload(String path) throws IOException {
        this.readANN(MoFunctions.readFile(path));
    }





}
