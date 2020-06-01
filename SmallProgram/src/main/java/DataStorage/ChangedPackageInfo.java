package DataStorage;

import java.util.ArrayList;

/** ChangedPackageInfo is responsible to store the package name and the dependencies this package has.
 *  In order to retrieve the package name or the package dependencies as a string a package lookup table has to be used.
 *  This class merely stores the integers that can be used to look up the string name in a package lookup table.
 */
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
