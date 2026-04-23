package model;
import java.util.*;


public class Timetable {

    private List<Lesson> lessons = new ArrayList<>();

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public List<Lesson> searchByDay(DayType day){

        List<Lesson> result = new ArrayList<>();

        for(Lesson l: lessons)
            if(l.getDay()==day)
                result.add(l);

        return result;
    }

    public List<Lesson> searchByExercise(String name){

        List<Lesson> result = new ArrayList<>();

        for(Lesson l: lessons)
            if(l.getExercise().getName().equalsIgnoreCase(name))
                result.add(l);

        return result;
    }
}