package model;

import model.CourseList;
import model.Course;
import model.Designer;
import model.ScheduleList;
import model.Scheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class ScheduleListTest {

    Course math120 = new Course("MATH 120", new ArrayList<>(Arrays.asList("201", "212")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{15,0}, {16,0}, {1,3,5}})));
    Course phys118 = new Course("PHYS 118", new ArrayList<>(Arrays.asList("201", "202")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{9,30}, {10,0}, {1,3,5}})),
            true, new ArrayList<>(Arrays.asList("L2A", "L2B")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{15,0}, {18,0}, {4}})),
            true, new ArrayList<>(Arrays.asList("T2A", "T2C")),
            new ArrayList<>(Arrays.asList(new int[][]{{20,0}, {21,0}, {1,3,5}},
                    new int[][]{{12,0}, {13,30}, {2}})));

    ArrayList<Course> coursesList;
    Designer designer;

    ScheduleList sl;

    @BeforeEach
    void setUp() {
        try {
            JsonReader reader = new JsonReader("./data/testScheduleListReading.json",
                    "./data/testCourseList.json");
            sl = reader.readSchedules();

            coursesList = new ArrayList<>();
            coursesList.add(phys118);
            coursesList.add(math120);

            designer = new Designer(coursesList, 2);
            designer.buildSchedulesOnlyMainWithPriority();
            designer.buildSchedulesWithLabsAndTutorials();

        } catch (IOException e) {
            fail("TEST FAIL: Exception should not have been thrown");
        }
    }

    @Test
    void addScheduleToListTest() {
        assertEquals(3, sl.getScheduleList().size());
        sl.addScheduleToList(designer.getSchedules().get(0));
        assertEquals(4, sl.getScheduleList().size());
    }

    @Test
    void addSchedulesToListTest() {
        assertEquals(3, sl.getScheduleList().size());
        sl.addSchedulesToList(designer.getSchedules());
        assertEquals(11, sl.getScheduleList().size());
    }

    @Test
    void removeScheduleFromListTest() {
        assertEquals(3, sl.getScheduleList().size());
        assertEquals(1, sl.getScheduleList().get(1).getCurrentCourses());

        assertTrue(sl.removeScheduleFromList(1));
        assertEquals(2, sl.getScheduleList().size());
        assertEquals(2, sl.getScheduleList().get(1).getCurrentCourses());

        assertFalse(sl.removeScheduleFromList(-1));
        assertEquals(2, sl.getScheduleList().size());

        assertFalse(sl.removeScheduleFromList(10));
        assertEquals(2, sl.getScheduleList().size());
        assertEquals(2, sl.getScheduleList().get(1).getCurrentCourses());
    }
}
