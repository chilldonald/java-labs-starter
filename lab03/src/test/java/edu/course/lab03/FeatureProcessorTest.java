package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class FeatureProcessorTest {
    private static final double EPS = 1e-9;

    @Test
    void strategyCanBeReplacedWithoutChangingProcessor() {
        double[] vector = {2, 4, 6};

        FeatureProcessor minMax = new FeatureProcessor(new MinMaxNormalization());
        FeatureProcessor centering = new FeatureProcessor(new MeanCenteringNormalization());

        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, minMax.process(vector), EPS);
        assertArrayEquals(new double[]{-2.0, 0.0, 2.0}, centering.process(vector), EPS);
    }

    @Test
    void processorDoesNotModifyInput() {
        double[] vector = {2, 4, 6};
        new FeatureProcessor(new MinMaxNormalization()).process(vector);
        assertArrayEquals(new double[]{2, 4, 6}, vector, EPS);
    }

    @Test
    void nullStrategyIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new FeatureProcessor(null));
    }

    @Test
        void thirdStrategyPluggedInWithoutChangingProcessor() {
            FeatureProcessor zScore = new FeatureProcessor(new ZScoreNormalization());
            double[] result = zScore.process(new double[]{1, 2, 3, 4, 5});
            assertEquals(0.0, result[2], EPS); // среднее значение переходит в 0
    }
}