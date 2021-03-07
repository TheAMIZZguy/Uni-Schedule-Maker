package persistence;

import model.CourseList;
import model.Course;
import model.Designer;
import model.ScheduleList;
import model.Scheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReadingAndWritingTest {

    //BECUASE
    Course math1203 = new Course("MATH 120", new ArrayList<>(Arrays.asList("212")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})));
    Course cpsc121 = new Course("CPSC 121", new ArrayList<>(Arrays.asList("212")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})));
    Course cpsc110 = new Course("CPSC 110", new ArrayList<>(Arrays.asList("212")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,30}, {11,0}, {1,3,5}}, new int[][]{})));
    Course phys118 = new Course("PHYS 118", new ArrayList<>(Arrays.asList("201", "202")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{9,30}, {10,0}, {1,3,5}})),
            true, new ArrayList<>(Arrays.asList("L2A", "L2B")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{15,0}, {18,0}, {4}})),
            true, new ArrayList<>(Arrays.asList("T2A", "T2C")),
            new ArrayList<>(Arrays.asList(new int[][]{{20,0}, {21,0}, {1,3,5}},
                    new int[][]{{12,0}, {13,30}, {2}})));

    Designer designer1;
    Designer designer2;
    Designer designer3;
    ArrayList<Course> courseList1;

    ArrayList<Scheduler> manySchedules;

    @BeforeEach
    void setUp() {
        courseList1 = new ArrayList<>();
        courseList1.add(math1203);
        courseList1.add(cpsc121);
        courseList1.add(cpsc110);
        courseList1.add(phys118);
        designer1 = new Designer(courseList1, 1);
        designer1.buildSchedulesOnlyMainWithPriority();
        designer1.buildSchedulesWithLabsAndTutorials();

        designer2 = new Designer(courseList1, 2);
        designer2.buildSchedulesOnlyMainWithPriority();
        designer2.buildSchedulesWithLabsAndTutorials();

        designer3 = new Designer(courseList1, 3);
        designer3.buildSchedulesOnlyMainWithPriority();
        designer3.buildSchedulesWithLabsAndTutorials();

        manySchedules = designer1.getListOfPossibilitiesWithLT();
        for (Scheduler sched : designer2.getListOfPossibilitiesWithLT()) {
            manySchedules.add(sched);
        }
        for (Scheduler sched : designer3.getListOfPossibilitiesWithLT()) {
            manySchedules.add(sched);
        }
    }


    @Test
    void testEmptyScheduleListReaderWriter() {
        try {
            ScheduleList sl = new ScheduleList(new ArrayList<>());
            JsonWriter writer = new JsonWriter("./data/testScheduleListEmpty.json",
                    "./data/testCourseListEmpty.json");
            writer.open(true);
            writer.writeScheduleList(sl);
            writer.close(true);

            JsonReader reader = new JsonReader("./data/testScheduleListEmpty.json",
                    "./data/testCourseListEmpty.json");
            sl = reader.readSchedules();
            assertEquals(0, sl.getScheduleList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testReadingWritingScheduleList() {
        try {
            ScheduleList sl = new ScheduleList(manySchedules);
            JsonWriter writer = new JsonWriter("./data/testScheduleList.json",
                    "./data/testCourseList.json");
            writer.open(true);
            writer.writeScheduleList(sl);
            writer.close(true);

            JsonReader reader = new JsonReader("./data/testScheduleList.json",
                    "./data/testCourseList.json");
            sl = reader.readSchedules();
            //technically 3, not then; the rest are duplicated because of how the test it, working as intended though
            assertEquals(10, sl.getScheduleList().size());
            assertEquals("MATH 120",sl.getScheduleList().get(1).getCoursesInSchedule()[0]);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testEmptyCourseListReaderWriter() {
        try {
            CourseList cl = new CourseList(new ArrayList<>());
            JsonWriter writer = new JsonWriter("./data/testScheduleListEmpty.json",
                    "./data/testCourseListEmpty.json");
            writer.open(false);
            writer.writeCourseList(cl);
            writer.close(false);

            JsonReader reader = new JsonReader("./data/testScheduleListEmpty.json",
                    "./data/testCourseListEmpty.json");
            cl = reader.readCourseList();
            assertEquals(0, cl.getCourseList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testReadingWritingCourseList() {

        try {

            CourseList cl = new CourseList(courseList1);
            JsonWriter writer = new JsonWriter("./data/testScheduleList.json",
                    "./data/testCourseList.json");
            writer.open(false);
            writer.writeCourseList(cl);
            writer.close(false);

            JsonReader reader = new JsonReader("./data/testScheduleList.json",
                    "./data/testCourseList.json");
            cl = reader.readCourseList();
            assertEquals(4, cl.getCourseList().size());
            assertEquals("PHYS 118", cl.getCourseList().get(3).getName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
