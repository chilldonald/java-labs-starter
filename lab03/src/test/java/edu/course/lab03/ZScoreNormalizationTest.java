package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ZScoreNormalizationTest {
    private static final double EPS = 1e-9;

    @Test
    void computesZScores() {
        double[] result = new ZScoreNormalization().normalize(new double[]{1, 2, 3, 4, 5});
        double s = Math.sqrt(2);
        assertArrayEquals(new double[]{-s, -s / 2, 0.0, s / 2, s}, result, EPS);
    }

    @Test
    void resultHasZeroMeanAndUnitStd() {
        double[] result = new ZScoreNormalization().normalize(new double[]{10, 20, 30, 50});

        double sum = 0;
        for (double v : result) sum += v;
        double mean = sum / result.length;

        double sq = 0;
        for (double v : result) sq += (v - mean) * (v - mean);
        double std = Math.sqrt(sq / result.length);

        assertEquals(0.0, mean, EPS);
        assertEquals(1.0, std, EPS);
    }

    @Test
    void doesNotModifyInputArray() {
        double[] input = {1, 2, 3, 4, 5};
        new ZScoreNormalization().normalize(input);
        assertArrayEquals(new double[]{1, 2, 3, 4, 5}, input, EPS);
    }

    @Test
    void constantVectorGivesZeros() {
        double[] result = new ZScoreNormalization().normalize(new double[]{7, 7, 7});
        assertArrayEquals(new double[]{0, 0, 0}, result, EPS);
    }

    @Test
    void emptyVectorIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new ZScoreNormalization().normalize(new double[0]));
    }
}