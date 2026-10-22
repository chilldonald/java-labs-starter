package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DiscountTest {
    private static final double EPS = 1e-9;

    @Test
    void noDiscountReturnsOriginalPrice() {
        assertEquals(100.0, new NoDiscount().apply(100.0), EPS);
    }

    @Test
    void percentDiscountAppliesPercent() {
        assertEquals(80.0, new PercentDiscount(20).apply(100.0), EPS);
    }

    @Test
    void policyCanBeReplacedWithoutChangingCalculator() {
        PriceCalculator withoutDiscount = new PriceCalculator(new NoDiscount());
        PriceCalculator withDiscount = new PriceCalculator(new PercentDiscount(20));

        assertEquals(100.0, withoutDiscount.finalPrice(100.0), EPS);
        assertEquals(80.0, withDiscount.finalPrice(100.0), EPS);
    }
}