package ui;

import java.awt.*;
import javax.swing.*;

public class TableSchedulePanel extends JPanel {

    private final String[] columnNames = {"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    //TODO temp
    String[][] data =  new String[2 * 14][6]; /*{
            {"7:00", "1", "2", "3", "4", "5"},
            {"7:30", "1", "2", "3", "4", "5"},
            {"8:00", "1", "2", "3", "4", "5"},
            {"8:30", "1", "2", "3", "4", "5"},
            {"9:00", "1", "2", "3", "4", "5"}};
            */

    public TableSchedulePanel() {
        //super(new GridLayout(0,6));

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(1000, 350));
        table.setMinimumSize(new Dimension(1000, 350));
        table.doLayout();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        table.getColumnModel().getColumn(3).setMinWidth(100);


        JTable rowTable = new JTable(28, 1);
        scrollPane.setRowHeaderView(rowTable);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());
    }


    public static void initializeGraphics() {
        JFrame frame = new JFrame("SimpleTableDemo");
        TableSchedulePanel newContentPane = new TableSchedulePanel();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
    }
}
