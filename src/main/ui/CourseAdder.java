package ui;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CourseAdder extends JPanel implements ActionListener, FocusListener {

    JTextField nameField;

    JFormattedTextField numSubCoursesField = new JFormattedTextField();
    JTextField[] subCourseNameFields;
    JFormattedTextField[] subCourseStartTimeFields;
    JFormattedTextField[] subCourseEndTimeFields;
    JFormattedTextField[] subCourseDayFields; //todo

    JFormattedTextField numLabsField = new JFormattedTextField();
    JTextField[] labNameFields;
    JFormattedTextField[] labStartTimeFields;
    JFormattedTextField[] labEndTimeFields;
    JFormattedTextField[] labDayFields; //todo

    JFormattedTextField numTutorialsField = new JFormattedTextField();
    JTextField[] tutorialNameFields;
    JFormattedTextField[] tutorialStartTimeFields;
    JFormattedTextField[] tutorialEndTimeFields;
    JFormattedTextField[] tutorialDayFields; //todo

    boolean addressSet = false;
    Font regularFont;
    Font italicFont;
    JLabel courseDisplay;

    static final int GAP = 10;

    public CourseAdder() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        numSubCoursesField.setValue(2);
        numLabsField.setValue(2); //todo
        numTutorialsField.setValue(2);

        changeSizeFields();

        JPanel leftHalf = new JPanel() {
            //Don't allow us to stretch vertically.
            public Dimension getMaximumSize() {
                Dimension pref = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE,
                        pref.height);
            }
        };
        leftHalf.setLayout(new BoxLayout(leftHalf,
                BoxLayout.PAGE_AXIS));
        leftHalf.add(createEntryFields());
        leftHalf.add(createButtons());

        add(leftHalf);
        add(createAddressDisplay());
    }

    private void changeSizeFields() {
        subCourseNameFields = new JTextField[(int) numSubCoursesField.getValue()];
        subCourseStartTimeFields = new JFormattedTextField[(int) numSubCoursesField.getValue()];
        subCourseEndTimeFields = new JFormattedTextField[(int) numSubCoursesField.getValue()];
        subCourseDayFields = new JFormattedTextField[(int) numSubCoursesField.getValue()]; //todo

        labNameFields = new JTextField[(int) numLabsField.getValue()];
        labStartTimeFields = new JFormattedTextField[(int) numLabsField.getValue()];
        labEndTimeFields = new JFormattedTextField[(int) numLabsField.getValue()];
        labDayFields = new JFormattedTextField[(int) numLabsField.getValue()]; //todo

        tutorialNameFields = new JTextField[(int) numTutorialsField.getValue()];
        tutorialStartTimeFields = new JFormattedTextField[(int) numTutorialsField.getValue()];
        tutorialEndTimeFields = new JFormattedTextField[(int) numTutorialsField.getValue()];
        tutorialDayFields = new JFormattedTextField[(int) numTutorialsField.getValue()]; //todo

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

    /**
     * Called when the user clicks the button or presses
     * Enter in a text field.
     */
    public void actionPerformed(ActionEvent e) {
        if ("clear".equals(e.getActionCommand())) {
            addressSet = false;
            nameField.setText("");
            for (int i = 0; i < (int) numSubCoursesField.getValue(); i++) {
                subCourseNameFields[i].setText("");
                subCourseStartTimeFields[i].setValue(null);
                subCourseEndTimeFields[i].setValue(null);
                subCourseDayFields[i].setValue(null);
            }
            for (int i = 0; i < (int) numLabsField.getValue(); i++) {
                labNameFields[i].setText("");
                labStartTimeFields[i].setValue(null);
                labEndTimeFields[i].setValue(null);
                labDayFields[i].setValue(null);
            }
            for (int i = 0; i < (int) numTutorialsField.getValue(); i++) {
                tutorialNameFields[i].setText("");
                tutorialStartTimeFields[i].setValue(null);
                tutorialEndTimeFields[i].setValue(null);
                tutorialDayFields[i].setValue(null);
            }
        } else {
            addressSet = true;
        }
        updateDisplays();
    }

    protected void updateDisplays() {
        courseDisplay.setText(formatAddress());
        if (addressSet) {
            courseDisplay.setFont(regularFont);
        } else {
            courseDisplay.setFont(italicFont);
        }
    }

    protected JComponent createAddressDisplay() {
        JPanel panel = new JPanel(new BorderLayout());
        courseDisplay = new JLabel();
        courseDisplay.setHorizontalAlignment(JLabel.CENTER);
        regularFont = courseDisplay.getFont().deriveFont(Font.PLAIN,
                16.0f);
        italicFont = regularFont.deriveFont(Font.ITALIC);
        updateDisplays();

        //Lay out the panel.
        panel.setBorder(BorderFactory.createEmptyBorder(
                GAP / 2, //top
                0,     //left
                GAP / 2, //bottom
                0));   //right
        panel.add(new JSeparator(JSeparator.VERTICAL),
                BorderLayout.LINE_START);
        panel.add(courseDisplay,
                BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(200, 150));

        return panel;
    }

    protected String formatAddress() {
        if (!addressSet) {
            return "No address set.";
        }

        //todo oh lawd it's course info input
        String name = nameField.getText();
        String subCourseName = subCourseNameFields[0].getText();
        String subCourseStartTime = subCourseStartTimeFields[0].getText();
        String empty = "";

        if ((name == null) || empty.equals(name)) {
            name = "<em>(no name specified)</em>";
        }
        if ((subCourseName == null) || empty.equals(subCourseName)) {
            subCourseName = "<em>(no subCourseName specified)</em>";
        }
        if ((subCourseStartTime == null) || empty.equals(subCourseStartTime)) {
            subCourseStartTime = "";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("<html><p align=center>");
        sb.append(name);
        sb.append("<br>");
        sb.append(subCourseName);
        sb.append(" ");
        sb.append(subCourseStartTime);
        sb.append("</p></html>");

        return sb.toString();
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

    /**
     * Called when one of the fields gets the focus so that
     * we can select the focused field.
     */
    public void focusGained(FocusEvent e) {
        Component c = e.getComponent();
        if (c instanceof JFormattedTextField) {
            selectItLater(c);
        } else if (c instanceof JTextField) {
            ((JTextField)c).selectAll();
        }
    }

    //Workaround for formatted text field focus side effects.
    protected void selectItLater(Component c) {
        if (c instanceof JFormattedTextField) {
            final JFormattedTextField ftf = (JFormattedTextField)c;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    ftf.selectAll();
                }
            });
        }
    }

    //Needed for FocusListener interface.
    public void focusLost(FocusEvent e) { } //ignore

    protected JComponent createEntryFields() {
        JPanel panel = new JPanel(new SpringLayout());

        String[] labelStrings = {
                "Name: ",
                "Sub Course Name: ",
                "Start Time: ",
        };

        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];
        int fieldNum = 0;

        //todo
        //Create the text field and set it up.
        nameField = new JTextField();
        nameField.setColumns(20);
        fields[fieldNum++] = nameField;

        subCourseNameFields[0] = new JTextField();
        subCourseNameFields[0].setColumns(20);
        fields[fieldNum++] = subCourseNameFields[0];

        subCourseStartTimeFields[0] = new JFormattedTextField(
                createFormatter("#####"));
        fields[fieldNum++] = subCourseStartTimeFields[0];

        //Associate label/field pairs, add everything,
        //and lay it out.
        for (int i = 0; i < labelStrings.length; i++) {
            labels[i] = new JLabel(labelStrings[i],
                    JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            panel.add(labels[i]);
            panel.add(fields[i]);

            //Add listeners to each field.
            JTextField tf = null;
            if (fields[i] instanceof JSpinner) {
                tf = getTextField((JSpinner)fields[i]);
            } else {
                tf = (JTextField)fields[i];
            }
            tf.addActionListener(this);
            tf.addFocusListener(this);
        }
        SpringUtilities.makeCompactGrid(panel,
                labelStrings.length, 2,
                GAP, GAP, //init x,y
                GAP, GAP / 2);//xpad, ypad
        return panel;
    }

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
