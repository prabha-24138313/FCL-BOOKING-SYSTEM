package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import model.Exercise;
class ExerciseTest {
   Exercise yoga = new Exercise("Yoga", 10);
    @Test
    void testExerciseValid() {
        assertEquals("Yoga", yoga.getName());
        assertEquals(10, yoga.getPrice());
    }

    @Test
    void testExerciseInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Exercise("", 10);
        });
    }

    @Test
    void testExerciseNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Exercise("Yoga", -5);
        });
    }

}