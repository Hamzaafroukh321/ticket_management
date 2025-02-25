package com.example.ticket_management.ui.components;



import com.example.ticket_management.dto.TicketDTO;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TicketTableModel extends AbstractTableModel {

    private final String[] columns = {"ID", "Title", "Priority", "Category", "Status", "Creation Date"};
    private final List<TicketDTO> tickets = new ArrayList<>();

    @Override
    public int getRowCount() {
        return tickets.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TicketDTO ticket = tickets.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> ticket.getId();
            case 1 -> ticket.getTitle();
            case 2 -> ticket.getPriority();
            case 3 -> ticket.getCategory();
            case 4 -> ticket.getStatus();
            case 5 -> ticket.getCreationDate();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void setTickets(List<TicketDTO> ticketList) {
        tickets.clear();
        tickets.addAll(ticketList);
        fireTableDataChanged();
    }

    public TicketDTO getTicketAt(int rowIndex) {
        return tickets.get(rowIndex);
    }
}
