package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChainedHashTableTest {

    private static DataSample sample(String label) {
        return new DataSample(new double[]{1.0}, label);
    }

    @Test
    void putAndGetByEqualKeyInstance() {
        ChainedHashTable table = new ChainedHashTable();
        DataSample s = sample("a");
        table.put(new SampleId("iris", 1), s);

        assertSame(s, table.get(new SampleId("iris", 1)));
        assertTrue(table.containsKey(new SampleId("iris", 1)));
    }

    @Test
    void putWithEqualKeyReplacesValueAndKeepsSize() {
        ChainedHashTable table = new ChainedHashTable();
        table.put(new SampleId("iris", 1), sample("old"));
        DataSample newer = sample("new");
        table.put(new SampleId("iris", 1), newer);

        assertEquals(1, table.size());
        assertSame(newer, table.get(new SampleId("iris", 1)));
    }

    @Test
    void removeDeletesValue() {
        ChainedHashTable table = new ChainedHashTable();
        DataSample s = sample("a");
        table.put(new SampleId("iris", 1), s);

        assertSame(s, table.remove(new SampleId("iris", 1)));
        assertNull(table.get(new SampleId("iris", 1)));
        assertEquals(0, table.size());
        assertNull(table.remove(new SampleId("iris", 1)));
    }

    @Test
    void collidingKeysAreKeptSeparately() {
        SampleId first = new SampleId("Aa", 1);
        SampleId second = new SampleId("BB", 1);
        assertEquals(first.hashCode(), second.hashCode());  // одна корзина
        assertNotEquals(first, second);

        ChainedHashTable table = new ChainedHashTable();
        DataSample s1 = sample("one");
        DataSample s2 = sample("two");
        table.put(first, s1);
        table.put(second, s2);

        assertEquals(2, table.size());
        assertSame(s1, table.get(first));
        assertSame(s2, table.get(second));

        table.remove(first);                       // удаляем одно звено цепочки
        assertNull(table.get(first));
        assertSame(s2, table.get(second));         // второе осталось
    }

    @Test
    void dataSurvivesResize() {
        ChainedHashTable table = new ChainedHashTable();
        for (int i = 0; i < 200; i++) {            // заведомо больше 16 * 0.75
            table.put(new SampleId("iris", i), sample("s" + i));
        }

        assertEquals(200, table.size());
        for (int i = 0; i < 200; i++) {
            assertEquals("s" + i, table.get(new SampleId("iris", i)).getLabel());
        }
    }

    @Test
    void nullArgumentsInPutAreRejected() {
        ChainedHashTable table = new ChainedHashTable();
        assertThrows(IllegalArgumentException.class, () -> table.put(null, sample("a")));
        assertThrows(IllegalArgumentException.class, () -> table.put(new SampleId("iris", 1), null));
    }
}