package a2_1901040226;

import a2_1901040226.GUI.MainGUI;
import a2_1901040226.manager.*;
import a2_1901040226.manager.Module;

import java.util.List;

public class CourseManProg {

    public static void main(String[] args) {
        DBConnector connector = new DBConnector();
        StudentManager studentManager = new StudentManager();
        ModuleManager moduleManager = new ModuleManager();
        EnrolmentManager enrolmentManager = new EnrolmentManager();

        List<Student> students = connector.createStudentList();
        studentManager.cloneToStudentManager(students);

        List<Module> modules = connector.createModuleList();
        moduleManager.cloneToModuleManager(modules);

        List<Enrolment> enrolments = connector.createEnrolmentList(students, modules);
        enrolmentManager.cloneToEnrolmentManager(enrolments);

        MainGUI mainGUI = new MainGUI(studentManager, moduleManager, enrolmentManager, connector);
        mainGUI.display();
    }
}
