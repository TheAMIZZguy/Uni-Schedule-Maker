package ui;

import model.Course;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

//Panel with saved course list and active course list, to be swapped between eachother and to generate schedules
public class CourseListDetailer extends JPanel implements ListSelectionListener, ActionListener {

    private JList savedCourseList;
    private JList activeCourseList;
    private DefaultListModel<Course> listModelSaved;
    private DefaultListModel<Course> listModelActive;

    private static final String SWAP_COURSE = "Swap Course";
    private static final String REMOVE_COURSE = "Remove Course";
    private JButton deleteSavedCourseButton;
    private JButton deleteActiveCourseButton;

    private JButton savedToActiveButton;
    private JButton activeToSavedButton;

    private JButton generateButton;

    private MainFrame parent;

    //MODIFIES: this
    //EFFECTS: sets up the panel detains and lists
    public CourseListDetailer(MainFrame parent, ArrayList<Course> savedCoursesList,
                              ArrayList<Course> activeCoursesList) {
        super(new BorderLayout());

        this.parent = parent;

        listSetup(savedCoursesList, activeCoursesList);

        JScrollPane listScrollPaneSaved = new JScrollPane(savedCourseList);
        JScrollPane listScrollPaneActive = new JScrollPane(activeCourseList);

        JPanel savedButtonPanel = setupButtons();

        if (listModelSaved.size() == 0) {
            deleteSavedCourseButton.setEnabled(false);
            savedToActiveButton.setEnabled(false);
        }

        if (listModelActive.size() == 0) {
            deleteActiveCourseButton.setEnabled(false);
            activeToSavedButton.setEnabled(false);
            generateButton.setEnabled(false);
        }


        JSplitPane leftSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPaneSaved, listScrollPaneActive);
        leftSplit.setDividerLocation((int) 500);

