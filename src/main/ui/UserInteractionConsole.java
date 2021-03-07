package ui;

import java.io.IOException;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Arrays;

import model.*;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

//User Interaction, asking info about classes and how they want to design schedules
public class UserInteractionConsole {

    Scanner scanner;

    ArrayList<Course> activeCourseList; //ACL
    Designer designer;

    ArrayList<ArrayList<Course>> listOfCoursesPermutation;
    ArrayList<Scheduler> activeScheduleList;

    int maxClassesAtOnce;

    ScheduleList scheduleList;
    CourseList courseList;
    JsonReader reader;

    //EFFECTS: Initializes the communication with the user and instantiates course info
    public UserInteractionConsole() {
        setupVariables();

        System.out.println("Select an Option");
        int choice = userInteractionTree();
        while (true) {
            if (choice < 7) {
                courseOptions(choice);
            } else if (choice < 11) {
                scheduleOptions(choice);
            } else if (choice == 11) {
                saveAndExit();
                break;
            } else {
                break;
            }
            System.out.println("\n\nSelect an Option");
            choice = userInteractionTree();
        }

    }

    private void saveAndExit() {
        try {
            JsonWriter writer = new JsonWriter("./data/testScheduleList.json",
                    "./data/testCourseList.json");
            writer.open();
            writer.writeScheduleList(scheduleList);
            writer.close(true);

            writer.open();
            writer.writeCourseList(courseList);
            writer.close(false);
        } catch (IOException ioe) {
            System.out.println("File Not Found, failed to save");
        } catch (Exception e) {
            System.out.println("Unexpected Error, failed to save");
        }

    }

    private void courseOptions(int choice) {
        switch (choice) {
            case 1:
                obtainCourseSafely();
                break;
            case 2:
                addSavedCourseToACL();
                break;
            case 3:
                viewActiveCourseList();
                break;
            case 4:
                removeFromActiveCourseList();
                break;
            case 5:
                removeFromSavedCourseList();
                break;
            case 6:
                addActiveCourseListToCourseList();
                break;
        }
    }

    private void removeFromSavedCourseList() {
        System.out.println("The current courses in the saved course list are: ");

        for (int i = 0; i < courseList.getCourseList().size(); i++) {
            System.out.println((i + 1) + ": " + courseList.getCourseList().get(i).getName());
        }

        System.out.println("Write the number of the one you wish to remove");
        int removeIndex = obtainIntSafely(1, courseList.getCourseList().size(), "Number out of bounds");
        courseList.getCourseList().remove(removeIndex - 1);
        System.out.println("Removed!");
    }

    private void removeFromActiveCourseList() {
        System.out.println("The current courses in the active course list are: ");

        for (int i = 0; i < activeCourseList.size(); i++) {
            System.out.println((i + 1) + ": " + activeCourseList.get(i).getName());
        }

        System.out.println("Write the number of the one you wish to remove");
        int removeIndex = obtainIntSafely(1, activeCourseList.size(), "Number out of bounds");
        activeCourseList.remove(removeIndex - 1);
        System.out.println("Removed!");
    }

    private void scheduleOptions(int choice) {
        switch (choice) {
            case 7:
                generate();
                if (yesNoQuestion("Would you like to filter through the generated schedules?")) {
                    showAllSchedulesFiltered(activeScheduleList);
                } else {
                    showAllSchedules(activeScheduleList);
                }
                break;
            case 8:
                saveGeneratedSchedules();
                break;
            case 9:
                viewSavedSchedules();
                break;
            case 10:
                removeFromSavedScheduleList();
                break;
        }
    }

    private void removeFromSavedScheduleList() {
        if (!yesNoQuestion("This will print every schedule, are you sure? ")) {
            return;
        }

        showAllSchedules(scheduleList.getScheduleList());

        System.out.println("What is the number of the one you want to remove?");
        int removeIndex = obtainIntSafely(1, scheduleList.getScheduleList().size(), "Number out of bounds");
        scheduleList.getScheduleList().remove(removeIndex - 1);
        System.out.println("Removed!");
    }

