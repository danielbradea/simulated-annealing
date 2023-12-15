# Traveling Salesman Problem Solver

## Overview
This Java application provides a solution to the Traveling Salesman Problem (TSP) using the Simulated Annealing algorithm.
The program defines classes to represent cities, tours, and utility functions for distance calculations and simulated annealing operations.

## Requirements
- Java 17 or later
- Maven 3.9.1
- Lombok library

## Classes

### 1. Tour Class
- Represents a tour that visits a list of cities in a specific order.
- Contains methods to shuffle the order of cities, calculate the total distance, get the number of cities, and swap two randomly selected cities.
```java
public class Tour {

    /**
     * The list of cities in the tour.
     */
    private List<City> cities;

    /**
     * The total distance of the tour.
     */
    private double distance;

    /**
     * Constructs a tour with the given list of cities.
     *
     * @param cities The list of cities to be included in the tour.
     */
    public Tour(List<City> cities) {
        this.cities = new ArrayList<>(cities);
        distance = 0;
    }

    /**
     * Shuffles the order of cities in the tour.
     */
    public void shuffle() {
        Collections.shuffle(cities);
        distance = 0;
    }

    /**
     * Calculates and returns the total distance of the tour.
     *
     * @return The total distance of the tour.
     */
    public double getDistance() {
        if (distance == 0) {
            double totalDistance = 0;
            for (int index = 0; index < tourSize(); index++) {
                var city1 = cities.get(index);
                var city2 = (index + 1 < tourSize()) ? cities.get(index + 1) : cities.get(0);
                totalDistance += Util.distance(city1, city2);
            }
            distance = totalDistance;
        }
        return distance;
    }


    /**
     * Returns the number of cities in the tour.
     *
     * @return The number of cities in the tour.
     */
    public int tourSize() {
        return cities.size();
    }

    /**
     * Returns a string representation of the tour.
     *
     * @return A string representation of the tour.
     */
    @Override
    public String toString() {
        return cities.stream()
                .map(city -> "('" + city.getName() + "'," + city.getX() + "," + city.getY() + ")")
                .collect(Collectors.joining("->"));
    }

    /**
     * Swaps the positions of two randomly selected cities in the tour.
     */
    public void swapTwoCities() {
        var index1 = (int) (Math.random() * tourSize());
        var index2 = (int) (Math.random() * tourSize());
        while (index1 == index2) {
            index2 = (int) (Math.random() * tourSize());
        }
        Collections.swap(this.cities, index1, index2);
        distance = 0;
    }
}
```

### 2. Util Class
- Contains utility methods for calculating the Euclidean distance between two cities and determining the acceptance probability in the simulated annealing algorithm.
```java
public class Util {

    /**
     * Calculates the Euclidean distance between two City objects based on their coordinates.
     *
     * @param c1 The first City.
     * @param c2 The second City.
     * @return The Euclidean distance between the two City objects.
     */
    public static double distance(City c1, City c2) {
        // Calculate the difference in x-coordinates
        double xDistance = Math.abs(c1.getX() - c2.getX());

        // Calculate the difference in y-coordinates
        double yDistance = Math.abs(c1.getY() - c2.getY());

        // Calculate the Euclidean distance using the distance formula
        return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }

    /**
     * Calculates the acceptance probability for a new solution in the simulated annealing algorithm.
     *
     * @param currentDistance The distance of the current solution.
     * @param newDistance     The distance of the new solution being considered.
     * @param temperature     The current temperature in the simulated annealing process.
     * @return The acceptance probability for the new solution.
     */
    public static double acceptanceProbability(double currentDistance, double newDistance, double temperature) {
        /**
         * If the new solution has a shorter distance than the current solution,
         * accept the new solution with certainty (probability = 1.0).
         */
        if (newDistance < currentDistance) {
            return 1.0;
        }

        /**
         * If the new solution has a longer distance than the current solution,
         * calculate the acceptance probability based on the temperature and the
         * difference in distances using the Metropolis criterion.
         */
        return Math.exp((currentDistance - newDistance) / temperature);
    }
}
```

