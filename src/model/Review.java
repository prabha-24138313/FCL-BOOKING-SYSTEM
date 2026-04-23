package model;

/**
 * Represents a review/rating given by a member for a lesson.
 *
 * <p>A Review contains a rating (1-5 stars) and an optional comment.
 * Members can only review lessons they have attended.</p>
 *
 * @author FLC Booking System
 * @version 1.0
 */
public class Review {

    /** The member who gave this review */
    private Member member;

    /** The rating value (1-5) */
    private int rating;

    /** The optional comment text */
    private String comment;

    /**
     * Creates a new Review with the given details.
     *
     * @param member  the member giving the review (must not be null)
     * @param rating  the rating value (1-5)
     * @param comment the comment text
     * @throws IllegalArgumentException if member is null or rating is invalid
     */
    public Review(Member member, int rating, String comment) {
        // Validate inputs
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        this.member = member;
        this.rating = rating;
        this.comment = comment;
    }

    /**
     * Gets the rating value of this review.
     *
     * @return the rating (1-5)
     */
    public int getRating() {
        return rating;
    }

    /**
     * Gets the comment text of this review.
     *
     * @return the comment, or empty string if no comment was provided
     */
    public String getComment() {
        return comment;
    }

    /**
     * Gets the member who gave this review.
     *
     * @return the member who wrote the review
     */
    public Member getMember() {
        return member;
    }
}

