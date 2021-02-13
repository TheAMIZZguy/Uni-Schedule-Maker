package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

import model.Course;
import model.Designer;
import model.Scheduler;

public class UserInteractionConsole {

    Scanner scanner;

    ArrayList<Course> listOfCourses;
    Designer designer;

    ArrayList<ArrayList<Course>> listOfCoursesPermutation;
    ArrayList<Scheduler> scheduleList;

    int numOfClasses;
    int maxClassesAtOnce;

    public UserInteractionConsole() {
        listOfCourses = new ArrayList<Course>();

        System.out.println("Hello! Welcome to your personalized Schedule Planner");
        System.out.println("To start off, Up to how many classes or events would you like to Add?");

        scanner = new Scanner(System.in);

        numOfClasses = obtainIntSafely(1, 100,
                "This must be a positive integer less than 100");

        System.out.println("And how many would you take at once?");
        maxClassesAtOnce = obtainIntSafely(1, 8, "This must be a positive integer less than 8");


        System.out.println("\nNote: Add Classes in the order you would most want to take them\n");
        for (int i = 0; i < numOfClasses; i++) {
            listOfCourses.add(obtainCourseSafely());
        }
    }

    public void generate() {
        int option = optionQuestion("Would you like to compute a list of possible schedules "
                + " via simple tree (1), permutation tree (2), or semi-permutation tree (3). "
                + "\n WARNING: PERMUTATION TREE CAN BE VERY SLOW AND SHOULD ONLY BE "
                + "USED WITH A SMALL SELECTION OF CLASSES", 3);


        switch (option) {
            case 1:
                designer = new Designer(listOfCourses, maxClassesAtOnce);
                designer.buildSchedulesOnlyMainWithPriority();
                designer.buildSchedulesWithLabsAndTutorials();
                scheduleList = designer.getSchedules();
                break;
            case 2:
                //TODO Permutation
                break;
            case 3:
                //TODO Semi-Permutation
                break;
            default:
                break;

        }
    }

    private int optionQuestion(String message, int max) {
        System.out.println(message + " (enter the number without parenthesis)");
        return obtainIntSafely(0, max, ("Type a number between 0 and " + max));
    }

    public void createPermutationSchedule() {

    }

    public void showAllSchedules() {
        for (int i = 0; i < scheduleList.size(); i++) {
            System.out.println(" ____ ");
            printSchedule(scheduleList.get(i));
            System.out.println(" ____ ");
        }
    }

    private void printSchedule(Scheduler scheduler) {
        System.out.println("\tMonday \t Tuesday \t Wednesday \t Thursday \t Friday \n");
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
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
    }

    public void showSchedulesWithClass() {

    }

    private ArrayList<String> subClassNames;
    private ArrayList<int[][]> subClassTimes;
    private ArrayList<String> labNames = new ArrayList<>();
    private ArrayList<int[][]> labTimes = new ArrayList<>();
    private ArrayList<String> tutorialNames = new ArrayList<>();
    private ArrayList<int[][]> tutorialTimes = new ArrayList<>();

    private Course obtainCourseSafely() {
        System.out.println("What is the name of the first/next Course (eg. CPSC 210)");
        String name = scanner.nextLine();

        System.out.println("How many Sub-Classes does this have? (eg. CPSC 210 201 and CPSC 210 202 would mean 2)");
        subClassNames = getNames(obtainIntSafely(1, 999, "Enter int > 0"), "Class");
        subClassTimes = getTimes(name, subClassNames);

        Boolean response1 = yesNoQuestion("Does this class have a separate lab component?");
        if (response1) {
            System.out.println("How many Labs does this have?");
            labNames = getNames(obtainIntSafely(1, 999, "Enter int > 0"), "Lab");
            labTimes = getTimes(name, labNames);
        }

        Boolean response2 = yesNoQuestion("Does this class have a separate tutorial component?");
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

    private boolean yesNoQuestion(String message) {
        System.out.println(message + " y/n");
        String answer = scanner.nextLine();
        while (!(answer.equals("y") || (answer.equals("n")))) {
            System.out.println("Type 'y' or 'n'");
            answer = scanner.nextLine();
        }
        return answer.equals("y");
    }

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

    private ArrayList<String> getNames(int numOfSub, String type) {
        ArrayList<String> subNames = new ArrayList<String>();
        for (int i = 1; i <= numOfSub; i++) {
            if (type.equals("Class")) {
                System.out.println("Name of Sub-Class " + i + " (eg. CPSC 210 202 Sub-Class Name would be 202)");
            } else if (type.equals("Lab")) {
                System.out.println("Name of Lab " + i + " (eg. CPSC 210 L2A Lab Name would be L2A)");
            } else {
                System.out.println("Name of Tutorial " + i + " (eg. CPSC 210 T2A Lab Name would be T2A)");
            }
            subNames.add(scanner.nextLine());
        }
        return subNames;
    }

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

    private int[] obtainTimeSafely(String name, String subClass, String startend) {
        System.out.println("At what time does " + name + " " + subClass + " " + startend);
        System.out.println("Use Military Time to nearest half hour (Example: 5:30pm would be 1730))");
        int num = 0;
        if (startend == "start?") {
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
        } else {
            returnMins = 0;
        }


        return new int[]{returnHour, returnHour};
    }

    private int obtainIntSafely(int min, int max, String failMessage) {
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
            } catch (InputMismatchException e) {
                validInput = false;
                scanner.nextLine();
                System.out.print(failMessage);
            }
        }
        return num;
    }
}
