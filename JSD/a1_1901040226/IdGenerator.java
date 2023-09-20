package a1_1901040226;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private String prefix;
    private String middle;
    private AtomicInteger index;

    public IdGenerator() {
        this.index = new AtomicInteger();
        this.prefix = "";
        this.middle = "";
    }

    public void init(String prefix, String middle, int index) {
        this.prefix = prefix;
        this.middle = middle;
        this.index.set(index);
    }

    public String getMiddle() {
        return middle;
    }

    public String generate() {
        return this.prefix + this.middle + this.index.incrementAndGet();
    }

    public static void generateStudentId (IdGenerator generator, List<Student> studentList) {
        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            String id = generator.generate();
            student.setId(id);
            // System.out.println(student);
        }
    }

    // create a list of entry semester
    public static void addSemesterList(List<Module> moduleList, List<Integer> semesterList) {
        for (int i = 0; i < moduleList.size(); i++) {
            Module module = moduleList.get(i);
            int semester = module.getSemester();

            if (semesterList.isEmpty()) {
                semesterList.add(semester);
            } else {
                for (int k = 0; k < semesterList.size(); k++) {
                    if (semester != semesterList.get(k)) {
                        semesterList.add(semester);
                    }
                }
            }
        }
    }

    /* create code generator depend on semester list
       index would increase independently of other semester
       and sequentially based on its semester
     */
    public static void createCodeGenerator(List<IdGenerator> codeGeneratorList, List<Integer> semesterList) {
        for (int i = 0; i < semesterList.size(); i++) {
            IdGenerator codeGenerator = new IdGenerator();
            String semesterChar = String.valueOf(semesterList.get(i));
            codeGenerator.init("M", semesterChar, 0);
            codeGeneratorList.add(codeGenerator);
        }
    }

    // set code to corresponding module respectively
    public static void generateModuleCode(List<Module> moduleList, List<IdGenerator> codeGeneratorList) {
        for (int i = 0; i < moduleList.size(); i++) {
            Module module = moduleList.get(i);
            for (int k = 0; k < codeGeneratorList.size(); k++) {
                IdGenerator codeGenerator = codeGeneratorList.get(k);
                if (String.valueOf(module.getSemester()).equals(codeGenerator.getMiddle())) {
                    String code = codeGenerator.generate();
                    module.setCode(code);
                    if (module.getCode().length() < 4) {  // if index < 10, add 0 after suffix and before index
                        String newCode = module.getCode();
                        newCode = new StringBuilder(newCode).insert(2, "0").toString();
                        module.setCode(newCode);
                    }
                }
            }
            // System.out.println(module);
        }
    }
}
