package ui;

import model.Scheduler;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class HoursTable extends AbstractTableModel {
    private final String[] columnNames = {"Hour"};
    String[][] data;

    public HoursTable() {
        this.data = new String[28][1];

        for (int i = 0; i < data.length; i++) {
            if (i % 2 == 0) {
                this.data[i][0] =  (String.valueOf(i / 2 + 7) + ":00");
            }
        }
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
