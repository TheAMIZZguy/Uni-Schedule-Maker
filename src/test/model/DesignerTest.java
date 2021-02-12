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


    Designer designer1;
    Designer designer2;
    Designer designer3;
    Designer designer4;

    ArrayList<Course> list;
    ArrayList<Course> list2;
    ArrayList<Course> list3;

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

    }

    //ArrayList<Scheduler>

    @Test
    public void buildSchedulesOnlyMainWithPriorityTest() {
        designer1.buildSchedulesOnlyMainWithPriority();
        ArrayList<Scheduler> alpha = new ArrayList<>();
        assertTrue(compareSchedulerList(alpha, designer1.getSchedules()));

        designer2.buildSchedulesOnlyMainWithPriority();
        Scheduler scheduler21 = new Scheduler(1);
        Scheduler scheduler22 = new Scheduler(1);
        scheduler21.addCourseToSchedule(phys118);
        scheduler22.addCourseToSchedule(phys1182);
        alpha.add(scheduler21);
        alpha.add(scheduler22);
        assertTrue(compareSchedulerList(alpha, designer2.getSchedules()));

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
    }

    private boolean compareSchedulerList(ArrayList<Scheduler> alpha, ArrayList<Scheduler> beta) {
        for (int i = 0; i < alpha.size(); i++) {
            if (!(compareScheduler(alpha.get(i), beta.get(i)))) {
                return false;
            }
        }
        return true;
    }

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

    //TODO Add Labs and Tutorials
    @Test
    public void buildSchedulesWithLabsAndTutorialsTest() {
        designer1.buildSchedulesOnlyMainWithPriority();
        designer1.buildSchedulesWithLabsAndTutorials();
        ArrayList<Scheduler> alpha = new ArrayList<>();
        assertTrue(compareSchedulerList(alpha, designer1.getSchedules()));

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
    }

    //TODO Without Priority?

}
