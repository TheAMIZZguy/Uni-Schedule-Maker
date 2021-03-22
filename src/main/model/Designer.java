package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.lang.Math;

//Takes classes and then designs a lot of different schedule possibilities.
public class Designer {

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

    private ArrayList<Scheduler> listOfPossibilities;
    private ArrayList<Course> coursesToTake;

    private ArrayList<Scheduler>[] tierXClasses;

    private int maxCourses;

    private ArrayList<Scheduler> listOfPossibilitiesWithLT;
    private ArrayList<Scheduler>[] tierXClassesWithLT;

    private boolean hasAddedLabsAndTutorials;

    //REQUIRES: maxCourses to be a positive integer
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

    /* They ended up being unnecessary but I didn't want to remove them incase they were needed later
    public Designer(ArrayList<Course> coursesToTake, int maxCourses, ArrayList<Scheduler> listOfPossibilities,
                    ArrayList<Scheduler> listOfPossibilitiesWithLT, boolean hasAddedLabsAndTutorials) {
        this.coursesToTake = coursesToTake;
        this.maxCourses = maxCourses;
        this.listOfPossibilities = listOfPossibilities;
        this.listOfPossibilitiesWithLT = listOfPossibilitiesWithLT;
        this.hasAddedLabsAndTutorials = hasAddedLabsAndTutorials;

        for (int i = 0; i < maxCourses; i++) {
            tierXClasses[i] = new ArrayList<>();
        }

        for (int i = 0; i < coursesToTake.size() * 2 + 1; i++) {
            tierXClassesWithLT[i] = new ArrayList<>();
        }
    }

    public Designer(Designer copyDesigner) {
        this.coursesToTake = copyDesigner.getCourses();
        this.maxCourses = copyDesigner.getMaxCourses();
        this.listOfPossibilities = copyDesigner.getListOfPossibilities();
        this.listOfPossibilitiesWithLT = copyDesigner.getListOfPossibilitiesWithLT();
        this.hasAddedLabsAndTutorials = copyDesigner.getHasAddedLabsAndTutorials();

        for (int i = 0; i < maxCourses; i++) {
            tierXClasses[i] = new ArrayList<>();
        }

        for (int i = 0; i < coursesToTake.size() * 2 + 1; i++) {
            tierXClassesWithLT[i] = new ArrayList<>();
        }
    }

    //getters (mostly for DeepCopy-esque Use)
    public ArrayList<Course> getCourses() {
        return coursesToTake;
    }

    public int getMaxCourses() {
        return maxCourses;
    }

    public ArrayList<Scheduler> getListOfPossibilities() {
        return listOfPossibilities;
    }

    public boolean getHasAddedLabsAndTutorials() {
        return hasAddedLabsAndTutorials;
    }

     */

    public ArrayList<Scheduler> getListOfPossibilitiesWithLT() {
        return listOfPossibilitiesWithLT;
    }

    public ArrayList<Scheduler> getSchedules() {
        if (hasAddedLabsAndTutorials) {
            return listOfPossibilitiesWithLT;
        }
        return listOfPossibilities;
    }

    //setters
    /*Methods weren't necessary, but I might want to implement it later
    public void addCourse(Course c) {
        coursesToTake.add(c);
    }

    public void addSchedule(Scheduler s) {
        listOfPossibilities.add(s);
    }
    */


    /*  THIS CLASS DOES STEPS 1-3. STEPS 4-6 NOT IMPLEMENTED FOR SPEED REASONS
     * 1) Takes the first course and makes schedules equal to all the subcourses it has
     * 2) Then to each of those it adds all the subcourses of the next class
     * 3) Repeat until done
     * 4) Then repeat 1-3 but with a different class as the starting class
     * 5) Repeat step 4 until all classes have appeared
     * 6) Then remove duplicates?
     * 7) Add Labs and Tutorials
     * Note: If a class doesn't fit then it isn't added
     */


    //MODIFIES: this
    //EFFECTS: Goes through classes in order and adds them to schedule if possible in a tree-like fashion
    public boolean buildSchedulesOnlyMainWithPriority() {
        if (coursesToTake.size() == 0) {
            return false;
        }

        for (int i = 0; i < Math.min(coursesToTake.size(), maxCourses); i++) {
            buildScheduleLoop(i);
        }

        int subtractor = 1;
        while (tierXClasses[tierXClasses.length - subtractor].size() == 0) {
            subtractor++;
            //if (tierXClasses.length - subtractor < 0) {
            //    break;
            //}
        }
        // reimplement this if-statement if the code above needs to be uncommented
        //if (tierXClasses[tierXClasses.length - subtractor].size() != 0) {
        this.listOfPossibilities = tierXClasses[tierXClasses.length - subtractor];
        //return true;
        //}
        return true; // I couldn't think of a test that would return false at this point
        //return false;
    }

    //MODIFIES: this
    //EFFECTS: Gets a deepCopy of a class with a different first hour and then adds it
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

