package edu.course.lab05;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SampleFileLoader {

    static final String HEADER = "id;label;source;featureCount;status";
    private static final char SEPARATOR = ';';
    private static final int COLUMNS = 5;

    public LoadResult load(Path path) {
        if (path == null) {
            throw new IllegalArgumentException("path не должен быть null");
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DataLoadingException("Cannot read " + path, e);
        }

        List<Sample> samples = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();

        if (lines.isEmpty()) {
            errors.add(new ValidationError(1, "header", "File is empty"));
            return new LoadResult(samples, errors);
        }
        if (!HEADER.equals(stripBom(lines.get(0)).trim())) {
            errors.add(new ValidationError(1, "header", "Expected header: " + HEADER));
            return new LoadResult(samples, errors);
        }

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) {
                continue;
            }
            Sample sample = parseLine(i + 1, line, errors);
            if (sample != null) {
                samples.add(sample);
            }
        }
        return new LoadResult(samples, errors);
    }

    private Sample parseLine(int lineNumber, String line, List<ValidationError> errors) {
        List<String> fields = splitFields(line);
        if (fields == null) {
            errors.add(new ValidationError(lineNumber, "row", "Unclosed quote"));
            return null;
        }
        if (fields.size() != COLUMNS) {
            errors.add(new ValidationError(lineNumber, "row",
                    "Expected " + COLUMNS + " fields but found " + fields.size()));
            return null;
        }

        String id = fields.get(0).trim();
        String label = fields.get(1).trim();
        String source = fields.get(2).trim();
        String countText = fields.get(3).trim();
        String statusText = fields.get(4).trim();

        if (id.isEmpty()) {
            errors.add(new ValidationError(lineNumber, "id", "must not be empty"));
            return null;
        }
        if (label.isEmpty()) {
            errors.add(new ValidationError(lineNumber, "label", "must not be empty"));
            return null;
        }
        if (source.isEmpty()) {
            errors.add(new ValidationError(lineNumber, "source", "must not be empty"));
            return null;
        }
        if (countText.isEmpty()) {
            errors.add(new ValidationError(lineNumber, "featureCount", "must not be empty"));
            return null;
        }

        int featureCount;
        try {
            featureCount = Integer.parseInt(countText);
        } catch (NumberFormatException e) {
            errors.add(new ValidationError(lineNumber, "featureCount",
                    "not an integer: '" + countText + "'"));
            return null;
        }
        if (featureCount <= 0) {
            errors.add(new ValidationError(lineNumber, "featureCount", "must be positive"));
            return null;
        }

        SampleStatus status;
        try {
            status = SampleStatus.valueOf(statusText);
        } catch (IllegalArgumentException e) {
            errors.add(new ValidationError(lineNumber, "status",
                    "unknown status: '" + statusText + "'"));
            return null;
        }

        return new Sample(id, label, source, featureCount, status);
    }

    static List<String> splitFields(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        boolean atFieldStart = true;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        current.append('"');      
                        i++;
                    } else {
                        inQuotes = false;         
                    }
                } else {
                    current.append(c);           
                }
            } else if (c == '"' && atFieldStart) {
                inQuotes = true;                  
                atFieldStart = false;
            } else if (c == SEPARATOR) {
                fields.add(current.toString());
                current.setLength(0);
                atFieldStart = true;
            } else {
                current.append(c);
                atFieldStart = false;
            }
        }
        if (inQuotes) {
            return null;                         
        }
        fields.add(current.toString());           
        return fields;
    }

    private static String stripBom(String s) {
        return s.startsWith("\uFEFF") ? s.substring(1) : s;
    }
}