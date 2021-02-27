package persistence;


import model.Scheduler;
import model.Course;
import model.ScheduleList;
import model.CourseList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

public class JsonReader {

    private String sourceSchedules;
    private String sourceCourses;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String sourceS, String sourceC) {
        this.sourceSchedules = sourceS;
        this.sourceCourses = sourceC;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ScheduleList readSchedules() throws IOException {
        String jsonData = readFile(sourceSchedules);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseScheduleList(jsonObject);
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CourseList readCourseList() throws IOException {
        String jsonData = readFile(sourceCourses);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCourseList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ScheduleList from JSON object and returns it
    private ScheduleList parseScheduleList(JSONObject jsonObject) {
        //ArrayList<Scheduler> scheduleList = jsonObject.getJSONArray("scheduleList");
        //jsonObject.getString("scheduleList");
        JSONArray jsonArray = jsonObject.getJSONArray("scheduleList");
        ArrayList<Scheduler> schedules = extractArrayListScheduleList(jsonArray);
        ScheduleList scheduleList = new ScheduleList(schedules);
        return scheduleList;
    }

    // EFFECTS: parses a ArrayList<Scheduler> from JSON Array and returns it
    private ArrayList<Scheduler> extractArrayListScheduleList(JSONArray jsonArray) {
        ArrayList<Scheduler> schedules = new ArrayList<>();
        for (Object json : jsonArray) {
            schedules.add((Scheduler) json);
        }
        return schedules;
    }

    // EFFECTS: parses CourseList from JSON object and returns it
    private CourseList parseCourseList(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("scheduleList");
        ArrayList<Course> courses = extractArrayListCourseList(jsonArray);
        CourseList courseList = new CourseList(courses);
        return courseList;
    }

    // EFFECTS: parses a ArrayList<Course> from JSON Array and returns it
    private ArrayList<Course> extractArrayListCourseList(JSONArray jsonArray) {
        ArrayList<Course> schedules = new ArrayList<>();
        for (Object json : jsonArray) {
            schedules.add((Course) json);
        }
        return schedules;
    }

}
