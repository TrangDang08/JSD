package a2_1901040226.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AssessmentReportGUI {
    private JFrame gui;
    private JFrame parentGUI;
    private Object[][] data = {{"", "", "", "", "", ""}};

    public AssessmentReportGUI(JFrame parentGUI, Object[][] data) {
        this.parentGUI = parentGUI;
        this.data = data;
        createGUI();
    }

    private void createGUI() {
        gui = new JFrame("Assessment Report");

        // header
        JPanel header = new JPanel();
        JLabel title = new JLabel("Assessment Report");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        header.add(title);
        gui.add(header, BorderLayout.NORTH);

        // middle
        JPanel content = new JPanel();
        String[] headers = {"ID", "Student ID", "Module code", "Internal mark", "Examination mark", "Final Grade"};
        DefaultTableModel tableModel = new DefaultTableModel(data, headers);
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
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
        System.out.println("Assessment report GUI displayed...");
    }
}
