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

public class CourseDetailer extends JPanel implements ListSelectionListener, ActionListener {

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

    private MainFrame parent;


    public CourseDetailer(MainFrame parent, ArrayList<Course> savedCoursesList, ArrayList<Course> activeCoursesList) {
        super(new BorderLayout());

        this.parent = parent;

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
        //savedCourseList.setSelectedIndex(1);
        activeCourseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        activeCourseList.addListSelectionListener(this);
        //list.setVisibleRowCount(5);
        JScrollPane listScrollPaneSaved = new JScrollPane(savedCourseList);
        JScrollPane listScrollPaneActive = new JScrollPane(activeCourseList);

//        JButton hireButton = new JButton(hireString);
//        HireListener hireListener = new HireListener(hireButton);
//        hireButton.setActionCommand(hireString);
//        hireButton.addActionListener(hireListener);
//        hireButton.setEnabled(false);

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

        //courseName = new JTextField(10);
        //courseName.addActionListener(hireListener);
        //courseName.getDocument().addDocumentListener(hireListener);
        //String name = listModel.getElementAt(list.getSelectedIndex()).toString();

        //Create a panel that uses BoxLayout.
        JPanel savedButtonPanel = new JPanel();
        savedButtonPanel.setLayout(new BoxLayout(savedButtonPanel, BoxLayout.LINE_AXIS));
        savedButtonPanel.add(deleteSavedCourseButton);
        savedButtonPanel.add(savedToActiveButton);
        savedButtonPanel.add(Box.createHorizontalStrut(5));
        savedButtonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        savedButtonPanel.add(Box.createHorizontalStrut(5));
        //savedButtonPanel.add(courseName);
        //savedButtonPanel.add(hireButton);
        savedButtonPanel.add(deleteActiveCourseButton);
        savedButtonPanel.add(activeToSavedButton);
        savedButtonPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        if (listModelSaved.size() == 0) {
            deleteSavedCourseButton.setEnabled(false);
            savedToActiveButton.setEnabled(false);
        }

        if (listModelActive.size() == 0) {
            deleteActiveCourseButton.setEnabled(false);
            activeToSavedButton.setEnabled(false);
        }


        JSplitPane leftSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPaneSaved,
                listScrollPaneActive);
        leftSplit.setDividerLocation((int) 500);
        //leftSplit.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        add(leftSplit);
        //frame.add(leftSplit, BorderLayout.CENTER);
        //add(listScrollPaneSaved, BorderLayout.CENTER);
        add(savedButtonPanel, BorderLayout.PAGE_END);
        //add(activeButtonPanel, BorderLayout.PAGE_END);
    }


    //Listens to the list
    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList)e.getSource();
        parent.courseViewerChange((Course) list.getSelectedValue());
        if (list == savedCourseList) {
            parent.setSelectedSaveCourse((Course) list.getSelectedValue());
        } else {
            parent.setSelectedActiveCourse((Course) list.getSelectedValue());
        }
        //list.updateUI();
    }


    public void actionPerformed(ActionEvent e) {
        //This method can be called only if
        //there's a valid selection
        //so go ahead and remove whatever's selected.
        if (e.getActionCommand() == REMOVE_COURSE) {
            JList list;
            DefaultListModel<Course> listModel;
            if (e.getSource() == deleteSavedCourseButton) {
                list = savedCourseList;
            } else {
                list = activeCourseList;
            }

            listModel = (DefaultListModel<Course>) list.getModel();

            //int index = list.getSelectedIndex();
            //listModel.remove(index);
            //System.out.println(parent.getSelectedSaveCourse().toString());
            //System.out.println(selectedSaveCourse.toString());
            listModel.removeElement(parent.getSelectedSaveCourse());
            //listModel.removeElement(selectedSaveCourse);
            //System.out.println(listModel.contains(parent.getSelectedSaveCourse()));
            //System.out.println(listModel.contains(selectedSaveCourse));

            int size = listModel.getSize();
            //System.out.println(size);

            if (size == 0) { //Nobody's left, disable firing.
                if (list == savedCourseList) {
                    deleteSavedCourseButton.setEnabled(false);
                    savedToActiveButton.setEnabled(false);
                } else {
                    deleteActiveCourseButton.setEnabled(false);
                    activeToSavedButton.setEnabled(false);
                }

            }
        } else {
            JList fromList;
            DefaultListModel<Course> fromModel;
            JList toList;
            DefaultListModel<Course> toModel;
            if (e.getSource() == savedToActiveButton) {
                fromList = savedCourseList;
                toList = activeCourseList;
                if (parent.getSelectedSaveCourse() == null) {
                    return;
                }
                deleteActiveCourseButton.setEnabled(true);
                activeToSavedButton.setEnabled(true);
            } else {
                fromList = activeCourseList;
                toList = savedCourseList;
                if (parent.getSelectedActiveCourse() == null) {
                    return;
                }
                deleteSavedCourseButton.setEnabled(true);
                savedToActiveButton.setEnabled(true);
            }

            fromModel = (DefaultListModel<Course>) fromList.getModel();
            toModel = (DefaultListModel<Course>) toList.getModel();

            if (e.getSource() == savedToActiveButton) {
                toModel.addElement(parent.getSelectedSaveCourse());
                fromModel.removeElement(parent.getSelectedSaveCourse());
            } else {
                toModel.addElement(parent.getSelectedActiveCourse());
                fromModel.removeElement(parent.getSelectedActiveCourse());
            }

            int size = fromModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                if (fromList == savedCourseList) {
                    deleteSavedCourseButton.setEnabled(false);
                    savedToActiveButton.setEnabled(false);
                } else {
                    deleteActiveCourseButton.setEnabled(false);
                    activeToSavedButton.setEnabled(false);
                }
            }
        }
        refreshParentLists();
    }

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



    /*
    class HireListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public HireListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = courseName.getText();

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                courseName.requestFocusInWindow();
                courseName.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.insertElementAt(courseName.getText(), index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.
            courseName.requestFocusInWindow();
            courseName.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

     */

}
