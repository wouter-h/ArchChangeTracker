package DataStorage;

import java.util.ArrayList;

public class ChangedPackageInfo implements ChangedPackageI {
    private Integer packageName;
    private ArrayList<Integer> dependencies;

    public ChangedPackageInfo(Integer packageName, ArrayList<Integer> dependencies){
        this.packageName = packageName;
        this.dependencies = dependencies;
    }

    public ArrayList<Integer> getDependencies() {
        return this.dependencies;
    }

    public Integer getPackageName() {
        return packageName;
    }

    public void setPackageName(Integer packageName) {
        this.packageName = packageName;
    }

    public ArrayList<Integer> getAddedPackages() {
        return dependencies;
    }

    public void setAddedPackages(ArrayList<Integer> dependencies) {
        this.dependencies = dependencies;
    }

}
