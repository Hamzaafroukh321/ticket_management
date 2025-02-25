package com.example.ticket_management.ui.views;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class LoginView extends JFrame {

    private final JButton employeeButton;
    private final JButton itSupportButton;

    public LoginView() {
        super("Login - Ticket Management");
        setLayout(new MigLayout("wrap 1", "[center]", "[][]"));

        JLabel label = new JLabel("Select your role:");
        add(label);

        employeeButton = new JButton("Employee");
        itSupportButton = new JButton("IT Support");
        add(employeeButton, "growx");
        add(itSupportButton, "growx");

        employeeButton.addActionListener(e -> {
            new EmployeeView("employeeUser").setVisible(true);
            dispose();
        });
        itSupportButton.addActionListener(e -> {
            new ITSupportView("itUser").setVisible(true);
            dispose();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
