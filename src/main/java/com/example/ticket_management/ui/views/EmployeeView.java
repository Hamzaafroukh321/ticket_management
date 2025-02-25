package com.example.ticket_management.ui.views;

import com.example.ticket_management.dto.TicketDTO;
import com.example.ticket_management.entity.TicketCategory;
import com.example.ticket_management.entity.TicketPriority;
import com.example.ticket_management.entity.TicketStatus;
import com.example.ticket_management.ui.components.TicketTableModel;

import com.example.ticket_management.ui.controllers.EmployeeController;
import com.example.ticket_management.ui.utils.ApiClient;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class EmployeeView extends JFrame {

    private final String username;
    private final EmployeeController employeeController = new EmployeeController();

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<String> priorityCombo;
    private JComboBox<String> categoryCombo;
    private JButton createButton;

    private JTable ticketTable;
    private TicketTableModel ticketTableModel;

    private JTextField searchIdField;
    private JComboBox<String> statusCombo;
    private JButton searchButton;

    public EmployeeView(String username) {
        super("Employee - " + username);
        this.username = username;
        initComponents();
        loadTickets();
    }

    private void initComponents() {
        setLayout(new MigLayout("wrap 1", "[grow]", "[][][grow]"));

        // Panel de création de ticket
        JPanel createPanel = new JPanel(new MigLayout("wrap 2", "[][grow]", "[][][][]"));
        createPanel.setBorder(BorderFactory.createTitledBorder("Create Ticket"));

        createPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        createPanel.add(titleField, "growx");

        createPanel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea(3, 20);
        createPanel.add(new JScrollPane(descriptionArea), "grow");

        createPanel.add(new JLabel("Priority:"));
        priorityCombo = new JComboBox<>(new String[]{"LOW", "MEDIUM", "HIGH"});
        createPanel.add(priorityCombo, "growx");

        createPanel.add(new JLabel("Category:"));
        categoryCombo = new JComboBox<>(new String[]{"NETWORK", "HARDWARE", "SOFTWARE", "OTHER"});
        createPanel.add(categoryCombo, "growx");

        createButton = new JButton("Create");
        createButton.addActionListener(e -> createTicket());
        createPanel.add(createButton, "span 2, center");

        add(createPanel, "growx");

        // Panel de recherche
        JPanel searchPanel = new JPanel(new MigLayout("wrap 4", "[][][grow][]", "[]"));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Tickets"));

        searchPanel.add(new JLabel("Ticket ID:"));
        searchIdField = new JTextField(5);
        searchPanel.add(searchIdField);

        searchPanel.add(new JLabel("Status:"));
        statusCombo = new JComboBox<>(new String[]{"", "NEW", "IN_PROGRESS", "RESOLVED"});
        searchPanel.add(statusCombo);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchTickets());
        searchPanel.add(searchButton, "span 4, align right");

        add(searchPanel, "growx");

        // Table des tickets
        ticketTableModel = new TicketTableModel();
        ticketTable = new JTable(ticketTableModel);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("My Tickets"));
        tablePanel.add(new JScrollPane(ticketTable), BorderLayout.CENTER);
        add(tablePanel, "grow, push");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void createTicket() {
        try {
            TicketDTO ticket = new TicketDTO();
            ticket.setTitle(titleField.getText());
            ticket.setDescription(descriptionArea.getText());
            ticket.setPriority(TicketPriority.valueOf(((String) priorityCombo.getSelectedItem()).toUpperCase()));
            ticket.setCategory(TicketCategory.valueOf(((String) categoryCombo.getSelectedItem()).toUpperCase()));
            ticket.setStatus(TicketStatus.valueOf("NEW"));
            ticket.setCreationDate(LocalDateTime.now());
            TicketDTO created = employeeController.createTicket(ticket.getTitle(), ticket.getDescription(),
                    ticket.getPriority(), ticket.getCategory());
            JOptionPane.showMessageDialog(this, "Ticket created with ID: " + created.getId(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            loadTickets();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error creating ticket: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTickets() {
        try {
            List<TicketDTO> tickets = employeeController.getMyTickets(username);
            ticketTableModel.setTickets(tickets);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading tickets: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchTickets() {
        String id = searchIdField.getText().trim();
        String status = (String) statusCombo.getSelectedItem();
        try {
            List<TicketDTO> tickets = employeeController.searchTickets(id, status);
            ticketTableModel.setTickets(tickets);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error searching tickets: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
