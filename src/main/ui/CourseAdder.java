package ui;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static java.lang.Integer.max;

public class CourseAdder extends JPanel implements ActionListener {

    JTextField nameField;

    int numSub;
    JTextField[] subCourseNameFields;
    JFormattedTextField[] subCourseStartTimeFields;
    JFormattedTextField[] subCourseEndTimeFields;
    JFormattedTextField[] subCourseDayFields; //todo

    int numLab;
    JTextField[] labNameFields;
    JFormattedTextField[] labStartTimeFields;
    JFormattedTextField[] labEndTimeFields;
    JFormattedTextField[] labDayFields; //todo

    int numTut;
    JTextField[] tutorialNameFields;
    JFormattedTextField[] tutorialStartTimeFields;
    JFormattedTextField[] tutorialEndTimeFields;
    JFormattedTextField[] tutorialDayFields; //todo

    static final int GAP = 10;

    public CourseAdder(int[] sizes) {
        setLayout(new SpringLayout());

        numSub = sizes[0];
        numLab = sizes[1];
        numTut = sizes[2];

        setup();
    }

    private void setup() {
        changeSizeFields();

        add(createEntryFields());
        add(createButtons());

    }


    private void changeSizeFields() {
        subCourseNameFields = new JTextField[numSub];
        subCourseStartTimeFields = new JFormattedTextField[numSub];
        subCourseEndTimeFields = new JFormattedTextField[numSub];
        subCourseDayFields = new JFormattedTextField[numSub]; //todo abstract
        for (int i = 0; i < numSub; i++) {
            subCourseNameFields[i] = new JFormattedTextField();
            subCourseStartTimeFields[i] = new JFormattedTextField();
            subCourseEndTimeFields[i] = new JFormattedTextField();
            subCourseDayFields[i] = new JFormattedTextField();
        }

        labNameFields = new JTextField[numLab];
        labStartTimeFields = new JFormattedTextField[numLab];
        labEndTimeFields = new JFormattedTextField[numLab];
        labDayFields = new JFormattedTextField[numLab]; //todo
        for (int i = 0; i < numLab; i++) {
            labNameFields[i] = new JFormattedTextField();
            labStartTimeFields[i] = new JFormattedTextField();
            labEndTimeFields[i] = new JFormattedTextField();
            labDayFields[i] = new JFormattedTextField();
        }

        tutorialNameFields = new JTextField[numTut];
        tutorialStartTimeFields = new JFormattedTextField[numTut];
        tutorialEndTimeFields = new JFormattedTextField[numTut];
        tutorialDayFields = new JFormattedTextField[numTut]; //todo
        for (int i = 0; i < numTut; i++) {
            tutorialNameFields[i] = new JFormattedTextField();
            tutorialStartTimeFields[i] = new JFormattedTextField();
            tutorialEndTimeFields[i] = new JFormattedTextField();
            tutorialDayFields[i] = new JFormattedTextField();
        }
    }

    protected JComponent createButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JButton button = new JButton("Add Course");
        button.addActionListener(this);
        panel.add(button);

        button = new JButton("Clear Information");
        button.addActionListener(this);
        button.setActionCommand("clear");
        panel.add(button);

