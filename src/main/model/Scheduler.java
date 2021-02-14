package model;


import java.util.Arrays;

public class Scheduler {

    private static final int EARLIEST_TIME_ALLOWED = 7; //7am
    // 14 possible hours, broken into half hour blocks. 5 days a week. 7am (07:00) to 9pm (21:00)
    private String[][] schedule = new String[2 * 14][5];
    // self Explanatory
    private String[] coursesInSchedule;
    //private Boolean[] hasAddedLab;
    //private Boolean[] hasAddedtutorial;
    private int currentCourses = 0;

    //REQUIRES: numOfClasses must be a positive integer
    public Scheduler(int numOfClasses) {
        this.coursesInSchedule = new String[numOfClasses];
    }

    //DeepCopy Constructor
    public Scheduler(Scheduler copySchedule) {
        this.schedule = scheduleDeepCopy(copySchedule.getSchedule());
        this.coursesInSchedule = coursesInScheduleDeepCopy(copySchedule.getCoursesInSchedule());
        this.currentCourses = copySchedule.getCurrentCourses();
    }

    //EFFECTS: makes a deepCopy of a Schedule
    public String[][] scheduleDeepCopy(String[][] schedulePrev) {
        String[][] scheduleNew = new String[2 * 14][5];
        for (int i = 0; i < schedulePrev.length; i++) {
            for (int j = 0; j < schedulePrev[i].length; j++) {
                scheduleNew[i][j] = schedulePrev[i][j];
            }
        }
        return scheduleNew;
    }

    //EFFECTS: makes a deepCopy of courses in a chedule
    public String[] coursesInScheduleDeepCopy(String[] coursesInSchedulePrev) {
        String[] coursesInScheduleNew = new String[coursesInSchedulePrev.length];
        for (int i = 0; i < coursesInSchedulePrev.length; i++) {
            coursesInScheduleNew[i] = coursesInSchedulePrev[i];
        }
        return coursesInScheduleNew;
    }

    //getters
    public String[][] getSchedule() {
        return schedule;
    }

    public int getCurrentCourses() {
        return currentCourses;
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
        //if (!Arrays.asList(coursesInSchedule).contains(a.getName()) && currentCourses < coursesInSchedule.length) {
        for (int i = 0; i < a.getSubClassNames().size(); i++) {
            //the index of 2 is because that is the location of days, the 0 to 2 is the times
            if (isOpenForClass(a.getSubClassTimes().get(a.getSubClassNames().get(i))[2],
                    Arrays.copyOfRange(a.getSubClassTimes().get(a.getSubClassNames().get(i)),0,2))) {
                addingClassToSchedule(a.getSubClassTimes().get(a.getSubClassNames().get(i))[2],
                        Arrays.copyOfRange(a.getSubClassTimes().get(a.getSubClassNames().get(i)),0,2),
                        a.getName() + " " + a.getSubClassNames().get(i));
                addToCoursesInSchedule(a.getName());// + " " + a.getSubClassNames().get(i));
                return true;
            }
        }
        //}
        return false;
    }

    /* Checks if the lab or tutorial would conflict with something, and then adds it   */
    //REQUIRES: times must follow the correct format from Course
    //MODIFIES: this
    //EFFECTS: Tries to add a lab/tutorial to the schedule, returns true if successful, false otherwise
    public boolean addLabOrTutorialToSchedule(String name, int[][] times) {
        int[] days = times[2];
        int[][] hours = Arrays.copyOfRange(times,0,2);

        if (isOpenForClass(days, hours)) {
            addingClassToSchedule(days, hours, name);
            return true;
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: Adds a course to the Courses in Schedule and increments the count
    private void addToCoursesInSchedule(String course) {
        coursesInSchedule[currentCourses] = course;
        currentCourses++;
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
                if (startTime <= currentTime && currentTime < endTime) {
                    this.schedule[currentTime - eta2][day - 1] = name;
                }
            }
        }
        return this.schedule; //for the sake of tests
    }

    //EFFECTS: returns true if a class is able to be added to a schedule
    public boolean isOpenForClass(int[] days, int[][] times) {
        int eta2 = EARLIEST_TIME_ALLOWED * 2;

        int startTime = times[0][0] * 2 + times[0][1] / 30;
        int endTime = times[1][0] * 2 + times[1][1] / 30;

        for (int day : days) {
            for (int currentTime = eta2; currentTime < eta2 + schedule.length; currentTime++) {
                if (startTime <= currentTime && currentTime < endTime) {
                    if (this.schedule[currentTime - eta2][day - 1] != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
