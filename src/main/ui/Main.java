package ui;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        UserInteractionConsole interactable = new UserInteractionConsole();

        if (interactable.generate()) {
            System.out.println("Showing all Schedules");
            if (interactable.yesNoQuestion("Would you want to filter through results?")) {
                interactable.showAllSchedulesFiltered();
            } else {
                interactable.showAllSchedules();
            }
        }
    }

}
