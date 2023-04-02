package a2_1901040226.GUI;

import a2_1901040226.DBConnector;
import a2_1901040226.manager.*;
import a2_1901040226.manager.Module;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI implements ActionListener{
    private JFrame gui;
    private ClosingEventListener closingEventListener = new ClosingEventListener();
    private ListStudentGUI listStudentGUI;
    private ListModuleGUI listModuleGUI;
    private AddEnrolmentGUI addEnrolmentGUI;
    private AddStudentGUI addStudentGUI;
    private AddModuleGUI addModuleGUI;
    private InitialReportGUI initialReportGUI;
    private AssessmentReportGUI assessmentReportGUI;
    private String[] students = {""};
    private String[] modules = {""};
    private EnrolmentManager enrolmentManager;
    private StudentManager studentManager;
    private ModuleManager moduleManager;
    private DBConnector dbConnector;

    public MainGUI(StudentManager studentManager, ModuleManager moduleManager, EnrolmentManager enrolmentManager, DBConnector dbConnector) {
        this.studentManager = studentManager;
        this.moduleManager = moduleManager;
        this.enrolmentManager = enrolmentManager;
        this.dbConnector = dbConnector;
        createDataForm();
        createGUI();
    }

    public void createDataForm() {
        students = studentManager.transformStudentToArray(students);
        modules = moduleManager.transformModuleToArray(modules);
    }

    private void createGUI() {
        gui = new JFrame("Course Management");

        gui.addWindowListener(closingEventListener);

        // create menu
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(closingEventListener);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        JMenu studentMenu = new JMenu("Student");
        JMenuItem newStudentMenuItem = new JMenuItem("New student");
        newStudentMenuItem.addActionListener(this);
        studentMenu.add(newStudentMenuItem);
        JMenuItem listStudentMenuItem = new JMenuItem("List students");
        listStudentMenuItem.addActionListener(this);
        studentMenu.add(listStudentMenuItem);
        menuBar.add(studentMenu);

        JMenu moduleMenu = new JMenu("Module");
        JMenuItem newModuleMenuItem = new JMenuItem("New module");
        newModuleMenuItem.addActionListener(this);
        moduleMenu.add(newModuleMenuItem);
        JMenuItem listModuleMenuItem = new JMenuItem("List modules");
        listModuleMenuItem.addActionListener(this);
        moduleMenu.add(listModuleMenuItem);
        menuBar.add(moduleMenu);

        JMenu enrolmentMenu = new JMenu("Enrolment");
        JMenuItem newEnrolmentMenuItem = new JMenuItem("New enrolment");
        newEnrolmentMenuItem.addActionListener(this);
        enrolmentMenu.add(newEnrolmentMenuItem);
        JMenuItem initialReportMenuItem = new JMenuItem("Initial report");
        initialReportMenuItem.addActionListener(this);
        enrolmentMenu.add(initialReportMenuItem);
        JMenuItem assessmentReportMenuItem = new JMenuItem("Assessment report");
        assessmentReportMenuItem.addActionListener(this);
        enrolmentMenu.add(assessmentReportMenuItem);
        menuBar.add(enrolmentMenu);

        gui.setJMenuBar(menuBar);

        // background
        JPanel background = new JPanel();
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.setBackground(Color.PINK);
        background.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));

        JLabel intro = new JLabel("Welcome to Course Management Application");
        intro.setFont(intro.getFont().deriveFont(Font.BOLD, 20));
        JLabel name = new JLabel("Name: Dang Thu Trang");
        name.setFont(intro.getFont().deriveFont(Font.BOLD, 16));
        JLabel myID = new JLabel("ID: 1901040226");
        myID.setFont(intro.getFont().deriveFont(Font.BOLD, 16));

        JLabel[] setColorArray = {intro, name, myID};

        for (int i = 0; i < setColorArray.length; i++) {
            setColorArray[i].setHorizontalAlignment(SwingConstants.CENTER);
            setColorArray[i].setOpaque(true);
            setColorArray[i].setForeground(Color.YELLOW);
            setColorArray[i].setBackground(Color.PINK);
            background.add(setColorArray[i]);
        }

        gui.add(background);

        gui.pack();
        gui.setSize(600, 300);
    }

    public void display() {
        gui.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("List students")) {
            Object[][] data = {{"", "", "", "", ""}};
            data = studentManager.transformStudentToArrayObject().toArray(data);
            listStudentGUI = new ListStudentGUI(gui, data);
            listStudentGUI.display();
        } else if (command.equals("List modules")) {
            Object[][] data = {{"", "", "", "", "", ""}};
            data = moduleManager.transformModuleToArrayObject().toArray(data);
            listModuleGUI = new ListModuleGUI(gui, data);
            listModuleGUI.display();
        } else if (command.equals("New enrolment")) {
            addEnrolmentGUI = new AddEnrolmentGUI(gui, this, students, modules);
            addEnrolmentGUI.display();
        } else if (command.equals("New student")) {
            addStudentGUI = new AddStudentGUI(gui, this);
            addStudentGUI.display();
        } else if (command.equals("New module")) {
            addModuleGUI = new AddModuleGUI(gui, this);
            addModuleGUI.display();
        } else if (command.equals("Initial report")) {
            Object[][] data = {{"", "", "", "", ""}};
            data = enrolmentManager.report().toArray(data);
            initialReportGUI = new InitialReportGUI(gui, data);
            initialReportGUI.display();
        } else if (command.equals("Assessment report")) {
            Object[][] data = {{"", "", "", "", "", ""}};
            data = enrolmentManager.reportAssessment().toArray(data);
            assessmentReportGUI = new AssessmentReportGUI(gui, data);
            assessmentReportGUI.display();
        }
    }

    public void addStudent(Student student) {
        studentManager.setStudentID(student);
        boolean check = studentManager.addStudent(student);
        if (check == true) {
            dbConnector.addStudentToDB(student);
        }

        // update drop down
        String[] temp = {""};
        students = temp;
        students = studentManager.transformStudentToArray(students);
    }

    public void addModule(Module module) {
        moduleManager.setModuleCode(module);
        boolean check = moduleManager.addModule(module);
        if (check == true) {
            dbConnector.addModuleToDB(module);
        }
        String[] temp = {""};
        modules = temp;
        modules = moduleManager.transformModuleToArray(modules);
    }

    public void addEnrolmentMain(String studentID, String moduleCode, double internalMark, double examinationMark) {
        Student student = null;
        Module module = null;
        for (int i = 0; i < studentManager.getAllStudent().size(); i++) {
            Student temp = studentManager.getAllStudent().get(i);
            if (temp.getId().equals(studentID)) {
                student = temp;
            }
        }
        for (int i = 0; i < moduleManager.getAllModule().size(); i++) {
            Module temp = moduleManager.getAllModule().get(i);
            if (temp.getCode().equals(moduleCode)) {
                module = temp;
            }
        }
        Enrolment enrolment = new Enrolment(student, module, internalMark, examinationMark);
        enrolmentManager.setEnrolmentID(enrolment);
        boolean check = enrolmentManager.addEnrolment(enrolment);
        if (check == true) {
            dbConnector.addEnrolmentToDB(enrolment);
        }
    }
}
