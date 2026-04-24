package model;

import java.util.*;


/**
 * Represents a fitness lesson in the FLC Booking System.
 *
 * A Lesson has an associated exercise, scheduled day, time slot,
 * and can hold up to 4 members. Each lesson can receive reviews from members.
 *

 */
public class Lesson {

    /** Unique identifier for the lesson */
    private int id;

    /** The exercise type for this lesson */
    private Exercise exercise;

    /** The day of the week for this lesson */
    private DayType day;

    /** The time slot for this lesson */
    private TimeSlot timeSlot;

    /** Maximum number of members that can book this lesson */
    private int capacity = 4;

    /** List of members booked for this lesson */
    private List<Member> members = new ArrayList<>();

    /** List of reviews for this lesson */


    /**
     * Creates a new Lesson with the given details.
     *
     * @param id       the unique identifier for this lesson
     * @param exercise the exercise type (must not be null)
     * @param day      the day of the week (must not be null)
     * @param timeSlot the time slot (must not be null)
     * @throws IllegalArgumentException if any parameter is null or id is invalid
     */
    public Lesson(int id, Exercise exercise, DayType day, TimeSlot timeSlot) {
        // Validate inputs
        if (exercise == null) {
            throw new IllegalArgumentException("Exercise cannot be null");
        }
        if (day == null) {
            throw new IllegalArgumentException("Day cannot be null");
        }
        if (timeSlot == null) {
            throw new IllegalArgumentException("TimeSlot cannot be null");
        }
        if (id < 0) {
            throw new IllegalArgumentException("Lesson ID cannot be negative");
        }

        this.id = id;
        this.exercise = exercise;
        this.day = day;
        this.timeSlot = timeSlot;
    }

    /**
     * Adds a member booking to this lesson.
     *
     * <p>Booking fails if the lesson is already at full capacity.</p>
     *
     * @param member the member to add (must not be null)
     * @return true if booking was successful, false if lesson is full
     * @throws IllegalArgumentException if member is null
     */
    public boolean addBooking(Member member) {
        // Validate input
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }

        // Check if lesson is at capacity
        if (members.size() >= capacity) {
            return false;
        }

        members.add(member);
        return true;
    }



    /**
     * Gets the current number of members booked for this lesson.
     *
     * @return the number of booked members
     */
    public int getMemberCount() {
        return members.size();
    }

    /**
     * Gets the exercise associated with this lesson.
     *
     * @return the exercise
     */
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * Gets the day of the week for this lesson.
     *
     * @return the day
     */
    public DayType getDay() {
        return day;
    }

    /**
     * Gets the time slot for this lesson.
     *
     * @return the time slot
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Gets the unique identifier for this lesson.
     *
     * @return the lesson ID
     */
    public int getId() {
        return id;
    }



    /**
     * Gets the capacity of this lesson.
     *
     * @return the maximum number of members
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Checks if this lesson is fully booked.
     *
     * @return true if at capacity, false otherwise
     */
    public boolean isFull() {
        return members.size() >= capacity;
    }

    /**
     * Gets an unmodifiable list of members booked for this lesson.
     *
     * @return the list of members
     */
    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }


}

