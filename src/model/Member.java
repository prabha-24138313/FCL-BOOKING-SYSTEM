package model;

import java.util.*;

/**
 * Represents a member in the FLC Booking System.
 *
 * <p>A Member can book lessons and leave reviews for lessons they have attended.
 * Members cannot book multiple lessons at the same time slot on the same day.</p>
 *
 * @author FLC Booking System
 * @version 1.0
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
     *
     * <p>Booking fails if:
     * <ul>
     *   <li>The lesson is null</li>
     *   <li>The member already has a booking at the same day and time</li>
     *   <li>The lesson is already at full capacity</li>
     * </ul>
     * </p>
     *
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
     * Adds a review for a lesson with a default comment.
     *
     * @param lesson the lesson to review (must not be null)
     * @param rating the rating value (1-5)
     * @throws IllegalArgumentException if lesson is null or rating is invalid
     */
    public void reviewLesson(Lesson lesson, int rating) {
        reviewLesson(lesson, rating, "Good session");
    }

    /**
     * Adds a review for a lesson with a custom comment.
     *
     * @param lesson  the lesson to review (must not be null)
     * @param rating  the rating value (1-5)
     * @param comment the comment text
     * @throws IllegalArgumentException if lesson is null or rating is invalid
     */
    public void reviewLesson(Lesson lesson, int rating, String comment) {
        // Validate input
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson cannot be null");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        // Create and add the review
        Review review = new Review(this, rating, comment);
        lesson.addReview(review);
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

