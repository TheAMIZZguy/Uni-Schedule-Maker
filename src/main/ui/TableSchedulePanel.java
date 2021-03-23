package ui;

import model.Scheduler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class TableSchedulePanel extends JPanel implements ActionListener {

    ArrayList<Scheduler> schedules;

    private JButton deleteSpecified;
    JComboBox numSchedBox;

    MainFrame parent;

    public TableSchedulePanel(MainFrame parent, ArrayList<Scheduler> schedules) {
        //super(new BoxLayout(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.parent = parent;

        this.schedules = schedules;

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

        if (schedules.size() == 0) {
            addScheduleTable(new Scheduler(0), "0");
        }

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
