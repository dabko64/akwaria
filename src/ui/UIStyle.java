package ui;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class UIStyle {
    public static final Color TURQUOISE = new Color(251, 236, 125);
    public static final Color DARK_GRAY = new Color(70, 99, 161);
    public static final Color CREAM = new Color(221, 217, 159);
    public static final Color LIGHT_MINT = new Color(149, 206, 160);
    public static final Color CORAL = new Color(236, 129, 176);

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 13);
    public static void styleButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(DARK_GRAY, 1));
    }

    public static void styleLabel(JLabel label) {
        label.setFont(TITLE_FONT);
        label.setForeground(DARK_GRAY);
    }

    public static void styleTextField(JTextField field) {
        field.setBackground(new Color(255, 255, 255, 220));
        field.setForeground(DARK_GRAY);
        field.setFont(NORMAL_FONT);
    }

    public static void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setBackground(CREAM);
        comboBox.setForeground(DARK_GRAY);
        comboBox.setFont(NORMAL_FONT);
    }

    public static void styleTable(JTable table) {
        table.setFont(NORMAL_FONT);
        table.setRowHeight(28);
        table.setBackground(new Color(255, 255, 255, 150));
        table.setForeground(DARK_GRAY);
        table.setSelectionBackground(new Color(148, 197, 198, 180));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(255, 255, 255, 80));
        table.setOpaque(false);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(164, 94, 106, 255));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    public static JScrollPane wrapTransparent(JComponent component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        return scrollPane;
    }

    public static JPanel createTransparentPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);
        return panel;
    }

    public static RoundedPanel createCardPanel(LayoutManager layout) {
        RoundedPanel panel = new RoundedPanel(
                30,
                new Color(255, 255, 255, 170),
                new Color(80, 80, 80, 50),
                8
        );
        panel.setLayout(layout);
        return panel;
    }

    public static void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
    }
}