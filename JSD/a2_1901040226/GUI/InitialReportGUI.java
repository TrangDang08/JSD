package a2_1901040226.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InitialReportGUI {
    private JFrame gui;
    private JFrame parentGUI;
    private Object[][] data = {{"", "", "", "", ""}};

    public InitialReportGUI(JFrame parentGUI, Object[][] data) {
        this.parentGUI = parentGUI;
        this.data = data;
        createGUI();
    }

    private void createGUI() {
        gui = new JFrame("Initial Report");

        // header
        JPanel header = new JPanel();
        JLabel title = new JLabel("Initial Report");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        header.add(title);
        gui.add(header, BorderLayout.NORTH);

        // middle
        JPanel content = new JPanel();
        String[] headers = {"ID", "Student ID", "Student name", "Module code", "Module name"};
        DefaultTableModel tableModel = new DefaultTableModel(data, headers);
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        JScrollPane scrollPane = new JScrollPane(table);
        content.add(scrollPane);
        gui.add(content);

        int x = (int) parentGUI.getLocation().getX() + 100;
        int y = (int) parentGUI.getLocation().getY() + 100;
        gui.setLocation(x, y);

        gui.pack();
    }

    public void display() {
        gui.setVisible(true);
        System.out.println("Initial report GUI displayed...");
    }
}
