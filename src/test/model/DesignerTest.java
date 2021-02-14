package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;


public class DesignerTest {


    Course math120 = new Course("MATH 120", new ArrayList<>(Arrays.asList("201", "212")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{15,0}, {16,0}, {1,3,5}})));
    Course phys118 = new Course("PHYS 118", new ArrayList<>(Arrays.asList("201", "202")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{9,30}, {10,0}, {1,3,5}})),
            true, new ArrayList<>(Arrays.asList("L2A", "L2B")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{15,0}, {18,0}, {4}})),
            true, new ArrayList<>(Arrays.asList("T2A", "T2C")),
            new ArrayList<>(Arrays.asList(new int[][]{{20,0}, {21,0}, {1,3,5}},
                    new int[][]{{12,0}, {13,30}, {2}})));

    Course phys1182 = new Course("PHYS 118", new ArrayList<>(Arrays.asList("202")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})),
            true, new ArrayList<>(Arrays.asList("L2A", "L2B")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{15,0}, {18,0}, {4}})),
            true, new ArrayList<>(Arrays.asList("T2A", "T2C")),
            new ArrayList<>(Arrays.asList(new int[][]{{20,0}, {21,0}, {1,3,5}},
                    new int[][]{{12,0}, {13,30}, {2}})));
    Course math1202 = new Course("MATH 120", new ArrayList<>(Arrays.asList("212")),
            new ArrayList<>(Arrays.asList(new int[][]{{15,0}, {16,0}, {1,3,5}}, new int[][]{})));

    Course phys1183 = new Course("PHYS 118", new ArrayList<>(Arrays.asList("202")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})),
            true, new ArrayList<>(Arrays.asList("L2A", "L2B")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{10,0}, {12,0}, {1,3,5}})),
            true, new ArrayList<>(Arrays.asList("T2A", "T2C")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{10,0}, {12,0}, {1,3,5}})));
    Course math1203 = new Course("MATH 120", new ArrayList<>(Arrays.asList("212")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})));

    Course phys1184 = new Course("PHYS 118", new ArrayList<>(Arrays.asList("202")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})),
            true, new ArrayList<>(Arrays.asList("L2A", "L2B")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{10,0}, {12,0}, {1,3,5}})),
            false, new ArrayList<>(Arrays.asList("T2A", "T2C")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{10,0}, {12,0}, {1,3,5}})));
    Course math1204 = new Course("MATH 120", new ArrayList<>(Arrays.asList("212", "213")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}},
                    new int[][]{{7,0}, {7,30}, {2}})));

    Course cpsc121 = new Course("CPSC 121", new ArrayList<>(Arrays.asList("212")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})));
    Course cpsc110 = new Course("CPSC 110", new ArrayList<>(Arrays.asList("212")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,30}, {11,0}, {1,3,5}}, new int[][]{})));

    Course phys1185 = new Course("PHYS 118", new ArrayList<>(Arrays.asList("202")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})),
            true, new ArrayList<>(Arrays.asList("L2A", "L2B")),
            new ArrayList<>(Arrays.asList(new int[][]{{9,30}, {10,0}, {1,3,5}}, new int[][]{})),
            false, new ArrayList<>(Arrays.asList("T2A", "T2C")),
            new ArrayList<>(Arrays.asList(new int[][]{{10,0}, {12,0}, {1,3,5}},
                    new int[][]{{10,0}, {12,0}, {1,3,5}})));


    Designer designer1;
    Designer designer2;
    Designer designer3;
    Designer designer4;
    Designer designer5;
    Designer designer6;
    Designer designer7;
    Designer designer8;
    Designer designer9;
    Designer designer10;
    Designer designer11;
    Designer designer12;

    ArrayList<Course> list;
    ArrayList<Course> list2;
    ArrayList<Course> list3;
    ArrayList<Course> list4;
    ArrayList<Course> list5;
    ArrayList<Course> list6;
    ArrayList<Course> list7;
    ArrayList<Course> list8;
    ArrayList<Course> list9;
    ArrayList<Course> list11;
    ArrayList<Course> list12;

    @BeforeEach
    public void setup() {
        designer1 = new Designer(new ArrayList<Course>(), 5);

        list = new ArrayList<>();
        list.add(phys118);
        designer2 = new Designer(list, 1);

        list2 = new ArrayList<>();
        list2.add(phys118);
        list2.add(math120);
        designer3 = new Designer(list2, 5);

        list3 = new ArrayList<>();
        list3.add(phys1182);
        designer4 = new Designer(list3, 1);

        list4 = new ArrayList<>();
        list4.add(phys1183);
        designer5 = new Designer(list4, 1);

        list5 = new ArrayList<>();
        list5.add(phys1183);
        list5.add(math1203);
        designer6 = new Designer(list5, 2);

        list6 = new ArrayList<>();
        list6.add(math120);
        designer7 = new Designer(list6, 1);

        list7 = new ArrayList<>();
        list7.add(phys1184);
        designer8 = new Designer(list7, 1);

        list8 = new ArrayList<>();
        list8.add(math1204);
        list8.add(phys1182);
        designer9 = new Designer(list8, 2);

        list9 = new ArrayList<>();
        list9.add(math1203);
        list9.add(cpsc121);
        list9.add(cpsc110);
        designer10 = new Designer(list9, 3);

        list11 = new ArrayList<>();
        list11.add(phys1185);
        designer11 = new Designer(list11, 1);

        list12 = new ArrayList<>();
        list12.add(math1203);
        list12.add(cpsc121);
        list12.add(cpsc110);
        designer12 = new Designer(list12, 1);
    }

    //ArrayList<Scheduler>

    @Test
    public void buildSchedulesOnlyMainWithPriorityTest() {
        //empty test
        designer1.buildSchedulesOnlyMainWithPriority();
        ArrayList<Scheduler> alpha = new ArrayList<>();
        assertTrue(compareSchedulerList(alpha, designer1.getSchedules()));

        //normal test
        designer2.buildSchedulesOnlyMainWithPriority();
        Scheduler scheduler21 = new Scheduler(1);
        Scheduler scheduler22 = new Scheduler(1);
        scheduler21.addCourseToSchedule(phys118);
        scheduler22.addCourseToSchedule(phys1182);
        alpha.add(scheduler21);
        alpha.add(scheduler22);
        assertTrue(compareSchedulerList(alpha, designer2.getSchedules()));

        //Case with some schedule impossibilities
        assertTrue(designer3.buildSchedulesOnlyMainWithPriority());
        Scheduler scheduler31 = new Scheduler(2);
        Scheduler scheduler32 = new Scheduler(2);
        Scheduler scheduler33 = new Scheduler(2);
        scheduler31.addCourseToSchedule(phys118);
        scheduler31.addCourseToSchedule(math120);
        scheduler32.addCourseToSchedule(phys1182);
        scheduler32.addCourseToSchedule(math120);
        scheduler33.addCourseToSchedule(phys1182);
        scheduler33.addCourseToSchedule(math1202);
        ArrayList<Scheduler> alpha2 = new ArrayList<>();
        alpha2.add(scheduler32);
        alpha2.add(scheduler31);
        alpha2.add(scheduler33);
        assertTrue(compareSchedulerList(alpha2, designer3.getSchedules()));

        //To make sure the code still runs with some edge cases
        //Will add and remove as the functionality changes
        assertTrue(designer6.buildSchedulesOnlyMainWithPriority());
    }

    //EFFECTS: tests if two ListOfScheduler objects have the same data
    private boolean compareSchedulerList(ArrayList<Scheduler> alpha, ArrayList<Scheduler> beta) {
        for (int i = 0; i < alpha.size(); i++) {
            if (!(compareScheduler(alpha.get(i), beta.get(i)))) {
                return false;
            }
        }
        return true;
    }

    //EFFECTS: tests if two Scheduler objects have the same data in their schedule
    private boolean compareScheduler(Scheduler alpha, Scheduler beta) {
        String[][] schedule1 = alpha.getSchedule();
        String[][] schedule2 = beta.getSchedule();

        for (int i = 0 ; i < schedule1.length; i++) {
            for (int j = 0 ; j < schedule1[i].length; j++) {
                if (schedule1[i][j] != null && schedule2[i][j] != null) {
                    if (!schedule1[i][j].equals(schedule2[i][j])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Test
    public void buildSchedulesWithLabsAndTutorialsTest() {
        //test empty
        designer1.buildSchedulesOnlyMainWithPriority();
        assertFalse(designer1.buildSchedulesWithLabsAndTutorials());
        ArrayList<Scheduler> alpha = new ArrayList<>();
        assertTrue(compareSchedulerList(alpha, designer1.getSchedules()));

        //test normal
        //most of this is building alpha2 as a test comparison schedule
        assertTrue(designer4.buildSchedulesOnlyMainWithPriority());
        assertTrue(designer4.buildSchedulesWithLabsAndTutorials());
        Scheduler scheduler1 = new Scheduler(1);
        scheduler1.addCourseToSchedule(phys1182);
        scheduler1.addLabOrTutorialToSchedule("PHYS 118 L2A", new int[][]{{10,0}, {12,0}, {1,3,5}});
        scheduler1.addLabOrTutorialToSchedule("PHYS 118 T2A", new int[][]{{20,0}, {21,0}, {1,3,5}});

        Scheduler scheduler2 = new Scheduler(1);
        scheduler2.addCourseToSchedule(phys1182);
        scheduler2.addLabOrTutorialToSchedule("PHYS 118 L2A", new int[][]{{10,0}, {12,0}, {1,3,5}});
        scheduler2.addLabOrTutorialToSchedule("PHYS 118 T2C", new int[][]{{12,0}, {13,30}, {2}});

        Scheduler scheduler3 = new Scheduler(1);
        scheduler3.addCourseToSchedule(phys1182);
        scheduler3.addLabOrTutorialToSchedule("PHYS 118 L2B", new int[][]{{15,0}, {18,0}, {4}});
        scheduler3.addLabOrTutorialToSchedule("PHYS 118 T2A", new int[][]{{20,0}, {21,0}, {1,3,5}});

        Scheduler scheduler4 = new Scheduler(1);
        scheduler4.addCourseToSchedule(phys1182);
        scheduler4.addLabOrTutorialToSchedule("PHYS 118 L2B", new int[][]{{15,0}, {18,0}, {4}});
        scheduler4.addLabOrTutorialToSchedule("PHYS 118 T2C", new int[][]{{12,0}, {13,30}, {2}});

        ArrayList<Scheduler> alpha2 = new ArrayList<>();
        alpha2.add(scheduler1);
        alpha2.add(scheduler2);
        alpha2.add(scheduler3);
        alpha2.add(scheduler4);
        assertTrue(compareSchedulerList(alpha2, designer4.getSchedules()));

        //A lot of possible edge cases to make sure the correct parts of the code run under certain situations
        //such as No Labs/Tuts, Impossible to Add Labs/Tuts, and some redundant tests that
        // change as the functionality changes
        assertTrue(designer5.buildSchedulesOnlyMainWithPriority());
        assertFalse(designer5.buildSchedulesWithLabsAndTutorials());

        assertTrue(designer7.buildSchedulesOnlyMainWithPriority());
        assertTrue(designer7.buildSchedulesWithLabsAndTutorials());

        assertTrue(designer8.buildSchedulesOnlyMainWithPriority());
        assertTrue(designer8.buildSchedulesWithLabsAndTutorials());

        assertTrue(designer9.buildSchedulesOnlyMainWithPriority());
        assertTrue(designer9.buildSchedulesWithLabsAndTutorials());

        assertTrue(designer10.buildSchedulesOnlyMainWithPriority());
        assertTrue(designer10.buildSchedulesWithLabsAndTutorials());

        assertTrue(designer11.buildSchedulesOnlyMainWithPriority());
        assertFalse(designer11.buildSchedulesWithLabsAndTutorials());
    }


}
