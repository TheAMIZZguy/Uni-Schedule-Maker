package ui;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static java.lang.Integer.max;

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

        numSubCoursesField.setText("01");
        numLabsField.setValue("00"); //todo
        numTutorialsField.setValue("00");

        changeSizeFields();

        JPanel leftHalf = new JPanel(); //{
            //Don't allow us to stretch vertically.
//            public Dimension getMaximumSize() {
//                Dimension pref = getPreferredSize();
//                return new Dimension(Integer.MAX_VALUE,
//                        pref.height);
//            }
//        };
        leftHalf.setLayout(new BoxLayout(leftHalf,
                BoxLayout.PAGE_AXIS));
        leftHalf.add(createEntryFields());
        leftHalf.add(createButtons());

        add(leftHalf);
        add(createAddressDisplay());
    }

    private void refresh() {
        removeAll();
        revalidate();
        repaint();

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        changeSizeFields();

        JPanel leftHalf = new JPanel(); //{
        leftHalf.setLayout(new BoxLayout(leftHalf,
                BoxLayout.PAGE_AXIS));
        leftHalf.add(createRefreshedFields());
        leftHalf.add(createButtons());

        add(leftHalf);
        add(createAddressDisplay());
    }

    private void changeSizeFields() {
        subCourseNameFields = new JTextField[cleanTxt(numSubCoursesField.getText())];
        subCourseStartTimeFields = new JFormattedTextField[cleanTxt(numSubCoursesField.getText())];
        subCourseEndTimeFields = new JFormattedTextField[cleanTxt(numSubCoursesField.getText())];
        subCourseDayFields = new JFormattedTextField[cleanTxt(numSubCoursesField.getText())]; //todo
        for (int i = 0; i < cleanTxt(numSubCoursesField.getText()); i++) {
            subCourseNameFields[i] = new JFormattedTextField();
            subCourseStartTimeFields[i] = new JFormattedTextField();
            subCourseEndTimeFields[i] = new JFormattedTextField();
            subCourseDayFields[i] = new JFormattedTextField();
        }

        labNameFields = new JTextField[cleanTxt(numLabsField.getText())];
        labStartTimeFields = new JFormattedTextField[cleanTxt(numLabsField.getText())];
        labEndTimeFields = new JFormattedTextField[cleanTxt(numLabsField.getText())];
        labDayFields = new JFormattedTextField[cleanTxt(numLabsField.getText())]; //todo
        for (int i = 0; i < cleanTxt(numLabsField.getText()); i++) {
            labNameFields[i] = new JFormattedTextField();
            labStartTimeFields[i] = new JFormattedTextField();
            labEndTimeFields[i] = new JFormattedTextField();
            labDayFields[i] = new JFormattedTextField();
        }

        tutorialNameFields = new JTextField[cleanTxt(numTutorialsField.getText())];
        tutorialStartTimeFields = new JFormattedTextField[cleanTxt(numTutorialsField.getText())];
        tutorialEndTimeFields = new JFormattedTextField[cleanTxt(numTutorialsField.getText())];
        tutorialDayFields = new JFormattedTextField[cleanTxt(numTutorialsField.getText())]; //todo
        for (int i = 0; i < cleanTxt(numTutorialsField.getText()); i++) {
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

    protected JComponent createEntryFields() {
        JPanel panel = new JPanel(new SpringLayout());

        String[] labelStrings = {
                "Name: ",
                "Number of Sub Courses: ",
                "Number of Labs: ",
                "Number of Tutorials: "
        };

        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];

        //todo
        //Create the text field and set it up.
        setTextFields(fields, 0);

        //Associate label/field pairs, add everything,
        //and lay it out.
        associateLabels(panel, labelStrings, labels, fields);

        SpringUtilities.makeCompactGrid(panel,
                labelStrings.length, 2,
                GAP, GAP, //init x,y
                GAP, GAP / 2);//xpad, ypad
        return panel;
    }

    protected JComponent createRefreshedFields() {
        JPanel panel = new JPanel(new SpringLayout());
        int numSub = 1;
        int numLab = 0;
        int numTut = 0;

        if (cleanTxt(numSubCoursesField.getText()) != 0) {
            numSub = max(cleanTxt(numSubCoursesField.getText()), 1);
        }

        if (cleanTxt(numLabsField.getText()) != 0) {
            numLab = cleanTxt(numLabsField.getText());
        }

        if (cleanTxt(numTutorialsField.getText()) != 0) {
            numTut = cleanTxt(numTutorialsField.getText());
        }

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
        setTextFieldsRefresh(fields, 0);

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
            tf.addFocusListener(this);
        }
    }

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

    private void setTextFieldsRefresh(JComponent[] fields, int fieldNum) {
        //nameField = new JTextField();
        //nameField.setColumns(20);
        fields[fieldNum++] = nameField;

        //This can be easily extracted
        //numSubCoursesField = new JFormattedTextField(createFormatter("##"));
        //numSubCoursesField.setColumns(20);
        //numSubCoursesField.setName("subCourses");
        fields[fieldNum++] = numSubCoursesField;
        for (int i = 0; i < cleanTxt(numSubCoursesField.getText()); i++) {
            fields[fieldNum++] = subCourseNameFields[i];
            fields[fieldNum++] = subCourseStartTimeFields[i];
            fields[fieldNum++] = subCourseEndTimeFields[i];
            fields[fieldNum++] = subCourseDayFields[i];
        }

        //numLabsField = new JFormattedTextField(createFormatter("##"));
        //numLabsField.setName("labs");
        fields[fieldNum++] = numLabsField;
        for (int i = 0; i < cleanTxt(numLabsField.getText()); i++) {
            fields[fieldNum++] = labNameFields[i];
            fields[fieldNum++] = labStartTimeFields[i];
            fields[fieldNum++] = labEndTimeFields[i];
            fields[fieldNum++] = labDayFields[i];
        }

        //numTutorialsField = new JFormattedTextField(createFormatter("##"));
        //numTutorialsField.setName("tutorials");
        fields[fieldNum++] = numTutorialsField;
        for (int i = 0; i < cleanTxt(numTutorialsField.getText()); i++) {
            fields[fieldNum++] = tutorialNameFields[i];
            fields[fieldNum++] = tutorialStartTimeFields[i];
            fields[fieldNum++] = tutorialEndTimeFields[i];
            fields[fieldNum++] = tutorialDayFields[i];
        }
    }

    public void focusLost(FocusEvent e) {
        //System.out.println(e.getSource().getClass());
        if (e.getSource().getClass().equals(JFormattedTextField.class)) {
            refreshNumFields((JFormattedTextField) e.getSource());
            JPanel leftHalf = new JPanel();
            leftHalf.setLayout(new BoxLayout(leftHalf,
                    BoxLayout.PAGE_AXIS));
            leftHalf.add(createEntryFields());
            leftHalf.add(createButtons());

            add(leftHalf);
            add(createAddressDisplay());
        }
    }

    void refreshNumFields(JFormattedTextField jte) {
        //System.out.println(jte.getText());
        System.out.println(jte.getName());
        //int fields = Integer.parseInt(jte.getText());
        createRefreshedFields();
        refresh();
//        if (jte.getName().equals("subCourses")) {
//            //reset subcourses fields
//        } else if (jte.getName().equals("labs")) {
//            //reset the lab fields
//        } else {
//            //reset the tutorial fields
//        }
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
