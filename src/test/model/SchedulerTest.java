package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class SchedulerTest {

    private Scheduler sched1;
    private String[][] emptySched; //this is to compare schedules more easily

    Course cpsc210 = new Course("CPSC 210", new ArrayList<>(Arrays.asList(new String[]{"201", "202", "203"})),
            new ArrayList<>(Arrays.asList(new int[][][] {new int[][]{{7,0}, {8,0}, {1,3,5}},
                                                    new int[][]{{15,0}, {16,0}, {1,3,5}},
                                                    new int[][]{{9,30}, {10,30}, {1,3,5}}})));
    Course math120 = new Course("MATH 120", new ArrayList<>(Arrays.asList(new String[]{"201", "212", "230"})),
            new ArrayList<>(Arrays.asList(new int[][][] {new int[][]{{10,0}, {12,0}, {1,3,5}},
                                                    new int[][]{{15,0}, {16,0}, {1,3,5}},
                                                    new int[][]{{10,0}, {12,0}, {1,3,5}}})));
    Course stat200 = new Course("STAT 200", new ArrayList<>(Arrays.asList(new String[]{"200"})),
            new ArrayList<>(Arrays.asList(new int[][][] {new int[][]{{10,0}, {12,0}, {2,4}}})));
    Course phys118 = new Course("PHYS 118", new ArrayList<>(Arrays.asList(new String[]{"201", "202"})),
            new ArrayList<>(Arrays.asList(new int[][][] {new int[][]{{10,0}, {12,0}, {1,3,5}},
                                                    new int[][]{{9,30}, {10,30}, {1,3,5}}})),
            true, new ArrayList<>(Arrays.asList(new String[]{"L2A", "L2B", "L2C"})),
            new ArrayList<>(Arrays.asList(new int[][][] {new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{15,0}, {18,0}, {4}},
                    new int[][]{{8,0}, {11,0}, {2}}})),
            true, new ArrayList<>(Arrays.asList(new String[]{"T2A", "T2C", "T2E"})),
            new ArrayList<>(Arrays.asList(new int[][][] {new int[][]{{20,0}, {21,0}, {1,3,5}},
                    new int[][]{{12,0}, {13,30}, {2}},
                    new int[][]{{14,30}, {16,0}, {1}}})));
    Course stat201 = new Course("STAT 201", new ArrayList<>(Arrays.asList(new String[]{"201"})),
            new ArrayList<>(Arrays.asList(new int[][][] {new int[][]{{10,0}, {12,0}, {2,4}}})));

    @BeforeEach
    public void setUp() {
        sched1 = new Scheduler(5);
        emptySched = new String[14*2][5];
    }

    @Test
    public void getScheduleTest(){
        assertEquals(14*2, sched1.getSchedule().length);
        assertEquals(5, sched1.getSchedule()[0].length);
    }

    @Test
    public void getCoursesInScheduleTest(){
        assertEquals((new String[5])[1], sched1.getCoursesInSchedule()[1]);
    }

    @Test
    public void addingClassToScheduleTest() {
        sched1.addCourseToSchedule(cpsc210);
        emptySched[0][0] = "CPSC 210 201";
        emptySched[0][1] = "CPSC 210 201";
        emptySched[2][0] = "CPSC 210 201";
        emptySched[2][1] = "CPSC 210 201";
        emptySched[4][0] = "CPSC 210 201";
        emptySched[4][1] = "CPSC 210 201";
        assertTrue(isEqualSchedule(emptySched, sched1.getSchedule()));

        sched1.addCourseToSchedule(math120);
        emptySched[0][6] = "MATH 120 201";
        emptySched[0][7] = "MATH 120 201";
        emptySched[0][8] = "MATH 120 201";
        emptySched[0][9] = "MATH 120 201";
        emptySched[2][6] = "MATH 120 201";
        emptySched[2][7] = "MATH 120 201";
        emptySched[2][8] = "MATH 120 201";
        emptySched[2][9] = "MATH 120 201";
        emptySched[4][6] = "MATH 120 201";
        emptySched[4][7] = "MATH 120 201";
        emptySched[4][8] = "MATH 120 201";
        emptySched[4][9] = "MATH 120 201";
        assertTrue(isEqualSchedule(emptySched, sched1.getSchedule()));
    }

    @Test
    public void addCourseToScheduleTest(){
        //initial test
        assertEquals(null, sched1.getCoursesInSchedule()[0]);
        //testing adding a class to empty
        assertTrue(sched1.addCourseToSchedule(math120));
        assertTrue(isEqualSchedule(courseScheduleHelper(math120, 0), sched1.getSchedule()));
        assertEquals(new String[]{"MATH 120", null, null, null, null}, sched1.getCoursesInSchedule());

        //testing adding a class to a schedule with a class
        assertTrue(sched1.addCourseToSchedule(stat200));
        assertTrue(isEqualSchedule(courseScheduleHelper(stat200, 0), sched1.getSchedule()));
        assertEquals(new String[]{"MATH 120", "STAT 200", null, null, null}, sched1.getCoursesInSchedule());

        //testing adding a class to a schedule with overlapping hours
        assertFalse(sched1.addCourseToSchedule(stat201));
        assertTrue(isEqualSchedule(courseScheduleHelper(stat201, 0), sched1.getSchedule()));
        assertEquals(new String[]{"MATH 120", "STAT 200", null, null, null}, sched1.getCoursesInSchedule());

        //testing adding a class with labs/tutorials to a schedule with other classes
        assertTrue(sched1.addCourseToSchedule(phys118));
        courseScheduleHelperLab(phys118, 1);
        courseScheduleHelperLab(phys118, 1);
        assertTrue(isEqualSchedule(courseScheduleHelper(phys118, 0), sched1.getSchedule()));
        assertEquals(new String[]{"MATH 120", "STAT 200", "PHYS 118", null, null}, sched1.getCoursesInSchedule());
    }

    public String[][] courseScheduleHelper(Course a, int sub) {
        return sched1.addingClassToSchedule(a.getSubClassTimes().get(a.getSubClassNames().get(sub))[1],
                Arrays.copyOfRange(a.getSubClassTimes().get(a.getSubClassNames().get(sub)),0,2),
                a.getName() + " " + a.getSubClassNames().get(sub));
    }

    public String[][] courseScheduleHelperLab(Course a, int sub) {
        return sched1.addingClassToSchedule(a.getLabTimes().get(a.getLabNames().get(sub))[1],
                Arrays.copyOfRange(a.getLabTimes().get(a.getLabNames().get(sub)),0,2),
                a.getName() + " " + a.getLabNames().get(sub));
    }

    public String[][] courseScheduleHelperTut(Course a, int sub) {
        return sched1.addingClassToSchedule(a.getTutorialTimes().get(a.getTutorialNames().get(sub))[1],
                Arrays.copyOfRange(a.getTutorialTimes().get(a.getTutorialNames().get(sub)),0,2),
                a.getName() + " " + a.getTutorialNames().get(sub));
    }

    public boolean isEqualSchedule(String[][] sched1, String[][] sched2) {
        for (int i = 0 ; i < sched1.length; i++) {
            for (int j = 0 ; j < sched1[i].length; j++) {
                if(sched1[i][j] != sched2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    
}
