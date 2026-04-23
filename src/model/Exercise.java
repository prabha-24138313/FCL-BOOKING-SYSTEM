package model;

/**
 * Represents an exercise type in the FLC Booking System.
 *
 * <p>Each exercise has a name and a price per session.</p>
 *
 * @author FLC Booking System
 * @version 1.0
 */
public class Exercise {

    /** The name of the exercise */
    private String name;

    /** The price per session for this exercise */
    private double price;

    /**
     * Creates a new Exercise with the given details.
     *
     * @param name  the name of the exercise (must not be empty)
     * @param price the price per session (must be non-negative)
     * @throws IllegalArgumentException if name is null/empty or price is negative
     */
    public Exercise(String name, double price) {
        // Validate name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise name cannot be null or empty");
        }
        // Validate price
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        this.name = name.trim();
        this.price = price;
    }

    /**
     * Gets the name of this exercise.
     *
     * @return the exercise name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price per session for this exercise.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns a string representation of this exercise.
     *
     * @return the exercise name
     */
    @Override
    public String toString() {
        return name;
    }
}

