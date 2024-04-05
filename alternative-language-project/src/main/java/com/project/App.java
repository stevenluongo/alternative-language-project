package com.project;

import java.util.HashMap;
import java.util.Set;

public class App 
{
    public static void main( String[] args )
    {
        // Replace "path/to/your/cells.csv" with the actual path to your CSV file
        CSVFile csvFile = new CSVFile("alternative-language-project/src/main/java/resources/cells.csv");
        HashMap<Integer, Cell> cells = csvFile.readAll();

        System.out.println(cells.get(1).toString()); // Example: Print data for the first row

        Float mean = Cell.calculateMeanDisplaySize(cells); // Access the static method in a static way

        String mode = Cell.findModeOem(cells);

        System.out.println(mean);

        System.out.println(mode);

        Set<String> listUniqueDisplayTypes = Cell.listUniqueDisplayTypes(cells);

        System.out.println(listUniqueDisplayTypes);
    }
}
