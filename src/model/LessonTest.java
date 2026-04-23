package model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class LessonTest {
   Exercise yoga = new Exercise("Yoga", 10);
    Lesson lesson = new Lesson(1, yoga, DayType.SATURDAY, TimeSlot.MORNING);
    Member member = new Member(1, "John");

    @Test
    void testLessonCreationValid() {
        assertEquals(1, lesson.getId());
        assertEquals(yoga, lesson.getExercise());
    }

    @Test
    void testLessonNullExercise() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Lesson(1, null, DayType.SATURDAY, TimeSlot.MORNING);
        });
    }

    @Test
    void testLessonNegativeId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Lesson(-1, yoga, DayType.SATURDAY, TimeSlot.MORNING);
        });
    }

    @Test
    void testAddBookingSuccess() {
        assertTrue(lesson.addBooking(member));
        assertEquals(1, lesson.getMemberCount());
    }

    @Test
    void testAddBookingNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            lesson.addBooking(null);
        });
    }

    @Test
    void testLessonCapacityLimit() {
        for (int i = 0; i < 4; i++) {
            lesson.addBooking(new Member(i, "M" + i));
        }
        assertTrue(lesson.isFull());
        assertFalse(lesson.addBooking(new Member(99, "Extra")));
    }

}