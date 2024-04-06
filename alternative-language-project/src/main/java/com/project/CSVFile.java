package com.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVFile {
    private String filePath;

    // Constructor to set the file path
    public CSVFile(String filePath) {
        this.filePath = filePath;
    }

    // Method to read the entire CSV file and return a HashMap of Cell objects
    public HashMap<Integer, Cell> readAll() {
        HashMap<Integer, Cell> rows = new HashMap<>();
        int rowIndex = 1; // Start indexing at 1 for readability

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read the header line to skip it

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1); // Include trailing empty strings for columns
                
                // Ensure there are enough columns for all attributes
                if (values.length >= 10) { // Adjust this based on the number of columns in your CSV
                    Cell row = new Cell();

                    // Set attributes for each Cell object from the CSV columns
                    row.setOem(values[0]);
                    row.setModel(values[1]);
                    row.setLaunchAnnounced(values[2]);
                    row.setLaunchStatus(values[3]);
                    row.setBodyDimensions(values[4]);
                    row.setBodyWeight(values[5]);
                    row.setBodySim(values[6]);
                    row.setDisplayType(values[7]);
                    row.setDisplaySize(values[8]);
                    row.setDisplayResolution(values[9]);
                    row.setFeaturesSensors(values[10]);
                    row.setPlatformOs(values.length > 11 ? values[11] : ""); // Check for presence of column

                    // Add the row object to the HashMap with a unique index
                    rows.put(rowIndex++, row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rows;
    }

    public static void ensureFileNotEmpty(File file) throws FileNotFoundException, IllegalArgumentException {
        Scanner scanner = new Scanner(file);
        if (!scanner.hasNext()) {
            scanner.close();
            throw new IllegalArgumentException("File is empty");
        }
        scanner.close();
    }
}
