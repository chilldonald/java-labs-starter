package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void valueIsFoundByAnotherInstanceWithSameIsbn() {
        Map<Book, String> shelf = new HashMap<>();
        shelf.put(new Book("978-1", "Effective Java"), "полка A");

        Book anotherInstance = new Book("978-1", "Другое название");

        assertEquals("полка A", shelf.get(anotherInstance));
    }

    @Test
    void booksWithDifferentIsbnAreNotEqual() {
        Book a = new Book("978-1", "Effective Java");
        Book b = new Book("978-2", "Effective Java");

        assertNotEquals(a, b);
    }

    @Test
    void equalBooksHaveEqualHashCodes() {
        Book a = new Book("978-1", "A");
        Book b = new Book("978-1", "B");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}