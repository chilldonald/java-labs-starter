package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SampleIdTest {

    @Test
    void equalKeysAreEqualAndHaveSameHashCode() {
        SampleId a = new SampleId("iris", 5);
        SampleId b = new SampleId("iris", 5);

        assertNotSame(a, b);                       
        assertEquals(a, b);                        
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void differentKeysAreNotEqual() {
        assertNotEquals(new SampleId("iris", 5), new SampleId("iris", 6));
        assertNotEquals(new SampleId("iris", 5), new SampleId("wine", 5));
    }

    @Test
    void equalsReturnsFalseForNullAndOtherType() {
        SampleId id = new SampleId("iris", 5);

        assertFalse(id.equals(null));
        assertFalse(id.equals("iris#5"));
    }

    @Test
    void invalidArgumentsAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new SampleId(null, 1));
        assertThrows(IllegalArgumentException.class, () -> new SampleId("iris", -1));
    }
}