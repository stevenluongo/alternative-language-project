# Java Language Exploration for Mobile Phone Data Analysis

<br/>

## Language Selection
Java Version 21.0.2
For this project, Java version 21.0.2 was chosen as the programming language. This LTS (Long-Term Support) version offers a blend of stability and modern features, making it ideal for building reliable and efficient applications.

### Why Java?
Java was selected for several key reasons:

* Portability: Write once, run anywhere (WORA) capability, thanks to the JVM (Java Virtual Machine).
* Maturity and Ecosystem: A vast array of libraries and frameworks are available, supporting a wide range of use cases from web applications to data analysis.
* Object-Oriented: Encourages cleaner, modular code that's easier to manage and extend.
* Performance: JIT (Just-In-Time) compilation provides high performance, close to native languages like C++.

<br/>

## Language Features
Java's rich feature set makes it well-suited for handling the diverse aspects of this project, from data parsing to complex analyses.

* Object-Oriented Programming (OOP):
Java is inherently object-oriented, which was crucial for structuring our data models (e.g., the Cell class) and encapsulating behavior related to mobile phone data.

* File Ingestion:
Java's java.io and java.nio packages facilitate file operations, enabling efficient reading from and writing to files. This capability was essential for processing the dataset of mobile phones.

* Conditional and Assignment Statements:
Java supports extensive control flow mechanisms including if-else blocks, switch statements, and traditional assignment operations. These were used to implement logic for data transformation and analysis.

* Loops and Subprograms:
For-loops, while-loops, and enhanced for-loops iterate over data collections. Java methods (subprograms) modularize the code, making the data processing pipeline clearer and more maintainable.

* Unit Testing and Exception Handling:
JUnit facilitated robust unit testing, ensuring the reliability of data processing methods. Java's exception handling framework helped in managing runtime errors effectively, ensuring the application's resilience.

<br/>

## Libraries Used

* JUnit:
For unit testing, JUnit was instrumental in validating the correctness of data parsing and analysis logic.

* Java Stream API:
Part of the standard library, the Stream API supported efficient data processing (filtering, mapping, reducing) operations on the dataset.

* Apache Commons CSV:
This library simplified CSV file operations, making it straightforward to read and parse the dataset of mobile phones.

<br/>

## Analysis Results
### 1. OEM with the Highest Average Weight: 

### My process:
* Iterate over the HashMap of Cell objects to accumulate weights for each OEM and then calculate the average.
* Store the results in a Map<String, Float> where keys are OEMs and values are the average weights.
* Find the entry with the highest value.

### Code
```
public static String oemWithHighestAverageWeight(HashMap<Integer, Cell> cells) {
  Map<String, Double> averageWeights = cells.values().stream()
          .filter(cell -> cell.bodyWeight != null)
          .collect(Collectors.groupingBy(Cell::getOem,
                  Collectors.averagingDouble(Cell::getBodyWeight)));
  
  return Collections.max(averageWeights.entrySet(), Map.Entry.comparingByValue()).getKey();
}
```

### Result
* Icemobile emerged as the OEM with the heaviest average phone body weight.

<br/>

### 2. Phones Announced and Released in Different Years:

### My process:
* This question requires comparing the launchAnnounced year to the launchStatus year.

### Code
```
public static List<Cell> phonesWithDifferentAnnounceAndReleaseYears(HashMap<Integer, Cell> cells) {
  return cells.values().stream()
          .filter(cell -> cell.launchAnnounced != null && cell.launchStatus != null && !cell.launchStatus.equals("Discontinued") &&  !cell.launchStatus.equals("Cancelled"))
          .filter(cell -> {
              Integer releaseYear = extractYear(cell.launchStatus);
              return releaseYear != null && !releaseYear.equals(cell.launchAnnounced);
          })
          .map(cell -> cell)
          .collect(Collectors.toList());
}
```

### Result:
* Mitac MIO Leap G50 - Announced: 2008, Released: 2009
* Mitac MIO Leap K1 - Announced: 2008, Released: 2009
* Motorola XT701 - Announced: 2009, Released: 2010
* Sharp AQUOS  941SH - Announced: 2009, Released: 2010
* Sharp 940SH - Announced: 2009, Released: 2010

<br/>

### 3. Number of Phones with Only One Feature Sensor: 

### My process:
* This question necessitates parsing the featuresSensors field to count the number of sensors listed for each phone.

### Code
```
public static long countPhonesWithSingleFeatureSensor(HashMap<Integer, Cell> cells) {
    return cells.values().stream()
            .filter(cell -> cell.featuresSensors != null && !cell.featuresSensors.isEmpty())
            .filter(cell -> Arrays.stream(cell.featuresSensors.split(",")).count() == 1)
            .count();
}
```

### Result:
* 845 phones were found to have just a single feature sensor.

<br/>

### 4. Year with Most Phone Launches Post-1999: 

### My process:
* For this, you'd filter Cell objects by launchAnnounced greater than 1999, then count the occurrences of each year. 

### Code
```
public static int mostPhonesLaunchedYearAfter1999(HashMap<Integer, Cell> cells) {
    return cells.values().stream()
            .filter(cell -> cell.launchAnnounced != null && cell.launchAnnounced > 1999)
            .collect(Collectors.groupingBy(Cell::getLaunchAnnounced, Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .get()
            .getKey();
}
```

### Result:
* 2019 was identified as the year with the highest number of phone launches.

<br/>

### Output: 
<img width="684" alt="Screenshot 2024-04-06 at 9 55 58 AM" src="https://github.com/stevenluongo/alternative-language-project/assets/53283472/c99c714e-96b1-4f04-ab0b-1b9c524a20d3">

