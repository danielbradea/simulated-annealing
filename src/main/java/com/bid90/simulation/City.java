package com.bid90.simulation;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a City with a name and geographical coordinates (x, y).
 */
@Data
@AllArgsConstructor
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
