package persistence;

import model.Course;
import model.Designer;
import model.ScheduleList;
import model.Scheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWritingTest {

    //BECUASE
    Course math1203 = new Course("MATH 120", new ArrayList<>(Arrays.asList("212")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})));
    Course cpsc121 = new Course("CPSC 121", new ArrayList<>(Arrays.asList("212")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})));
    Course cpsc110 = new Course("CPSC 110", new ArrayList<>(Arrays.asList("212")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,30}, {11,0}, {1,3,5}}, new int[][]{})));

    Designer designer1;
    Designer designer2;
    Designer designer3;
    ArrayList<Course> list1;

    ArrayList<Scheduler> manySchedules;

    @BeforeEach
    void setUp() {
        list1 = new ArrayList<>();
        list1.add(math1203);
        list1.add(cpsc121);
        list1.add(cpsc110);
        designer1 = new Designer(list1, 1);
        designer1.buildSchedulesOnlyMainWithPriority();
        designer1.buildSchedulesWithLabsAndTutorials();

        designer2 = new Designer(list1, 2);
        designer2.buildSchedulesOnlyMainWithPriority();
        designer2.buildSchedulesWithLabsAndTutorials();

        designer3 = new Designer(list1, 3);
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
    void testReadingWritingScheduleList() {

        try {

            ScheduleList sl = new ScheduleList(manySchedules);
            JsonWriter writer = new JsonWriter("./data/testScheduleList.json",
                    "./data/testCourseList.json");
            writer.open();
            writer.writeScheduleList(sl);
            writer.close(true);

            JsonReader reader = new JsonReader("./data/testScheduleList.json",
                    "./data/testCourseList.json");
            sl = reader.readSchedules();
            assertEquals(3, sl.getScheduleList().size());
            assertEquals("MATH 120",sl.getScheduleList().get(1).getCoursesInSchedule()[0]);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}