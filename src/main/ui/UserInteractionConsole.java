package ui;

import java.util.Scanner;

import java.util.ArrayList;
import java.util.Arrays;

import model.Course;
import model.Designer;
import model.Scheduler;

//User Interaction, asking info about classes and how they want to design schedules
public class UserInteractionConsole {

    Scanner scanner;

    ArrayList<Course> activeCourseList; //ACL
    Designer designer;

    ArrayList<ArrayList<Course>> listOfCoursesPermutation;
    ArrayList<Scheduler> scheduleList = new ArrayList<>();

    int maxClassesAtOnce;

    ScheduleList scheduleList;
    ScheduleList CourseList;

    //EFFECTS: Initializes the communication with the user and instantiates course info
    public UserInteractionConsole() {
        activeCourseList = new ArrayList<>();
        scanner = new Scanner(System.in);

        loadLists(); //TODO, do ScheduleList and CourseList initialization

        System.out.println("Hello! Welcome to your personalized Schedule Planner \n\n");

        int choice = userInteractionTree();
        while (choice != 8) {
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
                    addActiveCourseListToCourseList();
                    break;
                    //TODO clears stuff
                case 5:
                    generate();
                    //GET maxClassesAtOnce here
                    break;
                case 6:
                    saveGeneratedSchedules();
                    //TODO clears stuff
                    break;
                case 7:
                    viewSchedules();
                    break;
            }
            choice = userInteractionTree();
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
                this.scheduleList.add(schedules.get(i));
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
    public void showAllSchedules() {
        for (int i = 0; i < scheduleList.size(); i++) {
            System.out.println(" ____ ");
            printSchedule(scheduleList.get(i));
            System.out.println(" ____ ");
        }
    }

    //EFFECTS: Prints all the schedules in scheduleList that have the classes the user wants
    public void showAllSchedulesFiltered() {
        ArrayList<String> filters = new ArrayList<>();
        while (yesNoQuestion("Would you like to filter for another class?")) {
            System.out.println("What is the Base name of the class you want to filter for "
                    + "(eg. CPSC 210, NOT CPSC 210 201)");
            filters.add(scanner.nextLine());
        }
        for (int i = 0; i < scheduleList.size(); i++) {
            for (String s : filters) {
                if (Arrays.stream(scheduleList.get(i).getCoursesInSchedule()).anyMatch(s::equals)) {
                    System.out.println(" ____ ");
                    printSchedule(scheduleList.get(i));
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
    private Course obtainCourseSafely() {
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
            return new Course(name, subClassNames, subClassTimes,
                    response1, labNames, labTimes,
                    response2, tutorialNames, tutorialTimes);
        }

        return new Course(name, subClassNames, subClassTimes);
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
        System.out.println("\t 4) Save Active Course List to saved courses");
        System.out.println("\t 5) Generate schedules from Active Course List");
        System.out.println("\t 6) Save select generated schedules to saved schedules");
        System.out.println("\t 7) View all Schedules");
        System.out.println("\t 8) Exit");

        return obtainIntSafely(1,8, "That is not one of the options, try again");
    }
}
