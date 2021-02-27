package model;

import model.Course;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class CourseList implements Writable {
    private ArrayList<Course> courseList;

    public CourseList(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    public ArrayList<Course> getCourseList() {
        return this.courseList;
    }

    public void addCoursesToList(ArrayList<Course> differentCourseList) {
        for (Course course : differentCourseList) {
            this.courseList.add(course);
        }
    }

    public void addCourseToList(Course differentCourse) {
        this.courseList.add(differentCourse);
    }

    public void removeCourseFromList(int index) {
        this.courseList.remove(index);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("courseList", courseList);
        return json;
    }
}
