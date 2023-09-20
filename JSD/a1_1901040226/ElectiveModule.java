package a1_1901040226;

public class ElectiveModule extends Module {
    private String department;

    public ElectiveModule(String name, int semester, int credits, String department) {
        super(name, semester, credits);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return super.toString() + " + department=" + department;
    }
}
