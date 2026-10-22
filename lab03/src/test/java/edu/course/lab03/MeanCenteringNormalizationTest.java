package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MeanCenteringNormalizationTest {
    private static final double EPS = 1e-9;

    @Test
    void subtractsMeanFromEachValue() {
        // среднее = 4
        double[] result = new MeanCenteringNormalization().normalize(new double[]{2, 4, 6});
        assertArrayEquals(new double[]{-2.0, 0.0, 2.0}, result, EPS);
    }

    @Test
    void doesNotModifyInputArray() {
        double[] input = {1, 2, 3, 4};
        new MeanCenteringNormalization().normalize(input);
        assertArrayEquals(new double[]{1, 2, 3, 4}, input, EPS);
    }

    @Test
    void emptyVectorIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new MeanCenteringNormalization().normalize(new double[0]));
    }
}