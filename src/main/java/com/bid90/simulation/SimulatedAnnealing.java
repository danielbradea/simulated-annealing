package com.bid90.simulation;

import java.util.ArrayList;


/**
 * The main class representing the application for solving the Traveling Salesman Problem using Simulated Annealing.
 */
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