public class Csvobject {
    private String packageName;
    private int index;
    private int[] dependencies;

    public Csvobject(String packageName, int index, int arraySize){
        this.packageName = packageName;
        this.index = index;
        this.dependencies = new int[arraySize];
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int[] getDependencies() {
        return dependencies;
    }

    public void setDependencies(int[] dependencies) {
        this.dependencies = dependencies;
    }

    public int getDependenciesIdx(int i){
        return dependencies[i];
    }

    public void setDependenciesIdx(int i, int ele){
        this.dependencies[i] = ele;
    }
}
