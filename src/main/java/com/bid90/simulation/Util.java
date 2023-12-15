package com.bid90.simulation;

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
