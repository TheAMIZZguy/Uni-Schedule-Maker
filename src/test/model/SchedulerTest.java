package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class SchedulerTest {

    private Scheduler sched1;
    private String[][] emptySched; //this is to compare schedules more easily

    Course cpsc210 = new Course("CPSC 210", new ArrayList<>(Arrays.asList("201", "202", "203")),
            new ArrayList<>(Arrays.asList(new int[][]{{7,0}, {8,0}, {1,3,5}},
                    new int[][]{{15,0}, {16,0}, {1,3,5}},
                    new int[][]{{9,30}, {10,30}, {1,3,5}})));
    Course math120 = new Course("MATH 120", new ArrayList<>(Arrays.asList("201", "212", "230")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{15,0}, {16,0}, {1,3,5}},
                    new int[][]{{10,0}, {12,0}, {1,3,5}})));
    Course stat200 = new Course("STAT 200", new ArrayList<>(Arrays.asList("200")),
            new ArrayList<>(Arrays.asList(new int[][][] {new int[][]{{10,0}, {12,0}, {2,4}}})));
    Course phys118 = new Course("PHYS 118", new ArrayList<>(Arrays.asList("201", "202")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{9,30}, {10,0}, {1,3,5}})),
            true, new ArrayList<>(Arrays.asList("L2A", "L2B", "L2C")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{15,0}, {18,0}, {4}},
                    new int[][]{{8,0}, {11,0}, {2}})),
            true, new ArrayList<>(Arrays.asList("T2A", "T2C", "T2E")),
            new ArrayList<>(Arrays.asList(new int[][]{{20,0}, {21,0}, {1,3,5}},
                    new int[][]{{12,0}, {13,30}, {2}},
                    new int[][]{{14,30}, {16,0}, {1}})));
    Course stat201 = new Course("STAT 201", new ArrayList<>(Arrays.asList("201")),
            new ArrayList<>(Arrays.asList(new int[][][] {new int[][]{{10,0}, {12,0}, {2,4}}})));

    @BeforeEach
    public void setUp() {
        sched1 = new Scheduler(5);
        emptySched = new String[14*2][5];
    }

    //Tests are mostly self explanatory, a lot of repetition is manually making schedules to compare
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
        assertTrue(sched1.addCourseToSchedule(cpsc210));
        emptySched[0][0] = "CPSC 210 201";
        emptySched[0][2] = "CPSC 210 201";
        emptySched[0][4] = "CPSC 210 201";
        emptySched[1][0] = "CPSC 210 201";
        emptySched[1][2] = "CPSC 210 201";
        emptySched[1][4] = "CPSC 210 201";
        assertTrue(isEqualSchedule(emptySched, sched1.getSchedule()));

        assertTrue(sched1.addCourseToSchedule(math120));
        emptySched[6][0] = "MATH 120 201";
        emptySched[7][0] = "MATH 120 201";
        emptySched[8][0] = "MATH 120 201";
        emptySched[9][0] = "MATH 120 201";
        emptySched[6][2] = "MATH 120 201";
        emptySched[7][2] = "MATH 120 201";
        emptySched[8][2] = "MATH 120 201";
        emptySched[9][2] = "MATH 120 201";
        emptySched[6][4] = "MATH 120 201";
        emptySched[7][4] = "MATH 120 201";
        emptySched[8][4] = "MATH 120 201";
        emptySched[9][4] = "MATH 120 201";
        assertTrue(isEqualSchedule(emptySched, sched1.getSchedule()));
    }

    @Test
    public void addCourseToScheduleTest(){
        //initial test
        assertEquals(null, sched1.getCoursesInSchedule()[0]);


        String[] comparisonArray = new String[15];

        //testing adding a class to empty
        assertTrue(sched1.addCourseToSchedule(math120));
        comparisonArray[0] = "MATH 120 201";
        assertTrue(isSameArray(comparisonArray, sched1.getCoursesInSchedule()));

        //testing adding a class to a schedule with a class
        assertTrue(sched1.addCourseToSchedule(stat200));
        comparisonArray[1] = "STAT 200 200";
        assertTrue(isSameArray(comparisonArray, sched1.getCoursesInSchedule()));

        //testing adding a class to a schedule with overlapping hours
        assertFalse(sched1.addCourseToSchedule(stat201));
        assertTrue(isSameArray(comparisonArray, sched1.getCoursesInSchedule()));

        //testing adding a class with labs/tutorials to a schedule with other classes
        assertTrue(sched1.addCourseToSchedule(phys118));
        comparisonArray[2] = "PHYS 118 202";
        assertTrue(isSameArray(comparisonArray, sched1.getCoursesInSchedule()));
    }

    @Test
    public void addLabOrTutorialToScheduleTest(){
        sched1.addCourseToSchedule(math120);
        String name1 = phys118.getName() + " " + phys118.getLabNames().get(0);
        int[][] times1 = phys118.getLabTimes().get(phys118.getLabNames().get(0));
        assertFalse(sched1.addLabOrTutorialToSchedule(name1, times1));

        String name2 = phys118.getName() + " " + phys118.getLabNames().get(1);
        int[][] times2 = phys118.getLabTimes().get(phys118.getLabNames().get(1));
        assertTrue(sched1.addLabOrTutorialToSchedule(name2, times2));
    }

    /* THESE TESTS WERE MADE REDUNDANT/USELESS AFTER AN IMPROVEMENT, BUT LEFT INCASE THEY MIGHT BE NECESSARY LATER
    @Test
    public void deepCopyTest(){
        assertTrue(false);
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

     */

    //EFFECTS: compares two schedules of String[][] to see if they are equal
    public boolean isEqualSchedule(String[][] sched1, String[][] sched2) {
        for (int i = 0 ; i < sched1.length; i++) {
            for (int j = 0 ; j < sched1[i].length; j++) {
                if (sched1[i][j] != null && sched2[i][j] != null) {
                    if (!sched1[i][j].equals(sched2[i][j])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //EFFECTS: compares two arrays of String[] to see if they are equal
    public boolean isSameArray(String[] arr1, String[] arr2) {
        for (int i = 0 ; i < arr1.length; i++) {
            if (arr1[i] != null && arr2[i] != null) {
                if (!arr1[i].equals(arr2[i])) {
                    return false;
                }
            }
        }
        return true;
    }


    
}
