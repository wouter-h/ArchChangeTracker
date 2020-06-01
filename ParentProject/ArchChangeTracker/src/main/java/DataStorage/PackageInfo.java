package DataStorage;

import java.util.ArrayList;

public class PackageInfo {
    private Integer packageName;
    private ArrayList<Integer> dependencies;

    public PackageInfo(int packageName, ArrayList<Integer> dependencies){
        this.packageName = packageName;
        this.dependencies = dependencies;
    }

    public void addDependency(Integer dependency){
        if(!this.dependencies.contains(dependency)) {
            this.dependencies.add(dependency);
        }
    }

    @Override
    public boolean equals(Object o){
        boolean retVal = false;
        if(o instanceof PackageInfo){
            PackageInfo p = (PackageInfo) o;
            retVal = p.packageName.intValue() == this.packageName.intValue();
        }
        return retVal;
    }

    @Override
    public int hashCode(){
        return packageName.hashCode();
    }

    public int getPackageName() {
        return packageName;
    }

    public ArrayList<Integer> getDependencies() {
        return dependencies;
    }
}
