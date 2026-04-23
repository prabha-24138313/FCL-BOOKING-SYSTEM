package service;

import model.*;
import java.util.*;

/**
 * Manages the timetable of lessons in the FLC Booking System.
 *
 * <p>This class provides functionality to:
 * <ul>
 *   <li>Add lessons to the timetable</li>
 *   <li>Search lessons by day or exercise type</li>
 *   <li>Generate reports (lesson report, income report)</li>
 * </ul>
 * </p>
 *
 * @author FLC Booking System
 * @version 1.0
 */
public class Timetable {

    /** List of all lessons in the timetable */
    private List<Lesson> lessons = new ArrayList<>();

    /**
     * Adds a lesson to the timetable.
     *
     * @param lesson the lesson to add (must not be null)
     * @throws IllegalArgumentException if lesson is null
     */
    public void addLesson(Lesson lesson) {
        // Validate input
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson cannot be null");
        }
        lessons.add(lesson);
    }

    /**
     * Searches for lessons on a specific day.
     *
     * @param day the day to search for (must not be null)
     * @return list of lessons on that day
     * @throws IllegalArgumentException if day is null
     */
    public List<Lesson> searchByDay(DayType day) {
        // Validate input
        if (day == null) {
            throw new IllegalArgumentException("Day cannot be null");
        }

        List<Lesson> result = new ArrayList<>();

        for (Lesson l : lessons) {
            if (l.getDay() == day) {
                result.add(l);
            }
        }

        return result;
    }

    /**
     * Searches for lessons by exercise name.
     *
     * @param name the exercise name to search for (case-insensitive)
     * @return list of lessons with that exercise
     * @throws IllegalArgumentException if name is null or empty
     */
    public List<Lesson> searchByExercise(String name) {
        // Validate input
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise name cannot be null or empty");
        }

        List<Lesson> result = new ArrayList<>();

        for (Lesson l : lessons) {
            if (l.getExercise().getName().equalsIgnoreCase(name.trim())) {
                result.add(l);
            }
        }

        return result;
    }

    /**
     * Prints a report of all lessons showing details including
     * exercise name, day, time slot, member count, and average rating.
     */
    /*public void printLessonReport() {
        // Handle empty timetable
        if (lessons.isEmpty()) {
            System.out.println("\nNo lessons in the timetable.");
            return;
        }

        System.out.println("\n========== LESSON REPORT ==========");

        for (Lesson l : lessons) {
            System.out.println(
                    l.getExercise().getName()
                            + " | "
                            + l.getDay()
                            + " | "
                            + l.getTimeSlot()
                            + " | Members: "
                            + l.getMemberCount()
                            + "/" + l.getCapacity()
                            + " | Avg Rating: "
                            + String.format("%.2f", l.getAverageRating())
            );
        }
    }
**/
    /**
     * Prints an income report showing total income per exercise type
     * and identifies the highest earning exercise.
     */
    public void printIncomeReport() {
        // Handle empty timetable
        if (lessons.isEmpty()) {
            System.out.println("\nNo lessons in the timetable.");
            return;
        }

        Map<String, Double> income = new HashMap<>();

        // Calculate income for each exercise type
        for (Lesson l : lessons) {
            double lessonIncome = l.getMemberCount() * l.getExercise().getPrice();
            income.merge(
                    l.getExercise().getName(),
                    lessonIncome,
                    Double::sum
            );
        }

        // Find the highest earning exercise
        String bestExercise = "";
        double maxIncome = 0;

        for (String ex : income.keySet()) {
            if (income.get(ex) > maxIncome) {
                maxIncome = income.get(ex);
                bestExercise = ex;
            }
        }

        // Print the report
        System.out.println("\n========== INCOME REPORT ==========");

        if (income.isEmpty()) {
            System.out.println("No income data available.");
            return;
        }

        for (String ex : income.keySet()) {
            System.out.println(ex + ": £" + String.format("%.2f", income.get(ex)));
        }

        System.out.println("\nHighest Income: " + bestExercise + " - £" + String.format("%.2f", maxIncome));
    }

    /**
     * Gets all lessons in the timetable.
     *
     * @return an unmodifiable list of all lessons
     */
    public List<Lesson> getLessons() {
        return Collections.unmodifiableList(lessons);
    }

    /**
     * Gets a specific lesson by its ID.
     *
     * @param id the lesson ID to search for
     * @return the lesson with the given ID, or null if not found
     */
    public Lesson getLessonById(int id) {
        for (Lesson l : lessons) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    /**
     * Gets the total number of lessons in the timetable.
     *
     * @return the lesson count
     */
    public int getLessonCount() {
        return lessons.size();
    }
}

