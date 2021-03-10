package ui;

import model.Scheduler;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class TableSchedulePanel extends JPanel {

    private final String[] columnNames = {"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    //TODO temp
    String[][] data =  new String[2 * 14][5]; /*{
            {"7:00", "1", "2", "3", "4", "5"},
            {"7:30", "1", "2", "3", "4", "5"},
            {"8:00", "1", "2", "3", "4", "5"},
            {"8:30", "1", "2", "3", "4", "5"},
            {"9:00", "1", "2", "3", "4", "5"}};
            */

    public TableSchedulePanel() {
        super(new GridLayout(0,1));

        addScheduleTable(new Scheduler(0));
        addScheduleTable(new Scheduler(0));
    }

    private void addScheduleTable(Scheduler sched) {
        final JTable table = new JTable(new ScheduleTable(sched)); //TODO change yk
        table.setPreferredScrollableViewportSize(new Dimension(800, 450));
        JScrollPane scrollPane = new JScrollPane(table);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(0).setMinWidth(25);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(0).setMaxWidth(150);


        add(scrollPane);
    }


    public static void initializeGraphics() {
        JFrame frame = new JFrame("SimpleTableDemo");
        TableSchedulePanel newContentPane = new TableSchedulePanel();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
    }
}
