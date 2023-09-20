package a1_1901040226;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static a1_1901040226.IdGenerator.*;

public class CourseManProg {
    public static void main(String[] args) throws ParseException {
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);

        IdGenerator generator = new IdGenerator();
        generator.init("S", "", year - 1);

        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);

        Student sv1 = new Student("Trang", df.parse("08/12/2001"), "Hanoi", "trang@gmail.com");
        Student sv2 = new Student("An", df.parse("01/01/2001"), "HCM city", "an@gmail.com");
        Student sv3 = new Student("Phuong", df.parse("02/10/2000"), "Hanoi", "phuong@gmail.com");
        Student sv4 = new Student("Thuy", df.parse("09/04/2000"), "Danang", "thuy@gmail.com");
        Student sv5 = new Student("Nam", df.parse("02/11/2000"), "Hanoi", "nam@gmail.com");

        List<Student> studentList = new ArrayList<>();
        studentList.add(sv1);
        studentList.add(sv2);
        studentList.add(sv3);
        studentList.add(sv4);
        studentList.add(sv5);

        generateStudentId(generator, studentList);

        CompulsoryModule m1 = new CompulsoryModule("SAD", 1, 3);
        CompulsoryModule m2 = new CompulsoryModule("SE2", 2, 3);
        CompulsoryModule m3 = new CompulsoryModule("WPR", 2, 3);
        ElectiveModule m4 = new ElectiveModule("CGR", 1, 3, "IT");
        ElectiveModule m5 = new ElectiveModule("HCI", 3, 2, "MM");

        List<Module> moduleList = new ArrayList<>();
        moduleList.add(m1);
        moduleList.add(m2);
        moduleList.add(m3);
        moduleList.add(m4);
        moduleList.add(m5);

        List<IdGenerator> codeGeneratorList = new ArrayList<>();
        List<Integer> semesterList = new ArrayList<>();

        addSemesterList(moduleList, semesterList);
        createCodeGenerator(codeGeneratorList, semesterList);
        generateModuleCode(moduleList, codeGeneratorList);

        Enrolment enrolment = new Enrolment(sv1, m1);
        Enrolment enrolment1 = new Enrolment(sv2, m2);
        Enrolment enrolment2 = new Enrolment(sv3, m3);
        Enrolment enrolment3 = new Enrolment(sv4, m4);
        Enrolment enrolment4 = new Enrolment(sv5, m5);
        Enrolment enrolment5 = new Enrolment(sv1, m5);
        Enrolment enrolment6 = new Enrolment(sv2, m4);
        Enrolment enrolment7 = new Enrolment(sv3, m4);
        Enrolment enrolment8 = new Enrolment(sv4, m1);
        Enrolment enrolment9 = new Enrolment(sv5, m2);

        EnrolmentManager enrolmentManager = new EnrolmentManager();

        enrolmentManager.addEnrolment(enrolment);
        enrolmentManager.addEnrolment(enrolment1);
        enrolmentManager.addEnrolment(enrolment2);
        enrolmentManager.addEnrolment(enrolment3);
        enrolmentManager.addEnrolment(enrolment4);
        enrolmentManager.addEnrolment(enrolment5);
        enrolmentManager.addEnrolment(enrolment6);
        enrolmentManager.addEnrolment(enrolment7);
        enrolmentManager.addEnrolment(enrolment8);
        enrolmentManager.addEnrolment(enrolment9);

        String report = enrolmentManager.report();
        System.out.println(report);

        enrolmentManager.sort();

        enrolmentManager.setMark(sv1, m1, 9, 9);
        enrolmentManager.setMark(sv2, m4, 6, 7);
        enrolmentManager.setMark(sv3, m3, 5, 6);
        enrolmentManager.setMark(sv4, m1, 8, 7);
        enrolmentManager.setMark(sv5, m5, 4, 4);

        String reportAssessment = enrolmentManager.reportAssessment();
        System.out.println(reportAssessment);
    }
}
