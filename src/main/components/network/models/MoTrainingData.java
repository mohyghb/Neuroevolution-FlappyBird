package components.network.models;

public class MoTrainingData {


    private float[] input;
    private float[] output;


    /**
     * a training data for the neural network
     * @param i
     * i is the inputs to the neural network
     * @param o
     * o is the expected output to the neural network
     */
     // REQUIRES: i != null && o != null
     // MODIFIES: this
     // EFFECTS: constructor for MoTrainingData
    public MoTrainingData(float[] i, float[] o) {
        this.input = i;
        this.output = o;
    }


    // EFFECTS: returns the inputs
    public float[] getInput() {
        return input;
    }


    // MODIFIES: this
    // EFFECTS: sets the this.inputs to input
//    public void setInput(float[] input) {
//        this.input = input;
//    }



    // EFFECTS: returns the outputs
    public float[] getOutput() {
        return output;
    }


    // MODIFIES: this
    // EFFECTS: sets the this.outputs to output
//    public void setOutput(float[] output) {
//        this.output = output;
//    }
}
