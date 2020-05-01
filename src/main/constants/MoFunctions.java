package constants;

import components.network.MoNeuralNetwork;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoFunctions {

    public static final int SIGMOID_FUNCTION = 0;
    public static final int ReLU_FUNCTION = 1;
    public static final int SOFT_MAX_FUNCTION = 2;
    public static final int TAN_H_FUNCTION = 3;

    public static Random random = new Random();

    // EFFECTS: returns a random float
    // between max and min
    public static float getRandom(float max, float min) {
        return (min + random.nextFloat() * (max - min));
    }

    // EFFECTS: returns a random double
    // between max and min
    public static double getRandom(double max, double min) {
        return (min + random.nextDouble() * (max - min));
    }

    // EFFECTS: returns a random integer
    // between max and min
    public static int getRandom(int max, int min) {
        return random.nextInt(max - min) + min;
    }


    /**
     * changes an string in form of
     * [2,1,423,5]
     * into an int[] such that it has those values;
     * if an error happens then we
     * do nothing
     * @param text
     * text is the data that they want to be converted
     * @return
     * return
     */
    // EFFECTS: changes an string in form of
    // [2,1,423,5]
    // into an int[] such that it has those values
    // and then return it
    // if an error happens then we
    // do not add it to the array
    public static int[] getIntegerVersion(String text) {
        String[] numbers = MoConstants.extractNumbers(text);

        //Integer[] intNumbers = new Integer[numbers.length];
        ArrayList<Integer> intNumbers = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            try {
                int number = Integer.parseInt(numbers[i]);
                intNumbers.add(number);
            } catch (NumberFormatException illegalFormatException) {
                // exception
            }
        }
        return MoConverter.convertToIntegers(intNumbers);
    }


    /**
     * changes an string in form of
     * [2,1,423,5]
     * into an float[] such that it has those values;
     * if an error happens then we
     * do nothing
     * @param text
     * text is the data that they want to be converted
     * @return
     * return
     */
    // EFFECTS: changes an string in form of
    // [2,1,423,5]
    // into an float[] such that it has those values
    // and then return it
    // if an error happens then we
    // do not add it to the array
    public static float[] getFloatVersion(String text) {
        String[] numbers = MoConstants.extractNumbers(text);

        //Integer[] intNumbers = new Integer[numbers.length];
        ArrayList<Float> floatNumbers = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            try {
                float number = Float.parseFloat(numbers[i]);
                floatNumbers.add(number);
            } catch (NumberFormatException illegalFormatException) {
                // exception
            }
        }
        return MoConverter.convertToFloats(floatNumbers);
    }











    // EFFECTS: returns anything that comes after sign in line
    public static String parseAfter(String line, String sign) {
        String[] split = line.split(sign);
        if (split.length == 2) {
            return split[1].trim();
        }
        return "";
    }




    /**
     * !!! in work
     * @param number
     * number passed to be calculated through the activation function
     * @return
     * returns the calculated activationFunction(number)
     */
    // REQUIRES: value != null
    // EFFECTS: does mathematical operation on value
    // and returns the result
    // if function is null, return the number
    public static float activationFunction(int function, float number) {
        switch (function) {
            case SIGMOID_FUNCTION:
                return sigmoidFunction(number);
            default:
                return number;
        }
    }


    // REQUIRES: value != null
    // EFFECTS: does sigmoid mathematical operation on value
    // and returns the result
    public static float sigmoidFunction(float value) {
        return (float) (1 / (1 + Math.pow(Math.E, -value)));
    }





    public static String desktopPath(String fileName) {
        return System.getProperty("user.home") + "/Desktop/" + fileName + ".txt";
    }

    // reads files from the given path
    public static String readFile(String pathFile) throws IOException {

        Path path = Paths.get(pathFile);
        //byte[] bytes = Files.readAllBytes(path);
        StringBuilder stringBuilder = new StringBuilder();
        for (String line: Files.readAllLines(path, StandardCharsets.UTF_8)) {
            if (!line.isEmpty()) {
                stringBuilder.append(line).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    // saves the content into a file to the pathFile
    public static void saveFile(String content, String pathFile) throws IOException {
        final Path path = Paths.get(pathFile);

        try (
                final BufferedWriter writer = Files.newBufferedWriter(path,
                        StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        ) {
            writer.write(content);
            writer.flush();
        }
    }








}
