package main;

import model.*;
import service.Timetable;
import java.util.*;

/**
 * Command-line interface for the FLC Booking System.
 *
 * <p>This class provides a text-based menu system for:
 * <ul>
 *   <li>Viewing and searching lessons</li>
 *   <li>Booking and canceling lessons</li>
 *   <li>Adding reviews</li>
 *   <li>Generating reports</li>
 *   <li>Managing members</li>
 * </ul>
 * </p>
 *
 * @author FLC Booking System
 * @version 1.0
 */
public class BookingSystem {

    /** The timetable containing all lessons */
    private Timetable timetable;

    /** List of all members */
    private List<Member> members;

    /** Scanner for user input */
    private Scanner scanner;

    /** Counter for generating new member IDs */
    private int memberIdCounter = 11;

    /**
     * Creates a new BookingSystem and initializes the data.
     */
    public BookingSystem() {
        timetable = new Timetable();
        members = new ArrayList<>();
        scanner = new Scanner(System.in);
        initializeData();
    }

    /**
     * Initializes the system with sample data:
     * - Creates 4 exercise types
     * - Creates 48 lessons (8 weeks x 6 lessons per week)
     * - Creates 10 sample members
     */
    private void initializeData() {
        // Create exercises
        Exercise yoga = new Exercise("Yoga", 10);
        Exercise zumba = new Exercise("Zumba", 12);
        Exercise aquacise = new Exercise("Aquacise", 15);
        Exercise boxfit = new Exercise("Box Fit", 14);

        Exercise[] exercises = {yoga, zumba, aquacise, boxfit};

        // Create 48 lessons (8 weeks x 6 lessons per week)
        int id = 1;
        for (int week = 1; week <= 8; week++) {
            timetable.addLesson(new Lesson(id++, exercises[week % 4], DayType.SATURDAY, TimeSlot.MORNING));
            timetable.addLesson(new Lesson(id++, exercises[(week + 1) % 4], DayType.SATURDAY, TimeSlot.AFTERNOON));
            timetable.addLesson(new Lesson(id++, exercises[(week + 2) % 4], DayType.SATURDAY, TimeSlot.EVENING));
            timetable.addLesson(new Lesson(id++, exercises[(week + 3) % 4], DayType.SUNDAY, TimeSlot.MORNING));
            timetable.addLesson(new Lesson(id++, exercises[week % 4], DayType.SUNDAY, TimeSlot.AFTERNOON));
            timetable.addLesson(new Lesson(id++, exercises[(week + 1) % 4], DayType.SUNDAY, TimeSlot.EVENING));
        }

        // Create sample members
        members.add(new Member(1, "Alice"));
        members.add(new Member(2, "Bob"));
        members.add(new Member(3, "Carol"));
        members.add(new Member(4, "David"));
        members.add(new Member(5, "Emma"));
        members.add(new Member(6, "Frank"));
        members.add(new Member(7, "Grace"));
        members.add(new Member(8, "Henry"));
        members.add(new Member(9, "Isla"));
        members.add(new Member(10, "Jack"));
    }

    /**
     * Runs the main menu loop of the booking system.
     * Continues until the user chooses to exit.
     */
    public void run() {
        boolean running = true;
        while (running) {
            try {
                displayMainMenu();
                int choice = getChoice();
                running = handleMenuChoice(choice);
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
        System.out.println("Thank you for using FLC Booking System. Goodbye!");
    }

    /**
     * Displays the main menu options.
     */
    private void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println("     FLC BOOKING SYSTEM - MAIN MENU     ");
        System.out.println("========================================");
        System.out.println("1. View All Lessons");
        System.out.println("2. Search Lessons by Day");
        System.out.println("3. Search Lessons by Exercise");
        System.out.println("4. Book a Lesson");
        System.out.println("5. Cancel a Booking");
        System.out.println("6. Add Review");
        System.out.println("7. View Member Bookings");
        System.out.println("8. Lesson Report");
        System.out.println("9. Income Report");
        System.out.println("10. Add New Member");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Gets the user's menu choice with error handling.
     *
     * @return the user's choice as an integer, or -1 if invalid input
     */
    private int getChoice() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Clear the invalid input
            return -1;
        }
    }