        add(leftSplit);
        add(savedButtonPanel, BorderLayout.PAGE_END);
    }

    //MODIFIES: this
    //EFFECTS: adds the appropriate values to the lists
    private void listSetup(ArrayList<Course> savedCoursesList, ArrayList<Course> activeCoursesList) {
        listModelSaved = new DefaultListModel();
        listModelActive = new DefaultListModel();

        for (Course course : savedCoursesList) {
            listModelSaved.addElement(course);
        }

        for (Course course : activeCoursesList) {
            listModelActive.addElement(course);
        }

        //Create the list and put it in a scroll pane.
        savedCourseList = new JList(listModelSaved);
        savedCourseList.setModel(listModelSaved);

        activeCourseList = new JList(listModelActive);
        activeCourseList.setModel(listModelActive);

        savedCourseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        savedCourseList.addListSelectionListener(this);

        activeCourseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        activeCourseList.addListSelectionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: creates and then adds the buttons to the panel it makes
    private JPanel setupButtons() {
        buttonCreation();

        JPanel savedButtonPanel = new JPanel();
        savedButtonPanel.setLayout(new BoxLayout(savedButtonPanel, BoxLayout.LINE_AXIS));
        savedButtonPanel.add(deleteSavedCourseButton);
        savedButtonPanel.add(savedToActiveButton);
        savedButtonPanel.add(Box.createHorizontalStrut(5));
        savedButtonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        savedButtonPanel.add(Box.createHorizontalStrut(5));
        savedButtonPanel.add(generateButton);
        savedButtonPanel.add(deleteActiveCourseButton);
        savedButtonPanel.add(activeToSavedButton);
        savedButtonPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        return savedButtonPanel;
    }

    //MODIFIES: this
    //EFFECTS: creates the buttons
    private void buttonCreation() {
        savedToActiveButton = new JButton("Move From Saved to Active");
        savedToActiveButton.setActionCommand(SWAP_COURSE);
        savedToActiveButton.addActionListener(this);

        activeToSavedButton = new JButton("Move From Active to Saved");
        activeToSavedButton.setActionCommand(SWAP_COURSE);
        activeToSavedButton.addActionListener(this);

        deleteSavedCourseButton = new JButton("Remove Saved Course");
        deleteSavedCourseButton.setActionCommand(REMOVE_COURSE);
        deleteSavedCourseButton.addActionListener(this);

        deleteActiveCourseButton = new JButton("Remove Active Course");
        deleteActiveCourseButton.setActionCommand(REMOVE_COURSE);
        deleteActiveCourseButton.addActionListener(this);

        generateButton = new JButton("Generate Schedules From Active");
        generateButton.setActionCommand("Generate");
        generateButton.addActionListener(this);
    }

    //MODIFIES: parent
    //EFFECTS: sets the selected course to the appropriate selected course value in parent
    public void valueChanged(ListSelectionEvent e) {
        parent.playSound("click2.wav");
        JList list = (JList)e.getSource();
        parent.courseViewerChange((Course) list.getSelectedValue());
        if (list == savedCourseList) {
            parent.setSelectedSaveCourse((Course) list.getSelectedValue());
        } else {
            parent.setSelectedActiveCourse((Course) list.getSelectedValue());
        }
        //list.updateUI();
    }

    //MODIFIES: this, parent
    //EFFECTS: does the action described by the clicked button
    public void actionPerformed(ActionEvent e) {
        parent.playSound("click1.wav");

        if (e.getActionCommand() == REMOVE_COURSE) {
            removeCourseAction(e);
        } else if (e.getActionCommand() == SWAP_COURSE) {
            swapCourseAction(e);
        } else {
            generateScheduleAction();
        }
        refreshParentLists();
    }

    //MODIFIES: this, parent
    //EFFECTS: uses the current lists to generate schedules and add them in parent,
    //    then moves everything from the active course list to the saved course list
    private void generateScheduleAction() {
        parent.generateSchedules();
        for (int i = 0; i < listModelActive.size(); i++) {
            listModelSaved.addElement(listModelActive.get(i));
        }
        listModelActive.removeAllElements();

        deleteActiveCourseButton.setEnabled(false);
        activeToSavedButton.setEnabled(false);
        generateButton.setEnabled(false);
    }

    //MODIFIES: this, parent
    //EFFECTS: swaps the selected item from saved to active list
    private void swapFromSaved() {
        JList fromList;
        DefaultListModel<Course> fromModel;
        JList toList;
        DefaultListModel<Course> toModel;

        fromList = savedCourseList;
        toList = activeCourseList;
        if (parent.getSelectedSaveCourse() == null) {
            return;
        }
        deleteActiveCourseButton.setEnabled(true);
        activeToSavedButton.setEnabled(true);
        generateButton.setEnabled(true);

        fromModel = (DefaultListModel<Course>) fromList.getModel();
        toModel = (DefaultListModel<Course>) toList.getModel();

        toModel.addElement(parent.getSelectedSaveCourse());
        fromModel.removeElement(parent.getSelectedSaveCourse());

        int size = fromModel.getSize();

        if (size == 0) { //Nobody's left, disable firing.
            deleteSavedCourseButton.setEnabled(false);
            savedToActiveButton.setEnabled(false);
        }
    }

    //MODIFIES: this, parent
    //EFFECTS: swaps the selected item from active to saved list
    private void swapFromActive() {
        JList fromList;
        DefaultListModel<Course> fromModel;
        JList toList;
        DefaultListModel<Course> toModel;

        fromList = activeCourseList;
        toList = savedCourseList;
        if (parent.getSelectedActiveCourse() == null) {
            return;
        }
        deleteSavedCourseButton.setEnabled(true);
        savedToActiveButton.setEnabled(true);

        fromModel = (DefaultListModel<Course>) fromList.getModel();
        toModel = (DefaultListModel<Course>) toList.getModel();

        toModel.addElement(parent.getSelectedActiveCourse());
        fromModel.removeElement(parent.getSelectedActiveCourse());

        int size = fromModel.getSize();

        if (size == 0) { //Nobody's left, disable firing.
            deleteActiveCourseButton.setEnabled(false);
            activeToSavedButton.setEnabled(false);
            generateButton.setEnabled(false);
        }
    }

    //MODIFIES: this, parent
    //EFFECTS: swaps the current selected item into the other list
    private void swapCourseAction(ActionEvent e) {
        if (e.getSource() == savedToActiveButton) {
            swapFromSaved();
        } else {
            swapFromActive();
        }
    }

    //MODIFIES: this, parent
    //EFFECTS: removes a course from either the selected or saved course list
    private void removeCourseAction(ActionEvent e) {
        //could technically be abstracted into two identical methods depending on which button was pressed
        JList list;
        DefaultListModel<Course> listModel;
        if (e.getSource() == deleteSavedCourseButton) {
            list = savedCourseList;
            listModel = (DefaultListModel<Course>) list.getModel();
            listModel.removeElement(parent.getSelectedSaveCourse());
        } else {
            list = activeCourseList;
            listModel = (DefaultListModel<Course>) list.getModel();
            listModel.removeElement(parent.getSelectedActiveCourse());
        }

        int size = listModel.getSize();

        if (size == 0) { //disable buttons if nothing there
            if (list == savedCourseList) {
                deleteSavedCourseButton.setEnabled(false);
                savedToActiveButton.setEnabled(false);
            } else {
                deleteActiveCourseButton.setEnabled(false);
                activeToSavedButton.setEnabled(false);
                generateButton.setEnabled(false);
            }

        }
    }

    //MODIFIES: parent
    //EFFECTS: ensures parent has the same values in the list
    public void refreshParentLists() {
        ArrayList<Course> arrSaved = new ArrayList<>();
        ArrayList<Course> arrActive = new ArrayList<>();

        for (int i = 0; i < listModelSaved.size(); i++) {
            arrSaved.add(listModelSaved.get(i));
        }

        for (int i = 0; i < listModelActive.size(); i++) {
            arrActive.add(listModelActive.get(i));
        }

        parent.setSavedCourseList(arrSaved);
        parent.setActiveCourseList(arrActive);
    }

}