    private void viewSavedSchedules() {
        if (yesNoQuestion("Would you like to filter through the saved schedules?")) {
            showAllSchedulesFiltered(scheduleList.getScheduleList());
        } else {
            showAllSchedules(scheduleList.getScheduleList());
        }
    }

    private void saveGeneratedSchedules() {
        if (yesNoQuestion("(y) Do you want to save all of them? (n) Or just a few? ")) {
            scheduleList.addSchedulesToList(activeScheduleList);
            activeScheduleList = new ArrayList<>();
            System.out.println("Added and Cleared!");
            return;
        }

        System.out.println("How many of the schedules to add? (The rest will be cleared afterwards)");
        int numSchedulesToAdd = obtainIntSafely(0, activeScheduleList.size(), "That is out of bounds");
        if (numSchedulesToAdd == activeScheduleList.size()) {
            System.out.println("Selected maximum Amount, adding all");
        }

        for (int i = 0; i < numSchedulesToAdd; i++) {
            System.out.println("What is the number of the schedule to save?");
            int schedToAdd = obtainIntSafely(1, activeScheduleList.size(), "That is out of bounds");
            scheduleList.addScheduleToList(activeScheduleList.get(schedToAdd - 1));
            System.out.println("Done");
        }

        activeScheduleList = new ArrayList<>();
        System.out.println("Added and Cleared!");
    }

    private void addActiveCourseListToCourseList() {
        courseList.addCoursesToList(activeCourseList);
        activeCourseList = new ArrayList<>();
        System.out.println("Added and Cleared!");
    }

    private void viewActiveCourseList() {
        boolean detailed = yesNoQuestion("Would you like to look at the detailed version? (just names if no) \n");

        System.out.println("The current active courses are:\n ");
        for (int i = 0; i < activeCourseList.size(); i++) {
            if (detailed) {
                System.out.println((i + 1) + ":");
                detailedCoursePrint(activeCourseList.get(i));
            } else {
                System.out.println((i + 1) + ": " + activeCourseList.get(i).getName());
            }
        }

    }

    private void detailedCoursePrint(Course course) {
        System.out.println("Name: " + course.getName());
        for (String subCourse : course.getSubClassNames()) {
            printNameWithTimes(course, subCourse, "Section");
        }
        for (String lab : course.getLabNames()) {
            printNameWithTimes(course, lab, "Lab");
        }
        for (String tutorial : course.getTutorialNames()) {
            printNameWithTimes(course, tutorial, "Tutorial");
        }
    }

    private void printNameWithTimes(Course course, String name, String type) {
        String startTime = "";
        String endTime = "";
        int[][] selection;

        if (type.equals("Section")) {
            selection = course.getSubClassTimes().get(name);
        } else if (type.equals("Lab")) {
            selection = course.getLabTimes().get(name);
        } else  {
            selection = course.getTutorialTimes().get(name);
        }

        if (selection[0][1] == 0) {
            startTime = String.valueOf(selection[0][0]) + ":00";
        } else {
            startTime = String.valueOf(selection[0][0]) + ":" + String.valueOf(selection[0][1]);
        }

        if (selection[1][1] == 0) {
            endTime = String.valueOf(selection[1][0]) + ":00";
        } else {
            endTime = String.valueOf(selection[1][0]) + ":" + String.valueOf(selection[1][1]);
        }

        String days = intsToDays(selection[2]);

        System.out.println("\t" + type + ": " + name + "\t Start: " + startTime
                + "\t End: " + endTime + "\t Days: " + days);
    }

    private String intsToDays(int[] intArr) {
        String returnString = "";
        for (int i : intArr) {
            if (i == 1) {
                returnString = returnString + " Mon";
            }
            if (i == 2) {
                returnString = returnString + " Tue";
            }
            if (i == 3) {
                returnString = returnString + " Wed";
            }
            if (i == 4) {
                returnString = returnString + " Thu";
            }
            if (i == 5) {
                returnString = returnString + " Fri";
            }
        }
        return returnString;
    }

