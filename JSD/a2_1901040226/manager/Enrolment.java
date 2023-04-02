package a2_1901040226.manager;

import java.util.Objects;

public class Enrolment implements Comparable<Enrolment> {
    private int id;
    private Student student;
    private Module module;
    private double internalMark;
    private double examinationMark;
    private String finalGrade;

    public Enrolment(Student student, Module module, double internalMark, double examinationMark) {
        this.student = student;
        this.module = module;
        this.internalMark = internalMark;
        this.examinationMark = examinationMark;
        this.finalGrade = finalGradeCalc(internalMark, examinationMark);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public double getInternalMark() {
        return internalMark;
    }

    public void setInternalMark(double internalMark) {
        this.internalMark = internalMark;
    }

    public double getExaminationMark() {
        return examinationMark;
    }

    public void setExaminationMark(double examinationMark) {
        this.examinationMark = examinationMark;
    }

    public String getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(String finalGrade) {
        this.finalGrade = finalGrade;
    }

    public String finalGradeCalc(double internalMark, double examinationMark) {
        String finalGradeChar = "";
        double finalGradeNum = 0.4 * internalMark + 0.6 * examinationMark;
        if (finalGradeNum >= 9) {
            finalGradeChar = "E";
        } else if (finalGradeNum >= 7) {
            finalGradeChar = "G";
        } else if (finalGradeNum >= 5) {
            finalGradeChar = "P";
        } else if (finalGradeNum < 5) {
            finalGradeChar = "F";
        }
        return finalGradeChar;
    }

    @Override
    public String toString() {
        return "Enrolment{" +
                "student=" + student.getName() +
                ", module=" + module.getName() +
                ", internalMark=" + internalMark +
                ", examinationMark=" + examinationMark +
                ", finalGrade='" + finalGrade + '\'' +
                '}';
    }

    public String reportEnrol() {
        return "Enrolment {" + "\n" + "\t" +
                "Student ID: " + student.getId() + "\n" + "\t" +
                "Student name: " + student.getName() + "\n" + "\t" +
                "Module code: " + module.getCode() + "\n" + "\t" +
                "Module: " + module.getName() + "\n" +
                '}';
    }

    public String reportEnrolAssessment() {
        return "Enrolment {" + "\n" + "\t" +
                "Student ID: " + student.getId() + "\n" + "\t" +
                "Student name: " + student.getName() + "\n" + "\t" +
                "Module code: " + module.getCode() + "\n" + "\t" +
                "Module: " + module.getName() + "\n" + "\t" +
                "Internal mark: " + internalMark + "\n" + "\t" +
                "Examination mark: " + examinationMark + "\n" + "\t" +
                "Final grade: " + finalGrade + "\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrolment)) return false;
        Enrolment enrolment = (Enrolment) o;
        return student.equals(enrolment.student) && module.equals(enrolment.module);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, module);
    }

    @Override
    public int compareTo(Enrolment o) {
        String studentCode = this.getStudent().getId();
        String objectStudentCode = o.getStudent().getId();
        int studentIndex = Integer.parseInt(studentCode.substring(1, 5));
        int objStudentIndex = Integer.parseInt(objectStudentCode.substring(1, 5));
        if(studentIndex > objStudentIndex){
            return -1;
        } else if (studentIndex < objStudentIndex){
            return 1;
        }
        return 0;
    }
}
