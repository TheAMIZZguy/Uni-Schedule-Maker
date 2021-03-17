package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

public class ActionButtons extends JPanel implements ActionListener {
    /*
        System.out.println("\t 1) Add new course to Active Course List");
        System.out.println("\t 2) Add saved course to Active Course List");
        System.out.println("\t 3) View Active Course List");
        System.out.println("\t 4) View Saved Course List");
        System.out.println("\t 5) Remove from Active Course List");
        System.out.println("\t 6) Remove from Saved Courses");
        System.out.println("\t 7) Save Active Course List to saved courses");

        System.out.println("\t 8) Generate schedules from Active Course List");
        System.out.println("\t 9) Save generated schedules to saved schedules");
        System.out.println("\t 10) View saved schedules");
        System.out.println("\t 11) Delete saved schedule");

        System.out.println("\t 12) Save and Exit (Schedules must be saved separately with 9)");
        System.out.println("\t 13) Exit without Saving");
         */

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

    private Container parent = this.getParent();


    public ActionButtons() {
        super(new GridLayout(0,1));

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
        add(addNewCourseButton);
        add(addFromSavedButton);
        add(viewCoursesButton);
        add(generateButton);
        add(saveSchedulesButton);
        add(viewSchedulesButton);
        add(deleteSchedulesButton);
        add(saveAndExitButton);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "addNew":

                break;
            case "addFrom":

                break;
            case "viewC":

                break;
            case "generate":

                break;
            case "saveGenerated":

                break;
            case "viewS":

                break;
            case "delete":

                break;
            default:
                //save and exit
                break;
        }
    }

}
