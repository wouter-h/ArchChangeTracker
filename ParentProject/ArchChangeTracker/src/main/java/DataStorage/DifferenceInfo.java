package DataStorage;

import java.util.ArrayList;

/** The object that contains the information about the differences between 2 commits
 *
 */
public class DifferenceInfo {
    private String graphName;
    private String comparedGraphName;
    private ArrayList<Integer> addedPackages;
    private ArrayList<Integer> removedPackages;
    private ArrayList<ChangedPackageI> addedPackageDependencies;
    private ArrayList<ChangedPackageI> removedPackageDependencies;
    private ArrayList<ClassInfo> movedClasses;

    public DifferenceInfo(ArrayList<Integer> addedPackages, ArrayList<Integer> removedPackages, ArrayList<ChangedPackageI> addedPackageDependencies, ArrayList<ChangedPackageI> removedPackageDependencies, ArrayList<ClassInfo> movedClasses){
        this.addedPackages = addedPackages;
        this.removedPackages = removedPackages;
        this.addedPackageDependencies = addedPackageDependencies;
        this.removedPackageDependencies = removedPackageDependencies;
        this.movedClasses = movedClasses;
    }

    public DifferenceInfo(String graphName, String comparedGraphName,
                          ArrayList<Integer> addedPackages, ArrayList<Integer> removedPackages,
                          ArrayList<ChangedPackageI> addedPackageDependencies , ArrayList<ChangedPackageI> removedPackageDependencies,
                          ArrayList<ClassInfo> movedClasses){
        this.graphName = graphName;
        this.comparedGraphName = comparedGraphName;
        this.addedPackages = addedPackages;
        this.removedPackages = removedPackages;
        this.addedPackageDependencies = addedPackageDependencies;
        this.removedPackageDependencies = removedPackageDependencies;
        this.movedClasses = movedClasses;
    }

    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }

    public void setComparedGraphName(String comparedGraphName) {
        this.comparedGraphName = comparedGraphName;
    }

    public String getGraphName() {
        return graphName;
    }

    public String getComparedGraphName() {
        return comparedGraphName;
    }

    public ArrayList<Integer> getAddedPackages() {
        return addedPackages;
    }

    public ArrayList<Integer> getRemovedPackages() {
        return removedPackages;
    }

    public ArrayList<ChangedPackageI> getAddedPackageDependencies() {
        return this.addedPackageDependencies;
    }

    public ArrayList<ChangedPackageI> getRemovedPackageDependencies() {
        return this.removedPackageDependencies;
    }

    public ArrayList<ClassInfo> getMovedClasses() {
        return movedClasses;
    }

    public int getAddedPackageCount(){
        return this.addedPackages.size();
    }

    public int getRemovedPackageCount(){
        return this.removedPackages.size();
    }

    /** Returns the number of dependencies that have been added
     * (dependencies of packages)
     *
     * @return number of dependencies that were added
     */
    public int getAddedDependenciesCount(){
        int count = 0;
        for(ChangedPackageI cpi : addedPackageDependencies){
            count += cpi.getDependencies().size();
        }
        return count;
    }

    /** Returns the number of dependencies that have been removed
     * (dependencies of packages)
     *
     * @return number of dependencies that were removed
     */
    public int getRemovedDependenciesCount(){
        int count = 0;
        for(ChangedPackageI cpi : removedPackageDependencies){
            count += cpi.getDependencies().size();
        }
        return count;
    }

    /** Returns the number of moved classes
     *
     * @return number of moved classes
     */
    public int getMovedClassesCount(){
        return this.movedClasses.size();
    }
}
