package a2_1901040226.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEnrolmentGUI {
    private JFrame gui;
    private JFrame parentGUI;
    private MainGUI mainGUI;
    private JComboBox boxStudent;
    private JComboBox boxModule;
    private JTextField txtInternalMark;
    private JTextField txtExaminationMark;
    private String[] students;
    private String[] modules;

    public AddEnrolmentGUI(JFrame parentGUI, MainGUI mainGUI, String[] students, String[] modules) {
        this.parentGUI = parentGUI;
        this.mainGUI = mainGUI;
        this.students = students;
        this.modules = modules;
        createGUI();
    }

    private void createGUI() {
        gui = new JFrame("Add Enrolment");
        gui.setSize(600, 600);

        // header
        JPanel header = new JPanel();
        JLabel title = new JLabel("Add Enrolment");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        header.add(title);
        gui.add(header, BorderLayout.NORTH);

        // middle
        JPanel content = new JPanel(new GridLayout(4, 2));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        content.add(new JLabel("Student"));
        boxStudent = new JComboBox<>();
        for (int i = 0; i < students.length; i++) {
            boxStudent.addItem(students[i]);
        }
        content.add(boxStudent);

        content.add(new JLabel("Module"));
        boxModule = new JComboBox<>();
        for (int i = 0; i < modules.length; i++) {
            boxModule.addItem(modules[i]);
        }
        content.add(boxModule);

        content.add(new JLabel("Internal mark"));
        txtInternalMark = new JTextField(15);
        content.add(txtInternalMark);

        content.add(new JLabel("Examination mark"));
        txtExaminationMark = new JTextField(15);
        content.add(txtExaminationMark);

        gui.add(content);

        // footer
        JPanel footer = new JPanel();
        JButton btnAdd = new JButton("Add enrolment");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentID = boxStudent.getSelectedItem().toString().substring(0, 5);
                String moduleCode = boxModule.getSelectedItem().toString().substring(0, 4);
                String internalMarkStr = txtInternalMark.getText();
                String examMarkStr = txtExaminationMark.getText();
                double internalMark = 0;
                double examinationMark = 0;
                if (internalMarkStr.trim().isEmpty() || examMarkStr.trim().isEmpty()) {
                   internalMark = 0;
                   examinationMark = 0;
                } else {
                    internalMark = Double.parseDouble(internalMarkStr);
                    examinationMark = Double.parseDouble(examMarkStr);
                }
                mainGUI.addEnrolmentMain(studentID, moduleCode, internalMark, examinationMark);
                disposeGUI();
            }
        });
        footer.add(btnAdd);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disposeGUI();
            }
        });
        footer.add(btnCancel);

        gui.add(footer, BorderLayout.SOUTH);

        int x = (int) parentGUI.getLocation().getX() + 100;
        int y = (int) parentGUI.getLocation().getY() + 100;
        gui.setLocation(x, y);

        gui.pack();
    }

    public void display() {
        gui.setVisible(true);
        System.out.println("Add enrolment GUI displayed...");
    }

    private void disposeGUI() {
        boxStudent.setSelectedItem(null);
        boxModule.setSelectedItem(null);
        txtInternalMark.setText("");
        txtExaminationMark.setText("");
        gui.dispose();
        System.out.println("Add enrolment GUI is being disposed...");
    }
}
