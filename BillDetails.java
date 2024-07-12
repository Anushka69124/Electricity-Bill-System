package Electricity;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class BillDetails extends JFrame {

    JTable t1;
    DefaultTableModel model;
    String x[] = {"Meter Number", "Month", "Units", "Total Bill", "Status"};
    
    BillDetails(String meter) {
        super("Bill Details");
        setSize(700, 650);
        setLocation(600, 150);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        
        model = new DefaultTableModel(x, 0);
        t1 = new JTable(model);
        
        try {
            Conn c  = new Conn();
            String s1 = "select * from bill where meter = '" + meter + "'";
            ResultSet rs  = c.s.executeQuery(s1);
            
            // Populate the table model with data from the ResultSet
            populateTable(rs);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        JScrollPane sp = new JScrollPane(t1);
        sp.setBounds(0, 0, 700, 650);
        add(sp);
    }
    
    private void populateTable(ResultSet rs) throws SQLException {
        model.setRowCount(0); // Clear existing rows
        while (rs.next()) {
            String meter = rs.getString("meter");
            String month = rs.getString("month");
            String units = rs.getString("units");
            String totalBill = rs.getString("total_bill");
            String status = rs.getString("status");
            model.addRow(new Object[]{meter, month, units, totalBill, status});
        }
    }
    
    public static void main(String[] args) {
        new BillDetails("").setVisible(true);
    }
}

