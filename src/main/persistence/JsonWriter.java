package persistence;

import model.ScheduleList;
import model.CourseList;
import org.json.JSONObject;


import java.io.*;

//VERY largely inspired from the JSONSerializationDemo we were given from UBC
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writerScheduleList;
    private PrintWriter writerCourseList;
    private String destinationScheduleList;
    private String destinationCourseList;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destinationSL, String destinationCL) {
        this.destinationScheduleList = destinationSL;
        this.destinationCourseList = destinationCL;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open(boolean isScheduleList) throws FileNotFoundException {
        if (isScheduleList) {
            writerScheduleList = new PrintWriter(new File(destinationScheduleList));
        } else {
            writerCourseList = new PrintWriter(new File(destinationCourseList));
        }
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of ScheduleList to file
    public void writeScheduleList(ScheduleList scheduleList) {
        JSONObject json = scheduleList.toJson();
        saveToFile(json.toString(TAB), true);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of CourseList to file
    public void writeCourseList(CourseList courseList) {
        JSONObject json = courseList.toJson();
        saveToFile(json.toString(TAB), false);
    }


    // MODIFIES: this
    // EFFECTS: closes writer
    public void close(boolean isScheduleList) {
        if (isScheduleList) {
            writerScheduleList.close();
        } else {
            writerCourseList.close();
        }

    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json, boolean isScheduleList) {
        if (isScheduleList) {
            writerScheduleList.print(json);
        } else {
            writerCourseList.print(json);
        }
    }
}
