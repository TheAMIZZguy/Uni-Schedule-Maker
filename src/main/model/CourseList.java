package model;

import model.Course;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//Provides a simple ArrayList of Courses to Save and Load easily
public class CourseList implements Writable {
    private ArrayList<Course> courseList;

    public CourseList(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    //EFFECTS: getter of the arraylist
    public ArrayList<Course> getCourseList() {
        return this.courseList;
    }

    //MODIFIES: this
    //EFFECTS: Adds courses to the arraylist
    public void addCoursesToList(ArrayList<Course> differentCourseList) {
        for (Course course : differentCourseList) {
            this.courseList.add(course);
        }
    }

    //MODIFIES: this
    //EFFECTS: Adds a course to the arraylist
    public void addCourseToList(Course differentCourse) {
        this.courseList.add(differentCourse);
    }

    //MODIFIES: this
    //EFFECTS: Removes a course from the arraylist
    public boolean removeCourseFromList(int index) {
        if (0 > index || index >= courseList.size()) {
            return false;
        }
        this.courseList.remove(index);
        return true;
    }

    //EFFECTS: Turns the class arraylist into a JsonObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("courseList", courseList);
        return json;
    }
}
