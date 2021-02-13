package ui;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        UserInteractionConsole interactable = new UserInteractionConsole();

        interactable.generate();

        System.out.println("Showing all Schedules");
        interactable.showAllSchedules();
    }

}
