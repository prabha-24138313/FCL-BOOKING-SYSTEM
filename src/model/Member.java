package model;

import java.util.*;
import model.Lesson;

/**
 * Represents a member in the FLC Booking System.
 *

 * Members cannot book multiple lessons at the same time slot on the same day.
 *

 */
public class Member {

    /** Unique identifier for the member */
    private int id;

    /** Name of the member */
    private String name;

    /** List of lessons booked by this member */
    private List<Lesson> bookings = new ArrayList<>();

    /**
     * Creates a new Member with the given details.
     *
     * @param id   the unique identifier for this member
     * @param name the name of the member (must not be empty)
     * @throws IllegalArgumentException if name is null/empty or id is invalid
     */
    public Member(int id, String name) {
        // Validate name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Member name cannot be null or empty");
        }
        // Validate id
        if (id < 0) {
            throw new IllegalArgumentException("Member ID cannot be negative");
        }

        this.id = id;
        this.name = name.trim();
    }

    /**
     * Books a lesson for this member.

     * @param lesson the lesson to book (must not be null)
     * @return true if booking was successful, false otherwise
     * @throws IllegalArgumentException if lesson is null
     */
    public boolean bookLesson(Lesson lesson) {
        // Validate input
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson cannot be null");
        }

        // Check for time slot conflicts on the same day
        for (Lesson l : bookings) {
            if (l.getDay() == lesson.getDay() &&
                    l.getTimeSlot() == lesson.getTimeSlot()) {
                return false;
            }
        }

        // Try to add booking to the lesson
        if (lesson.addBooking(this)) {
            bookings.add(lesson);
            return true;
        }

        return false;
    }





    /**
     * Gets the name of this member.
     *
     * @return the member name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the unique identifier of this member.
     *
     * @return the member ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets an unmodifiable list of this member's bookings.
     *
     * @return the list of booked lessons
     */
    public List<Lesson> getBookings() {
        return Collections.unmodifiableList(bookings);
    }

    /**
     * Gets the number of lessons this member has booked.
     *
     * @return the booking count
     */
    public int getBookingCount() {
        return bookings.size();
    }

    /**
     * Cancels a specific booking for this member.
     *
     * @param lesson the lesson to cancel
     * @return true if the booking was found and cancelled, false otherwise
     */
    public boolean cancelBooking(Lesson lesson) {
        if (lesson == null) {
            return false;
        }
        return bookings.remove(lesson);
    }

    /**
     * Returns a string representation of this member.
     *
     * @return member name and ID
     */
    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}

