package com.project;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;


public class Cell {
    private String oem;
    private String model;
    private Integer launchAnnounced;
    private String launchStatus;
    private String bodyDimensions;
    private Float bodyWeight;
    private String bodySim;
    private String displayType;
    private Float displaySize;
    private String displayResolution;
    private String featuresSensors;
    private String platformOs;

    // Constructors
    public Cell() {
    }

    // Getters and setters with data cleaning and transformation logic

    public String getOem() {
        return oem;
    }

    public void setOem(String oem) {
        this.oem = cleanString(oem);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = cleanString(model);
    }

    public Integer getLaunchAnnounced() {
        return launchAnnounced;
    }

    public void setLaunchAnnounced(String launchAnnounced) {
        this.launchAnnounced = extractYear(launchAnnounced);
    }

    public String getLaunchStatus() {
        return launchStatus;
    }

    public void setLaunchStatus(String launchStatus) {
        if ("Discontinued".equals(launchStatus) || "Cancelled".equals(launchStatus)) {
            this.launchStatus = launchStatus;
        } else {
            this.launchStatus = cleanString(launchStatus);
            // Attempt to extract year, if it fails, set to null or keep original value based on requirements
            Integer year = extractYear(launchStatus);
            if(year != null) {
                this.launchStatus = String.valueOf(year);
            }
        }
    }

    public String getBodyDimensions() {
        return bodyDimensions;
    }

    public void setBodyDimensions(String bodyDimensions) {
        this.bodyDimensions = cleanString(bodyDimensions);
    }

    public Float getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(String bodyWeight) {
        this.bodyWeight = extractFloat(bodyWeight);
    }

    public String getBodySim() {
        return bodySim;
    }

    public void setBodySim(String bodySim) {
        this.bodySim = cleanString(bodySim);
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = cleanString(displayType);
    }

    public Float getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(String displaySize) {
        this.displaySize = extractFloat(displaySize);
    }

    public String getDisplayResolution() {
        return displayResolution;
    }

    public void setDisplayResolution(String displayResolution) {
        this.displayResolution = cleanString(displayResolution);
    }

    public String getFeaturesSensors() {
        return featuresSensors;
    }

    public void setFeaturesSensors(String featuresSensors) {
        this.featuresSensors = cleanString(featuresSensors);
    }

    public String getPlatformOs() {
        return platformOs;
    }

    public void setPlatformOs(String platformOs) {
        this.platformOs = cleanStringAndShorten(platformOs);
    }

    // Utility methods for data cleaning and transformation
    private String cleanString(String input) {
        if (input == null || input.trim().isEmpty() || "-".equals(input.trim())) {
            return null;
        }
        return input.trim();
    }

    public static Integer extractYear(String text) {
        Pattern pattern = Pattern.compile("\\d{4}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return Integer.valueOf(matcher.group());
        }
        return null;
    }

    private Float extractFloat(String text) {
        Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return Float.valueOf(matcher.group(1));
        }
        return null;
    }

    private String cleanStringAndShorten(String text) {
        text = cleanString(text);
        if (text != null) {
            Pattern pattern = Pattern.compile("^(.*?)(,|$)");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return text;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "oem='" + oem + '\'' +
                ", model='" + model + '\'' +
                ", launchAnnounced=" + launchAnnounced +
                ", launchStatus='" + launchStatus + '\'' +
                ", bodyDimensions='" + bodyDimensions + '\'' +
                ", bodyWeight=" + bodyWeight +
                ", bodySim='" + bodySim + '\'' +
                ", displayType='" + displayType + '\'' +
                ", displaySize=" + displaySize +
                ", displayResolution='" + displayResolution + '\'' +
                ", featuresSensors='" + featuresSensors + '\'' +
                ", platformOs='" + platformOs + '\'' +
                '}';
    }

    // Calculate the mean of a numeric column (e.g., displaySize) from a HashMap
    public static float calculateMeanDisplaySize(HashMap<Integer, Cell> cells) {
        float sum = 0;
        int count = 0;
        for (Cell cell : cells.values()) {
            if (cell.displaySize != null) {
                sum += cell.displaySize;
                count++;
            }
        }
        return count > 0 ? sum / count : 0;
    }

    // Find mode of a categorical column (e.g., oem) from a HashMap
    public static String findModeOem(HashMap<Integer, Cell> cells) {
        Map<String, Long> frequencyMap = cells.values().stream()
                .filter(c -> c.oem != null)
                .map(Cell::getOem)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return frequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    // List unique values for a column (e.g., displayType) from a HashMap
    public static Set<String> listUniqueDisplayTypes(HashMap<Integer, Cell> cells) {
        return cells.values().stream()
                .map(Cell::getDisplayType)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    // Method to filter cells by a minimum launch year
    public static HashMap<Integer, Cell> filterByLaunchYear(HashMap<Integer, Cell> cells, int year) {
        return cells.entrySet().stream()
                .filter(entry -> entry.getValue().getLaunchAnnounced() != null && entry.getValue().getLaunchAnnounced() >= year)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));
    }

    // Method to add a new Cell object to the collection
    public static void addCell(HashMap<Integer, Cell> cells, Integer key, Cell cell) {
        cells.put(key, cell);
    }

    // Method to remove a Cell object from the collection by key
    public static Cell removeCell(HashMap<Integer, Cell> cells, Integer key) {
        return cells.remove(key);
    }

    // 1. Calculate the OEM with the highest average body weight
    public static String oemWithHighestAverageWeight(HashMap<Integer, Cell> cells) {
        Map<String, Double> averageWeights = cells.values().stream()
                .filter(cell -> cell.bodyWeight != null)
                .collect(Collectors.groupingBy(Cell::getOem,
                        Collectors.averagingDouble(Cell::getBodyWeight)));

        return Collections.max(averageWeights.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    // 2. Find phones announced in one year and released in another
    public static List<Cell> phonesWithDifferentAnnounceAndReleaseYears(HashMap<Integer, Cell> cells) {
        return cells.values().stream()
                .filter(cell -> cell.launchAnnounced != null && cell.launchStatus != null && !cell.launchStatus.equals("Discontinued") && !cell.launchStatus.equals("Cancelled"))
                .filter(cell -> {
                    Integer releaseYear = extractYear(cell.launchStatus);
                    return releaseYear != null && !releaseYear.equals(cell.launchAnnounced);
                })
                .map(cell -> cell)
                .collect(Collectors.toList());
    }

    // 3. Count phones with only one feature sensor
    public static long countPhonesWithSingleFeatureSensor(HashMap<Integer, Cell> cells) {
        return cells.values().stream()
                .filter(cell -> cell.featuresSensors != null && !cell.featuresSensors.isEmpty())
                .filter(cell -> Arrays.stream(cell.featuresSensors.split(",")).count() == 1)
                .count();
    }
    
    // 4. Find the year with the most phone launches after 1999
    public static int mostPhonesLaunchedYearAfter1999(HashMap<Integer, Cell> cells) {
        return cells.values().stream()
                .filter(cell -> cell.launchAnnounced != null && cell.launchAnnounced > 1999)
                .collect(Collectors.groupingBy(Cell::getLaunchAnnounced, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }
    
    
}
