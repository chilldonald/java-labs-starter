package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MinMaxNormalizationTest {
    private static final double EPS = 1e-9;

    @Test
    void scalesValuesToZeroOneRange() {
        double[] result = new MinMaxNormalization().normalize(new double[]{2, 4, 6});
        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, result, EPS);
    }

    @Test
    void doesNotModifyInputArray() {
        double[] input = {2, 4, 6};
        new MinMaxNormalization().normalize(input);
        assertArrayEquals(new double[]{2, 4, 6}, input, EPS);
    }

    @Test
    void constantVectorGivesZeros() {
        double[] result = new MinMaxNormalization().normalize(new double[]{5, 5, 5});
        assertArrayEquals(new double[]{0, 0, 0}, result, EPS);
    }

    @Test
    void emptyVectorIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new MinMaxNormalization().normalize(new double[0]));
    }
}