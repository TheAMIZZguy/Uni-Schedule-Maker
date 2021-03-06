package ui;

import model.*;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import javax.sound.sampled.*;
import javax.swing.*;

//The main GUI, subpanels can interact with eachother and share global data through here
public class MainFrame extends JFrame implements ActionListener {
    public static final int WIDTH = 1400;
    public static final int HEIGHT = 1000;

    private static JFrame frame;

    private JSplitPane leftSplit;
    private JSplitPane horizontalSplit;

    JPanel upPane;
    JPanel downPane;

    //// temp courses
    Course cpsc121 = new Course("CPSC 121", new ArrayList<>(Arrays.asList("212")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})));
    Course cpsc110 = new Course("CPSC 110", new ArrayList<>(Arrays.asList("212")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,30}, {11,0}, {1,3,5}}, new int[][]{})));

    Course phys118 = new Course("PHYS 118", new ArrayList<>(Arrays.asList("202")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})),
            true, new ArrayList<>(Arrays.asList("L2A", "L2B")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{15,0}, {18,0}, {4}})),
            true, new ArrayList<>(Arrays.asList("T2A", "T2C")),
            new ArrayList<>(Arrays.asList(new int[][]{{20,0}, {21,0}, {1,3,5}},
                    new int[][]{{12,0}, {13,30}, {2}})));
    //

    ArrayList<Course> savedCourseList = new ArrayList<>();
    ArrayList<Course> activeCourseList = new ArrayList<>();

    private Course selectedSaveCourse;
    private Course selectedActiveCourse;

    ArrayList<ArrayList<Course>> listOfCoursesPermutation;
    ArrayList<Scheduler> activeScheduleList = new ArrayList<>();

    Designer designer;
    
    CourseList courseList;
    ScheduleList scheduleList;
    JsonReader reader;

    ArrayList<String> filterable = new ArrayList<>();
    ArrayList<String> filters = new ArrayList<>();

    protected JButton addNewCourseButton;
    protected JButton viewCoursesButton;
    protected JButton viewSchedulesButton;
    protected JButton saveAndExitButton;

    protected JButton vanityButton;


    //MODIFIES: this
    //EFFECTS: initializes the main starting screen
    public MainFrame() {
        //ActionButtons menuPane = new ActionButtons();
        JPanel menuPane = makeActionButtons();
        
        setupVariables();

        //start with the Schedule view because it needs the filters to be empty on first open
        upPane = new ScheduleFilter(this, filterable, filters);
        downPane = new TableSchedulePanel(this, activeScheduleList, new ArrayList<>());

        Dimension minimumSize = new Dimension(100, 50);
        menuPane.setMinimumSize(minimumSize);
        upPane.setMinimumSize(minimumSize);
        downPane.setMinimumSize(new Dimension(500, 500));


        horizontalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upPane, new JScrollPane(downPane));
        horizontalSplit.setDividerLocation((int) (HEIGHT * .40));
        horizontalSplit.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        leftSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuPane, horizontalSplit);
        leftSplit.setDividerLocation((int) (WIDTH * .20));
        leftSplit.setPreferredSize(new Dimension(WIDTH, HEIGHT));

    }

    private void setupFilters() {
        ArrayList<String> tempFilters = new ArrayList<>();
        for (Scheduler s : this.activeScheduleList) {
            for (String str : s.getCoursesInSchedule()) {
                if (!tempFilters.contains(str)) {
                    tempFilters.add(str);
                }
            }
        }

        this.filterable = tempFilters;
    }

    //MODIFIES: this
    //EFFECTS: initializes some class-level variables
    private void setupVariables() {
        activeCourseList = new ArrayList<>();
        activeScheduleList = new ArrayList<>();

        reader = new JsonReader("./data/ScheduleList.json",
                "./data/CourseList.json");

        loadLists();

        setupFilters();
    }

    //MODIFIES: this
    //EFFECTS: makes the mainframe visible
    public void initializeGraphics() {
        //Create and set up the window.
        frame = new JFrame("Graphical UI Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(getLeftmostSplitPane());

        frame.pack();
        frame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: loads scheduleList and courseList with information from their Json Files
    private void loadLists() {
        loadSavedCourses();
        loadSavedSchedules();
        
        savedCourseList = courseList.getCourseList();
        activeScheduleList = scheduleList.getScheduleList();
    }

    //MODIFIES: this
    //EFFECTS: loads courseList with information from its Json Files
    private void loadSavedCourses() {
        courseList = new CourseList(new ArrayList<>());
        try {
            courseList = reader.readCourseList();
        } catch (IOException ioe) {
            System.err.println("Course File Missing");
        } catch (JSONException je) {
            System.err.println("Empty File - Course");
            System.out.println(je);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads scheduleList with information from its Json Files
    private void loadSavedSchedules() {
        scheduleList = new ScheduleList(new ArrayList<>());
        try {
            scheduleList = reader.readSchedules();
        } catch (IOException e) {
            System.err.println("Schedule File Missing");
        } catch (JSONException je) {
            System.err.println("Empty File - Schedule");
            System.out.println(je);
        }
    }

    private JSplitPane getLeftmostSplitPane() {
        return leftSplit;
    }

    //MODIFIES: this
    //EFFECTS: makes the buttons panel on the leftmost side and assigns actions
    private JPanel makeActionButtons() {
        JPanel actionPanel  = new JPanel(new GridLayout(0,1));

        addNewCourseButton = new JButton("Add New Course");
        //addNewCourseButton.setVerticalTextPosition(AbstractButton.CENTER);
        //addNewCourseButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        addNewCourseButton.setActionCommand("addNew");

        viewCoursesButton = new JButton("View Courses");
        viewCoursesButton.setActionCommand("viewC");

        viewSchedulesButton = new JButton("View Schedules");
        viewSchedulesButton.setActionCommand("viewS");

        saveAndExitButton = new JButton("Save and Exit");
        saveAndExitButton.setActionCommand("saveExit");

        vanityButton = new JButton("Credits");
        vanityButton.setActionCommand("creds");


        //Listen for actions on buttons 1 and 3.
        addNewCourseButton.addActionListener(this);
        viewCoursesButton.addActionListener(this);
        viewSchedulesButton.addActionListener(this);
        saveAndExitButton.addActionListener(this);
        vanityButton.addActionListener(this);


        //accessibility feature on hover over button, maybe implement?
//        addNewCourseButton.setToolTipText("Click this button to disable the middle button.");
//        addFromSavedButton.setToolTipText("This middle button does nothing when you click it.");
//        generateButton.setToolTipText("Click this button to enable the middle button.");

        //Add Components to this container, using the default FlowLayout.
        actionPanel.add(addNewCourseButton);
        actionPanel.add(viewCoursesButton);
        actionPanel.add(viewSchedulesButton);
        actionPanel.add(vanityButton);
        actionPanel.add(saveAndExitButton);

        return actionPanel;
    }

    //MODIFIES: this
    //EFFECTS: does the associated action from a button, with sound
    public void actionPerformed(ActionEvent e) {
        playSound("click1.wav");
        switch (e.getActionCommand()) {
            case "addNew":
                addNewCourse();
                break;
            case "viewC":
                viewCoursePanes(new int[]{0});
                break;
            case "viewS":
                viewSchedulePanes();
                break;
            case "creds":
                credits();
                break;
            default:
                saveAndExit();
                break;
        }
    }

    private void credits() {
        JDialog dialog = new JDialog(new JFrame(), "Credits");

        JLabel label = dialogCredits();

        JPanel contentPane = formatDialogHelperCredit(dialog, label);
        dialog.setContentPane(contentPane);

        dialog.setSize(new Dimension(600, 300));
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    //EFFECTS: The dialog for credits
    private JLabel dialogCredits() {
        JLabel label = new JLabel("<html><p align=left> "
                + "Made By: The Better Snake <br><br>" //"Made By: Andres Zepeda Perez <br><br>"
                + "If you have any suggestions/improvement ideas email me at  <br>"
                + " **********@gmail.com: <br>" //"AndresZepeda137@gmail.com: <br>"
                + "Please Include how you expect this code to be implemented <br><br>"
                + "Any complaints can be sent to: <br>"
                + "notARealEmail@snakemail.com <br><br>"
                + "Made For use with UBC Classes<br>"
                + "Tutorial on how to use (TBA?)");
        label.setHorizontalAlignment(JLabel.CENTER);
        Font font = label.getFont();
        label.setFont(label.getFont().deriveFont(font.PLAIN, 14.0f));
        return label;
    }

    //EFFECTS: Formats the dialog for credit
    private JPanel formatDialogHelperCredit(JDialog dialog, JLabel label) {
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });

        JPanel closePanel = new JPanel();
        closePanel.setLayout(new BoxLayout(closePanel, BoxLayout.LINE_AXIS));
        closePanel.add(Box.createHorizontalGlue());
        closePanel.add(closeButton);
        closePanel.setBorder(BorderFactory.createEmptyBorder(0,0,5,5));

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(label, BorderLayout.CENTER);
        contentPane.add(closePanel, BorderLayout.PAGE_END);
        contentPane.setOpaque(true);
        return contentPane;
    }

    //MODIFIES: this
    //EFFECTS: saves the data through persistence and then exits the program
    private void saveAndExit() {
        addActiveCourseListToCourseList();

        primingListsForSaving();

        try {
            JsonWriter writer = new JsonWriter("./data/ScheduleList.json",
                    "./data/CourseList.json");
            writer.open(true);
            writer.writeScheduleList(scheduleList);
            writer.close(true);

            writer.open(false);
            writer.writeCourseList(courseList);
            writer.close(false);
        } catch (IOException ioe) {
            System.out.println("File Not Found, failed to save");
        } catch (Exception e) {
            System.out.println("Unexpected Error, failed to save");
        }
        System.exit(0);
    }

    //MODIFIES: this
    //EFFECTS: makes sure lists are all merged and aligned with eachother so they can be saved at once
    private void primingListsForSaving() {
        ArrayList<Scheduler> scheduleListScheds = scheduleList.getScheduleList();

        for (Scheduler s : activeScheduleList) {
            if (!scheduleListScheds.contains(s)) {
                scheduleList.addScheduleToList(s);
            }
        }

        scheduleList.getScheduleList().removeIf(s -> !activeScheduleList.contains(s));

//        for (Scheduler s : scheduleListScheds) {
//            if (!activeScheduleList.contains(s)) {
//                scheduleList.removeScheduleFromList(scheduleList.getScheduleList().indexOf(s));
//            }
//        }

        ArrayList<Course> courseListCourses = courseList.getCourseList();

        for (Course c : savedCourseList) {
            if (!courseListCourses.contains(c)) {
                courseList.addCourseToList(c);
            }
        }

        courseList.getCourseList().removeIf(c -> !savedCourseList.contains(c));

//        for (Course c : courseListCourses) {
//            if (!savedCourseList.contains(c)) {
//                courseList.removeCourseFromList(courseList.getCourseList().indexOf(c));
//            }
//        }
    }

    //MODIFIES: this
    //EFFECTS: moves all items in the active course list to the saved course list
    private void addActiveCourseListToCourseList() {
        for (Course c : activeCourseList) {
            if (!savedCourseList.contains(c)) {
                savedCourseList.add(c);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: generates schedules from courses in a way specified by the user in a dialog
    public void generateSchedules() {
        boolean madeSchedule = false;

        int maxClassesAtOnce = getNumberPopup("Up to how many classes would you want to take at once?", 1);
        playSound("click1.wav");
        switch (generationType()) {
            case 0:
                madeSchedule = simpleDesigner(activeCourseList, maxClassesAtOnce);
                break;
            case 1:
                madeSchedule = createPermutationSchedule(maxClassesAtOnce);
                break;
            case 2:
                madeSchedule = createSemiPermutationSchedule(maxClassesAtOnce);
                break;
        }

        clearDuplicateSchedules();

        if (!madeSchedule) {
            playSound("error.wav");
            System.out.println("Impossible to make a schedule");
        }

    }

    //MODIFIES: this
    //EFFECTS: loops through the schedules to make sure that any duplicate schedules are removed (relatively slow)
    private void clearDuplicateSchedules() {
        //first sort lists then compare (they are string lists)
        String[] tempScheds1;
        String[] tempScheds2;

        int size = activeScheduleList.size();
        for (int i = 0; i < size - 1; i++) {
            tempScheds1 = Arrays.stream(activeScheduleList.get(i).getCoursesInSchedule()).filter(
                    Objects::nonNull).toArray(String[]::new);

            Arrays.sort(tempScheds1);

            for (int j = i + 1; j < size; j++) {
                tempScheds2 = Arrays.stream(activeScheduleList.get(j).getCoursesInSchedule()).filter(
                        Objects::nonNull).toArray(String[]::new);
                Arrays.sort(tempScheds2);

                if (Arrays.equals(tempScheds1, tempScheds2)) {
                    activeScheduleList.remove(j);
                    j--; //becuase otherwise it would double skip, and it's more readable than continue;
                    size -= 1;
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: applies a designer to make a list of Scheduler
    //note: boolean return value was made redundant for the moment
    private boolean simpleDesigner(ArrayList<Course> listOfCoursesForDesigner, int maxClassesAtOnce) {
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

    //MODIFIES: this
    //EFFECTS: applies a designer to make a list of Scheduler, with several different iterations for variety
    //note: boolean return value was made redundant for the moment
    public boolean createPermutationSchedule(int maxClassesAtOnce) {
        listOfCoursesPermutation = permutationOfCourseList(activeCourseList);

        boolean returnBool = true;
        for (int i = 0; i < listOfCoursesPermutation.size(); i++) {
            if (!simpleDesigner(listOfCoursesPermutation.get(i), maxClassesAtOnce)) {
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
    public boolean createSemiPermutationSchedule(int maxClassesAtOnce) {

        listOfCoursesPermutation = semiPermutationOfCourseList(activeCourseList);

        boolean returnBool = true;
        for (int i = 0; i < listOfCoursesPermutation.size(); i++) {
            if (!simpleDesigner(listOfCoursesPermutation.get(i), maxClassesAtOnce)) {
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

    //MODIFIES: this
    //EFFECTS: sets up the courseAdderPane with data
    private void addNewCourse() {
        int[] returnInts = new int[3];
        returnInts[0] =  getSubCoursesAmount("Sub Courses", 1);
        playSound("click1.wav");
        returnInts[1] =  getSubCoursesAmount("Labs", 0);
        playSound("click1.wav");
        returnInts[2] =  getSubCoursesAmount("Tutorials", 0);
        playSound("click1.wav");
        viewCoursePanes(returnInts);
    }

    //EFFECTS: Creates Dialog to get a user int, minimim of min to min + 98. For a specific lab or sub course
    private int getSubCoursesAmount(String type, int min) {
        Integer[] maxOptions = new Integer[99 + min];
        for (int i = min; i < maxOptions.length; i++) {
            maxOptions[i] = i;
        }
        try {
            return (int) JOptionPane.showInputDialog(
                    frame,
                    "How many" +  type + "?:\n",
                    type + " Amount",
                    JOptionPane.PLAIN_MESSAGE,
                    new ImageIcon(),
                    maxOptions,
                    min);
        } catch (Exception e) {
            return min;
        }
    }

    //EFFECTS: Creates Dialog to get a user int, minimim of min, to min + 98
    private int getNumberPopup(String message, int min) {
        Integer[] maxOptions = new Integer[99 + min];
        for (int i = min; i < maxOptions.length; i++) {
            maxOptions[i] = i;
        }
        try {
            return (int) JOptionPane.showInputDialog(
                    frame,
                    message,
                    "Number selecter",
                    JOptionPane.PLAIN_MESSAGE,
                    new ImageIcon(),
                    maxOptions,
                    min);
        } catch (Exception e) {
            return min;
        }
    }

    //EFFECTS: creates a dialog to get the specific type of schedule generation the user wants
    private int generationType() {
        String[] options = new String[]{"Simple", "Permutation", "Loop"};
        try {
            String returnStr = (String) JOptionPane.showInputDialog(
                    frame,
                    "How do you want to generate schedules?\n Simple: Generate few schedules\n Permutation: "
                            + "Generate all possible schedules (high chance of duplication)\n Loop: Middle-Ground",
                     "Generation Type",
                    JOptionPane.PLAIN_MESSAGE,
                    new ImageIcon(),
                    options,
                     "Loop");
            if (returnStr.equals("Simple")) {
                return 0;
            } else if (returnStr.equals("Permutation")) {
                return 1;
            } else {
                return 2;
            }
        } catch (Exception e) {
            return 2;
        }
    }

    //setters and getters and adders
    public void setSelectedSaveCourse(Course course) {
        this.selectedSaveCourse = course;
    }

    public void setSelectedActiveCourse(Course course) {
        this.selectedActiveCourse = course;
    }

    public Course getSelectedSaveCourse() {
        return this.selectedSaveCourse;
    }

    public Course getSelectedActiveCourse() {
        return this.selectedActiveCourse;
    }

    public void addToActiveCourseList(Course course) {
        this.activeCourseList.add(course);
    }

    public void setActiveCourseList(ArrayList<Course> activeCourseList) {
        this.activeCourseList = activeCourseList;
    }

    public void setSavedCourseList(ArrayList<Course> savedCourseList) {
        this.savedCourseList = savedCourseList;
    }

    public void setFilters(ArrayList<String> filters) {
        this.filters = filters;
    }

    //REQUIRES: dimensions to be of size 1 with value 0, or size at least 3
    //MODIFIES: this
    //EFFECTS: initilizes panes to view courses and relevant details
    public void viewCoursePanes(int[] dimensions) {
        //frame.removeAll();
        frame.getContentPane().removeAll();

        JPanel menuPane = makeActionButtons();

        upPane = new CourseListDetailer(this, savedCourseList, activeCourseList);

        if (dimensions[0] != 0) {
            downPane = new CourseAdder(this, dimensions);
        } else {
            downPane = new CourseViewer();
        }

        Dimension minimumSize = new Dimension(100, 50);
        menuPane.setMinimumSize(minimumSize);
        upPane.setMinimumSize(minimumSize);
        downPane.setMinimumSize(new Dimension(500, 500));

        horizontalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upPane, new JScrollPane(downPane));
        horizontalSplit.setDividerLocation((int) (HEIGHT * .40));
        horizontalSplit.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        leftSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuPane, horizontalSplit);
        leftSplit.setDividerLocation((int) (WIDTH * .20));
        leftSplit.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        frame.getContentPane().add(getLeftmostSplitPane());
        frame.revalidate();
        frame.repaint();

        frame.pack();
        frame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: initializes panes to view schedules
    public void viewSchedulePanes() {
        //frame.removeAll();
        frame.getContentPane().removeAll();

        JPanel menuPane = makeActionButtons();

        setupFilters();

        upPane = new ScheduleFilter(this, filterable, filters);
        downPane = new TableSchedulePanel(this, activeScheduleList, filters);

        Dimension minimumSize = new Dimension(100, 50);
        menuPane.setMinimumSize(minimumSize);
        upPane.setMinimumSize(minimumSize);
        downPane.setMinimumSize(new Dimension(500, 500));

        horizontalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upPane, new JScrollPane(downPane));
        horizontalSplit.setDividerLocation((int) (HEIGHT * .40));
        horizontalSplit.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        leftSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuPane, horizontalSplit);
        leftSplit.setDividerLocation((int) (WIDTH * .20));
        leftSplit.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        frame.getContentPane().add(getLeftmostSplitPane());
        frame.revalidate();
        frame.repaint();

        frame.pack();
        frame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: changes the course viewer to one with the new course
    public void courseViewerChange(Course course) {

        frame.getContentPane().removeAll();

        JPanel menuPane = makeActionButtons();

        upPane = new CourseListDetailer(this, savedCourseList, activeCourseList);

        downPane = new CourseViewer(course);

        Dimension minimumSize = new Dimension(100, 50);
        menuPane.setMinimumSize(minimumSize);
        upPane.setMinimumSize(minimumSize);
        downPane.setMinimumSize(new Dimension(500, 500));

        horizontalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upPane, new JScrollPane(downPane));
        horizontalSplit.setDividerLocation((int) (HEIGHT * .40));
        horizontalSplit.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        leftSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuPane, horizontalSplit);
        leftSplit.setDividerLocation((int) (WIDTH * .20));
        leftSplit.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        frame.getContentPane().add(getLeftmostSplitPane());
        frame.revalidate();
        frame.repaint();

        frame.pack();
        frame.setVisible(true);

    }

    //REQUIRES: sound to be a .wav
    //EFFECTS: plays a specific soundfile in the audio data/audio folder
    public void playSound(String name) {
        try {
            File f = new File("./data/audio/" + name);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
