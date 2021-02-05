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

    private Course setupC1(){
        return new Course("CPSC 210", (ArrayList<String>) Arrays.asList(new String[]{"201", "202", "203"}),
                (ArrayList<int[][]>) Arrays.asList(new int[][][] {new int[][]{{7,0}, {8,0}, {1,3,5}},
                        new int[][]{{15,0}, {16,0}, {1,3,5}},
                        new int[][]{{9,30}, {10,30}, {1,3,5}}}));
    }

    private Course setupC2(){
        return new Course("ENGL 100", (ArrayList<String>) Arrays.asList(new String[]{"201", "202"}),
                (ArrayList<int[][]>) Arrays.asList(new int[][][] {new int[][]{{8,0}, {20,0}, {1,3,5}},
                        new int[][]{{9,30}, {10,30}, {1,3,5}}}),
                true, (ArrayList<String>) Arrays.asList(new String[]{"L2A", "L2B", "L2C"}),
                (ArrayList<int[][]>) Arrays.asList(new int[][][] {new int[][]{{10,0}, {12,0}, {1,3,5}},
                        new int[][]{{15,0}, {18,0}, {4}},
                        new int[][]{{8,0}, {11,0}, {2}}}),
                false, new ArrayList<String>(), new ArrayList<int[][]>());
    }

    private Course setupC3(){
        return new Course("PHYS 118", (ArrayList<String>) Arrays.asList(new String[]{"201", "202"}),
                (ArrayList<int[][]>) Arrays.asList(new int[][][] {new int[][]{{10,0}, {12,0}, {1,3,5}},
                        new int[][]{{9,30}, {10,30}, {1,3,5}}}),
                true, (ArrayList<String>) Arrays.asList(new String[]{"L2A", "L2B", "L2C"}),
                (ArrayList<int[][]>) Arrays.asList(new int[][][] {new int[][]{{10,0}, {12,0}, {1,3,5}},
                        new int[][]{{15,0}, {18,0}, {4}},
                        new int[][]{{8,0}, {11,0}, {2}}}),
                true, (ArrayList<String>) Arrays.asList(new String[]{"T2A", "T2C", "T2E"}),
                (ArrayList<int[][]>) Arrays.asList(new int[][][] {new int[][]{{20,0}, {21,0}, {1,3,5}},
                        new int[][]{{12,0}, {13,30}, {2}},
                        new int[][]{{14,30}, {16,0}, {1}}}));
    }

    @Test
    public void getLabTests() {
        assertFalse(c1.getHasLab());
        assertTrue(c2.getHasLab());
        assertTrue(c3.getHasLab());

        assertEquals(new ArrayList<>(), c1.getLabNames());
        assertEquals(Arrays.asList(new String[]{"L2A", "L2B", "L2C"}), c2.getLabNames());
        assertEquals(Arrays.asList(new String[]{"L2A", "L2B", "L2C"}), c3.getLabNames());

        assertEquals(new HashMap<>(), c1.getLabTimes());
        assertEquals(Arrays.asList(new int[][][] {new int[][]{{10,0}, {12,0}, {1,3,5}},
                new int[][]{{15,0}, {18,0}, {4}},
                new int[][]{{8,0}, {11,0}, {2}}}), c2.getLabTimes());
        assertEquals(Arrays.asList(new int[][][] {new int[][]{{10,0}, {12,0}, {1,3,5}},
                new int[][]{{15,0}, {18,0}, {4}},
                new int[][]{{8,0}, {11,0}, {2}}}), c3.getLabTimes());
    }

    @Test
    public void getTutorialTests() {
        assertFalse(c1.getHasTutorial());
        assertFalse(c2.getHasTutorial());
        assertTrue(c3.getHasTutorial());

        assertEquals(new ArrayList<>(), c1.getTutorialNames());
        assertEquals(new ArrayList<>(), c2.getTutorialNames());
        assertEquals(Arrays.asList(new String[]{"T2A", "T2C", "T2E"}), c3.getTutorialNames());

        assertEquals(new HashMap<>(), c1.getTutorialTimes());
        assertEquals(new HashMap<>(), c2.getTutorialTimes());
        assertEquals(Arrays.asList(new int[][][] {new int[][]{{20,0}, {21,0}, {1,3,5}},
                new int[][]{{12,0}, {13,30}, {2}},
                new int[][]{{14,30}, {16,0}, {1}}}), c3.getTutorialTimes());
    }

    @Test
    public void getOtherTests() {
        assertEquals("CPSC 210", c1.getName());
        assertEquals("ENGL 100", c2.getName());
        assertEquals("PHYS 118", c3.getName());

        assertEquals(Arrays.asList(new String[]{"201", "202", "203"}), c1.getSubClassNames());
        assertEquals(Arrays.asList(new String[]{"201", "202"}), c2.getSubClassNames());
        assertEquals(Arrays.asList(new String[]{"201", "202"}), c3.getSubClassNames());

        assertEquals(new HashMap<>(), c1.getSubClassTimes());
        assertEquals(new HashMap<>(), c2.getSubClassTimes());
        assertEquals(Arrays.asList(new int[][][] {new int[][]{{20,0}, {21,0}, {1,3,5}},
                new int[][]{{12,0}, {13,30}, {2}},
                new int[][]{{14,30}, {16,0}, {1}}}), c3.getSubClassTimes());
    }

    //TODO test setters
    @Test
    public void setLabTests() {
        assertFalse(c1.getHasLab());
        assertTrue(c2.getHasLab());
        assertTrue(c3.getHasLab());

        assertEquals(new ArrayList<>(), c1.getLabNames());
        assertEquals(Arrays.asList(new String[]{"L2A", "L2B", "L2C"}), c2.getLabNames());
        assertEquals(Arrays.asList(new String[]{"L2A", "L2B", "L2C"}), c3.getLabNames());

        assertEquals(new HashMap<>(), c1.getLabTimes());
        assertEquals(Arrays.asList(new int[][][] {new int[][]{{10,0}, {12,0}, {1,3,5}},
                new int[][]{{15,0}, {18,0}, {4}},
                new int[][]{{8,0}, {11,0}, {2}}}), c2.getLabTimes());
        assertEquals(Arrays.asList(new int[][][] {new int[][]{{10,0}, {12,0}, {1,3,5}},
                new int[][]{{15,0}, {18,0}, {4}},
                new int[][]{{8,0}, {11,0}, {2}}}), c3.getLabTimes());
    }

    //TODO test adders
    //TODO test editers
}