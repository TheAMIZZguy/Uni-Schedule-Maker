package ui;

import java.util.Scanner;

//Main, runs the program through UserInteractionConsole
public class Main {

    public static void main(String[] args) {
//        System.out.println("Hello! Welcome to your personalized Schedule Planner \n\n");
//        new UserInteractionConsole();
//        System.out.println("Goodbye!");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().initializeGraphics();
            }
        });
    }

}
