package edu.course.lab05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LoadResult {
    private final List<Sample> samples;
    private final List<ValidationError> errors;

    public LoadResult(List<Sample> samples, List<ValidationError> errors) {
        this.samples = Collections.unmodifiableList(new ArrayList<>(samples));
        this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
    }

    public List<Sample> getSamples() { return samples; }
    public List<ValidationError> getErrors() { return errors; }
    public boolean hasErrors() { return !errors.isEmpty(); }
}