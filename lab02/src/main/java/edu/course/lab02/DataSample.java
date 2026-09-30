package edu.course.lab02;

public class DataSample {

    private final String id;
    private final String label;
    private SampleStatus status;
    private final double[] features;

    public DataSample(String id, String label, SampleStatus status, double[] features) {
    if (id == null || id.isBlank()) {
        throw new IllegalArgumentException("id не должен быть null или пустым");
    }
    if (label == null || label.isBlank()) {
        throw new IllegalArgumentException("label не должен быть null или пустым");
    }
    if (status == null) {
        throw new IllegalArgumentException("status не должен быть null");
    }
    if (features == null || features.length == 0) {
        throw new IllegalArgumentException("features не должен быть null или пустым");
    }
    for (double value : features) {
        if (!Double.isFinite(value)) { //проверяем, что значение является числом
            throw new IllegalArgumentException("features содержит недопустимое значение: " + value);
        }
    }

    this.id = id;
    this.label = label;
    this.status = status;
    this.features = features.clone();
    }

    //геттеры

    public String getId() {
    return id;
    }

    public String getLabel() {
        return label;
    }

    //методы поведения

    public SampleStatus getStatus() {
        return status;
    }

    public double[] getFeatures() {
        return features.clone();
    }

    public void changeStatus(SampleStatus newStatus) {
    if (newStatus == null) {
        throw new IllegalArgumentException("Новый статус не должен быть null");
    }
    this.status = newStatus;
    }

    public boolean isReady() {
        return status == SampleStatus.READY;
    }

    public double meanFeature() {
        double sum = 0;
        for (double value : features) {
            sum += value;
        }
        return sum / features.length;
    }
}