    private void setupVariables() {
        activeCourseList = new ArrayList<>();
        activeScheduleList = new ArrayList<>();

        scanner = new Scanner(System.in);
        reader = new JsonReader("./data/ScheduleList.json",
                "./data/CourseList.json");

        loadLists();
    }

    private void loadLists() {
        loadSavedCourses();
        loadSavedSchedules();
    }


    private void addSavedCourseToACL() {
        System.out.println("(note that selecting a saved course will extract it and it will need to be saved again)\n");
        int numSavedCourses = courseList.getCourseList().size();
        if (numSavedCourses == 0) {
            System.out.println("There are no saved courses at the moment");
            return;
        }
        System.out.println("The current saved courses are \n");
        printSavedCoursesNames();
        System.out.println("Select which one you want to add (select number) ");
        int courseSelected = obtainIntSafely(1, numSavedCourses,
                "Please choose the number next to a course");

        activeCourseList.add(courseList.getCourseList().get(courseSelected));
        courseList.removeCourseFromList(courseSelected);
        System.out.println("Added!");
    }

    private void printSavedCoursesNames() {
        ArrayList<Course> tempCourseList = courseList.getCourseList();
        for (int i = 0; i < tempCourseList.size(); i++) {
            System.out.println((i + 1) + ": " + tempCourseList.get(i).getName());
        }
    }

    private void loadSavedSchedules() {
        scheduleList = new ScheduleList(new ArrayList<>());
        try {
            scheduleList = reader.readSchedules();
        } catch (IOException e) {
            System.err.println("Course File Missing");
        } catch (JSONException je) { //It's fine if this one runs, it's expected for the first ever run
            //System.err.println("Empty File");
            //System.out.println(je);
        }
    }

    private void loadSavedCourses() {
        courseList = new CourseList(new ArrayList<>());
        try {
            courseList = reader.readCourseList();
        } catch (IOException ioe) {
            System.err.println("Course File Missing");
        } catch (JSONException je) { //It's fine if this one runs, it's expected for the first ever run
            //System.err.println("Other Course Error");
            //System.out.println(je);
        }
    }

    //MODIFIES: this
    //EFFECTS: Generates a list of schedules with a chosen option
    public boolean generate() {
        System.out.println("Before generating, up to how many courses would you take at once?");
        maxClassesAtOnce = obtainIntSafely(1, 8, "This must be a positive integer less than 8");

        int option = optionQuestion("Would you like to compute a list of possible schedules "
                + " via simple tree (1), permutation tree (2), or semi-permutation tree (3). "
                + "\n WARNING: PERMUTATION TREE CAN BE VERY SLOW AND SHOULD ONLY BE "
                + "USED WITH A SMALL SELECTION OF CLASSES \n Option 3 is Recommended", 3);

        Boolean returnBool = true;

        switch (option) {
            case 1:
                returnBool = simpleDesigner(activeCourseList);
                break;
            case 2:
                returnBool = createPermutationSchedule();
                break;
            case 3:
                returnBool = createSemiPermutationSchedule();
                break;
        }
        if (!returnBool) {
            System.out.println("Impossible to make a schedule");
        }
        return returnBool;
    }

    private boolean simpleDesigner(ArrayList<Course> listOfCoursesForDesigner) {
        boolean returnBool = true;
        designer = new Designer(listOfCoursesForDesigner, maxClassesAtOnce);
        if (designer.buildSchedulesOnlyMainWithPriority() && designer.buildSchedulesWithLabsAndTutorials()) {
            ArrayList<Scheduler> schedules = designer.getSchedules();
            for (int i = 0; i < schedules.size(); i++) {
                this.activeScheduleList.add(schedules.get(i));
            }
        } else {
            returnBool = false;
        }
        return returnBool;
    }

    //REQUIRES: max to be a non-negative integer
    //EFFECTS: implements safe inputs for options method
    private int optionQuestion(String message, int max) {
        System.out.println(message + " (enter the number without parenthesis)");
        return obtainIntSafely(1, max, ("Type a number between 1 and " + max));
    }

