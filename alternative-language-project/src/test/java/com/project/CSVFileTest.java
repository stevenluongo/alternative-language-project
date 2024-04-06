package com.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.nio.file.Files;
import java.nio.file.Path;


class CSVFileTest {

    @TempDir
    File tempDir; // JUnit will provide a temporary directory for the test

    @Test
    public void testEnsureFileNotEmptyWithEmptyFile() throws IOException {
        // Create an empty file in the temporary directory
        File emptyFile = new File(tempDir, "emptyFile.txt");
        emptyFile.createNewFile();
        
        // Test
        assertThrows(IllegalArgumentException.class, () -> CSVFile.ensureFileNotEmpty(emptyFile),
            "Expected ensureFileNotEmpty to throw IllegalArgumentException for an empty file, but it didn't");
    }

    @Test
    public void testEnsureFileNotEmptyWithFullFile() throws IOException {
        // Create a file with content in the temporary directory
        File fullFile = new File(tempDir, "fullFile.txt");
        Files.writeString(Path.of(fullFile.getPath()), "This is some content in the file.");

        // Test to ensure no exception is thrown for a non-empty file
        assertDoesNotThrow(() -> CSVFile.ensureFileNotEmpty(fullFile),
            "Expected ensureFileNotEmpty to not throw any exception for a non-empty file, but it did");
    }
}