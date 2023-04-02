package a2_1901040226.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentManager {
    private List<Student> studentList = new ArrayList<>();

    public boolean addStudent(Student student) {
        studentList.add(student);

        if (studentList.size() > 1) {
            for (int i = 0; i < studentList.size() - 1; i++) {
                if (studentList.get(i).equals(student)) {
                    studentList.remove(studentList.size() - 1);
                    System.out.println("The student has already existed.");
                    return false;
                }
            }
        }

        return true;
    }

    public List<Student> getAllStudent() {
        return studentList;
    }

    public void cloneToStudentManager(List<Student> students) {
        for (int i = 0; i < students.size(); i++) {
            addStudent(students.get(i));
        }
    }

    public void setStudentID(Student student) {
        IdGenerator generator = new IdGenerator();
        if (studentList.size() == 0) {
            Calendar instance = Calendar.getInstance();
            int year = instance.get(Calendar.YEAR);
            generator.init("S", "", year);
            student.setId(generator.generate());
        } else {
            Student last = studentList.get(studentList.size() - 1);
            int id = Integer.parseInt(last.getId().substring(1)) + 1;
            generator.init("S", "", id);
            student.setId(generator.generate());
        }
    }

    public ArrayList<Object[]> transformStudentToArrayObject() {
        ArrayList<Object[]> rows = new ArrayList<>();
        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            rows.add(new Object[]{
                    student.getId(),
                    student.getName(),
                    student.getDob(),
                    student.getAddress(),
                    student.getEmail()
            });
        }
        return rows;
    }

    public String[] transformStudentToArray(String[] students) {
        ArrayList<String> arrayListStudent = new ArrayList<>();
        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            String content = student.getId() + " - " + student.getName();
            arrayListStudent.add(content);
        }
        students = arrayListStudent.toArray(students);
        return students;
    }
}
