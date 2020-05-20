package DataStorage;

import java.util.ArrayList;

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

    public int getAddedDependenciesCount(){
        int count = 0;
        for(ChangedPackageI cpi : addedPackageDependencies){
            count += cpi.getDependencies().size();
        }
        return count;
    }

    public int getRemovedDependenciesCount(){
        int count = 0;
        for(ChangedPackageI cpi : removedPackageDependencies){
            count += cpi.getDependencies().size();
        }
        return count;
    }

    public int getMovedClassesCount(){
        return this.movedClasses.size();
    }
}