    public Boolean createPermutationSchedule() {
        listOfCoursesPermutation = permutationOfCourseList(activeCourseList);

        Boolean returnBool = true;
        for (int i = 0; i < listOfCoursesPermutation.size(); i++) {
            if (!simpleDesigner(listOfCoursesPermutation.get(i))) {
                returnBool = false;
            }
        }
        return returnBool;
    }

    //These next two functions were pulled from StackOverflow in order to get a permutation of a list,
    //I modified it to work with the courses
    //EFFECTS: make an arraylist of arraylists of every permutation of the arraylist
    public ArrayList<ArrayList<Course>> permutationOfCourseList(ArrayList<Course> originalCourseList) {
        ArrayList<ArrayList<Course>> results = new ArrayList<ArrayList<Course>>();
        if (originalCourseList == null || originalCourseList.size() == 0) {
            return results;
        }
        ArrayList<Course> result = new ArrayList<>();
        recursivePermuter(originalCourseList, results, result);
        return results;
    }

    //EFFECTS: make an arraylist of arraylists of every permutation of the arraylist.
    //     Does so by recursively switching values around.
    public void recursivePermuter(ArrayList<Course> originalCourseList,
                                  ArrayList<ArrayList<Course>> results, ArrayList<Course> result) {
        if (originalCourseList.size() == result.size()) {
            ArrayList<Course> temp = new ArrayList<>(result);
            results.add(temp);
        }
        for (int i = 0; i < originalCourseList.size(); i++) {
            if (!result.contains(originalCourseList.get(i))) {
                result.add(originalCourseList.get(i));
                recursivePermuter(originalCourseList, results, result);
                result.remove(result.size() - 1);
            }
        }
    }

    //EFFECTS: creates n arrays where each one is the same but with a different starting point, returns true if success
    public boolean createSemiPermutationSchedule() {

        listOfCoursesPermutation = semiPermutationOfCourseList(activeCourseList);

        boolean returnBool = true;
        for (int i = 0; i < listOfCoursesPermutation.size(); i++) {
            if (!simpleDesigner(listOfCoursesPermutation.get(i))) {
                returnBool = false;
            }
        }
        return returnBool;
    }

    //EFFECTS: creates n arrays where each one is the same but with a different starting point
    private ArrayList<ArrayList<Course>> semiPermutationOfCourseList(ArrayList<Course> originalCourseList) {
        ArrayList<ArrayList<Course>> results = new ArrayList<ArrayList<Course>>();
        if (originalCourseList == null || originalCourseList.size() == 0) {
            return results;
        }

        for (int i = 0; i < originalCourseList.size(); i++) {
            ArrayList<Course> tempList = new ArrayList<>();
            tempList = new ArrayList<>(originalCourseList.subList(i, originalCourseList.size()));
            for (int j = 0; j < i; j++) {
                tempList.add(originalCourseList.get(j));
            }
            results.add(tempList);
        }

        return results;
    }

    //EFFECTS: Prints all the schedules in scheduleList
    public void showAllSchedules(ArrayList<Scheduler> schedulesToPrint) {
        for (int i = 0; i < schedulesToPrint.size(); i++) {
            System.out.println(" ____ " + (i + 1) + ":");
            printSchedule(schedulesToPrint.get(i));
            System.out.println(" ____ ");
        }
    }

    //EFFECTS: Prints all the schedules in scheduleList that have the classes the user wants
    public void showAllSchedulesFiltered(ArrayList<Scheduler> schedulesToPrint) {
        ArrayList<String> filters = new ArrayList<>();
        while (yesNoQuestion("Would you like to filter for another class?")) {
            System.out.println("What is the Base name of the class you want to filter for "
                    + "(eg. CPSC 210, NOT CPSC 210 201)");
            filters.add(scanner.nextLine());
        }
        for (int i = 0; i < schedulesToPrint.size(); i++) {
            for (String s : filters) {
                if (Arrays.stream(schedulesToPrint.get(i).getCoursesInSchedule()).anyMatch(s::equals)) {
                    System.out.println(" ____ " + (i + 1) + ":");
                    printSchedule(schedulesToPrint.get(i));
                    System.out.println(" ____ ");
                }
            }
        }
    }

