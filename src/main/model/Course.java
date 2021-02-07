package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Course {
    //self-explanatory
    private boolean hasLab;
    private boolean hasTutorial;

    //Name of the course: Example is CPSC 210
    private String name;

    //A specific subclass offered with the days/times offered: Example is 201    (This would be CPSC 210 201)
    private ArrayList<String> subClassNames;
    private HashMap<String, int[][]> subClassTimes = new HashMap<>();
    //will be in format of   subClassName : [[hh,mm],[hh,mm],[d,d,d,d,d]]
    //the first one will be the starting time, the second will be ending time,
    //        and the third one will represent days available like: [1,3,5] for MWF

    //Similar as above, but labs
    private ArrayList<String> labNames = new ArrayList<>();
    private HashMap<String, int[][]> labTimes = new HashMap<>();

    //Similar as above, but tutorials
    private ArrayList<String> tutorialNames = new ArrayList<>();
    private HashMap<String, int[][]> tutorialTimes = new HashMap<>();


    //REQUIRES: subClassNames and subClassTimes to be the same length,
    //                      the int[][] needs to be in the specific format mentioned above
    //EFFECTS:  adds subClassNames and subClassTimes to respective ArrayLists with like indexes
    //          if there is no lab and tutorial it makes it easier to call the class
    public Course(String name, ArrayList<String> subClassNames, ArrayList<int[][]> subClassTimes) {
        this.name = name;
        this.subClassNames = subClassNames;
        for (int i = 0; i < subClassNames.size(); i++) {
            this.subClassTimes.put(subClassNames.get(i),subClassTimes.get(i));
        }
        this.hasLab = false;
        this.hasTutorial = false;
    }

    //REQUIRES: subClassNames and subClassTimes to be the same length
    //          labNames and labTimes to be the same length
    //          tutorialNames and tutorialTimes to be the same length
    //EFFECTS: adds xNames and xTimes to respective ArrayLists with like indexes
    public Course(String name, ArrayList<String> subClassNames, ArrayList<int[][]> subClassTimes,
                  boolean hasLab, ArrayList<String> labNames, ArrayList<int[][]> labTimes,
                  boolean hasTutorial, ArrayList<String> tutorialNames, ArrayList<int[][]> tutorialTimes) {
        this.name = name;
        this.subClassNames = subClassNames;
        for (int i = 0; i < subClassNames.size(); i++) {
            this.subClassTimes.put(subClassNames.get(i), subClassTimes.get(i));
        }

        this.hasLab = hasLab;
        this.labNames = labNames;
        for (int i = 0; i < labNames.size(); i++) {
            this.labTimes.put(labNames.get(i), labTimes.get(i));
        }

        this.hasTutorial = hasTutorial;
        this.tutorialNames = tutorialNames;
        for (int i = 0; i < tutorialNames.size(); i++) {
            this.tutorialTimes.put(tutorialNames.get(i), tutorialTimes.get(i));
        }
    }

    //getters
    public boolean getHasLab() {
        return hasLab;
    }

    public boolean getHasTutorial() {
        return hasTutorial;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSubClassNames() {
        return subClassNames;
    }

    public HashMap<String, int[][]> getSubClassTimes() {
        return subClassTimes;
    }

    public ArrayList<String> getLabNames() {
        return labNames;
    }

    public HashMap<String, int[][]> getLabTimes() {
        return labTimes;
    }

    public ArrayList<String> getTutorialNames() {
        return tutorialNames;
    }

    public HashMap<String, int[][]> getTutorialTimes() {
        return tutorialTimes;
    }


    //setters
    public void setHasLab(boolean hasLab) {
        this.hasLab = hasLab;
    }

    public void setHasTutorial(boolean hasTutorial) {
        this.hasTutorial = hasTutorial;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubClassNames(ArrayList<String> subClassNames) {
        this.subClassNames = subClassNames;
    }

    public void setSubClassTimes(HashMap<String, int[][]> subClassTimes) {
        this.subClassTimes = subClassTimes;
    }

    public void setLabNames(ArrayList<String> labNames) {
        this.labNames = labNames;
    }

    public void setLabTimes(HashMap<String, int[][]> labTimes) {
        this.labTimes = labTimes;
    }

    public void setTutorialNames(ArrayList<String> tutorialNames) {
        this.tutorialNames = tutorialNames;
    }

    public void setTutorialTimes(HashMap<String, int[][]> tutorialTimes) {
        this.tutorialTimes = tutorialTimes;
    }


    //REQUIRES: the int[][] needs to be in the specific format mentioned above
    //MODIFIES: this
    //EFFECTS: adds a sub class and times
    public void addSubClass(String subClassName, int[][] subClassTime) {
        this.subClassTimes.put(subClassName, subClassTime);
    }

    //REQUIRES: the int[][] needs to be in the specific format mentioned above
    //MODIFIES: this
    //EFFECTS: adds a lab and times
    public void addLab(String labName, int[][] labTime) {
        this.labTimes.put(labName, labTime);
    }

    //REQUIRES: the int[][] needs to be in the specific format mentioned above
    //MODIFIES: this
    //EFFECTS: adds a tutorial and times
    public void addTutorial(String tutorialName, int[][] tutorialTime) {
        this.tutorialTimes.put(tutorialName, tutorialTime);
    }


    //MODIFIES: this
    //EFFECTS: removes a sub class and times
    public void removeSubClass(String name) {
        this.subClassTimes.remove(name);
    }

    //MODIFIES: this
    //EFFECTS: removes a lab and times
    public void removeLab(String name) {
        this.labTimes.remove(name);
    }

    //MODIFIES: this
    //EFFECTS: removes a tutorial and times
    public void removeTutorial(String name) {
        this.tutorialTimes.remove(name);
    }


    //REQUIRES: the oldName to exist in the HashMap
    //MODIFIES: this
    //EFFECTS: edits a sub class name
    public void editSubClassName(String oldName, String newName) {
        this.subClassTimes.put(newName, subClassTimes.get(oldName));
        this.subClassTimes.remove(oldName);
    }

    //REQUIRES: the name to exist in the HashMap, newTimes to follow the int[][] format from above
    //MODIFIES: this
    //EFFECTS: removes a lab and times
    public void editSubClassTime(String name, int[][] newTimes) {
        this.subClassTimes.replace(name, newTimes);
    }

    //REQUIRES: the oldName to exist in the HashMap
    //MODIFIES: this
    //EFFECTS: edits a lab name
    public void editLabName(String oldName, String newName) {
        this.labTimes.put(newName, labTimes.get(oldName));
        this.labTimes.remove(oldName);
    }

    //REQUIRES: the name to exist in the HashMap, newTimes to follow the int[][] format from above
    //MODIFIES: this
    //EFFECTS: edits a lab time
    public void editLabTime(String name, int[][] newTimes) {
        this.labTimes.replace(name, newTimes);
    }

    //REQUIRES: the oldName to exist in the HashMap
    //MODIFIES: this
    //EFFECTS: edits a tutorial name
    public void editTutorialName(String oldName, String newName) {
        this.tutorialTimes.put(newName, tutorialTimes.get(oldName));
        this.tutorialTimes.remove(oldName);
    }

    //REQUIRES: the name to exist in the HashMap, newTimes to follow the int[][] format from above
    //MODIFIES: this
    //EFFECTS: edits a tutorial times
    public void editTutorialTime(String name, int[][] newTimes) {
        this.tutorialTimes.replace(name, newTimes);
    }

}
