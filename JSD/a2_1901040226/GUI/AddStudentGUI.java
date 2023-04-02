package a2_1901040226.GUI;

import a2_1901040226.manager.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AddStudentGUI {
    private JFrame gui;
    private JFrame parentGUI;
    private MainGUI mainGUI;
    private JTextField txtName;
    private JTextField txtDOB;
    private JTextField txtAddress;
    private JTextField txtEmail;

    public AddStudentGUI(JFrame parentGUI, MainGUI mainGUI) {
        this.parentGUI = parentGUI;
        this.mainGUI = mainGUI;
        createGUI();
    }

    private void createGUI() {
        gui = new JFrame("Add student");

        // header
        JPanel header = new JPanel();
        JLabel title = new JLabel("Add Student");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        header.add(title);
        gui.add(header, BorderLayout.NORTH);

        // middle
        JPanel content = new JPanel(new GridLayout(4, 2));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        content.add(new JLabel("Name"));
        txtName = new JTextField(15);
        content.add(txtName);

        content.add(new JLabel("Date of birth"));
        txtDOB = new JTextField("DD/MM/YYYY", 15);
        txtDOB.setForeground(Color.GRAY);
        txtDOB.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtDOB.getText().equals("DD/MM/YYYY")) {
                    txtDOB.setText("");
                    txtDOB.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtDOB.getText().trim().isEmpty()) {
                    txtDOB.setText("DD/MM/YYYY");
                    txtDOB.setForeground(Color.GRAY);
                }
            }
        });
        content.add(txtDOB);

        content.add(new JLabel("Address"));
        txtAddress = new JTextField(15);
        content.add(txtAddress);

        content.add(new JLabel("Email"));
        txtEmail = new JTextField(15);
        content.add(txtEmail);

        gui.add(content);

        // footer
        JPanel footer = new JPanel();

        JButton btnAdd = new JButton("Add student");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                String dob = txtDOB.getText();
                String address = txtAddress.getText();
                String email = txtEmail.getText();
                if (name.trim().isEmpty() || dob.trim().isEmpty()) {
                    gui.dispose();
                    // warning window
                    JOptionPane.showMessageDialog(null, "Cannot add student due to left empty field", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Student student = new Student(name, dob, address, email);
                    mainGUI.addStudent(student);
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
        System.out.println("Add student GUI displayed...");
    }

    private void disposeGUI() {
        txtName.setText("");
        txtDOB.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        gui.dispose();
        System.out.println("Add student GUI is being disposed...");
    }
}
