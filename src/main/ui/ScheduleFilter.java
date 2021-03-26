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
    ArrayList<String> filters;

    MainFrame parent;

    public ScheduleFilter(MainFrame parent, ArrayList<Course> courseList1, ArrayList<Course> courseList2,
                          ArrayList<String> filters) {
        super(new BorderLayout());

        this.parent = parent;
        this.filters = filters;

        ArrayList<Course> courseList = new ArrayList<>(courseList1);
        courseList.addAll(courseList2);

        int size = courseList.size();

        JPanel checkPanel = new JPanel(new GridLayout((int) Math.sqrt(size), (int) Math.sqrt(size)));

        courses = new JCheckBox[size]; //TODO

        for (int i = 0; i < size; i++) {
            courses[i] = new JCheckBox(courseList.get(i).getName());
            if (filters.contains(courseList.get(i).getName())) {
                courses[i].setSelected(true);
            } else {
                courses[i].setSelected(false);
            }
            courses[i].addItemListener(this);
            checkPanel.add(courses[i]);
        }

        parent.setFilters(this.filters);
        add(checkPanel, BorderLayout.LINE_START);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        parent.playSound("click2.wav");
        Object source = e.getItemSelectable();

        for (JCheckBox courseBox : courses) {
            if (source == courseBox) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    filters.remove(courseBox.getText());
                } else {
                    filters.add(courseBox.getText());
                }
                break;
            }
        }

//        System.out.println("-----");
//        for (String filter : filters) {
//            System.out.print(" - ");
//            System.out.print(filter);
//        }

        parent.setFilters(this.filters);
        parent.viewSchedulePanes();
    }
}
