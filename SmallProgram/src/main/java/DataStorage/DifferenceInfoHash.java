package DataStorage;

import java.util.ArrayList;

public class DifferenceInfoHash {
    String graphName;
    String comparedGraphName;
    ArrayList<Integer> changedPackages;
    ArrayList<Integer> addedPackages;
    ArrayList<Integer> removedPackages;

    public DifferenceInfoHash(ArrayList<Integer> changedPackages,
                          ArrayList<Integer> addedPackages, ArrayList<Integer> removedPackages){
        this.changedPackages = changedPackages;
        this.addedPackages = addedPackages;
        this.removedPackages = removedPackages;
    }

    public DifferenceInfoHash(String graphName, String comparedGraphName, ArrayList<Integer> changedPackages,
                          ArrayList<Integer> addedPackages, ArrayList<Integer> removedPackages){
        this.graphName = graphName;
        this.comparedGraphName = comparedGraphName;
        this.changedPackages = changedPackages;
        this.addedPackages = addedPackages;
        this.removedPackages = removedPackages;
    }

    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }

    public void setComparedGraphName(String comparedGraphName) {
        this.comparedGraphName = comparedGraphName;
    }
}
