package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MemberTest {
    Exercise yoga = new Exercise("Yoga", 10);
    Lesson lesson = new Lesson(1, yoga, DayType.SATURDAY, TimeSlot.MORNING);
    Member member = new Member(1, "John");
    @Test
    void testMemberValid() {
        assertEquals("John", member.getName());
    }

    @Test
    void testMemberInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Member(1, "");
        });
    }

    @Test
    void testBookLessonSuccess() {
        assertTrue(member.bookLesson(lesson));
        assertEquals(1, member.getBookingCount());
    }

    @Test
    void testBookLessonNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            member.bookLesson(null);
        });
    }

    @Test
    void testBookingConflictSameTime() {
        Lesson l2 = new Lesson(2, yoga, DayType.SATURDAY, TimeSlot.MORNING);

        member.bookLesson(lesson);
        assertFalse(member.bookLesson(l2));
    }

    @Test
    void testCancelBooking() {
        member.bookLesson(lesson);
        assertTrue(member.cancelBooking(lesson));
        assertEquals(0, member.getBookingCount());
    }

    @Test
    void testCancelNullBooking() {
        assertFalse(member.cancelBooking(null));
    }

}