    //REQUIRES: scheduler to be non-null
    //EFFECTS: prints the schedule in a human readable manner
    private void printSchedule(Scheduler scheduler) {
        System.out.println("  \tMonday      \t Tuesday    \t Wednesday  \t Thursday   \t Friday     \n");
        String[][] scheduleToPrint = scheduler.getSchedule();
        for (int i = 0; i < scheduleToPrint.length; i++) {
            if (i % 2 == 0) {
                System.out.print(String.valueOf(i / 2 + 7) + "\t");
            } else {
                System.out.print("\t");
            }
            for (int j = 0; j < scheduleToPrint[i].length; j++) {
                if (scheduleToPrint[i][j] != null) {
                    System.out.print(scheduleToPrint[i][j] + "\t");
                } else {
                    System.out.print("            \t");
                }
            }
            System.out.println();
        }
    }


    //These were extracted for easy use
    private ArrayList<String> subClassNames;
    private ArrayList<int[][]> subClassTimes;
    private ArrayList<String> labNames = new ArrayList<>();
    private ArrayList<int[][]> labTimes = new ArrayList<>();
    private ArrayList<String> tutorialNames = new ArrayList<>();
    private ArrayList<int[][]> tutorialTimes = new ArrayList<>();

    //EFFECTS: guides the users through the steps of designing a course
    private void obtainCourseSafely() {
        System.out.println("What is the name of the Course you wish to add (eg. CPSC 210)");
        String name = scanner.nextLine();

        System.out.println("How many Sub-Classes does this have? (eg. CPSC 210 201 and CPSC 210 202 would mean 2)");
        subClassNames = getNames(obtainIntSafely(1, 999, "Enter int > 0"), "Class");
        subClassTimes = getTimes(name, subClassNames);

        boolean response1 = yesNoQuestion("Does this class have a separate lab component?");
        if (response1) {
            System.out.println("How many Labs does this have?");
            labNames = getNames(obtainIntSafely(1, 999, "Enter int > 0"), "Lab");
            labTimes = getTimes(name, labNames);
        }

        boolean response2 = yesNoQuestion("Does this class have a separate tutorial component?");
        if (response2) {
            System.out.println("How many Tutorials does this have?");
            tutorialNames = getNames(obtainIntSafely(1, 999, "Enter int > 0"), "Tutorial");
            tutorialTimes = getTimes(name, tutorialNames);
        }

        if (response1 || response2) {
            activeCourseList.add(new Course(name, subClassNames, subClassTimes,
                    response1, labNames, labTimes,
                    response2, tutorialNames, tutorialTimes));
        } else {
            activeCourseList.add(new Course(name, subClassNames, subClassTimes));
        }
    }

    //EFFECTS: safely guides the user through answering a yes/no question
    public boolean yesNoQuestion(String message) {
        System.out.println(message + " y/n");
        String answer = scanner.nextLine();
        while (!(answer.equals("y") || (answer.equals("n")))) {
            System.out.println("Type 'y' or 'n'");
            answer = scanner.nextLine();
        }
        return answer.equals("y");
    }

    //EFFECTS: safely guides the user through designing the start and end times of a class
    private ArrayList<int[][]> getTimes(String name, ArrayList<String> subNames) {
        ArrayList<int[][]> subTimes = new ArrayList<>();
        for (String sub : subNames) {
            int[] startTime = obtainTimeSafely(name, sub, "start?");
            int[] endTime = obtainTimeSafely(name, sub, "end?");
            int[] days = obtainDaysSafely(name, sub);
            subTimes.add(new int[][]{startTime, endTime, days});
        }
        return subTimes;
    }

