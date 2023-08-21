package guwan21.common.util;

import java.util.HashMap;
import java.util.Map;

public class Sigmoid {
    //Sigmoid function : M / ( 1 + e ^ - growthRate * (x - pointOfInflection))
    //Inverted sigmoid : M - ( M / ... )

    private static final Map<String,Float> hashTableInverted = new HashMap<>();
    private static final Map<String,Float> hashTableNormal = new HashMap<>();


    private static String compositeKeyOf(float x, float maximum, float minimum, float growthRate, float pointOfInflection){
        return x + "," + maximum + "," + minimum + "," + growthRate + "," + pointOfInflection;
    }

    /**
     * Interpolates the value: X along an inverted sigmoid curve where
     * @param x is the value to be interpolated
     * @param maximum is the upper bound
     * @param minimum is the lower bound
     * @param growthRate is how slow / fast the values should increase with changes to x
     * @param pointOfInflection is the value of x where the middle of the sigmoid curve lies (where it is near-linear)
     * @return interpolated value
     */
    public static float inverted(float x, float maximum, float minimum, float growthRate, float pointOfInflection){
        return hashTableInverted.computeIfAbsent(
                compositeKeyOf(x,maximum,minimum,growthRate,pointOfInflection),
                value -> (float) ((maximum) - (maximum - minimum) / (1 + Math.pow(Math.E, -1 * growthRate * (x - pointOfInflection))))
        );
    }
    /**
     * Interpolates the value: X along a sigmoid curve where
     * @param x is the value to be interpolated
     * @param maximum is the upper bound
     * @param minimum is the lower bound
     * @param growthRate is how slow / fast the values should increase with changes to x
     * @param pointOfInflection is the value of x where the middle of the sigmoid curve lies (where it is near-linear)
     * @return interpolated value
     */
    public static float normal(float x, float maximum, float minimum, float growthRate, float pointOfInflection){
        return hashTableNormal.computeIfAbsent(
                compositeKeyOf(x,maximum,minimum,growthRate,pointOfInflection),
                value -> (float) (minimum + (maximum - minimum) / (1 + Math.pow(Math.E, -1 * growthRate * (x - pointOfInflection))))
        );
    }
}
