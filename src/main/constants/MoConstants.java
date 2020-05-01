package constants;

import constants.exceptions.IllegalRatio;

public class MoConstants {

    public static float MAX_WEIGHT = 1;
    public static float MIN_WEIGHT = -1;
    public static final float LEARNING_RATE = 0.05f;
    public static final int LEARNING_ITERATIONS = 100000;

    public static final String EQUAL = "=";
    public static final String LEFT_SQUARE_BRACKET = "[";
    public static final String RIGHT_SQUARE_BRACKET = "]";
    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String COMMA = ",";


    /**
     *
     * @param max
     * max value of a weight
     * @param min
     * min value of a weight
     */
    // REQUIRES: max > min
    // MODIFIES: MoConstants.MAX_WEIGHT && MoConstants.MIN_WEIGHT
    // EFFECTS: sets the max and min weights for the neural network
    public static void changeMaxMin(float max, float min) throws IllegalRatio {
        if (min > max) {
            throw new IllegalRatio();
        }
        MAX_WEIGHT = max;
        MIN_WEIGHT = min;
    }


    // REQUIRES: text that is in the form of [2,31,3, .... , n ]
    // EFFECTS: extracts numbers that are in a form of
    // [2,3,4]
    // then it returns 2 3 4 in an array
    public static String[] extractNumbers(String text) {
        String removedBracket = text.replace(LEFT_SQUARE_BRACKET,SPACE)
                .replace(RIGHT_SQUARE_BRACKET,SPACE).replace(SPACE,EMPTY);
        return removedBracket.split(COMMA);
    }

}
