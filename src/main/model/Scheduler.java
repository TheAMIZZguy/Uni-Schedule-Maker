package model;

import com.sun.deploy.util.ArrayUtil;

import java.util.Arrays;

public class Scheduler {

    private static final int EARLIEST_TIME_ALLOWED = 7; //7am
    // 14 possible hours, broken into half hour blocks. 5 days a week. 7am (07:00) to 9pm (21:00)
    private String[][] schedule = new String[2 * 14][5];
    // self Explanatory
    private String[] coursesInSchedule;
    private int currentCourses = 0;

    //REQUIRES: numOfClasses must be a positive integer
    public Scheduler(int numOfClasses) {
        this.coursesInSchedule = new String[numOfClasses];
    }

    //getters
    public String[][] getSchedule() {
        return schedule;
    }

    public String[] getCoursesInSchedule() {
        return coursesInSchedule;
    }

    /* Checks if the class has already been added to the schedule,
          If it has then do nothing and return false
          Otherwise test if the class can be added to the schedule without overlap
          do so and return true, or if it can't; do nothing and return false           */
    //MODIFIES: this
    //EFFECTS: Tries to add a course to the schedule, returns true if successful, false otherwise
    public boolean addCourseToSchedule(Course a) {
        if (Arrays.asList(coursesInSchedule).contains(a.getName())) {
            //TODO, add tests first too
        }
        return false;
    }

    private void addToCoursesInSchedule(String course) {
        coursesInSchedule[currentCourses] = course;
    }

    // VISUAL EXAMPLE OF SCHEDULE WITH TIMES STARTING AT 7AM, TOP ROW
    /*   M   T   W   Th   F
    {7  {"", "", "", "", ""},
        {"", "", "", "", ""},
     8  {"", "", "", "", ""},
        {"", "", "", "", ""},
     9  {"", "", "", "", ""},
        {"", "", "", "", ""},
     10 {"", "", "", "", ""},
        {"", "", "", "", ""},
     11 {"", "", "", "", ""},
        {"", "", "", "", ""},
    . . .
     20 {"", "", "", "", ""},
        {"", "", "", "", ""}}
     */
    // shows time index's as double their time (because half hours), and subtracts by 14 so the first time is index 0
    // returns the schedule for the sake of tests, not actually used
    //REQUIRES: days and times to adhere to the expected format from Course.java
    //MODIFIES: this
    //EFFECTS: adds the name of a course to the schedule at it's expected time
    public String[][] addingClassToSchedule(int[] days, int[][] times, String name) {
        int eta2 = EARLIEST_TIME_ALLOWED * 2;

        int startTime = times[0][0] * 2 + times[0][1] / 30;
        int endTime = times[1][0] * 2 + times[1][1] / 30;

        for (int day : days) {
            for (int currentTime = eta2; currentTime < eta2 + schedule.length; currentTime++) {
                if (startTime < currentTime && currentTime < endTime) {
                    this.schedule[currentTime - eta2][day] = name;
                }
            }
        }
        return this.schedule; //for the sake of tests
    }
}
