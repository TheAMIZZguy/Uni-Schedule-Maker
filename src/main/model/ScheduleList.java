package model;

import model.Scheduler;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//Provides a simple ArrayList of Courses to Save and Load easily
public class ScheduleList implements Writable {

    private ArrayList<Scheduler> scheduleList;

    public ScheduleList(ArrayList<Scheduler> scheduleList) {
        this.scheduleList = scheduleList;
    }

    //EFFECTS: getter of the arraylist
    public ArrayList<Scheduler> getScheduleList() {
        return this.scheduleList;
    }

    //MODIFIES: this
    //EFFECTS: Adds schedules to the arraylist
    public void addSchedulesToList(ArrayList<Scheduler> differentScheduleList) {
        for (Scheduler schedule : differentScheduleList) {
            this.scheduleList.add(schedule);
        }
    }

    //MODIFIES: this
    //EFFECTS: Adds a scheduler to the arraylist
    public void addScheduleToList(Scheduler differentSchedule) {
        this.scheduleList.add(differentSchedule);
    }

    //MODIFIES: this
    //EFFECTS: Removes a scheduler from the arraylist
    public boolean removeScheduleFromList(int index) {
        if (0 > index || index >= scheduleList.size()) {
            return false;
        }
        this.scheduleList.remove(index);
        return true;
    }

    //EFFECTS: Turns the class arraylist into a JsonObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("scheduleList", scheduleList);
        return json;
    }
}
