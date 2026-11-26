package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MutableKeyExperimentTest {

    private static class MutableKey {
        private int id;

        MutableKey(int id) { this.id = id; }
        void setId(int id) { this.id = id; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MutableKey)) return false;
            return id == ((MutableKey) o).id;
        }

        @Override
        public int hashCode() { return id; }
    }

    @Test
    void changingKeyFieldAfterPutBreaksLookup() {
        Map<MutableKey, String> map = new HashMap<>();
        MutableKey key = new MutableKey(1);
        map.put(key, "значение");

        assertEquals("значение", map.get(key));   

        key.setId(2);                             

        assertNull(map.get(key));                
        assertFalse(map.containsKey(key));
        assertEquals(1, map.size());             
    }
}