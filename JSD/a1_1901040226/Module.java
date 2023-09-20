package a1_1901040226;

import java.util.Objects;

public class Module {
    private String code;
    private String name;
    private int semester;
    private int credits;

    public Module(String name, int semester, int credits) {
        this.code = "";
        this.name = name;
        this.semester = semester;
        this.credits = credits;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", semester=" + semester +
                ", credits=" + credits +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Module)) return false;
        Module module = (Module) o;
        return semester == module.semester && credits == module.credits &&
                code.equals(module.code) && name.equals(module.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, semester, credits);
    }
}
