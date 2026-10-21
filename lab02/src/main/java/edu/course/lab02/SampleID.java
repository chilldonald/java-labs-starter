package edu.course.lab02;

public record SampleID(String value) {

    public SampleID {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Идентификатор не должен быть null или пустым");
        }
    }
}