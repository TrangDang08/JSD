package a2_1901040226;

import a2_1901040226.manager.*;
import a2_1901040226.manager.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnector {
    public Connection connection;
    private StudentManager studentManager = new StudentManager();
    private ModuleManager moduleManager = new ModuleManager();
    private EnrolmentManager enrolmentManager = new EnrolmentManager();

    public DBConnector() {
        connectDB();
    }

    public void connectDB() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src\\a2_1901040226\\database.sqlite3");
            System.out.println("Connected to DB");
        } catch (SQLException e) {
            System.err.println("Unable to connect DB");
            System.exit(1);
        }
    }

    public List<Student> createStudentList() {
        List<Student> studentList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String dob = rs.getString("dob");
                String address = rs.getString("address");
                String email = rs.getString("email");
                Student student = new Student(name, dob, address, email);
                student.setId(id);
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Cannot fetch data from DB");
        }
        return studentList;
    }

    public List<Module> createModuleList() {
        List<Module> moduleList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM modules");
            while (rs.next()) {
                String code = rs.getString("code");
                String name = rs.getString("name");
                int semester = rs.getInt("semester");
                int credit = rs.getInt("credit");
                String type = rs.getString("type");
                String department = rs.getString("department");
                if (type.equals("Compulsory")) {
                    CompulsoryModule compulsoryModule = new CompulsoryModule(name, semester, credit);
                    compulsoryModule.setCode(code);
                    moduleList.add(compulsoryModule);
                } else if (type.equals("Elective")) {
                    ElectiveModule electiveModule = new ElectiveModule(name, semester, credit, department);
                    electiveModule.setCode(code);
                    moduleList.add(electiveModule);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Cannot fetch data from DB");
        }
        return moduleList;
    }

    public List<Enrolment> createEnrolmentList(List<Student> studentList, List<Module> moduleList) {
        List<Enrolment> enrolmentList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM enrolments");
            while (rs.next()) {
                int id = rs.getInt("id");
                String studentID = rs.getString("student_id");
                String moduleCode = rs.getString("module_code");
                Double internalMark = Double.valueOf(rs.getFloat("internal_mark"));
                Double examinationMark = Double.valueOf(rs.getFloat("examination_mark"));
                String finalGrade = rs.getString("final_grade");
                Student student = null;
                Module module = null;
                for (int i = 0; i < studentList.size(); i++) {
                    Student temp = studentList.get(i);
                    if (temp.getId().equals(studentID)) {
                        student = temp;
                    }
                }
                for (int i = 0; i < moduleList.size(); i++) {
                    Module temp = moduleList.get(i);
                    if (temp.getCode().equals(moduleCode)) {
                        module = temp;
                    }
                }
                Enrolment enrolment = new Enrolment(student, module, internalMark, examinationMark);
                enrolment.setId(id);
                enrolment.setFinalGrade(finalGrade);
                enrolmentList.add(enrolment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Cannot fetch data from DB");
        }
        return enrolmentList;
    }

    public void addStudentToDB(Student student) {
        String id = student.getId();
        String name = student.getName();
        String dob = student.getDob();
        String address = student.getAddress();
        String email = student.getEmail();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO students (id, name, dob, address, email) VALUES ("
                    + "'" + id + "', "
                    + "'" + name + "', "
                    + "'" + dob + "', "
                    + "'" + address + "', "
                    + "'" + email + "'"
                    + ")");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error add student to db");
        }
    }

    public void addModuleToDB(Module module) {
        String code = module.getCode();
        String name = module.getName();
        int semester = module.getSemester();
        int credit = module.getCredits();
        String moduleClass = module.getClass().getSimpleName();
        String type = "";
        String department = "";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            if (moduleClass.equals("CompulsoryModule")) {
                type = "Compulsory";
                statement.executeUpdate("INSERT INTO modules (code, name, semester, credit, type, department) VALUES ("
                        + "'" + code + "', "
                        + "'" + name + "', "
                        + "'" + semester + "', "
                        + "'" + credit + "', "
                        + "'" + type + "', "
                        + "'" + department + "'"
                        + ")");
            } else if (moduleClass.equals("ElectiveModule")) {
                type = "Elective";
                ElectiveModule electiveModule = (ElectiveModule) module;
                department = electiveModule.getDepartment();
                statement.executeUpdate("INSERT INTO modules (code, name, semester, credit, type, department) VALUES ("
                        + "'" + code + "', "
                        + "'" + name + "', "
                        + "'" + semester + "', "
                        + "'" + credit + "', "
                        + "'" + type + "', "
                        + "'" + department + "'"
                        + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error add module to db");
        }
    }

    public void addEnrolmentToDB(Enrolment enrolment) {
        int id = enrolment.getId();
        String studentID = enrolment.getStudent().getId();
        String moduleCode = enrolment.getModule().getCode();
        Double internalMark = enrolment.getInternalMark();
        Double examinationMark = enrolment.getExaminationMark();
        String finalGrade = enrolment.getFinalGrade();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO enrolments (id, student_id, module_code, internal_mark, examination_mark, final_grade) VALUES ("
                    + "'" + id + "', "
                    + "'" + studentID + "', "
                    + "'" + moduleCode + "', "
                    + "'" + internalMark.floatValue() + "', "
                    + "'" + examinationMark.floatValue() + "', "
                    + "'" + finalGrade + "'"
                    + ")");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error add enrolment to db");
        }
    }
}
