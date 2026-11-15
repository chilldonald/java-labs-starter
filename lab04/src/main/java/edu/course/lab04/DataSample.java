package edu.course.lab04;

public final class DataSample {
    private final double[] features;
    private final String label;

    public DataSample(double[] features, String label) {
        if (features == null || label == null) {
            throw new IllegalArgumentException("features и label не должны быть null");
        }
        this.features = features.clone();   
        this.label = label;
    }

    public double[] getFeatures() {
        return features.clone();           
    }

    public String getLabel() { return label; }
}