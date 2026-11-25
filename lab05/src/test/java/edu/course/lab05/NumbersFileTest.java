package edu.course.lab05;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class NumbersFileTest {

    @TempDir
    Path tempDir;

    private Path writeNumbers() throws IOException {
        Path file = tempDir.resolve("numbers.txt");
        Files.write(file, List.of("10", "bad", "20"), StandardCharsets.UTF_8);
        return file;
    }

    private static List<Integer> parseAll(List<String> lines) {
        List<Integer> result = new ArrayList<>();
        for (String line : lines) {
            result.add(Integer.parseInt(line));
        }
        return result;
    }

    @Test
    void fileContentIsReadAsWritten() throws IOException {
        List<String> lines = Files.readAllLines(writeNumbers(), StandardCharsets.UTF_8);
        assertEquals(List.of("10", "bad", "20"), lines);
    }

    @Test
    void badLineCausesNumberFormatException() throws IOException {
        List<String> lines = Files.readAllLines(writeNumbers(), StandardCharsets.UTF_8);
        assertThrows(NumberFormatException.class, () -> parseAll(lines));
    }

    @Test
    void goodLinesAreParsed() {
        assertEquals(List.of(10, 20), parseAll(List.of("10", "20")));
    }
}