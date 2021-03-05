package model;

import model.Scheduler;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class ScheduleList implements Writable {

    private ArrayList<Scheduler> scheduleList;

    public ScheduleList(ArrayList<Scheduler> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public ArrayList<Scheduler> getScheduleList() {
        return this.scheduleList;
    }

    public void addSchedulesToList(ArrayList<Scheduler> differentScheduleList) {
        for (Scheduler schedule : differentScheduleList) {
            this.scheduleList.add(schedule);
        }
    }

    public void addScheduleToList(Scheduler differentSchedule) {
        this.scheduleList.add(differentSchedule);
    }

    public boolean removeScheduleFromList(int index) {
        if (0 > index || index >= scheduleList.size()) {
            return false;
        }
        this.scheduleList.remove(index);
        return true;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("scheduleList", scheduleList);
        return json;
    }
}
