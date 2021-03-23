package ui;

import model.Course;
import model.Designer;
import model.Scheduler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.sound.midi.MidiChannel;
import javax.sound.sampled.*;
import javax.swing.*;

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


    public MainFrame() {
        boolean isSchedule = true;
        //ActionButtons menuPane = new ActionButtons();
        JPanel menuPane = makeActionButtons();

        savedCourseList.add(cpsc110);
        savedCourseList.add(cpsc121);
        savedCourseList.add(phys118);

        if (isSchedule) {
            upPane = new ScheduleFilter(savedCourseList, activeCourseList);
            downPane = new TableSchedulePanel(this, activeScheduleList);
        } else {
            upPane = new CourseDetailer(this, savedCourseList, activeCourseList);
            downPane = new CourseAdder(this, new int[]{2, 1, 1});
        }


        Dimension minimumSize = new Dimension(100, 50);
        menuPane.setMinimumSize(minimumSize);
        upPane.setMinimumSize(minimumSize);
        downPane.setMinimumSize(new Dimension(500, 500));

        //downScrollPane. ();

        horizontalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upPane, new JScrollPane(downPane));
        horizontalSplit.setDividerLocation((int) (HEIGHT * .40));
        horizontalSplit.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        leftSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuPane, horizontalSplit);
        leftSplit.setDividerLocation((int) (WIDTH * .20));
        leftSplit.setPreferredSize(new Dimension(WIDTH, HEIGHT));

    }

    public void initializeGraphics() {
        //Create and set up the window.
        frame = new JFrame("Graphical UI Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(getLeftmostSplitPane());

        frame.pack();
        frame.setVisible(true);
    }

    private JSplitPane getLeftmostSplitPane() {
        return leftSplit;
    }


    protected JButton addNewCourseButton;
    //protected JButton addFromSavedButton;
    protected JButton viewCoursesButton; //selector of top right
    //4: Same with View Saved Course List
    //5: Same with Remove from AC
    //6: Same with Remove from SC
    //7: Button In Course Detailer
    //protected JButton generateButton;
    //protected JButton saveSchedulesButton;
    protected JButton viewSchedulesButton; //selector of top right
    //protected JButton deleteSchedulesButton;
    protected JButton saveAndExitButton;

    private JPanel makeActionButtons() {
        JPanel actionPanel  = new JPanel(new GridLayout(0,1));

        addNewCourseButton = new JButton("Add New Course");
        addNewCourseButton.setVerticalTextPosition(AbstractButton.CENTER);
        addNewCourseButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        addNewCourseButton.setActionCommand("addNew");

        viewCoursesButton = new JButton("View Courses");
        viewCoursesButton.setActionCommand("viewC");

        viewSchedulesButton = new JButton("View Schedules");
        viewSchedulesButton.setActionCommand("viewS");

        saveAndExitButton = new JButton("Save and Exit");
        saveAndExitButton.setActionCommand("saveExit");


        //Listen for actions on buttons 1 and 3.
        addNewCourseButton.addActionListener(this);
        viewCoursesButton.addActionListener(this);
        viewSchedulesButton.addActionListener(this);
        saveAndExitButton.addActionListener(this);

//        addNewCourseButton.setToolTipText("Click this button to disable the middle button.");
//        addFromSavedButton.setToolTipText("This middle button does nothing when you click it.");
//        generateButton.setToolTipText("Click this button to enable the middle button.");

        //Add Components to this container, using the default FlowLayout.
        actionPanel.add(addNewCourseButton);
        actionPanel.add(viewCoursesButton);
        actionPanel.add(viewSchedulesButton);
        actionPanel.add(deleteSchedulesButton);
        actionPanel.add(saveAndExitButton);

        return actionPanel;
    }

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
            default:
                //save and exit
                break;
        }
    }

    public void generateSchedules() {
        boolean madeSchedule = false;

        int maxClassesAtOnce = getNumberPopup("Up to how many classes would you want to take at once?", 1);
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
        if (!madeSchedule) {
            //todo make error popup
            System.out.println("Impossible to make a schedule");
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

    public void viewCoursePanes(int[] dimensions) {
        //frame.removeAll();
        frame.getContentPane().removeAll();

        JPanel menuPane = makeActionButtons();

        upPane = new CourseDetailer(this, savedCourseList, activeCourseList);

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

    public void viewSchedulePanes() {
        //frame.removeAll();
        frame.getContentPane().removeAll();

        JPanel menuPane = makeActionButtons();

        upPane = new ScheduleFilter(savedCourseList, activeCourseList);
        downPane = new TableSchedulePanel(this, activeScheduleList);

        Dimension minimumSize = new Dimension(100, 50);
        menuPane.setMinimumSize(minimumSize);
        upPane.setMinimumSize(minimumSize);
        downPane.setMinimumSize(new Dimension(500, 500));

        //downScrollPane. ();

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

    public void courseViewerChange(Course course) {

        //frame.removeAll();
        frame.getContentPane().removeAll();

        JPanel menuPane = makeActionButtons();

        upPane = new CourseDetailer(this, savedCourseList, activeCourseList);

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
