package ui;

import model.Scheduler;

import javax.swing.table.AbstractTableModel;

//Converts a scheduler object into a human viewable table
public class ScheduleTable extends AbstractTableModel {

    private String[] columnNames = {"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    String[][] data = new String[2 * 14][6];

    //MODIFIES: this
    //EFFECTS: fills a table with the schedule data
    public ScheduleTable(Scheduler schedule, String num) {
        columnNames[0] = num;
        //this.data = schedule.getSchedule();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (i % 2 == 0 && j == 0) {
                    this.data[i][0] =  (String.valueOf(i / 2 + 7) + ":00");
                } else if (j != 0) {
                    data[i][j] = schedule.getSchedule()[i][j - 1];
                }
            }
        }
    }

    //getters
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    //EFFECTS: makes the table uneditable
    public boolean isCellEditable(int row, int col) {
        return false;
    }

}