    /**
     * Handles the user's menu choice.
     *
     * @param choice the menu choice number
     * @return true to continue the menu loop, false to exit
     */
    private boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                viewAllLessons();
                break;
            case 2:
                searchByDay();
                break;
            case 3:
                searchByExercise();
                break;
            case 4:
                bookLesson();
                break;
            case 5:
                cancelBooking();
                break;
            case 6:
                addReview();
                break;
            case 7:
                viewMemberBookings();
                break;
            case 8:
                timetable.printLessonReport();
                break;
            case 9:
                timetable.printIncomeReport();
                break;
            case 10:
                addNewMember();
                break;
            case 0:
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true;
    }

    /**
     * Displays all lessons in the timetable.
     */
    private void viewAllLessons() {
        System.out.println("\n========== ALL LESSONS ==========");
        List<Lesson> lessons = timetable.getLessons();

        if (lessons.isEmpty()) {
            System.out.println("No lessons available.");
            return;
        }

        for (Lesson l : lessons) {
            System.out.printf("ID: %d | %s | %s | %s | Members: %d/%d | £%.2f%n",
                    l.getId(),
                    l.getExercise().getName(),
                    l.getDay(),
                    l.getTimeSlot(),
                    l.getMemberCount(),
                    l.getCapacity(),
                    l.getExercise().getPrice());
        }
    }

    /**
     * Searches for lessons by day.
     */
    private void searchByDay() {
        System.out.println("\n========== SEARCH BY DAY ==========");
        System.out.println("1. Saturday");
        System.out.println("2. Sunday");
        System.out.print("Select day: ");

        int dayChoice = getChoice();

        if (dayChoice < 1 || dayChoice > 2) {
            System.out.println("Invalid day selection.");
            return;
        }

        DayType day = (dayChoice == 1) ? DayType.SATURDAY : DayType.SUNDAY;
        List<Lesson> results = timetable.searchByDay(day);

        displayLessonList(results);
    }

    /**
     * Searches for lessons by exercise name.
     */
    private void searchByExercise() {
        System.out.print("\nEnter exercise name (Yoga, Zumba, Aquacise, Box Fit): ");

        try {
            scanner.nextLine(); // Consume the newline
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Exercise name cannot be empty.");
                return;
            }

            List<Lesson> results = timetable.searchByExercise(name);
            displayLessonList(results);
        } catch (Exception e) {
            System.out.println("Error searching for exercise: " + e.getMessage());
        }
    }

    /**
     * Displays a list of lessons.
     *
     * @param lessons the list of lessons to display
     */
    private void displayLessonList(List<Lesson> lessons) {
        if (lessons == null || lessons.isEmpty()) {
            System.out.println("No lessons found.");
            return;
        }

        System.out.println("\n========== SEARCH RESULTS ==========");
        for (Lesson l : lessons) {
            System.out.printf("ID: %d | %s | %s | %s | Members: %d/%d | £%.2f%n",
                    l.getId(),
                    l.getExercise().getName(),
                    l.getDay(),
                    l.getTimeSlot(),
                    l.getMemberCount(),
                    l.getCapacity(),
                    l.getExercise().getPrice());
        }
    }

    /**
     * Books a lesson for a member.
     */
    private void bookLesson() {
        System.out.println("\n========== BOOK A LESSON ==========");
        viewAllLessons();

        System.out.print("\nEnter Member ID: ");
        int memberId = getChoice();

        Member member = findMember(memberId);

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        System.out.print("Enter Lesson ID: ");
        int lessonId = getChoice();

        Lesson lesson = findLesson(lessonId);

        if (lesson == null) {
            System.out.println("Lesson not found.");
            return;
        }

        // Check if lesson is full
        if (lesson.isFull()) {
            System.out.println("Booking failed! This lesson is already full.");
            return;
        }

        if (member.bookLesson(lesson)) {
            System.out.println("Booking successful!");
        } else {
            System.out.println("Booking failed! You may already have a booking at this time.");
        }
    }

    /**
     * Cancels a booking for a member.
     */
    private void cancelBooking() {
        System.out.println("\n========== CANCEL BOOKING ==========");

        System.out.print("Enter Member ID: ");
        int memberId = getChoice();

        Member member = findMember(memberId);

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        // Show member's bookings
        List<Lesson> bookings = member.getBookings();
        if (bookings.isEmpty()) {
            System.out.println("This member has no bookings.");
            return;
        }

        System.out.println("\nCurrent bookings:");
        for (Lesson l : bookings) {
            System.out.printf("ID: %d | %s | %s | %s%n",
                    l.getId(),
                    l.getExercise().getName(),
                    l.getDay(),
                    l.getTimeSlot());
        }

        System.out.print("\nEnter Lesson ID to cancel: ");
        int lessonId = getChoice();

        Lesson lessonToRemove = null;
        for (Lesson l : bookings) {
            if (l.getId() == lessonId) {
                lessonToRemove = l;
                break;
            }
        }

        if (lessonToRemove == null) {
            System.out.println("Booking not found.");
            return;
        }

        if (member.cancelBooking(lessonToRemove)) {
            System.out.println("Booking cancelled successfully!");
        } else {
            System.out.println("Failed to cancel booking.");
        }
    }

    /**
     * Adds a review for a lesson.
     */
    private void addReview() {
        System.out.println("\n========== ADD REVIEW ==========");
        viewAllLessons();

        System.out.print("\nEnter Member ID: ");
        int memberId = getChoice();

        Member member = findMember(memberId);

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        System.out.print("Enter Lesson ID: ");
        int lessonId = getChoice();

        Lesson lesson = findLesson(lessonId);

        if (lesson == null) {
            System.out.println("Lesson not found.");
            return;
        }

        System.out.print("Enter rating (1-5): ");
        int rating = getChoice();

        if (rating < 1 || rating > 5) {
            System.out.println("Rating must be between 1 and 5.");
            return;
        }

        System.out.print("Enter comment: ");
        scanner.nextLine(); // Consume newline
        String comment = scanner.nextLine();

        try {
            member.reviewLesson(lesson, rating, comment);
            System.out.println("Review added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding review: " + e.getMessage());
        }
    }

    /**
     * Displays bookings for all members.
     */
    private void viewMemberBookings() {
        System.out.println("\n========== MEMBER BOOKINGS ==========");

        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        for (Member m : members) {
            System.out.println("Member: " + m.getName() + " (ID: " + m.getId() + ")");
            List<Lesson> bookings = m.getBookings();
            if (bookings.isEmpty()) {
                System.out.println("  - No bookings");
            } else {
                for (Lesson l : bookings) {
                    System.out.printf("  - %s | %s | %s%n",
                            l.getExercise().getName(),
                            l.getDay(),
                            l.getTimeSlot());
                }
            }
            System.out.println();
        }
    }

    /**
     * Adds a new member to the system.
     */
    private void addNewMember() {
        System.out.println("\n========== ADD NEW MEMBER ==========");

        try {
            System.out.print("Enter member name: ");
            scanner.nextLine(); // Consume newline
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }

            Member newMember = new Member(memberIdCounter++, name);
            members.add(newMember);
            System.out.println("Member added successfully! ID: " + newMember.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding member: " + e.getMessage());
        }
    }

    /**
     * Finds a member by their ID.
     *
     * @param id the member ID to search for
     * @return the member if found, null otherwise
     */
    private Member findMember(int id) {
        for (Member m : members) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    /**
     * Finds a lesson by its ID.
     *
     * @param id the lesson ID to search for
     * @return the lesson if found, null otherwise
     */
    private Lesson findLesson(int id) {
        return timetable.getLessonById(id);
    }

    /**
     * Main entry point for the Booking System.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        BookingSystem system = new BookingSystem();
        system.run();
    }
}

