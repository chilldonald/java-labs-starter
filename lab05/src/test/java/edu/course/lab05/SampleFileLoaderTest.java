package edu.course.lab05;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SampleFileLoaderTest {

    private static final String HEADER = SampleFileLoader.HEADER;

    @TempDir
    Path tempDir;

    private final SampleFileLoader loader = new SampleFileLoader();

    private Path write(String... lines) throws IOException {
        Path file = tempDir.resolve("samples.txt");
        Files.write(file, Arrays.asList(lines), StandardCharsets.UTF_8);
        return file;
    }

    @Test
    void validFileIsLoaded() throws IOException {
        LoadResult result = loader.load(write(
                HEADER,
                "S-101;cat;camera-1;128;READY",
                "S-102;dog;camera-2;64;PENDING"));

        assertFalse(result.hasErrors());
        assertEquals(2, result.getSamples().size());
        Sample first = result.getSamples().get(0);
        assertEquals("S-101", first.getId());
        assertEquals("cat", first.getLabel());
        assertEquals(128, first.getFeatureCount());
        assertEquals(SampleStatus.READY, first.getStatus());
    }

    @Test
    void unicodeTextIsReadCorrectly() throws IOException {
        LoadResult result = loader.load(write(HEADER, "S-1;кошка;камера-1;16;READY"));

        assertFalse(result.hasErrors());
        assertEquals("кошка", result.getSamples().get(0).getLabel());
        assertEquals("камера-1", result.getSamples().get(0).getSource());
    }

    @Test
    void emptyFieldIsReported() throws IOException {
        LoadResult result = loader.load(write(HEADER, "S-1;;camera-1;16;READY"));

        assertTrue(result.getSamples().isEmpty());
        assertEquals(1, result.getErrors().size());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
        assertEquals("label", result.getErrors().get(0).getField());
    }

    @Test
    void emptyLastFieldIsNotLostBySplit() throws IOException {
        LoadResult result = loader.load(write(HEADER, "S-1;cat;camera-1;16;"));

        assertEquals(1, result.getErrors().size());
        assertEquals("status", result.getErrors().get(0).getField());
    }

    @Test
    void invalidNumberIsReported() throws IOException {
        LoadResult result = loader.load(write(HEADER, "S-1;cat;camera-1;abc;READY"));

        assertTrue(result.getSamples().isEmpty());
        assertEquals("featureCount", result.getErrors().get(0).getField());
    }

    @Test
    void nonPositiveNumberIsReported() throws IOException {
        LoadResult result = loader.load(write(HEADER, "S-1;cat;camera-1;0;READY"));

        assertEquals("featureCount", result.getErrors().get(0).getField());
    }

    @Test
    void unknownStatusIsReported() throws IOException {
        LoadResult result = loader.load(write(HEADER, "S-1;cat;camera-1;16;BROKEN"));

        assertTrue(result.getSamples().isEmpty());
        assertEquals("status", result.getErrors().get(0).getField());
    }

    @Test
    void wrongColumnCountIsReported() throws IOException {
        LoadResult result = loader.load(write(HEADER, "S-1;cat;camera-1;16"));

        assertTrue(result.getSamples().isEmpty());
        assertEquals("row", result.getErrors().get(0).getField());
    }

    @Test
    void goodAndBadRowsAreProcessedTogether() throws IOException {
        LoadResult result = loader.load(write(
                HEADER,
                "S-1;cat;cam;128;READY",
                "S-2;dog;cam;abc;READY",
                "",
                "S-3;fox;cam;64;UNKNOWN",
                "S-4;owl;cam;32",
                "S-5;bat;cam;16;READY"));

        assertEquals(2, result.getSamples().size());
        assertEquals("S-1", result.getSamples().get(0).getId());
        assertEquals("S-5", result.getSamples().get(1).getId());

        assertEquals(3, result.getErrors().size());
        assertEquals(3, result.getErrors().get(0).getLineNumber());
        assertEquals(5, result.getErrors().get(1).getLineNumber());
        assertEquals(6, result.getErrors().get(2).getLineNumber());
    }

    @Test
    void wrongHeaderIsReportedSeparately() throws IOException {
        LoadResult result = loader.load(write("a;b;c", "S-1;cat;camera-1;16;READY"));

        assertTrue(result.getSamples().isEmpty());
        assertEquals("header", result.getErrors().get(0).getField());
        assertEquals(1, result.getErrors().get(0).getLineNumber());
    }

    @Test
    void emptyFileIsReportedAsHeaderError() throws IOException {
        LoadResult result = loader.load(write());

        assertEquals("header", result.getErrors().get(0).getField());
    }

    @Test
    void missingFileThrowsExceptionWithCause() {
        Path missing = tempDir.resolve("nope.txt");

        DataLoadingException ex = assertThrows(DataLoadingException.class,
                () -> loader.load(missing));

        assertNotNull(ex.getCause());
        assertTrue(ex.getCause() instanceof IOException);
        assertTrue(ex.getMessage().contains("nope.txt"));
    }

    @Test
    void invalidUtf8IsSourceError() throws IOException {
        Path file = tempDir.resolve("broken.txt");
        Files.write(file, new byte[]{(byte) 0xC3, (byte) 0x28});

        DataLoadingException ex = assertThrows(DataLoadingException.class,
                () -> loader.load(file));

        assertNotNull(ex.getCause());
    }

    @Test
    void nullPathIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> loader.load(null));
    }
}