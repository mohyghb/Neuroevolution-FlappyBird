package constants;

import constants.exceptions.IllegalConversion;
import constants.exceptions.IllegalRatio;

public class MoHelper {

    public static float getFloat(String data) throws IllegalConversion {
        float i = 0;
        try {
            i = Float.parseFloat(data);
        } catch (Exception e) {
            throw new IllegalConversion();
        }
        return i;
    }


    public static void changeRatio(String max, String min) throws IllegalConversion,IllegalRatio {
        MoConstants.changeMaxMin(getFloat(max), getFloat(min));
    }




}
