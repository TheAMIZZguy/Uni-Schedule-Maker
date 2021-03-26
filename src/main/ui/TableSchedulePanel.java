package ui;

import model.Scheduler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class TableSchedulePanel extends JPanel implements ActionListener {

    ArrayList<Scheduler> schedules;
    ArrayList<String> filters;

    private JButton deleteSpecified;
    JComboBox numSchedBox;

    MainFrame parent;

    //MODIFIES: this
    //EFFECTS: displays the tables and buttons from the schedules, filtered if required
    public TableSchedulePanel(MainFrame parent, ArrayList<Scheduler> schedules, ArrayList<String> filters) {
        //super(new BoxLayout(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.parent = parent;
        this.schedules = schedules;
        this.filters = filters;

        deleteSpecified = new JButton("Delete Schedule At Index");
        deleteSpecified.setActionCommand("Delete");
        deleteSpecified.addActionListener(this);

        int size = schedules.size();
        String[] numSchedsArr = new String[size];
        for (int i = 0; i < size; i++) {
            numSchedsArr[i] = String.valueOf(i);
        }

        numSchedBox = new JComboBox(numSchedsArr);


        JPanel listButtonPanel = new JPanel();
        listButtonPanel.setLayout(new BoxLayout(listButtonPanel, BoxLayout.X_AXIS));
        listButtonPanel.setMaximumSize(new Dimension(400,20));
        listButtonPanel.add(numSchedBox);
        listButtonPanel.add(Box.createHorizontalStrut(10));
        listButtonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        listButtonPanel.add(Box.createHorizontalStrut(10));
        listButtonPanel.add(deleteSpecified);

        add(listButtonPanel);

        tableAdder(schedules, filters);
    }

    //MODIFIES: this
    //EFFECTS: adds tables to the panel
    private void tableAdder(ArrayList<Scheduler> schedules, ArrayList<String> filters) {
        if (schedules.size() == 0) {
            addScheduleTable(new Scheduler(0), "0");
        }

        int sizeFilters = filters.size();

        boolean hasAFilter = false;
        for (int i = 0; i < schedules.size(); i++) {
            if (sizeFilters != 0) {
                hasAFilter = false;
                for (String s : filters) {
                    if (Arrays.asList(schedules.get(i).getCoursesInSchedule()).contains(s)) {
                        hasAFilter = true;
                        break;
                    }
                }
                if (hasAFilter) {
                    addScheduleTable(schedules.get(i), Integer.toString(i));
                }
            } else {
                addScheduleTable(schedules.get(i), Integer.toString(i));
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: adds a schedule to the panel after being converted to a table
    private void addScheduleTable(Scheduler sched, String num) {
        final JTable table = new JTable(new ScheduleTable(sched, num));
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

    //MODIFIES: this, parent
    //EFFECTS: plays a sound and possibly removes a schedule
    @Override
    public void actionPerformed(ActionEvent e) {
        parent.playSound("click1.wav");
        try {
            schedules.remove(numSchedBox.getSelectedIndex());
            parent.viewSchedulePanes();
        } catch (Exception e1) {
            //just ignore
        }
        //System.out.println(numSchedBox.getSelectedIndex());
    }
}
