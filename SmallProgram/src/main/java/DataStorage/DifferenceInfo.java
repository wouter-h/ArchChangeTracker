package DataStorage;

import java.util.ArrayList;

public class DifferenceInfo {
    String graphName;
    String comparedGraphName;
    ArrayList<String> changedPackages;
    ArrayList<String> addedPackages;
    ArrayList<String> removedPackages;

    public DifferenceInfo(ArrayList<String> changedPackages,
                          ArrayList<String> addedPackages, ArrayList<String> removedPackages){
        this.changedPackages = changedPackages;
        this.addedPackages = addedPackages;
        this.removedPackages = removedPackages;
    }

    public DifferenceInfo(String graphName, String comparedGraphName, ArrayList<String> changedPackages,
                          ArrayList<String> addedPackages, ArrayList<String> removedPackages){
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
