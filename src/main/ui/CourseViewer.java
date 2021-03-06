package ui;

import model.Course;

import javax.swing.*;
import java.awt.*;

//Stores the data of a class into labels for simple visualization
public class CourseViewer extends JPanel {
    Course course;
    JLabel display;

    //MODIFIES: this
    //EFFECTS: Displays a default text
    public CourseViewer() {
        display = new JLabel("No Selected Course");
        add(display);
    }

    //MODIFIES: this
    //EFFECTS: displays course details
    public CourseViewer(Course course) {
        this.course = course;
        setDisplay();
        add(display);
    }

    //MODIFIES: this
    //EFFECTS: adds the course data to a label in a specific format
    public void setDisplay() {
        String labelString =  "<html><p align=left>" + detailedCoursePrint(course) + "</p></html>";

        display = new JLabel(labelString);
        display.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
    }

    //EFFECTS: turns a course into a formatted label
    private String detailedCoursePrint(Course course) {
        String message = "&emsp&emsp&emsp&emsp&emsp Name: " + course.getName() + "<br><br>";
        for (String subCourse : course.getSubClassNames()) {
            message += printNameWithTimes(course, subCourse, "Section") + "<br>";
        }
        message += "<br>";
        for (String lab : course.getLabNames()) {
            message += printNameWithTimes(course, lab, "Lab") + "<br>";
        }
        message += "<br>";
        for (String tutorial : course.getTutorialNames()) {
            message += printNameWithTimes(course, tutorial, "Tutorial") + "<br>";
        }

        return message;
    }

    //EFFECTS: Prints a Sub part of a course in an easy to read fashion, specifically a subpart with name and times
    private String printNameWithTimes(Course course, String name, String type) {
        String startTime = "";
        String endTime = "";
        int[][] selection;

        if (type.equals("Section")) {
            selection = course.getSubClassTimes().get(name);
        } else if (type.equals("Lab")) {
            selection = course.getLabTimes().get(name);
        } else  {
            selection = course.getTutorialTimes().get(name);
        }

        if (selection[0][1] == 0) {
            startTime = String.valueOf(selection[0][0]) + ":00";
        } else {
            startTime = String.valueOf(selection[0][0]) + ":" + String.valueOf(selection[0][1]);
        }

        if (selection[1][1] == 0) {
            endTime = String.valueOf(selection[1][0]) + ":00";
        } else {
            endTime = String.valueOf(selection[1][0]) + ":" + String.valueOf(selection[1][1]);
        }

        String days = intsToDays(selection[2]);

        return ("\t" + type + ": " + name + "\t Start: " + startTime + "\t End: " + endTime + "\t Days: " + days);
    }

    //EFFECTS: Converts an int array containing day information to a String containing Days
    private String intsToDays(int[] intArr) {
        String returnString = "";
        for (int i : intArr) {
            if (i == 1) {
                returnString = returnString + " Mon";
            }
            if (i == 2) {
                returnString = returnString + " Tue";
            }
            if (i == 3) {
                returnString = returnString + " Wed";
            }
            if (i == 4) {
                returnString = returnString + " Thu";
            }
            if (i == 5) {
                returnString = returnString + " Fri";
            }
        }
        return returnString;
    }
}