    //EFFECTS: safely guides the user through naming their labs and tutorials and sub classes
    private ArrayList<String> getNames(int numOfSub, String type) {
        ArrayList<String> subNames = new ArrayList<String>();
        for (int i = 1; i <= numOfSub; i++) {
            if (type.equals("Class")) {
                System.out.println("Name of Sub-Class " + i + " (eg. CPSC 210 202 Sub-Class Name would be 202)");
            } else if (type.equals("Lab")) {
                System.out.println("Name of Lab " + i + " (eg. CPSC 210 L2A Lab Name would be L2A)");
            } else {
                System.out.println("Name of Tutorial " + i + " (eg. CPSC 210 T2A Tutorial Name would be T2A)");
            }
            subNames.add(scanner.nextLine());
        }
        return subNames;
    }

    //EFFECTS: safely guides the user through selecting the days for their class or subpart
    private int[] obtainDaysSafely(String name, String subClass) {
        System.out.println("At what days does " + name + " " + subClass + " take place?");
        ArrayList<Integer> preDays = new ArrayList();
        int[] days;
        String[] daysArray = new String[]{"Monday?", "Tuesday?", "Wednesday?", "Thursday?", "Friday?"};
        for (int i = 0; i < 5; i++) {
            System.out.println("Does " + name + " " + subClass + " take place on " + daysArray[i] + " y/n");
            String answer = scanner.nextLine();
            while (!(answer.equals("y") || (answer.equals("n")))) {
                System.out.println("Type 'y' or 'n'");
                answer = scanner.nextLine();
            }
            if (answer.equals("y")) {
                preDays.add(i + 1);
            }
        }
        days = new int[preDays.size()];
        for (int i = 0; i < preDays.size(); i++) {
            days[i] = preDays.get(i);
        }
        return days;
    }

    //EFFECTS: safely guides the user through selecting the time for their class or subpart
    private int[] obtainTimeSafely(String name, String subClass, String startend) {
        System.out.println("At what time does " + name + " " + subClass + " " + startend);
        System.out.println("Use Military Time to nearest half hour (Example: 5:30pm would be 1730))");
        int num = 0;
        if (startend.equals("start?")) {
            System.out.println("Earliest a class can start is 700 and the latest is 2030");
            num = obtainIntSafely(700, 2030, "That is an invalid input");
        } else {
            System.out.println("Earliest a class can end is 730 and the latest is 2100");
            num = obtainIntSafely(730, 2100, "That is an invalid input");
        }

        String numString = String.valueOf(num);

        int returnHour = num / 100;
        int returnMins = 0;

        if (numString.toCharArray()[numString.toCharArray().length - 2] == '3') {
            returnMins = 30;
        }

        return new int[]{returnHour, returnMins};
    }

    //EFFECTS: safely guides the user through inputing an integer between a range of numbers
    public int obtainIntSafely(int min, int max, String failMessage) {
        int num = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                num = Integer.parseInt(scanner.nextLine());
                if (min <= num && num <= max) {
                    validInput = true;
                    break;
                } else {
                    validInput = false;
                    scanner.nextLine();
                    System.out.print(failMessage);
                }
            } catch (Exception e) {
                validInput = false;
                scanner.nextLine();
                System.out.print(failMessage);
            }
        }
        return num;
    }




    public int userInteractionTree() {
        System.out.println("\t 1) Add new course to Active Course List");
        System.out.println("\t 2) Add saved course to Active Course List");
        System.out.println("\t 3) View Active Course List");
        System.out.println("\t 4) Remove from Active Course List");
        System.out.println("\t 5) Remove from Saved Courses");
        System.out.println("\t 6) Save Active Course List to saved courses");

        System.out.println("\t 7) Generate schedules from Active Course List");
        System.out.println("\t 8) Save generated schedules to saved schedules");
        System.out.println("\t 9) View saved schedules");
        System.out.println("\t 10) Delete saved schedule");

        System.out.println("\t 11) Save and Exit");
        System.out.println("\t 12) Exit without Saving");

        return obtainIntSafely(1,12, "That is not one of the options, try again");
    }
}
