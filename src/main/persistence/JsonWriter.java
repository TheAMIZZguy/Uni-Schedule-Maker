package persistence;

import model.ScheduleList;
import model.CourseList;
import org.json.JSONObject;


import java.io.*;

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
    public void open() throws FileNotFoundException {
        writerScheduleList = new PrintWriter(new File(destinationScheduleList));
        writerCourseList = new PrintWriter(new File(destinationCourseList));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of ScheduleList to file
    public void writeScheduleList(ScheduleList scheduleList) {
        JSONObject json = scheduleList.toJson();
        saveToFile(json.toString(TAB), writerScheduleList);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of CourseList to file
    public void writeCourse(CourseList courseList) {
        JSONObject json = courseList.toJson();
        saveToFile(json.toString(TAB), writerCourseList);
    }


    // MODIFIES: this
    // EFFECTS: closes writer
    public void close(PrintWriter writer) {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json, PrintWriter writer) {
        writer.print(json);
    }
}
