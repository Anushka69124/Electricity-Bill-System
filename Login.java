package Electricity;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4;
    JTextField tf1;
    JPasswordField pf2;
    JButton b1, b2, b3;
    JPanel p1, p2, p3, p4;
    Choice c1;

    Login() {
        super("Login Page");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        l1 = new JLabel("Username");
        l1.setBounds(300, 20, 100, 20);
        add(l1);

        l2 = new JLabel("Password");
        l2.setBounds(300, 60, 100, 20);
        add(l2);

        tf1 = new JTextField(15);
        tf1.setBounds(400, 20, 150, 20);
        add(tf1);

        pf2 = new JPasswordField(15);
        pf2.setBounds(400, 60, 150, 20);
        add(pf2);

        l4 = new JLabel("Logging in as");
        l4.setBounds(300, 100, 100, 20);
        add(l4);

        c1 = new Choice();
        c1.add("Admin");
        c1.add("Customer");
        c1.setBounds(400, 100, 150, 20);
        add(c1);

        try {
            ImageIcon ic1 = new ImageIcon(ClassLoader.getSystemResource("icon/login.png"));
            if (ic1.getImageLoadStatus() == MediaTracker.ERRORED) throw new Exception("Image not found");
            Image i1 = ic1.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            b1 = new JButton("Login", new ImageIcon(i1));
        } catch (Exception e) {
            System.out.println("Error loading login.png: " + e);
            b1 = new JButton("Login");
        }
        b1.setBounds(330, 160, 100, 20);
        add(b1);

        try {
            ImageIcon ic2 = new ImageIcon(ClassLoader.getSystemResource("icon/cancel.jpg"));
            if (ic2.getImageLoadStatus() == MediaTracker.ERRORED) throw new Exception("Image not found");
            Image i2 = ic2.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            b2 = new JButton("Cancel", new ImageIcon(i2));
        } catch (Exception e) {
            System.out.println("Error loading cancel.jpg: " + e);
            b2 = new JButton("Cancel");
        }
        b2.setBounds(450, 160, 100, 20);
        add(b2);

        try {
            ImageIcon ic4 = new ImageIcon(ClassLoader.getSystemResource("icon/signup.png"));
            if (ic4.getImageLoadStatus() == MediaTracker.ERRORED) throw new Exception("Image not found");
            Image i4 = ic4.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            b3 = new JButton("Signup", new ImageIcon(i4));
        } catch (Exception e) {
            System.out.println("Error loading signup.png: " + e);
            b3 = new JButton("Signup");
        }
        b3.setBounds(380, 200, 130, 20);
        add(b3);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        try {
            ImageIcon ic3 = new ImageIcon(ClassLoader.getSystemResource("icon/second.jpg"));
            if (ic3.getImageLoadStatus() == MediaTracker.ERRORED) throw new Exception("Image not found");
            Image i3 = ic3.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
            ImageIcon icc3 = new ImageIcon(i3);
            l3 = new JLabel(icc3);
        } catch (Exception e) {
            System.out.println("Error loading second.jpg: " + e);
            l3 = new JLabel("Image not found");
        }
        l3.setBounds(0, 0, 250, 250);
        add(l3);

        setSize(640, 300);
        setLocation(600, 300);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            try {
                Conn c = new Conn();
                String a = tf1.getText();
                String b = pf2.getText();
                String user = c1.getSelectedItem();
                // Note: This approach is vulnerable to SQL Injection. Make sure to sanitize inputs properly.
                String q = "SELECT * FROM login WHERE username = '" + a + "' AND password = '" + b + "' AND user = '" + user + "'";
                ResultSet rs = c.s.executeQuery(q);
                if (rs.next()) {
                    String meter = rs.getString("meter_no");
                    new Project(meter, user).setVisible(true);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login");
                    tf1.setText("");
                    pf2.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error: " + e);
            }
        } else if (ae.getSource() == b2) {
            this.setVisible(false);
        } else if (ae.getSource() == b3) {
            this.setVisible(false);
            new Signup().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}
