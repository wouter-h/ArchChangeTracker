package DataStorage;

public class ClassInfo {
    private String packageName;
    private String name;

    public ClassInfo(String packageName, String name){
        this.packageName = packageName;
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }
}
