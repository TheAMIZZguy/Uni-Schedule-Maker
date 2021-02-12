package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class Designer {

    private ArrayList<Scheduler> listOfPossibilities;
    private ArrayList<Course> coursesToTake;

    private ArrayList<Scheduler>[] tierXClasses;

    private int maxCourses;

    public Designer(ArrayList<Course> courses, int maxCourses) {
        this.coursesToTake = courses;
        this.maxCourses = maxCourses;
        this.tierXClasses = new ArrayList[maxCourses];

        for (int i = 0; i < maxCourses; i++) {
            tierXClasses[i] = new ArrayList<>();
        }
    }

    //getters
    public ArrayList<Course> getCourses() {
        return coursesToTake;
    }

    public ArrayList<Scheduler> getSchedules() {
        return listOfPossibilities;
    }

    //setters
    public void addCourse(Course c) {
        coursesToTake.add(c);
    }

    public void addSchedule(Scheduler s) {
        listOfPossibilities.add(s);
    }

    /*  THIS CLASS DOES STEPS 1-3
     * 1) Takes the first course and makes schedules equal to all the subcourses it has
     * 2) Then to each of those it adds all the subcourses of the next class
     * 3) Repeat until done
     * 4) Then repeat 1-3 but with a different class as the starting class
     * 5) Repeat step 4 until all classes have appeared
     * 6) Then remove duplicates?
     * 7) Add Labs and Tutorials
     * Note: If a class doesn't fit then it isn't added
     */
    public boolean buildSchedulesOnlyMainWithPriority() {
        if (coursesToTake.size() == 0) {
            return false;
        }

        for (int i = 0; i < coursesToTake.size(); i++) {
            buildScheduleLoop(i);
        }

        int subtractor = 1;
        while (tierXClasses[tierXClasses.length - subtractor].size() == 0) {
            subtractor++;
            if (tierXClasses.length - subtractor < 0) {
                break;
            }
        }
        if (tierXClasses[tierXClasses.length - subtractor].size() != 0) {
            this.listOfPossibilities = tierXClasses[tierXClasses.length - subtractor];
            return true;
        }

        return false;
    }

    private void buildScheduleLoop(int i) {
        String pseudoName;
        Course pseudoCourse;
        for (int j = 0; j < coursesToTake.get(i).getSubClassNames().size(); j++) {
            ArrayList<String> pseudoSubName = new ArrayList<>();
            ArrayList<int[][]> pseudoSubTimes = new ArrayList<>();

            pseudoName = coursesToTake.get(i).getName();
            pseudoSubName.add(coursesToTake.get(i).getSubClassNames().get(j));
            pseudoSubTimes.add(coursesToTake.get(i).getSubClassTimes().get(pseudoSubName.get(0)));

            pseudoCourse = new Course(pseudoName, pseudoSubName, pseudoSubTimes);

            addPseudoCourse(i, pseudoCourse);
        }
    }

    private void addPseudoCourse(int i, Course pseudoCourse) {
        if (i == 0) {
            Scheduler schedule1 = new Scheduler(maxCourses);
            schedule1.addCourseToSchedule(pseudoCourse);
            tierXClasses[i].add(schedule1);
        } else {
            int subtractor = 1;
            while (tierXClasses[i - subtractor].size() == 0) {
                subtractor++;
                if (i - subtractor < 0) {
                    break;
                }
            }
            if (tierXClasses[i - subtractor].size() != 0) {
                for (int k = 0; k < tierXClasses[i - subtractor].size(); k++) {
                    Scheduler deepCopy = new Scheduler(tierXClasses[i - subtractor].get(k));
                    if (deepCopy.addCourseToSchedule(pseudoCourse)) {
                        tierXClasses[i].add(deepCopy);
                    }
                }
            }
        }
    }

    public boolean buildSchedulesWithLabsAndTutorials() {
        return false;
    }


    /*a class that makes schedules
     * 1) OBTAIN ALL THE CLASSES THAT THEY WANT
     * 2) ADD ALL THOSE CLASSES THROUGH LOOPS - NO LABS OR TUTORIALS
     * 3) ADDING LABS/ TUTS
     *   3.1) IN EACH SCHEDULE GET THE COURSES FROM THE STRING[] COURSES IN SCHEDULER.JAVA AND
     *          THEN GET EACH COURSE,
     *   3.2) IF IT HAS A LAB, THEN A FOR LOOP OF (LAB IN LABS), WHERE THE SCHEDULER
     *          ADDS THE LAB TO THE SCHEDULE IF POSSIBLE, resulting in several more schedules
     *   3.3) DO SAME THING WITH TUTORIALS, BUT ON THE LAB ADDED SCHEDULES
     */
}
