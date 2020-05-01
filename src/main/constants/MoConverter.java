package constants;

import java.util.ArrayList;
import java.util.Iterator;

public class MoConverter {


    // REQUIRES: integers != null
    // EFFECTS: converts an arrayList of Integers to
    // an array of int
    public static int[] convertToIntegers(ArrayList<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }

    // REQUIRES: floats != null
    // EFFECTS: converts an arrayList of Floats to
    // an array of float
    public static float[] convertToFloats(ArrayList<Float> floats) {
        float[] ret = new float[floats.size()];
        Iterator<Float> iterator = floats.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().floatValue();
        }
        return ret;
    }




}
