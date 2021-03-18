package ui;

import model.Scheduler;

import java.awt.*;
import java.util.ArrayList;
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

    ArrayList<Scheduler> schedules;

    public TableSchedulePanel() {
        super(new GridLayout(0,1));

        addScheduleTable(new Scheduler(0), "1");
        addScheduleTable(new Scheduler(0), "2");
    }

    public void addSchedules(ArrayList<Scheduler> schedules) {
        this.schedules = schedules;

        for (int i = 0; i < schedules.size(); i++) {
            addScheduleTable(schedules.get(i), Integer.toString(i));
        }

        //initializeGraphics();
    }

    private void addScheduleTable(Scheduler sched, String num) {
        final JTable table = new JTable(new ScheduleTable(sched, num)); //TODO change yk
        table.setPreferredScrollableViewportSize(new Dimension(800, 450));
        JScrollPane scrollPane = new JScrollPane(table);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(0).setMinWidth(25);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(0).setMaxWidth(150);

        table.getTableHeader().setReorderingAllowed(false);

        add(scrollPane);
    }


    public static void initializeGraphics() {
        JFrame frame = new JFrame("SimpleTableDemo");
        TableSchedulePanel newContentPane = new TableSchedulePanel();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
    }
}
