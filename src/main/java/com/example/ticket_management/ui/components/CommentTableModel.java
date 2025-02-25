package com.example.ticket_management.ui.components;

import com.example.ticket_management.dto.CommentDTO;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CommentTableModel extends AbstractTableModel {
    private final String[] columnNames = { "ID", "Content", "Created Date" };
    private List<CommentDTO> comments = new ArrayList<>();

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return comments.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CommentDTO comment = comments.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> comment.getId();
            case 1 -> comment.getContent();
            case 2 -> comment.getCreatedDate();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
