package ui;

import model.Course;
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
        setLayout(new GridBagLayout());

        numSub = sizes[0];
        numLab = sizes[1];
        numTut = sizes[2];

        setup();
    }

    private void setup() {
        changeSizeFields();
        //JScrollPane scrollPane = new JScrollPane();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        add(createEntryFields(), c);
        c.gridy = 1;
        add(createButtons(), c);

        //add(scrollPane);
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
        //JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;

        JButton button = new JButton("How to Format");
        button.addActionListener(this);
        button.setActionCommand("format");
        panel.add(button, c);

        c.gridx = 3;
        button = new JButton("Add Course");
        button.addActionListener(this);
        panel.add(button, c);

        c.gridx = 4;
        button = new JButton("Clear Information");
        button.addActionListener(this);
        button.setActionCommand("clear");
        panel.add(button, c);

        //Match the SpringLayout's gap, subtracting 5 to make
        //up for the default gap FlowLayout provides.
        //panel.setBorder(BorderFactory.createEmptyBorder(0, 0, GAP - 5, GAP - 5));
        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        if ("clear".equals(e.getActionCommand())) {
            clearFields();
        } else if ("format".equals(e.getActionCommand())) {
            JDialog dialog = new JDialog(new JFrame(), "How To Format");

            JLabel label = dialogLabelHelper();

            JPanel contentPane = formatDialogHelper(dialog, label);
            dialog.setContentPane(contentPane);

            dialog.setSize(new Dimension(600, 300));
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } else {
            //todo
            //add Course
        }
    }

    private JLabel dialogLabelHelper() {
        JLabel label = new JLabel("<html><p align=left> "
                + "How to Format: <br>"
                + "Name - (Main Course Name): <br> &emsp Ex.  CPSC 210  <br>"
                + "Sub Course Name/Lab Name/Tutorial Name - (What you add to Main name): <br>"
                + "&emsp Ex: 201 or L2A or T1B <br>"
                + "Start Time - (Military Time From 700 to 2030): <br> &emsp Ex: 1030 <br>"
                + "End Time - (Military Time From 730 to 2100): <br> &emsp Ex: 1200 <br>"
                + "Days - (Calendar Format): <br> &emsp Ex: MTWThF or TTh or MWF or T");
        label.setHorizontalAlignment(JLabel.CENTER);
        Font font = label.getFont();
        label.setFont(label.getFont().deriveFont(font.PLAIN, 14.0f));
        return label;
    }

    private JPanel formatDialogHelper(JDialog dialog, JLabel label) {
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });

        JPanel closePanel = new JPanel();
        closePanel.setLayout(new BoxLayout(closePanel, BoxLayout.LINE_AXIS));
        closePanel.add(Box.createHorizontalGlue());
        closePanel.add(closeButton);
        closePanel.setBorder(BorderFactory.createEmptyBorder(0,0,5,5));

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(label, BorderLayout.CENTER);
        contentPane.add(closePanel, BorderLayout.PAGE_END);
        contentPane.setOpaque(true);
        return contentPane;
    }

    private void clearFields() {
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
    }

    protected JComponent createEntryFields() {
        //JPanel panel = new JPanel(new SpringLayout());
        JPanel panel = new JPanel(new GridBagLayout());

        int effLab = Math.max(numLab, 0);
        int effTut = Math.max(numLab, 0);

        JLabel[] labels = setupLabels(effLab, effTut);
        JComponent[] fields = setTextFields(effLab, effTut, 0);

        int count = 1 + numSub * 4 + effLab * 4 + effTut * 4;

        fieldReSizer(fields);
        setUpLayout(panel, labels, fields);

        return panel;
    }

    private void fieldReSizer(JComponent[] fields) {
        for (JComponent field : fields) {
            field.setPreferredSize(new Dimension(40, 30));
        }
    }

    private void setUpLayout(JPanel panel, JLabel[] labels, JComponent[] fields) {
        //layout.putConstraint(SpringLayout.WEST, labels[0], 5,SpringLayout.WEST, this);
        //layout.putConstraint(SpringLayout.WEST, fields[1], 3, SpringLayout.NORTH, labels[0]);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(labels[0], c);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        panel.add(fields[0], c);
        c.gridx = 4;
        c.gridwidth = 2;
        panel.add(new Label(" - Buttons at the Bottom"), c);
        c.gridwidth = 1;
        int labelInt = 1;
        labelInt = setUpLayout(panel, c, labels, fields, labelInt, numSub, 0);
        labelInt = setUpLayout(panel, c, labels, fields, labelInt, numLab, numSub);
        labelInt = setUpLayout(panel, c, labels, fields, labelInt, numTut, numSub + Math.max(numLab, 0));
    }

    private int setUpLayout(JPanel panel, GridBagConstraints c, JLabel[] labels,
                               JComponent[] fields, int labelInt, int numType, int start) {
        for (int i = start; i < numType + start; i++) {
            c.gridx = 0;
            c.gridy = i * 2 + 1;
            panel.add(labels[labelInt], c); //name
            c.gridx = 1;
            c.gridwidth = 2;
            panel.add(fields[labelInt++], c);

            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = i * 2 + 2;
            panel.add(labels[labelInt], c); //Start Time
            c.gridx = 1;
            panel.add(fields[labelInt++], c);
            c.gridx = 2;
            panel.add(labels[labelInt], c); //End Time
            c.gridx = 3;
            panel.add(fields[labelInt++], c);
            c.gridx = 4;
            panel.add(labels[labelInt], c); //Days
            c.gridx = 5;
            panel.add(fields[labelInt++], c);
        }
        return labelInt;
    }

    private JLabel[] setupLabels(int effLab, int effTut) {

        JLabel[] labels = new JLabel[1 + numSub * 4 + effLab * 4 + effTut * 4];
        int labelNum = 0;

        labels[labelNum++] = new JLabel("Name: ", JLabel.TRAILING);

        for (int i = 0; i < numSub; i++) {
            labels[labelNum++] = new JLabel("Sub Course Name: ", JLabel.TRAILING);
            labels[labelNum++] = new JLabel("Start Time: ", JLabel.TRAILING);
            labels[labelNum++] = new JLabel("End Time: ", JLabel.TRAILING);
            labels[labelNum++] = new JLabel("Days: ", JLabel.TRAILING);
        }

        for (int i = 0; i < effLab; i++) {
            labels[labelNum++] = new JLabel("Lab Name: ", JLabel.TRAILING);
            labels[labelNum++] = new JLabel("Start Time: ", JLabel.TRAILING);
            labels[labelNum++] = new JLabel("End Time: ", JLabel.TRAILING);
            labels[labelNum++] = new JLabel("Days: ", JLabel.TRAILING);
        }

        for (int i = 0; i < effTut; i++) {
            labels[labelNum++] = new JLabel("Tutorial Name: ", JLabel.TRAILING);
            labels[labelNum++] = new JLabel("Start Time: ", JLabel.TRAILING);
            labels[labelNum++] = new JLabel("End Time: ", JLabel.TRAILING);
            labels[labelNum++] = new JLabel("Days: ", JLabel.TRAILING);
        }

        return labels;
    }

    private JComponent[] setTextFields(int effLab, int effTut, int fieldNum) {
        JComponent[] fields = new JComponent[1 + numSub * 4 + effLab * 4 + effTut * 4];

        nameField = new JTextField();
        nameField.setColumns(20);
        fields[fieldNum++] = nameField;

        for (int i = 0; i < numSub; i++) {
            fields[fieldNum++] = subCourseNameFields[i];
            fields[fieldNum++] = subCourseStartTimeFields[i];
            fields[fieldNum++] = subCourseEndTimeFields[i];
            fields[fieldNum++] = subCourseDayFields[i];
        }

        for (int i = 0; i < effLab; i++) {
            fields[fieldNum++] = labNameFields[i];
            fields[fieldNum++] = labStartTimeFields[i];
            fields[fieldNum++] = labEndTimeFields[i];
            fields[fieldNum++] = labDayFields[i];
        }

        for (int i = 0; i < effTut; i++) {
            fields[fieldNum++] = tutorialNameFields[i];
            fields[fieldNum++] = tutorialStartTimeFields[i];
            fields[fieldNum++] = tutorialEndTimeFields[i];
            fields[fieldNum++] = tutorialDayFields[i];
        }

        return fields;
    }


}