        //Match the SpringLayout's gap, subtracting 5 to make
        //up for the default gap FlowLayout provides.
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, GAP - 5, GAP - 5));
        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        if ("clear".equals(e.getActionCommand())) {
            nameField.setText("");
            for (int i = 0; i < numSub; i++) {
                subCourseNameFields[i].setText("");
                subCourseStartTimeFields[i].setText(null);
                subCourseEndTimeFields[i].setText(null);
                subCourseDayFields[i].setText(null);
            }
            for (int i = 0; i < numLab; i++) {
                labNameFields[i].setText("");
                labStartTimeFields[i].setText(null);
                labEndTimeFields[i].setText(null);
                labDayFields[i].setText(null);
            }
            for (int i = 0; i < numTut; i++) {
                tutorialNameFields[i].setText("");
                tutorialStartTimeFields[i].setText(null);
                tutorialEndTimeFields[i].setText(null);
                tutorialDayFields[i].setText(null);
            }
        } else {
            //todo
            //add Course
        }
    }

    //A convenience method for creating a MaskFormatter.
    protected MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    protected JComponent createEntryFields() {
        JPanel panel = new JPanel(new SpringLayout());

        String[] labelStrings = new String[4 + numSub * 4 + numLab * 4 + numTut * 4];

        labelStrings[0] = "Name: ";
        labelStrings[1] = "Number of Sub Courses: ";
        addVariableFields(numSub * 4, labelStrings, 2, "Sub Course");
        labelStrings[2 + numSub * 4] = "Number of Labs: ";
        addVariableFields(numLab * 4, labelStrings, 3 + numSub * 4, "Lab");
        labelStrings[3 + ((numLab > 0) ? 1 : 0) + numSub * 4 + numLab * 4] = "Number of Tutorials: ";
        addVariableFields(numTut * 4, labelStrings, 4 + ((numLab > 0) ? 1 : 0)
                + numSub * 4 + numLab * 4, "Tutorial");

        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];

        changeSizeFields();
        //todo
        //Create the text field and set it up.
        //setTextFields(fields, 0);

        //Associate label/field pairs, add everything,
        //and lay it out.
        associateLabels(panel, labelStrings, labels, fields);

        SpringUtilities.makeCompactGrid(panel,
                labelStrings.length, 2,
                GAP, GAP, //init x,y
                GAP, GAP / 2);//xpad, ypad

        //revalidate();
        return panel;
    }



    private int cleanTxt(String text) {
        if (text.equals("  ") || text.equals("") || text.equals(" ")) {
            return 0;
        } else if (text.charAt(0) == ' ') {
            return (int) text.charAt(1);
        } else if (text.charAt(1) == ' ') {
            return (int) text.charAt(0);
        } else {
            return Integer.parseInt(text);
        }
    }

    private void addVariableFields(int num, String[] labelStrings, int startIndex, String type) {
        for (int i = startIndex; i < num + startIndex; i++) {
            if ((i - startIndex) % 4 == 0) {
                labelStrings[i] = type + " Name: ";
            } else if ((i - startIndex) % 4 == 1) {
                labelStrings[i] = "Start Time: ";
            } else if ((i - startIndex) % 4 == 2) {
                labelStrings[i] = "End Time: ";
            } else {
                labelStrings[i] = "Days: ";
            }
        }
    }

    private void associateLabels(JPanel panel, String[] labelStrings, JLabel[] labels, JComponent[] fields) {
        for (int i = 0; i < labelStrings.length; i++) {
            labels[i] = new JLabel(labelStrings[i],
                    JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            panel.add(labels[i]);
            panel.add(fields[i]);

            //Add listeners to each field.
            JTextField tf = null;
            if (fields[i] instanceof JSpinner) {
                tf = getTextField((JSpinner) fields[i]);
            } else {
                tf = (JTextField) fields[i];
            }
            tf.addActionListener(this);
        }
    }

    /*
    private void setTextFields(JComponent[] fields, int fieldNum) {
        nameField = new JTextField();
        nameField.setColumns(20);
        fields[fieldNum++] = nameField;

        //This can be easily extracted
        numSubCoursesField = new JFormattedTextField(createFormatter("##"));
        numSubCoursesField.setColumns(20);
        numSubCoursesField.setName("subCourses");
        fields[fieldNum++] = numSubCoursesField;
        for (int i = 0; i < cleanTxt(numSubCoursesField.getText()); i++) {
            fields[fieldNum++] = subCourseNameFields[i];
            fields[fieldNum++] = subCourseStartTimeFields[i];
            fields[fieldNum++] = subCourseEndTimeFields[i];
            fields[fieldNum++] = subCourseDayFields[i];
        }

        numLabsField = new JFormattedTextField(createFormatter("##"));
        numLabsField.setName("labs");
        fields[fieldNum++] = numLabsField;
        for (int i = 0; i < cleanTxt(numLabsField.getText()); i++) {
            fields[fieldNum++] = labNameFields[i];
            fields[fieldNum++] = labStartTimeFields[i];
            fields[fieldNum++] = labEndTimeFields[i];
            fields[fieldNum++] = labDayFields[i];
        }

        numTutorialsField = new JFormattedTextField(createFormatter("##"));
        numTutorialsField.setName("tutorials");
        fields[fieldNum++] = numTutorialsField;
        for (int i = 0; i < cleanTxt(numTutorialsField.getText()); i++) {
            fields[fieldNum++] = tutorialNameFields[i];
            fields[fieldNum++] = tutorialStartTimeFields[i];
            fields[fieldNum++] = tutorialEndTimeFields[i];
            fields[fieldNum++] = tutorialDayFields[i];
        }
    }
     */



    public JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            return ((JSpinner.DefaultEditor)editor).getTextField();
        } else {
            System.err.println("Unexpected editor type: "
                    + spinner.getEditor().getClass()
                    + " isn't a descendant of DefaultEditor");
            return null;
        }
    }

}
