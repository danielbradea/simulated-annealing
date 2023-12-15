package com.bid90.simulation;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a tour that visits a list of cities in a specific order.
 */
@Data
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