    //MODIFIES: this
    //EFFECTS: Creates new schedules by adding this class to other possible classes in the previous layer of the tree
    //          Adds them in the next layer
    private void addPseudoCourse(int i, Course pseudoCourse) {
        if (i == 0) {
            Scheduler schedule1 = new Scheduler(maxCourses);
            schedule1.addCourseToSchedule(pseudoCourse);
            tierXClasses[i].add(schedule1);
        } else {
            int subtractor = 1;
            while (tierXClasses[i - subtractor].size() == 0) {
                subtractor++;
                //if (i - subtractor < 0) {
                //    break;
                //}
            }
            // reimplement this if-statement if the code above needs to be uncommented
            //if (tierXClasses[i - subtractor].size() != 0) {
            for (int k = 0; k < tierXClasses[i - subtractor].size(); k++) {
                Scheduler deepCopy = new Scheduler(tierXClasses[i - subtractor].get(k));
                if (deepCopy.addCourseToSchedule(pseudoCourse)) {
                    tierXClasses[i].add(deepCopy);
                }
            }
            //}
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

    //THESE VARIABLES ARE USED HERE BECAUSE THEY SHOULD *NOT* BE USED ELSEWHERE OUTSIDE THIS FUNCTION
    //NEEDED TO BE OUTSIDE TO ABSTRACT THE METHOD
    private int currentTier = 1;

    private boolean successLab = false;
    private boolean hadLab = false;

    private boolean successTutorial = false;
    private boolean hadTutorial = false;

    //MODIFIES: this
    //EFFECTS: Changes the current listOfPossibilites to one with labs
    public boolean buildSchedulesWithLabsAndTutorials() {
        if (!(listOfPossibilities != null)) {
            return false;
        }
        tierXClassesWithLT[0] = deepCopyLoP();
        int currentTier = 1;

        successLab = false;
        hadLab = false;
        currentTier = buildScheduleWithLabs(currentTier);
        if (!hadLab) {
            successLab = true;
        }

        successTutorial = false;
        hadTutorial = false;
        currentTier = buildScheduleWithTutorials(currentTier);
        if (!hadTutorial) {
            successTutorial = true;
        }

        if (!(!successLab || !successTutorial)) {
            this.listOfPossibilitiesWithLT = tierXClassesWithLT[currentTier - 1];
            hasAddedLabsAndTutorials = true;
        }

        return (!(!successLab || !successTutorial));
    }

    //REQUIRES: currentTier to be a non-zero index of the list
    //MODIFIES: this
    //EFFECTS: builds the schedule by tiers based on labs
    private int buildScheduleWithTutorials(int currentTier) {
        for (int i = 0; i < coursesToTake.size(); i++) {
            Course course = coursesToTake.get(i);
            if (course.getHasTutorial()) {
                hadTutorial = true;
                if (fillNextTierWithTutorials(course, currentTier)) {
                    successTutorial = true;
                }
                currentTier++;
            }
        }
//        for (int i = 0; i < tierXClassesWithLT[currentTier - 1].size(); i++) {
//            Scheduler schedule = tierXClassesWithLT[currentTier - 1].get(i);
//            String[] coursesInSchedule = schedule.getCoursesInSchedule();
//            for (int j = 0; j < coursesToTake.size(); j++) {
//                Course course = coursesToTake.get(j);
//                if (course.getHasTutorial() && Arrays.asList(coursesInSchedule).contains(course.getName())) {
//                    hadTutorial = true;
//                    if (fillNextTierWithTutorials(course, currentTier)) {
//                        successTutorial = true;
//                    }
//                    currentTier++;
//                }
//            }
//        }
        return currentTier;
    }

    //REQUIRES: currentTier to be a non-zero index of the list
    //MODIFIES: this
    //EFFECTS: builds the schedule by tiers based on tutorials
    private int buildScheduleWithLabs(int currentTier) {
        for (int i = 0; i < coursesToTake.size(); i++) {
            Course course = coursesToTake.get(i);
            if (course.getHasLab()) {
                hadLab = true;
                if (fillNextTierWithLabs(course, currentTier)) {
                    successLab = true;
                }
                currentTier++;
            }
        }
//        for (int i = 0; i < tierXClassesWithLT[currentTier - 1].size(); i++) {
//            Scheduler schedule = tierXClassesWithLT[currentTier - 1].get(i);
//            String[] coursesInSchedule = schedule.getCoursesInSchedule();
//            for (int j = 0; j < coursesToTake.size(); j++) {
//                Course course = coursesToTake.get(j);
//                if (course.getHasLab() && Arrays.asList(coursesInSchedule).contains(course.getName())) {
//                    hadLab = true;
//                    if (fillNextTierWithLabs(course, currentTier)) {
//                        successLab = true;
//                    }
//                    currentTier++;
//                }
//            }
//        }
        return currentTier;
    }

    //EFFECTS: creates a new deepCopy of the listOfPossibilities
    private ArrayList<Scheduler> deepCopyLoP() {
        ArrayList<Scheduler> copiedLoP = new ArrayList<>();

        //if (listOfPossibilities != null) {
        for (int i = 0; i < listOfPossibilities.size(); i++) {
            copiedLoP.add(new Scheduler(listOfPossibilities.get(i)));
        }
        //}
        return copiedLoP;
    }

    //REQUIRES: curTier to be a non-zero layer of the tree
    //MODIFIES: this
    //EFFECTS: fills next later of tree with labs
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

    //REQUIRES: curTier to be a non-zero layer of the tree
    //MODIFIES: this
    //EFFECTS: fills next later of tree with tutorials
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

    //REQUIRES: curTier to be a non-zero layer of the tree, times to be in the correct format from Course
    //MODIFIES: this
    //EFFECTS: actually does the adding of the labs and tutorials
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
            // With how the tree is currently build, it will be impossible for the else to run, but
            // a future improvement might change that so this is kept for safety
        }
        return returnBool;
    }

}
