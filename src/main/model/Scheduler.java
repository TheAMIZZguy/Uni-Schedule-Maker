package model;

import com.sun.deploy.util.ArrayUtil;

import java.util.Arrays;

public class Scheduler {
    // 14 possible hours, broken into half hour blocks. 5 days a week. 7am(07:00) to 9pm (21:00)
    private String[][] schedule = new String[2 * 14][5];
    // self Explanatory
    private String[] coursesInSchedule;

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
}
