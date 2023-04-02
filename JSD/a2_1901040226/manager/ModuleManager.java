package a2_1901040226.manager;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private List<Module> moduleList = new ArrayList<>();

    public boolean addModule(Module module) {
        moduleList.add(module);

        if (moduleList.size() > 1) {
            for (int i = 0; i < moduleList.size() - 1; i++) {
                if (moduleList.get(i).equals(module)) {
                    moduleList.remove(moduleList.size() - 1);
                    System.out.println("The module has already existed.");
                    return false;
                }
            }
        }

        return true;
    }

    public List<Module> getAllModule() {
        return moduleList;
    }

    public void cloneToModuleManager(List<Module> modules) {
        for (int i = 0; i < modules.size(); i++) {
            addModule(modules.get(i));
        }
    }

    public void setModuleCode(Module module) {
        IdGenerator generator = new IdGenerator();
        String semesterStr = String.valueOf(module.getSemester());
        if (moduleList.size() == 0) {
            generator.init("M", semesterStr, 1);
            module.setCode(generator.generate());
        } else {
            List<Module> sameSemester = new ArrayList<>();
            for (int i = 0; i < moduleList.size(); i++) {
                Module temp = moduleList.get(i);
                if (module.getSemester() == temp.getSemester()) {
                    sameSemester.add(temp);
                }
            }
            if (sameSemester.size() == 0) {
                generator.init("M", semesterStr, 1);
                String moduleCode = generator.generate();
                moduleCode = new StringBuilder(moduleCode).insert(2, "0").toString();
                module.setCode(moduleCode);
            } else {
                Module last = sameSemester.get(sameSemester.size() - 1);
                int code = Integer.parseInt(last.getCode().substring(2)) + 1;
                generator.init("M", semesterStr, code);
                String moduleCode = generator.generate();
                if (code < 10) {
                    moduleCode = new StringBuilder(moduleCode).insert(2, "0").toString();
                }
                module.setCode(moduleCode);
            }
        }
    }

    public ArrayList<Object[]> transformModuleToArrayObject() {
        ArrayList<Object[]> rows = new ArrayList<>();
        for (int i = 0; i < moduleList.size(); i++) {
            Module module = moduleList.get(i);
            if (module.getClass().getSimpleName().equals("CompulsoryModule")) {
                rows.add(new Object[]{
                        module.getCode(),
                        module.getName(),
                        module.getSemester(),
                        module.getCredits(),
                        "Compulsory",
                        ""
                });
            } else if (module.getClass().getSimpleName().equals("ElectiveModule")) {
                ElectiveModule module1 = (ElectiveModule) module;
                rows.add(new Object[]{
                        module1.getCode(),
                        module1.getName(),
                        module1.getSemester(),
                        module1.getCredits(),
                        "Elective",
                        module1.getDepartment()
                });
            }
        }
        return rows;
    }

    public String[] transformModuleToArray(String[] modules) {
        ArrayList<String> arrModule = new ArrayList<>();
        for (int i = 0; i < moduleList.size(); i++) {
            Module module = moduleList.get(i);
            String content = module.getCode() + " - " + module.getName();
            arrModule.add(content);
        }
        modules = arrModule.toArray(modules);
        return modules;
    }
}