### 3. City Class
- Represents a city with a name and coordinates (x, y).
```java
public class City {
    /**
     * The name of the city.
     */
    private String name;

    /**
     * The x-coordinate of the city.
     */
    private double x;

    /**
     * The y-coordinate of the city.
     */
    private double y;
}
```

### 4. SimulatedAnnealing Class
- The main class representing the application for solving the Traveling Salesman Problem using Simulated Annealing.
- Defines constants for temperature and cooling rate.
- Instantiates City objects, creates a Tour object, shuffles the order of cities, and performs the simulated annealing algorithm to find the optimal tour.
```java
public class SimulatedAnnealing {

    // Constants for temperature and cooling rate
    public static final double TEMP = 10000;
    public static final double COOLING_RATE = 0.003;

    /**
     * The main method of the application.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // Instantiate City objects for six different cities
        var city1 = new City("Bucharest", 44.4268, 26.1025);
        var city2 = new City("Cluj-Napoca", 46.7712, 23.6236);
        var city3 = new City("Timisoara", 45.9432, 21.2356);
        var city4 = new City("Iasi", 47.1585, 27.6014);
        var city5 = new City("Constanta", 44.1810, 28.6348);
        var city6 = new City("Resita", 45.2970, 21.8867);

        // Create an ArrayList to store City objects
        var cities = new ArrayList<City>();
        cities.add(city1);
        cities.add(city2);
        cities.add(city3);
        cities.add(city4);
        cities.add(city5);
        cities.add(city6);

        // Create a Tour object representing the current solution
        var currentSolution = new Tour(cities);

        // Shuffle the order of cities in the current solution
        currentSolution.shuffle();

        // Output initial solution information
        System.out.println("Total distance of initial solution: " + currentSolution.getDistance());
        System.out.println("Tour: " + currentSolution);

        // Create a Tour object representing the best solution
        var bestTourSolution = new Tour(currentSolution.getCities());

        // Simulated Annealing loop
        for (double temperature = TEMP; temperature > 1; temperature *= 1 - COOLING_RATE) {

            // Create a new solution by swapping two cities in the current solution
            var newTour = new Tour(currentSolution.getCities());
            newTour.swapTwoCities();

            // Calculate distances for comparison
            var bestSolutionDistance = bestTourSolution.getDistance();
            var newTourDistance = newTour.getDistance();

            // Determine whether to accept the new solution based on acceptance probability
            if (Math.random() < Util.acceptanceProbability(bestSolutionDistance, newTourDistance, temperature)) {
                currentSolution = new Tour(newTour.getCities());
            }

            // Update the best solution if the current solution is better
            if (currentSolution.getDistance() < bestTourSolution.getDistance()) {
                bestTourSolution = new Tour(currentSolution.getCities());
            }
        }

        // Output final solution information
        System.out.println("Final solution distance: " + bestTourSolution.getDistance());
        System.out.println("Tour: " + bestTourSolution);
    }
}
```

## How to Use
1. Ensure you have Java 17 or later installed on your system.
2. Ensure you have Maven 3.9.1 installed.
3. Make sure the Lombok library is included in your project.

## Building and Running with Maven
```
mvn clean install
java -jar target/traveling-salesman-1.0-SNAPSHOT.jar
```
1. View the initial solution information, including the total distance and tour order.
2. Observe the simulated annealing loop, where the program iteratively tries to improve the solution by swapping cities based on acceptance probabilities.
3. Once the loop is complete, view the final solution information, including the optimal tour and its total distance.

Feel free to customize the city coordinates, add more cities, or adjust simulation parameters in the **App** class to explore different scenarios.

**Note:** Ensure that your Java environment is set up correctly to run Java 17 code, and Lombok is properly configured in your project. If you encounter any issues, refer to the Lombok documentation for integration instructions.
