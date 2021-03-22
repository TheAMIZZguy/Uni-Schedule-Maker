package ui;

import model.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ScheduleFilter extends JPanel implements ItemListener {

    JCheckBox[] courses;

    public ScheduleFilter(ArrayList<Course> courseList1, ArrayList<Course> courseList2) {
        super(new BorderLayout());

        ArrayList<Course> courseList = new ArrayList<>(courseList1);
        courseList.addAll(courseList2);

        int size = courseList.size();

        JPanel checkPanel = new JPanel(new GridLayout((int) Math.sqrt(size), (int) Math.sqrt(size)));

        courses = new JCheckBox[size]; //TODO

        for (int i = 0; i < size; i++) {
            courses[i] = new JCheckBox(courseList.get(i).getName());
            courses[i].setSelected(true);
            courses[i].addItemListener(this);
            checkPanel.add(courses[i]);
        }


        add(checkPanel, BorderLayout.LINE_START);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        int index = 0;
        char c = '-';
        Object source = e.getItemSelectable();

        if (source == courses[0]) {
            index = 0;
            c = 'c';
        } else if (source == courses[1]) {
            index = 1;
            c = 'g';
        } else if (source == courses[2]) {
            index = 2;
            c = 'h';
        } else if (source == courses[3]) {
            index = 3;
            c = 't';
        }

        //Now that we know which button was pushed, find out
        //whether it was selected or deselected.
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            c = '-';
        }
    }
}
