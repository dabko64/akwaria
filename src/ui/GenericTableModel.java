package ui;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class GenericTableModel<T> extends AbstractTableModel {
    private List<T> data;
    private final List<ColumnDefinition<T>> columns;

    public GenericTableModel(List<T> data, List<ColumnDefinition<T>> columns) {
        this.data = new ArrayList<>(data);
        this.columns = columns;
    }

    public void setData(List<T> data) {
        this.data = new ArrayList<>(data);
        fireTableDataChanged();
    }

    public T getRow(int rowIndex) {
        return data.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(int column) {
        return columns.get(column).getName();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return columns.get(columnIndex).getValue(data.get(rowIndex));
    }
}