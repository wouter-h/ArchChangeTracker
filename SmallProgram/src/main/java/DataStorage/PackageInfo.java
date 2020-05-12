package DataStorage;

import java.util.ArrayList;

public class PackageInfo {
    private String packageName;
    private ArrayList<String> dependencies;

    public PackageInfo(String packageName, ArrayList<String> dependencies){
        this.packageName = packageName;
        this.dependencies = dependencies;
    }

    public void addDependency(String dependency){
        this.dependencies.add(dependency);
    }

    @Override
    public boolean equals(Object o){
        boolean retVal = false;
        if(o instanceof PackageInfo){
            PackageInfo p = (PackageInfo) o;
            retVal = p.packageName.equals(this.packageName);
        }
        return retVal;
    }

    @Override
    public int hashCode(){
        return packageName.hashCode();
    }

    public String getPackageName() {
        return packageName;
    }

    public ArrayList<String> getDependencies() {
        return dependencies;
    }
}
