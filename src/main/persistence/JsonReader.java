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

//Reads the JSON files holding ScheduleList and CourseList data
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
        JSONArray jsonArray = jsonObject.getJSONArray("scheduleList");
        ArrayList<Scheduler> schedules = extractArrayListScheduleList(jsonArray);
        ScheduleList scheduleList = new ScheduleList(schedules);
        return scheduleList;
    }

    // EFFECTS: parses a ArrayList<Scheduler> from JSON Array and returns it
    private ArrayList<Scheduler> extractArrayListScheduleList(JSONArray jsonArray) {
        ArrayList<Scheduler> schedules = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject json2 = (JSONObject) json;
            String[][] sched = convertJsonMuliDimToStringMultiDim(json2.getJSONArray("schedule"));
            String[] coursesInSchedule = convertJsonArrayToStringArray(json2.getJSONArray("coursesInSchedule"));
            int currentCourses = json2.getInt("currentCourses");
            Scheduler scheduler = new Scheduler(sched, coursesInSchedule, currentCourses);
            schedules.add(scheduler);
        }
        return schedules;
    }


    //EFFECTS: Takes a JSON Multidimentional Array and turns it into a Multidimentional String Array
    private String[][] convertJsonMuliDimToStringMultiDim(JSONArray schedule) {
        String[][] returnScheduleList = new String[2 * 14][5];
        for (int i = 0; i < returnScheduleList.length; i++) {
            returnScheduleList[i] = convertJsonArrayToStringArray((JSONArray) schedule.get(i));
        }
        return returnScheduleList;
    }

    //EFFECTS: Takes a JSON Array and turns it into a String Array
    private String[] convertJsonArrayToStringArray(JSONArray arr) {
        String[] returnArray = new String[arr.length()];
        for (int i = 0; i < arr.length(); i++) {
            if (!JSONObject.NULL.equals(arr.get(i))) {
                returnArray[i] = (String) arr.get(i);
            }
        }
        return returnArray;
    }

    // EFFECTS: parses CourseList from JSON object and returns it
    private CourseList parseCourseList(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courseList");
        ArrayList<Course> courses = extractArrayListCourseList(jsonArray);
        CourseList courseList = new CourseList(courses);
        return courseList;
    }

    // EFFECTS: parses a ArrayList<Course> from JSON Array and returns it
    private ArrayList<Course> extractArrayListCourseList(JSONArray jsonArray) {
        ArrayList<Course> courses = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject jsonObject = (JSONObject) json;

            String name = jsonObject.getString("name");
            ArrayList<String> subClassNames = getNameArrayFromJson(jsonObject.getJSONArray("subClassNames"));
            ArrayList<int[][]> subClassTimes =  getTimesArrayFromJson(jsonObject.getJSONObject("subClassTimes"),
                    subClassNames);
            boolean hasLab = jsonObject.getBoolean("hasLab");
            ArrayList<String> labNames =  getNameArrayFromJson(jsonObject.getJSONArray("labNames"));
            ArrayList<int[][]> labTimes = getTimesArrayFromJson(jsonObject.getJSONObject("labTimes"),
                    labNames);
            boolean hasTutorial = jsonObject.getBoolean("hasTutorial");
            ArrayList<String> tutorialNames =  getNameArrayFromJson(jsonObject.getJSONArray("tutorialNames"));
            ArrayList<int[][]> tutorialTimes = getTimesArrayFromJson(jsonObject.getJSONObject("tutorialTimes"),
                    tutorialNames);

            Course course = new Course(name, subClassNames, subClassTimes,
                    hasLab, labNames, labTimes, hasTutorial, tutorialNames, tutorialTimes);
            courses.add(course);
        }
        return courses;
    }

    //EFFECTS: Extracts an arraylist of a multidimentional array from a JSONObject
    private ArrayList<int[][]> getTimesArrayFromJson(JSONObject timesArray, ArrayList<String> namesArray) {
        ArrayList<int[][]> returnArray = new ArrayList<>();
        for (int i = 0; i < namesArray.size(); i++) {
            int[][] alpha = getIntArray(timesArray.getJSONArray(namesArray.get(i)));
            returnArray.add(alpha);
        }
        return returnArray;
    }

    //EFFECTS: Extracts a multidimentional int array from a JSONArray
    private int[][] getIntArray(JSONArray timesArray) {
        int[][] returnArray = new int[3][];
        for (int i = 0; i < timesArray.length(); i++) {
            if (!JSONObject.NULL.equals(timesArray.get(i))) {
                returnArray[i] = getInts((JSONArray) timesArray.get(i));
            }
        }
        return returnArray;
    }

    //EFFECTS: Extracts an int array from a JSONArray
    private int[] getInts(JSONArray intArr) {
        int[] returnArray = new int[intArr.length()];
        for (int i = 0; i < intArr.length(); i++) {
            if (!JSONObject.NULL.equals(intArr.get(i))) {
                returnArray[i] = ((int) intArr.get(i));
            }
        }
        return returnArray;
    }

    //EFFECTS: Extracts a string ArrayList from a JSONArray
    private ArrayList<String> getNameArrayFromJson(JSONArray namesArray) {
        ArrayList<String> returnArray = new ArrayList<>();
        for (int i = 0; i < namesArray.length(); i++) {
            if (!JSONObject.NULL.equals(namesArray.get(i))) {
                returnArray.add((String) namesArray.get(i));
            }
        }
        return returnArray;
    }

}
