package ui;

import java.util.Scanner;

//Main, runs the program through the MainFrame
public class Main {

    private static final boolean USE_GUI = true;

    public static void main(String[] args) {
        if (USE_GUI) {
            System.out.println("Goodbye!");
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new MainFrame().initializeGraphics();
                }
            });
        } else {
            System.out.println("Hello! Welcome to your personalized Schedule Planner \n\n");
            new UserInteractionConsole();
            System.out.println("Goodbye!");
        }

    }

}
