package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

public class ScheduleFilter extends JPanel implements ItemListener {

    JCheckBox[] courses;
    StringBuffer choices; //Todo rid
    JLabel testLabel;

    public ScheduleFilter() {
        super(new BorderLayout());

        courses = new JCheckBox[4]; //TODO

        //Create the check boxes.
        courses[0] = new JCheckBox("CPSC 121");
        courses[0].setMnemonic(KeyEvent.VK_C);
        courses[0].setSelected(true);

        courses[1] = new JCheckBox("MATH 121");
        courses[1].setMnemonic(KeyEvent.VK_G);
        courses[1].setSelected(true);

        courses[2] = new JCheckBox("CPSC 210");
        courses[2].setMnemonic(KeyEvent.VK_H);
        courses[2].setSelected(true);

        courses[3] = new JCheckBox("ENGL 100");
        courses[3].setMnemonic(KeyEvent.VK_T);
        courses[3].setSelected(true);

        //Register a listener for the check boxes.
        courses[0].addItemListener(this);
        courses[1].addItemListener(this);
        courses[2].addItemListener(this);
        courses[3].addItemListener(this);

        //Indicates what's on the geek.
        choices = new StringBuffer("cght");

        //Set up the picture label
        testLabel = new JLabel();
        testLabel.setFont(testLabel.getFont().deriveFont(Font.ITALIC));
        doSomething("Start");

        //Put the check boxes in a column in a panel
        JPanel checkPanel = new JPanel(new GridLayout(0, 1));
        checkPanel.add(courses[0]);
        checkPanel.add(courses[1]);
        checkPanel.add(courses[2]);
        checkPanel.add(courses[3]);

        add(checkPanel, BorderLayout.LINE_START);
        add(testLabel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    private void doSomething(String str) {
        testLabel.setText(str);
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

        //Apply the change to the string.
        choices.setCharAt(index, c);

        doSomething("c is: " + c);
    }
}
