package edu.course.lab03;

public class PriceCalculator {
    private final DiscountPolicy policy; // тип ссылки интерфейс

    public PriceCalculator(DiscountPolicy policy) {
        if (policy == null) {
            throw new IllegalArgumentException("Политика не задана");
        }
        this.policy = policy;
    }

    public double finalPrice(double price) {
        return policy.apply(price);
    }
}