package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    Course c1;
    Course c2;
    Course c3;

    @BeforeEach
    public void setUp() {
        c1 = setupC1(); // Course no lab/tutorial
        c2 = setupC2(); // Course with lab, no tutorial
        c3 = setupC3(); // Course with lab and tutorial
    }

    //Initializes the Courses So they can be edited every time and then reset
    private Course setupC1(){
        return new Course("CPSC 210", new ArrayList<>(Arrays.asList("201", "202", "203")),
                new ArrayList<>(Arrays.asList(new int[][]{{7, 0}, {8, 0}, {1, 3, 5}},
                        new int[][]{{15, 0}, {16, 0}, {1, 3, 5}},
                        new int[][]{{9, 30}, {10, 30}, {1, 3, 5}})));
    }

    private Course setupC2(){
        return new Course("ENGL 100", new ArrayList<>(Arrays.asList("201", "202")),
                new ArrayList<>(Arrays.asList(new int[][]{{8, 0}, {20, 0}, {1, 3, 5}},
                        new int[][]{{9, 30}, {10, 30}, {1, 3, 5}})),
                true, new ArrayList<>(Arrays.asList("L2A", "L2B", "L2C")),
                new ArrayList<>(Arrays.asList(new int[][]{{10, 0}, {12, 0}, {1, 3, 5}},
                        new int[][]{{15, 0}, {18, 0}, {4}},
                        new int[][]{{8, 0}, {11, 0}, {2}})),
                false, new ArrayList<>(), new ArrayList<>());
    }

    private Course setupC3(){
        return new Course("PHYS 118", new ArrayList<>(Arrays.asList("201", "202")),
                new ArrayList<>(Arrays.asList(new int[][]{{10, 0}, {12, 0}, {1, 3, 5}},
                        new int[][]{{9, 30}, {10, 30}, {1, 3, 5}})),
                true, new ArrayList<>(Arrays.asList("L2A", "L2B", "L2C")),
                new ArrayList<>(Arrays.asList(new int[][]{{10, 0}, {12, 0}, {1, 3, 5}},
                        new int[][]{{15, 0}, {18, 0}, {4}},
                        new int[][]{{8, 0}, {11, 0}, {2}})),
                true, new ArrayList<>(Arrays.asList("T2A", "T2C", "T2E")),
                new ArrayList<>(Arrays.asList(new int[][]{{20, 0}, {21, 0}, {1, 3, 5}},
                        new int[][]{{12, 0}, {13, 30}, {2}},
                        new int[][]{{14, 30}, {16, 0}, {1}})));
    }

    private boolean compareLists(ArrayList a, ArrayList b) {
        for (int i = 0 ; i < a.size(); i++) {
            if(a.get(i) != b.get(i)) {
                return false;
            }
        }
        return true;
    }

    //All the tests are self explanatory and work pretty straightforwardly
    @Test
    public void getLabTests() {
        assertFalse(c1.getHasLab());
        assertTrue(c2.getHasLab());
        assertTrue(c3.getHasLab());

        assertEquals(new ArrayList<>(), c1.getLabNames());
        assertTrue(compareLists(new ArrayList<>(Arrays.asList("L2A", "L2B", "L2C")), c2.getLabNames()));
        assertTrue(compareLists(new ArrayList<>(Arrays.asList("L2A", "L2B", "L2C")), c3.getLabNames()));

        assertEquals(new HashMap<>(), c1.getLabTimes());
        //.get(X)[X][X] will appear often because testing for every separate case will largely be useless as the
        // specific values of X are what primarily change
        assertEquals(new ArrayList<>(c2.getLabTimes().values()).get(0)[0][0],
                new int[][]{{10,0}, {12,0}, {1,3,5}}[0][0]);
        assertEquals(new ArrayList<>(c3.getLabTimes().values()).get(0)[1][1],
                new int[][]{{10,0}, {12,0}, {1,3,5}}[1][1]);
    }


    @Test
    public void getTutorialTests() {
        assertFalse(c1.getHasTutorial());
        assertFalse(c2.getHasTutorial());
        assertTrue(c3.getHasTutorial());

        assertEquals(new ArrayList<>(), c1.getTutorialNames());
        assertEquals(new ArrayList<>(), c2.getTutorialNames());
        assertTrue(compareLists(new ArrayList<>(Arrays.asList("T2A", "T2C", "T2E")), c3.getTutorialNames()));

        assertEquals(new HashMap<>(), c1.getTutorialTimes());
        assertEquals(new HashMap<>(), c2.getTutorialTimes());
        assertEquals(new ArrayList<>(c3.getTutorialTimes().values()).get(0)[1][1],
                new int[][]{{20,0}, {21,0}, {1,3,5}}[1][1]);
    }

    @Test
    public void getOtherTests() {
        assertEquals("CPSC 210", c1.getName());
        assertEquals("ENGL 100", c2.getName());
        assertEquals("PHYS 118", c3.getName());

        assertEquals(Arrays.asList("201", "202", "203"), c1.getSubClassNames());
        assertEquals(Arrays.asList("201", "202"), c2.getSubClassNames());
        assertEquals(Arrays.asList("201", "202"), c3.getSubClassNames());

        assertEquals(new ArrayList<>(c1.getSubClassTimes().values()).get(0)[1][1],
                new int[][]{{7,0}, {8,0}, {1,3,5}}[1][1]);
    }

    @Test
    public void setLabTests() {
        c1.setHasLab(true);
        assertTrue(c1.getHasLab());
        c2.setHasLab(false);
        assertFalse(c2.getHasLab());
        c2.setHasLab(true);
        assertTrue(c3.getHasLab());

        c3.setLabNames(new ArrayList<>(Arrays.asList("L2D", "L2C", "L2F")));
        assertEquals(Arrays.asList("L2D", "L2C", "L2F"), c3.getLabNames());

        HashMap<String, int[][]> forTest = new HashMap<>();
        forTest.put("L2D", (new int[][]{{10,0}, {12,0}, {1,3,5}}));
        forTest.put("L2C", (new int[][]{{15,0}, {18,0}, {4}}));
        forTest.put("L2F", (new int[][]{{8,0}, {11,0}, {2}}));

        c3.setLabTimes(forTest);
        assertEquals(new ArrayList<>(forTest.values()), new ArrayList<>(c3.getLabTimes().values()));
    }

    @Test
    public void setTutorialTests() {
        c1.setHasTutorial(true);
        assertTrue(c1.getHasTutorial());
        c2.setHasTutorial(false);
        assertFalse(c2.getHasTutorial());
        c2.setHasTutorial(true);
        assertTrue(c3.getHasTutorial());

        c3.setTutorialNames(new ArrayList<>(Arrays.asList("L2D", "L2C", "L2F")));
        assertEquals(Arrays.asList("L2D", "L2C", "L2F"), c3.getTutorialNames());

        HashMap<String, int[][]> forTest = new HashMap<>();
        forTest.put("L2D", (new int[][]{{10,0}, {12,0}, {1,3,5}}));
        forTest.put("L2C", (new int[][]{{15,0}, {18,0}, {4}}));
        forTest.put("L2F", (new int[][]{{8,0}, {11,0}, {2}}));

        c3.setTutorialTimes(forTest);
        assertEquals(new ArrayList<>(forTest.values()), new ArrayList<>(c3.getTutorialTimes().values()));
    }

    @Test
    public void setOtherTests() {
        c1.setName("HYDR 200");
        assertEquals("HYDR 200", c1.getName());

        c3.setSubClassNames(new ArrayList<>(Arrays.asList("L2D", "L2C", "L2F")));
        assertEquals(Arrays.asList("L2D", "L2C", "L2F"), c3.getSubClassNames());

        HashMap<String, int[][]> forTest = new HashMap<>();
        forTest.put("L2D", (new int[][]{{10,0}, {12,0}, {1,3,5}}));
        forTest.put("L2C", (new int[][]{{15,0}, {18,0}, {4}}));
        forTest.put("L2F", (new int[][]{{8,0}, {11,0}, {2}}));

        c3.setSubClassTimes(forTest);
        assertEquals(new ArrayList<>(forTest.values()), new ArrayList<>(c3.getSubClassTimes().values()));
    }



    @Test
    public void addersTest() {
        c1.addSubClass("205", (new int[][]{{9,0}, {10,0}, {1,3}}));
        assertEquals( new ArrayList<>(Arrays.asList("201", "202", "203", "205")), c1.getSubClassNames());
        HashMap<String, int[][]> forTest = new HashMap<>();
        forTest.put("201", (new int[][]{{7,0}, {8,0}, {1,3,5}}));
        forTest.put("202", (new int[][]{{15,0}, {16,0}, {1,3,5}}));
        forTest.put("203", (new int[][]{{9,30}, {10,30}, {1,3,5}}));
        forTest.put("205", (new int[][]{{9,0}, {10,0}, {1,3}}));
        assertEquals( new ArrayList<>(forTest.values()).get(0)[0][0],
                new ArrayList<>(c1.getSubClassTimes().values()).get(0)[0][0]);

        c1.addLab("L1", (new int[][]{{9,0}, {10,0}, {1,3}}));
        assertEquals( new ArrayList<>(Arrays.asList("L1")), c1.getLabNames());
        HashMap<String, int[][]> forTest2 = new HashMap<>();
        forTest2.put("L1", (new int[][]{{9,0}, {10,0}, {1,3}}));
        assertEquals( new ArrayList<>(forTest2.values()).get(0)[0][0],
                new ArrayList<>(c1.getLabTimes().values()).get(0)[0][0]);

        c1.addTutorial("T1", (new int[][]{{9,0}, {10,0}, {1,3}}));
        assertEquals( new ArrayList<>(Arrays.asList("T1")), c1.getTutorialNames());
        HashMap<String, int[][]> forTest3 = new HashMap<>();
        forTest3.put("T1", (new int[][]{{9,0}, {10,0}, {1,3}}));
        assertEquals( new ArrayList<>(forTest3.values()).get(0)[0][0],
                new ArrayList<>(c1.getTutorialTimes().values()).get(0)[0][0]);
    }


    @Test
    public void removersTest() {
        c3.removeSubClass("202");
        assertEquals( new ArrayList<>(Arrays.asList("201")), c3.getSubClassNames());
        HashMap<String, int[][]> forTest = new HashMap<>();
        forTest.put("201", (new int[][]{{10,0}, {12,0}, {1,3,5}}));
        assertEquals(  new ArrayList<>(forTest.values()).get(0)[0][0],
                new ArrayList<>(c3.getSubClassTimes().values()).get(0)[0][0]);
        c3.removeSubClass("201");
        HashMap<String, int[][]> forTest4 = new HashMap<>();
        assertEquals( Arrays.asList(), c3.getSubClassNames());
        assertEquals(  new ArrayList<>(forTest4.values()), new ArrayList<>(c3.getSubClassTimes().values()));

        c3.removeLab("L2C");
        c3.removeLab("L2B");
        assertEquals( Arrays.asList("L2A"), c3.getLabNames());
        HashMap<String, int[][]> forTest2 = new HashMap<>();
        forTest2.put("L2A", (new int[][]{{10,0}, {12,0}, {1,3,5}}));
        assertEquals(  new ArrayList<>(forTest2.values()).get(0)[1][1],
                new ArrayList<>(c3.getLabTimes().values()).get(0)[1][1]);

        c3.removeTutorial("T2C");
        c3.removeTutorial("T2E");
        assertEquals( Arrays.asList("T2A"), c3.getTutorialNames());
        HashMap<String, int[][]> forTest3 = new HashMap<>();
        forTest3.put("T2A", (new int[][]{{20,0}, {21,0}, {1,3,5}}));
        assertEquals(  new ArrayList<>(forTest3.values()).get(0)[1][0],
                new ArrayList<>(c3.getTutorialTimes().values()).get(0)[1][0]);
    }


    @Test
    public void editSubClassTest() {
        c1.removeSubClass("202");
        c1.removeSubClass("203");
        c1.editSubClassName("201","222");
        assertEquals( new ArrayList<>(Arrays.asList("222")), c1.getSubClassNames());

        c1.editSubClassName("201","223");
        assertEquals(new ArrayList<>(Arrays.asList("222")), c1.getSubClassNames());
        c1.editSubClassTime("222", new int[][]{{9,30}, {10,30}, {1,3,5}});
        assertEquals( new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,30}, {1,3,5}})).get(0)[0],
                new ArrayList<>(c1.getSubClassTimes().values()).get(0)[0][0]);

    }


    @Test
    public void editLabTest() {
        c3.removeLab("L2B");
        c3.removeLab("L2C");
        c3.editLabName("L2A", "L2D");
        assertEquals( new ArrayList<>(Arrays.asList("L2D")), c3.getLabNames());

        c3.editLabName("201","223");
        assertEquals( new ArrayList<>(Arrays.asList("L2D")), c3.getLabNames());

        c3.editLabTime("L2D", new int[][]{{9,30}, {10,30}, {1,3,5}});
        assertEquals( new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,30}, {1,3,5}})).get(0)[0],
                new ArrayList<>(c3.getLabTimes().values()).get(0)[0][0]);

    }

    @Test
    public void editTutorialTest() {
        c3.removeTutorial("T2E");
        c3.removeTutorial("T2C");
        c3.editTutorialName("T2A", "T2D");
        assertEquals(new ArrayList<>(Arrays.asList("T2D")), c3.getTutorialNames());

        c3.editTutorialName("201","223");
        assertEquals(new ArrayList<>(Arrays.asList("T2D")), c3.getTutorialNames());

        c3.editTutorialTime("T2D", new int[][]{{9,30}, {10,30}, {1,3,5}});
        assertEquals(new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,30}, {1,3,5}})).get(0)[0],
                new ArrayList<>(c3.getTutorialTimes().values()).get(0)[0][0]);

    }
}