package edu.course.lab02;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DataSampleTest {

    private static DataSample validSample() {
        return new DataSample("s1", "cat", SampleStatus.NEW, new double[] {1.0, 2.0, 3.0});
    }


    @Test
    void validSampleIsCreated() {
        DataSample sample = validSample();

        assertEquals("s1", sample.getId());
        assertEquals("cat", sample.getLabel());
        assertEquals(SampleStatus.NEW, sample.getStatus());
        assertArrayEquals(new double[] {1.0, 2.0, 3.0}, sample.getFeatures());
    }

    @Test
    void nullIdIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample(null, "cat", SampleStatus.NEW, new double[] {1.0}));
    }

    @Test
    void blankIdIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("   ", "cat", SampleStatus.NEW, new double[] {1.0}));
    }

    @Test
    void nullLabelIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("s1", null, SampleStatus.NEW, new double[] {1.0}));
    }

    @Test
    void emptyLabelIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("s1", "", SampleStatus.NEW, new double[] {1.0}));
    }

    @Test
    void nullStatusIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("s1", "cat", null, new double[] {1.0}));
    }


    @Test
    void nullFeaturesAreRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("s1", "cat", SampleStatus.NEW, null));
    }

    @Test
    void emptyFeaturesAreRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("s1", "cat", SampleStatus.NEW, new double[0]));
    }

    @Test
    void nanFeatureIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("s1", "cat", SampleStatus.NEW, new double[] {1.0, Double.NaN}));
    }

    @Test
    void infiniteFeatureIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("s1", "cat", SampleStatus.NEW,
                        new double[] {Double.POSITIVE_INFINITY}));
    }

    @Test
    void changeStatusUpdatesStatus() {
        DataSample sample = validSample();

        sample.changeStatus(SampleStatus.READY);

        assertEquals(SampleStatus.READY, sample.getStatus());
    }

    @Test
    void changeStatusToNullIsRejected() {
        DataSample sample = validSample();

        assertThrows(IllegalArgumentException.class, () -> sample.changeStatus(null));
        assertEquals(SampleStatus.NEW, sample.getStatus());
    }

    @Test
    void isReadyIsTrueOnlyForReadyStatus() {
        DataSample sample = validSample();
        assertFalse(sample.isReady());

        sample.changeStatus(SampleStatus.READY);
        assertTrue(sample.isReady());
    }

    @Test
    void meanFeatureIsComputed() {
        assertEquals(2.0, validSample().meanFeature(), 1e-9);
    }

    @Test
    void constructorCopiesInputArray() {
        double[] source = {1.0, 2.0, 3.0};
        DataSample sample = new DataSample("s1", "cat", SampleStatus.NEW, source);

        source[0] = 100.0;

        assertArrayEquals(new double[] {1.0, 2.0, 3.0}, sample.getFeatures());
    }

    @Test
    void getterReturnsCopy() {
        DataSample sample = validSample();

        double[] returned = sample.getFeatures();
        returned[0] = 100.0;

        assertArrayEquals(new double[] {1.0, 2.0, 3.0}, sample.getFeatures());
    }
}