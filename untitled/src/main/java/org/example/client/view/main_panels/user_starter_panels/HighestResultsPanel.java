package org.example.client.view.main_panels.user_starter_panels;

import org.example.local_data.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class HighestResultsPanel extends JPanel implements ActionListener {
    private final String[] columnNames = {"Username" , "Max Score"};
    private final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    private JTable table;
    private User user;
    private JButton back;
    private LinkedList<User> sort;
    public HighestResultsPanel(User user){
        this.user = user;
//        for(int i = 0; i < User.getUsers().size(); i++){
//            User.getUsers().get(i).highestScore();
//        }
        sort = User.sortResultUsers();

        this.setSize(1080, 771);
        this.setLayout(new BorderLayout());
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        back = new JButton("back");
        this.add(back , BorderLayout.AFTER_LAST_LINE);
        back.setBounds(0,0,100, 50);
        back.setFocusable(false);
        back.addActionListener(this);

        table = new JTable(tableModel);
        table.setEnabled(false);
        JScrollPane p = new JScrollPane();
        p.setViewportView(table);
        retrieveData();
        this.add(p, BorderLayout.NORTH);
    }
    private void retrieveData() {
        for (int i = 0; i < sort.size(); i++){
            Object[] rowData = new Object[columnNames.length];
            //
            rowData[0] = sort.get(i).getUsername();
            rowData[1] = sort.get(i).getMaxScore();
            //
            tableModel.addRow(rowData);
        }
        for(int i = 0; i < 26; i ++){
            Object[] rowData = new Object[columnNames.length];
            rowData[0] = "";
            rowData[1] = "";
            tableModel.addRow(rowData);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(back)){
          //  MainFrame.getInstance().setContentPane(new UserStarterPanel(user));
        }
    }
}
