package ui;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    public static final int WIDTH = 1400;
    public static final int HEIGHT = 1000;

    private JSplitPane leftSplit;
    private JSplitPane horizontalSplit;


    public MainFrame() {
        JScrollPane leftScrollPane = new JScrollPane();
        JScrollPane upScrollPane = new JScrollPane();
        TableSchedulePanel downScrollPane = new TableSchedulePanel();

        Dimension minimumSize = new Dimension(100, 50);
        leftScrollPane.setMinimumSize(minimumSize);
        upScrollPane.setMinimumSize(minimumSize);
        downScrollPane.setMinimumSize(new Dimension(500, 500));

        //downScrollPane. ();

        horizontalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upScrollPane, new JScrollPane(downScrollPane));
        horizontalSplit.setDividerLocation((int) (HEIGHT * .40));
        horizontalSplit.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        leftSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScrollPane, horizontalSplit);
        leftSplit.setDividerLocation((int) (WIDTH * .20));
        leftSplit.setPreferredSize(new Dimension(WIDTH, HEIGHT));

    }


    public static void initializeGraphics() {
        //Create and set up the window.
        JFrame frame = new JFrame("Graphical UI Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainFrame mainFrame = new MainFrame();
        frame.getContentPane().add(mainFrame.getLeftmostSplitPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private JSplitPane getLeftmostSplitPane() {
        return leftSplit;
    }


}
