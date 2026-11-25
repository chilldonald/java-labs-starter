package edu.course.lab05;

public final class Sample {
    private final String id;
    private final String label;
    private final String source;
    private final int featureCount;
    private final SampleStatus status;

    public Sample(String id, String label, String source, int featureCount, SampleStatus status) {
        if (id == null || id.isEmpty() || label == null || label.isEmpty()
                || source == null || source.isEmpty() || status == null) {
            throw new IllegalArgumentException("Поля не должны быть null или пустыми");
        }
        if (featureCount <= 0) {
            throw new IllegalArgumentException("featureCount должен быть положительным");
        }
        this.id = id;
        this.label = label;
        this.source = source;
        this.featureCount = featureCount;
        this.status = status;
    }

    public String getId() { return id; }
    public String getLabel() { return label; }
    public String getSource() { return source; }
    public int getFeatureCount() { return featureCount; }
    public SampleStatus getStatus() { return status; }
}