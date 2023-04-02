package a2_1901040226.manager;

public class IdGenerator {
    private String prefix;
    private String middle;
    private int index;

    public IdGenerator() {
        this.index = 0;
        this.prefix = "";
        this.middle = "";
    }

    public void init(String prefix, String middle, int index) {
        this.prefix = prefix;
        this.middle = middle;
        this.index = index;
    }

    public String generate() {
        return this.prefix + this.middle + this.index;
    }
}
