package a2_1901040226.manager;

import java.util.ArrayList;
import java.util.List;

public class EnrolmentManager {
    private List<Enrolment> enrolmentList = new ArrayList<>();

    public boolean addEnrolment(Enrolment enrolment) {
        enrolmentList.add(enrolment);

        if (enrolmentList.size() > 1) {
            for (int i = 0; i < enrolmentList.size() - 1; i++) {
                if (enrolmentList.get(i).equals(enrolment)) {
                    enrolmentList.remove(enrolmentList.size() - 1);
                    System.out.println("The enrolment has already existed.");
                    return false;
                }
            }
        }

        return true;
    }

    public List<Enrolment> getAllEnrolment() {
        return enrolmentList;
    }

    public void cloneToEnrolmentManager(List<Enrolment> enrolments) {
        for (int i = 0; i < enrolments.size(); i++) {
            addEnrolment(enrolments.get(i));
        }
    }

    public void setEnrolmentID (Enrolment enrolment) {
        if (enrolmentList.size() > 0) {
            Enrolment last = enrolmentList.get(enrolmentList.size() - 1);
            int id = last.getId() + 1;
            enrolment.setId(id);
        } else {
            enrolment.setId(1);
        }
    }

    public ArrayList<Object[]> report() {
        ArrayList<Object[]> rows = new ArrayList<>();
        for (int i = 0; i < enrolmentList.size(); i++) {
            Enrolment enrolment = enrolmentList.get(i);
            rows.add(new Object[]{
                    enrolment.getId(),
                    enrolment.getStudent().getId(),
                    enrolment.getStudent().getName(),
                    enrolment.getModule().getCode(),
                    enrolment.getModule().getName()
            });
        }
        return rows;
    }

    public ArrayList<Object[]> reportAssessment() {
        ArrayList<Object[]> rows = new ArrayList<>();
        for (int i = 0; i < enrolmentList.size(); i++) {
            Enrolment enrolment = enrolmentList.get(i);
            rows.add(new Object[]{
                    enrolment.getId(),
                    enrolment.getStudent().getId(),
                    enrolment.getModule().getCode(),
                    enrolment.getInternalMark(),
                    enrolment.getExaminationMark(),
                    enrolment.getFinalGrade()
            });
        }
        return rows;
    }
}
