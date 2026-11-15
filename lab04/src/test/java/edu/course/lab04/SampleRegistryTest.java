package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SampleRegistryTest {

    private static DataSample sample(String label) {
        return new DataSample(new double[]{1.0, 2.0}, label);
    }

    @Test
    void twoEqualKeyInstancesFindSameValue() {
        SampleRegistry registry = new SampleRegistry();
        DataSample s = sample("cat");
        registry.put(new SampleId("iris", 5), s);

        assertSame(s, registry.get(new SampleId("iris", 5)));
        assertTrue(registry.contains(new SampleId("iris", 5)));
    }

    @Test
    void putWithEqualKeyReplacesValueAndKeepsSize() {
        SampleRegistry registry = new SampleRegistry();
        registry.put(new SampleId("iris", 5), sample("cat"));
        DataSample newer = sample("dog");
        registry.put(new SampleId("iris", 5), newer);

        assertEquals(1, registry.size());
        assertSame(newer, registry.get(new SampleId("iris", 5)));
    }

    @Test
    void removeDeletesValue() {
        SampleRegistry registry = new SampleRegistry();
        DataSample s = sample("cat");
        registry.put(new SampleId("iris", 5), s);

        assertSame(s, registry.remove(new SampleId("iris", 5)));
        assertFalse(registry.contains(new SampleId("iris", 5)));
        assertEquals(0, registry.size());
        assertNull(registry.get(new SampleId("iris", 5)));
    }

    @Test
    void sizeCountsDifferentKeys() {
        SampleRegistry registry = new SampleRegistry();
        registry.put(new SampleId("iris", 1), sample("a"));
        registry.put(new SampleId("iris", 2), sample("b"));

        assertEquals(2, registry.size());
    }

    @Test
    void keysWithSameHashCodeButNotEqualKeepDifferentValues() {
        // Строки "Aa" и "BB" имеют одинаковый hashCode, поэтому и у SampleId хеши совпадут
        SampleId first = new SampleId("Aa", 1);
        SampleId second = new SampleId("BB", 1);

        assertEquals(first.hashCode(), second.hashCode()); // коллизия есть
        assertNotEquals(first, second);                    // но ключи не равны

        SampleRegistry registry = new SampleRegistry();
        DataSample s1 = sample("one");
        DataSample s2 = sample("two");
        registry.put(first, s1);
        registry.put(second, s2);

        assertEquals(2, registry.size());
        assertSame(s1, registry.get(first));
        assertSame(s2, registry.get(second));
    }
}