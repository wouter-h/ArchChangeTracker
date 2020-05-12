package DataStorage;

import java.util.ArrayList;

public class PackageInfoHash {
    private Integer packageName;
    private ArrayList<Integer> dependencies;

    public PackageInfoHash(int packageName, ArrayList<Integer> dependencies){
        this.packageName = packageName;
        this.dependencies = dependencies;
    }

    public void addDependency(Integer dependency){
        this.dependencies.add(dependency);
    }

    @Override
    public boolean equals(Object o){
        boolean retVal = false;
        if(o instanceof PackageInfoHash){
            PackageInfoHash p = (PackageInfoHash) o;
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
