package edu.course.lab03;

public class FeatureProcessor {
    private final NormalizationStrategy strategy; // зависим от интерфейса

    public FeatureProcessor(NormalizationStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Стратегия не задана");
        }
        this.strategy = strategy;
    }

    public double[] process(double[] vector) {
        return strategy.normalize(vector);
    }
}