package com.project;

import java.util.HashMap;
import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        CSVFile csvFile = new CSVFile("alternative-language-project/src/main/java/resources/cells.csv");
        HashMap<Integer, Cell> cells = csvFile.readAll();

        String oem = Cell.oemWithHighestAverageWeight(cells);
        System.out.println("OEM with highest average weight: " + oem);

        //2. Were there any phones announced in one year and released in another? What are they?
        System.out.println("\nPhones announced in one year and released in another:");
        List<Cell> phones = Cell.phonesWithDifferentAnnounceAndReleaseYears(cells);
        phones.forEach((phone) -> {
            System.out.println(phone.getOem() + " " + phone.getModel() + " - Announced: " + phone.getLaunchAnnounced() + ", Released: " + phone.getLaunchStatus());
        });

        //3. How many phones have only one feature sensor?
        long count = Cell.countPhonesWithSingleFeatureSensor(cells);
        System.out.println("\nNumber of phones with only one feature sensor: " + count);

        //4. What year had the most phones launched in any year later than 1999?
        int most = Cell.mostPhonesLaunchedYearAfter1999(cells);
        System.out.println("\nYear with the most phone launches after 1999: " + most);
    }

}

