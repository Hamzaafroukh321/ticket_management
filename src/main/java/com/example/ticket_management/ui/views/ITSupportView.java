package com.example.ticket_management.ui.views;

import com.example.ticket_management.dto.TicketDTO;
import com.example.ticket_management.dto.CommentDTO;
import com.example.ticket_management.ui.components.TicketTableModel;
import com.example.ticket_management.ui.components.CommentTableModel;
import com.example.ticket_management.ui.controllers.ITSupportController;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ITSupportView extends JFrame {

    private final String username;
    private final ITSupportController itSupportController = new ITSupportController();

    private JTable ticketTable;
    private TicketTableModel ticketTableModel;

    // Nouveau tableau pour les commentaires
    private JTable commentTable;
    private CommentTableModel commentTableModel;

    private JComboBox<String> statusCombo;
    private JButton updateStatusButton;

    private JTextArea commentArea;
    private JButton addCommentButton;

    public ITSupportView(String username) {
        super("IT Support - " + username);
        this.username = username;
        initComponents();
        loadTickets();
    }

    private void initComponents() {
        // Layout sur 2 colonnes, 3 lignes
        setLayout(new MigLayout("wrap 2", "[grow 50][grow 50]", "[][][grow]"));

        JPanel actionPanel = new JPanel(new MigLayout("wrap 2", "[][grow]", "[][]"));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        actionPanel.add(new JLabel("Change Status:"));
        statusCombo = new JComboBox<>(new String[]{"NEW", "IN_PROGRESS", "RESOLVED"});
        actionPanel.add(statusCombo, "growx");

        updateStatusButton = new JButton("Update Status");
        updateStatusButton.addActionListener(e -> updateStatus());
        actionPanel.add(updateStatusButton, "span 2, align right");

        actionPanel.add(new JLabel("Add Comment:"), "newline");
        commentArea = new JTextArea(2, 20);
        actionPanel.add(new JScrollPane(commentArea), "grow, span 2");

        addCommentButton = new JButton("Add Comment");
        addCommentButton.addActionListener(e -> addComment());
        actionPanel.add(addCommentButton, "span 2, align right");

        // Le panel d'actions occupe les 2 colonnes
        add(actionPanel, "span 2, growx");

        // Panel (colonne de gauche) pour les tickets
        ticketTableModel = new TicketTableModel();
        ticketTable = new JTable(ticketTableModel);
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("All Tickets"));
        leftPanel.add(new JScrollPane(ticketTable), BorderLayout.CENTER);
        add(leftPanel, "grow, push");

        // Panel (colonne de droite) pour les commentaires
        commentTableModel = new CommentTableModel();
        commentTable = new JTable(commentTableModel);
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Comments"));
        rightPanel.add(new JScrollPane(commentTable), BorderLayout.CENTER);
        add(rightPanel, "grow, push, wrap");

        // Quand on sélectionne un ticket, on met à jour la table des commentaires
        ticketTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = ticketTable.getSelectedRow();
                if (selectedRow >= 0) {
                    TicketDTO selectedTicket = ticketTableModel.getTicketAt(selectedRow);
                    List<CommentDTO> comments = selectedTicket.getComments();
                    if (comments != null) {
                        commentTableModel.setComments(comments);
                    } else {
                        commentTableModel.setComments(List.of());
                    }
                } else {
                    commentTableModel.setComments(List.of());
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    private void loadTickets() {
        try {
            List<TicketDTO> tickets = itSupportController.getAllTickets();
            ticketTableModel.setTickets(tickets);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading tickets: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStatus() {
        int selectedRow = ticketTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a ticket", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        TicketDTO ticket = ticketTableModel.getTicketAt(selectedRow);
        String newStatus = (String) statusCombo.getSelectedItem();
        try {
            TicketDTO updated = itSupportController.updateTicketStatus(ticket.getId(), newStatus);
            JOptionPane.showMessageDialog(this, "Status updated to: " + updated.getStatus(),
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            loadTickets();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating status: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addComment() {
        int selectedRow = ticketTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a ticket", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        TicketDTO ticket = ticketTableModel.getTicketAt(selectedRow);
        String content = commentArea.getText().trim();
        try {
            itSupportController.addComment(ticket.getId(), username, content);
            JOptionPane.showMessageDialog(this, "Comment added!", "Info", JOptionPane.INFORMATION_MESSAGE);
            commentArea.setText("");

            // Recharger le ticket pour récupérer la liste à jour des commentaires
            TicketDTO updatedTicket = itSupportController.getTicketById(ticket.getId());
            if (updatedTicket.getComments() != null) {
                commentTableModel.setComments(updatedTicket.getComments());
            } else {
                commentTableModel.setComments(List.of());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding comment: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
