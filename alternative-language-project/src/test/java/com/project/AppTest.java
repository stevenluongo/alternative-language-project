package com.project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private HashMap<Integer, Cell> cells;
    @BeforeEach
    void setUp() {
        cells = new HashMap<>();

        Cell cell1 = new Cell();
        cell1.setOem("BrandA");
        cell1.setBodyWeight("150.0");
        cell1.setLaunchAnnounced("2020");
        cell1.setLaunchStatus("2021");
        cell1.setFeaturesSensors("GPS");
        cells.put(1, cell1);

        Cell cell2 = new Cell();
        cell2.setOem("BrandB");
        cell2.setBodyWeight("200.0");
        cell2.setLaunchAnnounced("2019");
        cell2.setLaunchStatus("2019");
        cell2.setFeaturesSensors("GPS, Bluetooth");
        cells.put(2, cell2);

        Cell cell3 = new Cell();
        cell3.setOem("BrandA");
        cell3.setBodyWeight("100.0"); // Lower weight to test average calculation
        cell3.setLaunchAnnounced("2019");
        cell3.setLaunchStatus("2019"); // Test data for different announce and release years
        cell3.setFeaturesSensors("");
        cells.put(3, cell3);

        Cell cell4 = new Cell();
        cell4.setOem("BrandC");
        cell4.setBodyWeight("250.0");
        cell4.setLaunchAnnounced("2021");
        cell4.setLaunchStatus("2021");
        cells.put(4, cell4); // BrandC should push the average higher for testing
    }

    @Test
    void testOemWithHighestAverageWeight() {
        assertEquals("BrandC", Cell.oemWithHighestAverageWeight(cells), "BrandC should have the highest average weight");
    }

    @Test
    void testPhonesWithDifferentAnnounceAndReleaseYears() {
        List<Cell> result = Cell.phonesWithDifferentAnnounceAndReleaseYears(cells);
        assertEquals(1, result.size(), "There should be 1 phone with different announce and release years");
        assertEquals("2020", result.get(0).getLaunchAnnounced().toString(), "The phone with different announce and release years should be from 2020");
    }

    @Test
    void testCountPhonesWithSingleFeatureSensor() {
        assertEquals(1, Cell.countPhonesWithSingleFeatureSensor(cells), "There should be 1 phone with only one feature sensor");
    }

    @Test
    void testMostPhonesLaunchedYearAfter1999() {
        assertEquals(2019, Cell.mostPhonesLaunchedYearAfter1999(cells), "The year with the most launches after 1999 should be 2020");
    }
    
}
