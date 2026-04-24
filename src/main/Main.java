package main;

import model.*;
import model.Timetable;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Timetable timetable = new Timetable();

        Exercise yoga = new Exercise("Yoga",10);
        Exercise zumba = new Exercise("Zumba",12);
        Exercise aquacise = new Exercise("Aquacise",15);
        Exercise boxfit = new Exercise("Box Fit",14);

        Exercise[] exercises = {yoga,zumba,aquacise,boxfit};

        int id = 1;

        for(int week=1; week<=8; week++){

            timetable.addLesson(new Lesson(id++, exercises[week%4], DayType.SATURDAY, TimeSlot.MORNING));
            timetable.addLesson(new Lesson(id++, exercises[(week+1)%4], DayType.SATURDAY, TimeSlot.AFTERNOON));
            timetable.addLesson(new Lesson(id++, exercises[(week+2)%4], DayType.SATURDAY, TimeSlot.EVENING));

            timetable.addLesson(new Lesson(id++, exercises[(week+3)%4], DayType.SUNDAY, TimeSlot.MORNING));
            timetable.addLesson(new Lesson(id++, exercises[week%4], DayType.SUNDAY, TimeSlot.AFTERNOON));
            timetable.addLesson(new Lesson(id++, exercises[(week+1)%4], DayType.SUNDAY, TimeSlot.EVENING));
        }

        List<Member> members = new ArrayList<>();

        members.add(new Member(1,"Alice"));
        members.add(new Member(2,"Bob"));
        members.add(new Member(3,"Carol"));
        members.add(new Member(4,"David"));
        members.add(new Member(5,"Emma"));
        members.add(new Member(6,"Frank"));
        members.add(new Member(7,"Grace"));
        members.add(new Member(8,"Henry"));
        members.add(new Member(9,"Isla"));
        members.add(new Member(10,"Jack"));

        Random rand = new Random();

        for(Member m : members){

            for(int i=0;i<3;i++){

                Lesson lesson = timetable.getLessons()
                        .get(rand.nextInt(48));

                m.bookLesson(lesson);
            }
        }

        for(int i=0;i<20;i++){

            Member m = members.get(rand.nextInt(10));
            Lesson l = timetable.getLessons().get(rand.nextInt(48));


        }


    }
}