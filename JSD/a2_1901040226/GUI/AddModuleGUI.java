package a2_1901040226.GUI;

import a2_1901040226.manager.CompulsoryModule;
import a2_1901040226.manager.ElectiveModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddModuleGUI {
    private String[] type = {"Compulsory", "Elective"};
    private JFrame gui;
    private JFrame parentGUI;
    private MainGUI mainGUI;
    private JTextField txtName;
    private JTextField txtSemester;
    private JTextField txtCredit;
    private JComboBox boxType;
    private JTextField txtDepartment;

    public AddModuleGUI(JFrame parentGUI, MainGUI mainGUI) {
        this.parentGUI = parentGUI;
        this.mainGUI = mainGUI;
        createGUI();
    }

    // handle combo box

    private void createGUI() {
        gui = new JFrame("Add Module");

        // header
        JPanel header = new JPanel();
        JLabel title = new JLabel("Add Module");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        header.add(title);
        gui.add(header, BorderLayout.NORTH);

        // middle
        JPanel content = new JPanel(new GridLayout(5, 2));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        content.add(new JLabel("Name"));
        txtName = new JTextField(15);
        content.add(txtName);

        content.add(new JLabel("Semester"));
        txtSemester = new JTextField();
        content.add(txtSemester);

        content.add(new JLabel("Credit"));
        txtCredit = new JTextField(15);
        content.add(txtCredit);

        content.add(new JLabel("Type"));
        boxType = new JComboBox<>();
        for (int i = 0; i < type.length; i++) {
            boxType.addItem(type[i]);
        }
        content.add(boxType);

        content.add(new JLabel("Department"));
        txtDepartment = new JTextField(15);
        txtDepartment.setEnabled(false);
        txtDepartment.setBackground(Color.GRAY);
        txtDepartment.setText("");
        content.add(txtDepartment);

        boxType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(((JComboBox) e.getSource()).getSelectedItem());
                if (boxType.getSelectedItem() != null) {
                    if (((JComboBox) e.getSource()).getSelectedItem().equals("Elective")) {
                        txtDepartment.setEnabled(true);
                        txtDepartment.setBackground(Color.WHITE);
                    } else if (((JComboBox) e.getSource()).getSelectedItem().equals("Compulsory")) {
                        txtDepartment.setEnabled(false);
                        txtDepartment.setBackground(Color.GRAY);
                        txtDepartment.setText("");
                    }
                }
            }
        });

        gui.add(content);

        // footer
        JPanel footer = new JPanel();
        JButton btnAdd = new JButton("Add module");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                int semester = Integer.parseInt(txtSemester.getText());
                int credit = Integer.parseInt(txtCredit.getText());
                String type = boxType.getSelectedItem().toString();
                String department = txtDepartment.getText();
                if (name.trim().isEmpty()) {
                    gui.dispose();
                    // warning window
                    JOptionPane.showMessageDialog(null, "Cannot add student due to left empty field", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (type.equals("Compulsory")) {
                    CompulsoryModule compulsoryModule = new CompulsoryModule(name, semester, credit);
                    mainGUI.addModule(compulsoryModule);
                    disposeGUI();
                } else if (type.equals("Elective")) {
                    ElectiveModule electiveModule = new ElectiveModule(name, semester, credit, department);
                    mainGUI.addModule(electiveModule);
                    disposeGUI();
                }
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
        System.out.println("Add module GUI displayed...");
    }

    private void disposeGUI() {
        txtName.setText("");
        txtSemester.setText("");
        txtCredit.setText("");
        boxType.setSelectedItem(null);
        txtDepartment.setText("");
        gui.dispose();
        System.out.println("Add module GUI is being disposed...");
    }
}
