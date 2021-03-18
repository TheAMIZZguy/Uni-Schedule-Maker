package ui;

import model.Course;

import javax.swing.*;

public class CourseViewer extends JPanel {
    Course course;
    JLabel display;

    public CourseViewer() {
        display = new JLabel("No Selected Course");
        //display.setText("No Selected Course");
        add(display);
    }

    public CourseViewer(Course course) {
        this.course = course;
        setDisplay();
        add(display);
    }

    public void setDisplay() {
        String labelString =  "<html><p align=center>" + detailedCoursePrint(course) + "</p></html>";
        display = new JLabel(labelString);
    }

///////

    private String detailedCoursePrint(Course course) {
        String message = "Name: " + course.getName() + "<br>";
        for (String subCourse : course.getSubClassNames()) {
            message += printNameWithTimes(course, subCourse, "Section") + "<br>";
        }
        for (String lab : course.getLabNames()) {
            message += printNameWithTimes(course, lab, "Lab") + "<br>";
        }
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
