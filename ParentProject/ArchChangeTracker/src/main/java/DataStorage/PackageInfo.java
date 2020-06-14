package DataStorage;

import java.util.ArrayList;

public class PackageInfo {
    private Integer packageName;
    private ArrayList<Integer> dependencies;

    public PackageInfo(int packageName, ArrayList<Integer> dependencies){
        this.packageName = packageName;
        this.dependencies = dependencies;
    }

    /** Adds a dependency
     *
     * @param dependency the dependency to be added
     */
    public void addDependency(Integer dependency){
        if(!this.dependencies.contains(dependency)) {
            this.dependencies.add(dependency);
        }
    }

    /** Equals method is overridden. It now checks if the object is an instance of PackageInfo. If it is it will check
     * whether the package names match and return true if they do.
     *
     * @param o the object to compare
     * @return true if the package names match, false otherwise
     */
    @Override
    public boolean equals(Object o){
        boolean retVal = false;
        if(o instanceof PackageInfo){
            PackageInfo p = (PackageInfo) o;
            retVal = p.packageName.intValue() == this.packageName.intValue();
        }
        return retVal;
    }

    /** returns the hashcode of the package name
     *
     * @return hashcode of the package name
     */
    @Override
    public int hashCode(){
        return packageName.hashCode();
    }

    /** returns the package name
     *
     * @return package name
     */
    public int getPackageName() {
        return packageName;
    }

    /** returns the dependencies
     *
     * @return dependencies
     */
    public ArrayList<Integer> getDependencies() {
        return dependencies;
    }
}
