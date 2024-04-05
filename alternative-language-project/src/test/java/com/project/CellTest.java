package com.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    private Cell cell;
    private HashMap<Integer, Cell> cellMap;


    @BeforeEach
    void setUp() {
        cell = new Cell();
        cellMap = new HashMap<>();
    }

    @Test
    void testSetAndGetOem() {
        cell.setOem("Samsung ");
        assertEquals("Samsung", cell.getOem(), "OEM should be trimmed.");

        cell.setOem(" - ");
        assertNull(cell.getOem(), "OEM should be null when set to '-'.");
    }

    @Test
    void testSetAndGetModel() {
        cell.setModel(" Galaxy S21 ");
        assertEquals("Galaxy S21", cell.getModel(), "Model should be trimmed.");

        cell.setModel("-");
        assertNull(cell.getModel(), "Model should be null when set to '-'.");
    }

    @Test
    void testSetAndGetLaunchAnnounced() {
        cell.setLaunchAnnounced("2020");
        assertEquals(Integer.valueOf(2020), cell.getLaunchAnnounced(), "Launch announced year should be extracted correctly.");

        cell.setLaunchAnnounced("Announced 2020");
        assertEquals(Integer.valueOf(2020), cell.getLaunchAnnounced(), "Launch announced year should be extracted from text.");

        cell.setLaunchAnnounced("-");
        assertNull(cell.getLaunchAnnounced(), "Launch announced should be null when set to '-'.");
    }

    @Test
    void testSetAndGetLaunchStatus() {
        cell.setLaunchStatus("Discontinued");
        assertEquals("Discontinued", cell.getLaunchStatus(), "Launch status should remain unchanged if it's a valid value.");

        cell.setLaunchStatus("Announced 2020");
        assertEquals("2020", cell.getLaunchStatus(), "Launch status should be converted to year if possible.");

        cell.setLaunchStatus("-");
        assertNull(cell.getLaunchStatus(), "Launch status should be null when set to '-'.");
    }

    @Test
    void testSetAndGetBodyDimensions() {
        cell.setBodyDimensions("163.7 x 76.4 x 7.8 mm");
        assertEquals("163.7 x 76.4 x 7.8 mm", cell.getBodyDimensions(), "Body dimensions should be trimmed.");

        cell.setBodyDimensions("-");
        assertNull(cell.getBodyDimensions(), "Body dimensions should be null when set to '-'.");
    }

    @Test
    void testSetAndGetBodyWeight() {
        cell.setBodyWeight("202 g");
        assertEquals(202f, cell.getBodyWeight(), "Body weight should be converted to float.");

        cell.setBodyWeight("-");
        assertNull(cell.getBodyWeight(), "Body weight should be null when set to '-'.");
    }

    @Test
    void testSetAndGetBodySim() {
        cell.setBodySim("Nano-SIM");
        assertEquals("Nano-SIM", cell.getBodySim(), "Body SIM should be trimmed.");

        cell.setBodySim("-");
        assertNull(cell.getBodySim(), "Body SIM should be null when set to '-'.");
    }

    @Test
    void testSetAndGetDisplayType() {
        cell.setDisplayType("Super AMOLED");
        assertEquals("Super AMOLED", cell.getDisplayType(), "Display type should be trimmed.");

        cell.setDisplayType("-");
        assertNull(cell.getDisplayType(), "Display type should be null when set to '-'.");
    }

    @Test
    void testSetAndGetDisplaySize() {
        cell.setDisplaySize("6.2 inches");
        assertEquals(6.2f, cell.getDisplaySize(), "Display size should be converted to float.");

        cell.setDisplaySize("-");
        assertNull(cell.getDisplaySize(), "Display size should be null when set to '-'.");
    }

    @Test
    void testSetAndGetDisplayResolution() {
        cell.setDisplayResolution("1080 x 2400 pixels");
        assertEquals("1080 x 2400 pixels", cell.getDisplayResolution(), "Display resolution should be trimmed.");

        cell.setDisplayResolution("-");
        assertNull(cell.getDisplayResolution(), "Display resolution should be null when set to '-'.");
    }

    @Test
    void testSetAndGetFeaturesSensors() {
        cell.setFeaturesSensors("Fingerprint (under display, optical), accelerometer, gyro, proximity, compass");
        assertEquals("Fingerprint (under display, optical), accelerometer, gyro, proximity, compass", cell.getFeaturesSensors(), "Features sensors should be trimmed.");

        cell.setFeaturesSensors("-");
        assertNull(cell.getFeaturesSensors(), "Features sensors should be null when set to '-'.");
    }

    @Test
    void testSetAndGetPlatformOs() {
        cell.setPlatformOs("Android 10, upgradable to Android 11");
        assertEquals("Android 10", cell.getPlatformOs(), "Platform OS should be trimmed and shortened to the first occurrence.");

        cell.setPlatformOs("-");
        assertNull(cell.getPlatformOs(), "Platform OS should be null when set to '-'.");
    }

    // Test utility methods directly affecting Cell objects
    @Test
    void testCalculateMeanDisplaySize() {
        Cell cell1 = new Cell();
        cell1.setDisplaySize("5.0");
        Cell cell2 = new Cell();
        cell2.setDisplaySize("7.0"); // Mean = (5.0 + 7.0) / 2

        cellMap.put(1, cell1);
        cellMap.put(2, cell2);

        assertEquals(6.0, Cell.calculateMeanDisplaySize(cellMap), 0.001, "Mean display size should be correctly calculated.");
    }
    
    @Test
    void testFindModeOem() {
        Cell cell1 = new Cell();
        cell1.setOem("BrandA");
        Cell cell2 = new Cell();
        cell2.setOem("BrandB");
        Cell cell3 = new Cell();
        cell3.setOem("BrandA"); // Mode = BrandA

        cellMap.put(1, cell1);
        cellMap.put(2, cell2);
        cellMap.put(3, cell3);

        assertEquals("BrandA", Cell.findModeOem(cellMap), "Mode OEM should be correctly identified.");
    }

    @Test
    void testListUniqueDisplayTypes() {
        Cell cell1 = new Cell();
        cell1.setDisplayType("LCD");
        Cell cell2 = new Cell();
        cell2.setDisplayType("OLED");
        Cell cell3 = new Cell();
        cell3.setDisplayType("LCD"); // Unique = LCD, OLED

        cellMap.put(1, cell1);
        cellMap.put(2, cell2);
        cellMap.put(3, cell3);

        Set<String> uniqueTypes = Cell.listUniqueDisplayTypes(cellMap);
        assertTrue(uniqueTypes.contains("LCD") && uniqueTypes.contains("OLED") && uniqueTypes.size() == 2, "Unique display types should be correctly listed.");
    }

    @Test
    void testFilterByLaunchYear() {
        Cell cell1 = new Cell();
        cell1.setLaunchAnnounced("2019");
        Cell cell2 = new Cell();
        cell2.setLaunchAnnounced("2020");

        cellMap.put(1, cell1);
        cellMap.put(2, cell2);

        HashMap<Integer, Cell> filteredCells = Cell.filterByLaunchYear(cellMap, 2020);
        assertTrue(filteredCells.containsValue(cell2) && filteredCells.size() == 1, "Cells should be filtered by launch year correctly.");
    }

    @Test
    void testAddAndRemoveCell() {
        Cell newCell = new Cell();
        newCell.setModel("NewModel");

        Cell.addCell(cellMap, 1, newCell);
        assertTrue(cellMap.containsKey(1) && cellMap.containsValue(newCell), "Cell should be added to the collection correctly.");

        Cell removedCell = Cell.removeCell(cellMap, 1);
        assertEquals(newCell, removedCell, "Removed cell should be the same as the added one.");
        assertFalse(cellMap.containsKey(1), "Cell should be removed from the collection correctly.");
    }

    // Example test for toString
    @Test
    void testToString() {
        cell.setOem("Brand");
        cell.setModel("Model123");
        cell.setLaunchAnnounced("2020");
        cell.setLaunchStatus("Available");
        String expected = "Cell{oem='Brand', model='Model123', launchAnnounced=2020, launchStatus='Available', bodyDimensions='null', bodyWeight=null, bodySim='null', displayType='null', displaySize=null, displayResolution='null', featuresSensors='null', platformOs='null'}";
        assertEquals(expected, cell.toString(), "toString should return the correct string representation.");
    }
}
