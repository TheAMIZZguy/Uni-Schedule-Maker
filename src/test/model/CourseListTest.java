package model;

import model.CourseList;
import model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class CourseListTest {

    Course chem121 = new Course("CHEM 121", new ArrayList<>(Arrays.asList("202")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})),
            true, new ArrayList<>(Arrays.asList("L2A")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})),
            false, new ArrayList<>(Arrays.asList("T2A", "T2C")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{10,0}, {12,0}, {1,3,5}})));

    Course engl121 = new Course("ENGL 121", new ArrayList<>(Arrays.asList("212")),
            new ArrayList<>(Arrays.asList(new int[][]{{15,0}, {16,0}, {1,3,5}}, new int[][]{})));

    ArrayList<Course> coursesList;

    CourseList cl;

    @BeforeEach
    void setUp() {
        try {
            JsonReader reader = new JsonReader("./data/testScheduleListReading.json",
                    "./data/testCourseListReading.json");
            cl = reader.readCourseList();

            coursesList = new ArrayList<>();
            coursesList.add(chem121);
            coursesList.add(engl121);
        } catch (IOException e) {
            fail("TEST FAIL: Exception should not have been thrown");
        }
    }

    @Test
    void addCourseToListTest() {
        assertEquals(4, cl.getCourseList().size());
        cl.addCourseToList(coursesList.get(0));
        assertEquals(5, cl.getCourseList().size());
    }

    @Test
    void addCoursesToListTest() {
        assertEquals(4, cl.getCourseList().size());
        cl.addCoursesToList(coursesList);
        assertEquals(6, cl.getCourseList().size());
    }

    @Test
    void removeCourseFromListTest() {
        assertEquals(4, cl.getCourseList().size());
        assertEquals("CPSC 110", cl.getCourseList().get(2).getName());

        cl.removeCourseFromList(2);
        assertEquals(3, cl.getCourseList().size());
        assertEquals("PHYS 118", cl.getCourseList().get(2).getName());

        cl.removeCourseFromList(-1);
        assertEquals(3, cl.getCourseList().size());

        cl.removeCourseFromList(4);
        assertEquals(3, cl.getCourseList().size());
    }
}
