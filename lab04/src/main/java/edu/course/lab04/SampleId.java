package edu.course.lab04;

import java.util.Objects;

public final class SampleId {
    private final String datasetName;
    private final int index;

    public SampleId(String datasetName, int index) {
        if (datasetName == null || datasetName.isEmpty()) {
            throw new IllegalArgumentException("Имя набора не должно быть пустым");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Номер не может быть отрицательным");
        }
        this.datasetName = datasetName;
        this.index = index;
    }

    public String getDatasetName() { return datasetName; }
    public int getIndex() { return index; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleId)) return false;
        SampleId other = (SampleId) o;
        return index == other.index && datasetName.equals(other.datasetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datasetName, index);
    }

    @Override
    public String toString() {
        return datasetName + "#" + index;
    }
}