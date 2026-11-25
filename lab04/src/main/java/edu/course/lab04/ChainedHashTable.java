package edu.course.lab04;

public class ChainedHashTable {

    private static class Node {
        final SampleId key;
        DataSample value;
        Node next;

        Node(SampleId key, DataSample value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Node[] buckets = new Node[INITIAL_CAPACITY];
    private int size = 0;

    private int indexFor(SampleId key, int length) {
        return (key.hashCode() & 0x7fffffff) % length;
    }

    /** Добавляет или заменяет значение. */
    public void put(SampleId key, DataSample value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("key и value не должны быть null");
        }
        int i = indexFor(key, buckets.length);
        for (Node n = buckets[i]; n != null; n = n.next) {
            if (n.key.equals(key)) {   // равный ключ: заменяем значение
                n.value = value;
                return;
            }
        }
        buckets[i] = new Node(key, value, buckets[i]);  // новая запись в начало списка
        size++;
        if (size > buckets.length * LOAD_FACTOR) {
            resize();
        }
    }

    /** Возвращает значение или null. */
    public DataSample get(SampleId key) {
        if (key == null) return null;
        for (Node n = buckets[indexFor(key, buckets.length)]; n != null; n = n.next) {
            if (n.key.equals(key)) return n.value;
        }
        return null;
    }

    /** Удаляет запись, возвращает старое значение или null. */
    public DataSample remove(SampleId key) {
        if (key == null) return null;
        int i = indexFor(key, buckets.length);
        Node prev = null;
        for (Node n = buckets[i]; n != null; prev = n, n = n.next) {
            if (n.key.equals(key)) {
                if (prev == null) buckets[i] = n.next;  // удаляем голову списка
                else prev.next = n.next;                // удаляем из середины/конца
                size--;
                return n.value;
            }
        }
        return null;
    }

    public boolean containsKey(SampleId key) {
        return get(key) != null;   // значения null запрещены, так что это корректно
    }

    public int size() {
        return size;
    }

    private void resize() {
        Node[] old = buckets;
        Node[] bigger = new Node[old.length * 2];
        for (Node head : old) {
            for (Node n = head; n != null; ) {
                Node next = n.next;                      // запоминаем до перевешивания
                int i = indexFor(n.key, bigger.length);
                n.next = bigger[i];
                bigger[i] = n;
                n = next;
            }
        }
        buckets = bigger;
    }
}