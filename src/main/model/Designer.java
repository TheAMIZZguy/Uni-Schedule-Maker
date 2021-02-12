package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class Designer {

    private ArrayList<Scheduler> listOfPossibilities;
    private ArrayList<Course> coursesToTake;

    private ArrayList<Scheduler>[] tierXClasses;

    private int maxCourses;

    private ArrayList<Scheduler> listOfPossibilitiesWithLT;
    private ArrayList<Scheduler>[] tierXClassesWithLT;

    private boolean hasAddedLabsAndTutorials;

    public Designer(ArrayList<Course> courses, int maxCourses) {
        this.coursesToTake = courses;
        this.maxCourses = maxCourses;
        this.tierXClasses = new ArrayList[maxCourses];

        for (int i = 0; i < maxCourses; i++) {
            tierXClasses[i] = new ArrayList<>();
        }

        this.tierXClassesWithLT = new ArrayList[courses.size() * 2 + 1];

        for (int i = 0; i < courses.size() * 2 + 1; i++) {
            tierXClassesWithLT[i] = new ArrayList<>();
        }
    }

    //getters
    public ArrayList<Course> getCourses() {
        return coursesToTake;
    }

    public ArrayList<Scheduler> getSchedules() {
        if (hasAddedLabsAndTutorials) {
            return listOfPossibilitiesWithLT;
        }
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

    /*
     * 1) DeepCopy LoP into some other Array of Arraylists
     * 2) Get the first class from CoursesToTake
     * 2.1) Get whether this class has labs
     * 2.1.1) If No, then do nothing, don't increment any indexes for list
     * 2.1.2) If Yes, then try to add the first lab to all schedules with this class in this layer:
     * 2.1.2.1) Start a bool at false
     * 2.1.2.2) Try to add the first lab to all classes like the previous add Class method,
     * 2.1.2.3) If a schedule doesn't have the parent class, then just add that schedule to next layer
     * 2.1.2.4) Repeat 2.1.2.2-3 With the rest of the labs,
     * 2.1.2.5) If at least one lab or class was added to next layer, change the bool
     * 2.1.2.6) If bool stays false, say adding labs and tutorials was impossible, else continue
     * 3) Repeat step 2 but with the next class
     * 4) Repeat everything but with tutorials
     * 5) If there is at least one schedule at the end, success.
     * 2) For the first lab of the first class
     */
    public boolean buildSchedulesWithLabsAndTutorials() {
        if (!(listOfPossibilities != null)) {
            return false;
        }
        tierXClassesWithLT[0] = deepCopyLoP();
        int currentTier = 1;

        boolean success = false;

        for (int i = 0; i < coursesToTake.size(); i++) {
            Course course = coursesToTake.get(i);
            if (course.getHasLab()) {
                if (fillNextTierWithLabs(course, currentTier)) {
                    success = true;
                }
            }
            currentTier++;
        }
        for (int i = 0; i < coursesToTake.size(); i++) {
            Course course = coursesToTake.get(i);
            if (course.getHasTutorial()) {
                if (fillNextTierWithTutorials(course, currentTier)) {
                    success = true;
                }
            }
            currentTier++;
        }

        if (success) {
            this.listOfPossibilitiesWithLT = tierXClassesWithLT[currentTier - 1];
            hasAddedLabsAndTutorials = true;
        }

        return success;
    }

    private ArrayList<Scheduler> deepCopyLoP() {
        ArrayList<Scheduler> copiedLoP = new ArrayList<>();

        if (listOfPossibilities != null) {
            for (int i = 0; i < listOfPossibilities.size(); i++) {
                copiedLoP.add(new Scheduler(listOfPossibilities.get(i)));
            }
        }
        return copiedLoP;
    }

    private boolean fillNextTierWithLabs(Course course, int curTier) {
        String name = course.getName();
        boolean addedAtLeastOne = false;
        ArrayList<String> namesOfLabs = course.getLabNames();
        HashMap<String, int[][]> timesOfLabs = course.getLabTimes();

        for (int i = 0; i < namesOfLabs.size(); i++) {
            if (fillNextTierWithLabOrTutorial(name, namesOfLabs.get(i), timesOfLabs.get(namesOfLabs.get(i)), curTier)) {
                addedAtLeastOne = true;
            }
        }

        return addedAtLeastOne;
    }

    private boolean fillNextTierWithTutorials(Course course, int curTier) {
        String name = course.getName();
        boolean addedAtLeastOne = false;
        ArrayList<String> namesOfTutorials = course.getTutorialNames();
        HashMap<String, int[][]> timesOfTutorials = course.getTutorialTimes();

        for (int i = 0; i < namesOfTutorials.size(); i++) {
            if (fillNextTierWithLabOrTutorial(name, namesOfTutorials.get(i),
                    timesOfTutorials.get(namesOfTutorials.get(i)), curTier)) {
                addedAtLeastOne = true;
            }
        }

        return addedAtLeastOne;
    }

    private boolean fillNextTierWithLabOrTutorial(String name, String nameOfLab, int[][] times, int curTier) {
        boolean returnBool = false;
        ArrayList<Scheduler> prevScheds = new ArrayList<>(tierXClassesWithLT[curTier - 1]);
        for (int i = 0; i < prevScheds.size(); i++) {
            if (Arrays.asList(prevScheds.get(i).getCoursesInSchedule()).contains(name)) {
                Scheduler newSched = new Scheduler(prevScheds.get(i));
                if (newSched.addLabOrTutorialToSchedule(name + " " + nameOfLab, times)) {
                    tierXClassesWithLT[curTier].add(newSched);
                    returnBool = true;
                }
            } else {
                Scheduler newSched = new Scheduler(prevScheds.get(i));
                tierXClassesWithLT[curTier].add(newSched);
                returnBool = true;
            }
        }

        return returnBool;
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
