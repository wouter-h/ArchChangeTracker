package DataStorage;

import java.util.ArrayList;

public class DifferenceInfoHash {
    private String graphName;
    private String comparedGraphName;
    private ArrayList<Integer> addedPackages;
    private ArrayList<Integer> removedPackages;
    private ArrayList<ChangedPackageI> addedPackageDependencies;
    private ArrayList<ChangedPackageI> removedPackageDependencies;

    public DifferenceInfoHash(ArrayList<Integer> addedPackages, ArrayList<Integer> removedPackages, ArrayList<ChangedPackageI> addedPackageDependencies, ArrayList<ChangedPackageI> removedPackageDependencies){
        this.addedPackages = addedPackages;
        this.removedPackages = removedPackages;
        this.addedPackageDependencies = addedPackageDependencies;
        this.removedPackageDependencies = removedPackageDependencies;
    }

    public DifferenceInfoHash(String graphName, String comparedGraphName,
                              ArrayList<Integer> addedPackages, ArrayList<Integer> removedPackages,
                              ArrayList<ChangedPackageI> addedPackageDependencies , ArrayList<ChangedPackageI> removedPackageDependencies){
        this.graphName = graphName;
        this.comparedGraphName = comparedGraphName;
        this.addedPackages = addedPackages;
        this.removedPackages = removedPackages;
        this.addedPackageDependencies = addedPackageDependencies;
        this.removedPackageDependencies = removedPackageDependencies;
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
}
