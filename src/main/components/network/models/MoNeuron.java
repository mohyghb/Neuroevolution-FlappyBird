package components.network.models;


import constants.MoFunctions;

public class MoNeuron {



    public static final String SEP_KEY = ",";

    /**
     * Neurons can be either:
     *  - input
     *  - hidden
     *  - output
     */


    private float value;
    private float bias;
    private float[] weights;
    private float[] savedWeights;
    private float gradient;


    /**
     * if this neuron is an [input]
     * it does not have any weights to it
     * @param v
     * v is the value of the input neuron
     */
    // MODIFIES: this
    // EFFECTS: constructor for input neurons
    public MoNeuron(float v) {
        this.value = v;
        this.weights = null;
        this.savedWeights = null;
        this.bias = -1;
        this.gradient = -1;
    }

    /**
     * if this neuron is [hidden/output]
     * then the value is  = 0;
     * and bias and ws are set accordingly
     * @param ws
     * weights provided
     * @param b
     * bias provided
     */
    // MODIFIES: this
    // EFFECTS: constructor for hidden/output neuron
    public MoNeuron(float[] ws, float b) {
        this.weights = ws;
        this.savedWeights = ws;
        this.bias = b;
        this.value = 0;
        this.gradient = 1;
    }


    // for reading from a file
    public MoNeuron(String data) {
        this.readNeuron(data);
    }


    // MODIFIES: this
    // EFFECTS: updates the weights to the savedWeights
    public void updateWeights() {
        this.weights = this.savedWeights;
    }



    // EFFECTS: returns the neuron's value
    public float getValue() {
        return value;
    }

    // MODIFIES: this
    // EFFECTS: sets the value of this neuron to value
    public void setValue(float value) {
        this.value = value;
    }

    // EFFECTS: returns the neuron's bias
    public float getBias() {
        return bias;
    }

//    // MODIFIES: this
//    // EFFECTS: sets the bias of this neuron to bias
//    public void setBias(float bias) {
//        this.bias = bias;
//    }

    // EFFECTS: returns the neuron's weights
    public float[] getWeights() {
        return weights;
    }

    // MODIFIES: this
    // EFFECTS: sets the weights of this neuron to weights
//    public void setWeights(float[] weights) {
//        this.weights = weights;
//    }

    // EFFECTS: returns the neuron's gradient
    public float getGradient() {
        return gradient;
    }


    // MODIFIES: this
    // EFFECTS: sets the gradient of this neuron to gradient
    public void setGradient(float gradient) {
        this.gradient = gradient;
    }


    // EFFECTS: returns the neuron's savedWeights
    public float[] getSavedWeights() {
        return savedWeights;
    }

    // MODIFIES: this
    // EFFECTS: sets the savedWeights of this neuron to savedWeights
    public void setSavedWeights(float[] savedWeights) {
        this.savedWeights = savedWeights;
    }

    /**
     *
     * @param number
     * number of random weights
     * @return
     * returns an array of float of random weights
     */
    // EFFECTS: returns an array of float of random weights used to
    // initialize the first weights for the neuron
    public static float[] getRandomWeights(int number, float max, float min) {
        float[] randomWeights = new float[number];
        for (int i = 0; i < randomWeights.length; i++) {
            randomWeights[i] = MoFunctions.getRandom(max,min);
        }
        return randomWeights;
    }




    // randomly adds an amount to each weight
    public void mutateWeights() {
        if (this.weights != null) {
            for (int i = 0; i < weights.length; i++) {
                double chance = MoFunctions.getRandom(1.0,0.0);
                if (chance >= 0.5) {
                    float randomMutation = MoFunctions.getRandom(0.02f,-0.02f);
                    if (Math.abs(weights[i] + randomMutation) <= 1) {
                        weights[i] += randomMutation;
                    } else {
                        weights[i] -= randomMutation;
                    }
                    break;
                }
            }
        }
    }


    public String downloadNeuron() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.value).append(SEP_KEY);
        stringBuilder.append(this.gradient).append(SEP_KEY);
        stringBuilder.append(this.bias).append(SEP_KEY);
        for (int i = 0; i < this.weights.length; i++) {
            stringBuilder.append(weights[i]);
            if (i < this.weights.length - 1) {
                stringBuilder.append(SEP_KEY);
            } else {
                stringBuilder.append("]");
            }
        }
        return stringBuilder.toString();
    }



    public void readNeuron(String line) {
        float[] values = MoFunctions.getFloatVersion(line);
        if (values.length > 3) {
            this.value = values[0];
            this.gradient = values[1];
            this.bias = values[2];
            this.weights = new float[values.length - 3];
            for (int i = 0; i < this.weights.length; i++) {
                this.weights[i] = values[i + 3];
            }
            this.savedWeights = this.weights;
        }

    }


}
