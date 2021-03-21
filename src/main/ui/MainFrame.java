package ui;

import model.Course;
import model.Scheduler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
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
            true, new ArrayList<>(Arrays.asList("L2A")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})),
            false, new ArrayList<>(Arrays.asList("T2A", "T2C")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{10,0}, {12,0}, {1,3,5}})));
    //

    ArrayList<Course> savedCourseList = new ArrayList<>();
    ArrayList<Course> activeCourseList = new ArrayList<>();

    private Course selectedSaveCourse;
    private Course selectedActiveCourse;


    public MainFrame() {
        boolean isSchedule = true;
        //ActionButtons menuPane = new ActionButtons();
        JPanel menuPane = makeActionButtons();

        //ScheduleFilter filterPaneUp = new ScheduleFilter();
        //TableSchedulePanel schedulePaneDown = new TableSchedulePanel();
        ArrayList<Scheduler> help = new ArrayList<>();
        help.add(new Scheduler(0));
        help.add(new Scheduler(0));
        help.add(new Scheduler(0));

        savedCourseList.add(cpsc110);
        savedCourseList.add(cpsc121);
        savedCourseList.add(phys118);

        if (isSchedule) {
            upPane = new ScheduleFilter();
            downPane = new TableSchedulePanel(help);
        } else {
            upPane = new CourseDetailer(this, savedCourseList);
            downPane = new CourseAdder(new int[]{2, 1, 1});
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
    protected JButton addFromSavedButton;
    protected JButton viewCoursesButton; //selector of top right
    //4: Same with View Saved Course List
    //5: Same with Remove from AC
    //6: Same with Remove from SC
    //7: Button In Course Detailer
    protected JButton generateButton;
    protected JButton saveSchedulesButton;
    protected JButton viewSchedulesButton; //selector of top right
    protected JButton deleteSchedulesButton;
    protected JButton saveAndExitButton;

    private JPanel makeActionButtons() {
        JPanel actionPanel  = new JPanel(new GridLayout(0,1));

        addNewCourseButton = new JButton("Add New Course");
        addNewCourseButton.setVerticalTextPosition(AbstractButton.CENTER);
        addNewCourseButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        addNewCourseButton.setActionCommand("addNew");

        addFromSavedButton = new JButton("Add Course From Saved List");
        addFromSavedButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        addFromSavedButton.setHorizontalTextPosition(AbstractButton.CENTER);
        addFromSavedButton.setActionCommand("addFrom");

        viewCoursesButton = new JButton("View Courses");
        viewCoursesButton.setActionCommand("viewC");

        generateButton = new JButton("Generate New Schedules From Active");
        generateButton.setActionCommand("generate");

        saveSchedulesButton = new JButton("Save Generated Schedules");
        saveSchedulesButton.setActionCommand("saveGenerated");

        viewSchedulesButton = new JButton("View Schedules");
        viewSchedulesButton.setActionCommand("viewS");

        deleteSchedulesButton = new JButton("Delete Saved schedules");
        deleteSchedulesButton.setActionCommand("delete");

        saveAndExitButton = new JButton("Save and Exit");
        saveAndExitButton.setActionCommand("saveExit");


        //Listen for actions on buttons 1 and 3.
        addNewCourseButton.addActionListener(this);
        addFromSavedButton.addActionListener(this);
        viewCoursesButton.addActionListener(this);
        generateButton.addActionListener(this);
        saveSchedulesButton.addActionListener(this);
        viewSchedulesButton.addActionListener(this);
        deleteSchedulesButton.addActionListener(this);
        saveAndExitButton.addActionListener(this);

//        addNewCourseButton.setToolTipText("Click this button to disable the middle button.");
//        addFromSavedButton.setToolTipText("This middle button does nothing when you click it.");
//        generateButton.setToolTipText("Click this button to enable the middle button.");

        //Add Components to this container, using the default FlowLayout.
        actionPanel.add(addNewCourseButton);
        actionPanel.add(addFromSavedButton);
        actionPanel.add(viewCoursesButton);
        actionPanel.add(generateButton);
        actionPanel.add(saveSchedulesButton);
        actionPanel.add(viewSchedulesButton);
        actionPanel.add(deleteSchedulesButton);
        actionPanel.add(saveAndExitButton);

        return actionPanel;
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "addNew":
                addNewCourse();
                break;
            case "addFrom":

                break;
            case "viewC":
                viewCoursePanes(new int[]{0});
                break;
            case "generate":

                break;
            case "saveGenerated":

                break;
            case "viewS":
                viewSchedulePanes();
                break;
            case "delete":

                break;
            default:
                //save and exit
                break;
        }
    }

    private void addNewCourse() {
        int[] returnInts = new int[3];
        returnInts[0] =  getSubCoursesAmount("Sub Courses", 1);
        returnInts[1] =  getSubCoursesAmount("Labs", 0);
        returnInts[2] =  getSubCoursesAmount("Tutorials", 0);
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

    private void viewCoursePanes(int[] dimensions) {
        //frame.removeAll();
        frame.getContentPane().removeAll();

        JPanel menuPane = makeActionButtons();

        upPane = new CourseDetailer(this, savedCourseList);

        if (dimensions[0] != 0) {
            downPane = new CourseAdder(dimensions);
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

    private void viewSchedulePanes() {
        //frame.removeAll();
        frame.getContentPane().removeAll();

        JPanel menuPane = makeActionButtons();

        ArrayList<Scheduler> help = new ArrayList<>();
        help.add(new Scheduler(0));
        help.add(new Scheduler(0));
        help.add(new Scheduler(0));

        upPane = new ScheduleFilter();
        downPane = new TableSchedulePanel(help);

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

        upPane = new CourseDetailer(this, savedCourseList);

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
}
