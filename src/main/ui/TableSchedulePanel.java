package ui;

import model.Scheduler;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class TableSchedulePanel extends JPanel {

    ArrayList<Scheduler> schedules;

    public TableSchedulePanel(ArrayList<Scheduler> schedules) {
        super(new GridLayout(0,1));

        this.schedules = schedules;

        for (int i = 0; i < schedules.size(); i++) {
            addScheduleTable(schedules.get(i), Integer.toString(i));
        }
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

    public void initializeGraphics() {
        JFrame frame = new JFrame("SimpleTableDemo");
        this.setOpaque(true); //content panes must be opaque
        frame.setContentPane(this);
    }
}
