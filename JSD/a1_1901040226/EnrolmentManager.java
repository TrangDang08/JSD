package a1_1901040226;

import java.util.ArrayList;
import java.util.List;

public class EnrolmentManager {
    private List<Enrolment> enrolments = new ArrayList<>();

    public void addEnrolment(Enrolment enrolment) {
        enrolments.add(enrolment);

        if (enrolments.size() > 1) {
            for (int i = 0; i < enrolments.size() - 1; i++) {
                if (enrolments.get(i).equals(enrolment)) {
                    enrolments.remove(enrolments.size() - 1);
                    System.out.println("The enrolment has already existed.");
                    break;
                }
            }
        }
    }

    public Enrolment getEnrolment(Student student, Module module) {
        if (enrolments != null) {
            for (int i = 0; i < enrolments.size(); i++) {
                Enrolment enrolment = enrolments.get(i);
                Student st = enrolment.getStudent();
                Module md = enrolment.getModule();

                if (st.equals(student) && md.equals(module)) {
                    return enrolment;
                }
            }
        }

        return null;
    }

    public void setMark(Student student, Module module, double internal, double examination) {
        if (enrolments != null) {
            boolean check = false;
            Enrolment enrolment = getEnrolment(student, module);
            if (enrolment != null) {
                enrolment.setInternalMark(internal);
                enrolment.setExaminationMark(examination);
                enrolment.setFinalGrade(enrolment.finalGradeCalc(internal, examination));
                check = true;
            }
            if (check == false) {
                System.out.println("There is no such enrolment of " +
                        student.getName() + " for " + module.getName());
            }
        } else {
            System.out.println("There is no enrolment available.");
        }
    }

    public String report() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---Report of enrolments---" + "\n");
        for (int i = 0; i < enrolments.size(); i++) {
            Enrolment enrolment = enrolments.get(i);
            stringBuilder.append(String.valueOf(i + 1) + ". " + enrolment.reportEnrol() + "\n");
        }
        stringBuilder.append("---Report end---" + "\n");
        return stringBuilder.toString();
    }

    public String reportAssessment() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---Report of enrolments' assessments---" + "\n");
        for (int i = 0; i < enrolments.size(); i++) {
            Enrolment enrolment = enrolments.get(i);
            stringBuilder.append(String.valueOf(i + 1) + ". " + enrolment.reportEnrolAssessment() + "\n");
        }
        stringBuilder.append("---Report end---" + "\n");
        return stringBuilder.toString();
    }

    public void sort() {
        Comparable[] students = new Comparable[enrolments.size()];
        students = enrolments.toArray(students);

        ArraySorting.sort(students);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---Report of enrolments after sorted---" + "\n");
        for (int i = 0; i < students.length; i++) {
            Enrolment student = (Enrolment) students[i];
            stringBuilder.append(String.valueOf(i + 1) + ". " + student.reportEnrol() + "\n");
        }
        stringBuilder.append("---Report end---" + "\n");
        System.out.println(stringBuilder.toString());
    }
}
