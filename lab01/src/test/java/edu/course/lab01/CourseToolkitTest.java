package edu.course.lab01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseToolkitTest {

    @Test
    void returnsTrueForEvenNumber() {
        boolean result = CourseToolkit.isEven(8);

        assertTrue(result);
    }

    @Test
    void returnsFalseForOddNumber() {
        boolean result = CourseToolkit.isEven(7);

        assertFalse(result);
    }

    @Test
    void returnsTrueForNegativeEvenNumber() {
        boolean result = CourseToolkit.isEven(-8);

        assertTrue(result);
    }
    // тесты на isPrime
    @Test
    void isPrimeReturnsFalseForNumbersLessThanTwo() {
        assertFalse(CourseToolkit.isPrime(-5));
    }

    @Test
    void isPrimeReturnsTrueForTwo() {
        assertTrue(CourseToolkit.isPrime(2));
    }

    @Test
    void isPrimeReturnsTrueForPrimeNumbers() {
        assertTrue(CourseToolkit.isPrime(3));
    }

    @Test
    void isPrimeReturnsFalseForCompositeNumbers() {
        assertFalse(CourseToolkit.isPrime(100));
    }

    // тесты на isPalindrome
    @Test
    void isPalindromeReturnsTrueForPalindrome() {
        assertTrue(CourseToolkit.isPalindrome("level"));

    }

    @Test
    void isPalindromeReturnsFalseForNonPalindrome() {
        assertFalse(CourseToolkit.isPalindrome("hello"));

    }
    @Test
    void isPalindromeIsCaseAndSpaceSensitive() {
        assertFalse(CourseToolkit.isPalindrome("Level"));
    }

    // тесты для average
    @Test
    void averageReturnsFractionalResult() {
        assertEquals(2.5, CourseToolkit.average(new int[]{1, 2, 3, 4}));
    }

    @Test
    void averageHandlesNegativeNumbers() {
        assertEquals(-4.0, CourseToolkit.average(new int[]{-2, -4, -6}));
    }

    @Test
    void averageThrowsForNullOrEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.average(null));
    }

    // тесты для min и max
    @Test
    void minReturnsSmallestElement() {
        assertEquals(1, CourseToolkit.min(new int[]{3, 1, 4, 1, 5, 9, 2, 6}));
    }

    @Test
    void minHandlesSingleElement() {
        assertEquals(42, CourseToolkit.min(new int[]{42}));
    }

    @Test
    void maxReturnsLargestElement() {
        assertEquals(11, CourseToolkit.max(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}));
    }

    @Test
    void maxHandlesSingleElement() {
        assertEquals(1000, CourseToolkit.max(new int[]{1000}));
    }
}
