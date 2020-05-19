package DataStorage;

import java.util.ArrayList;

public class CommitDependencies {
    private ArrayList<PackageInfo> packages;
    private ArrayList<String> classes;
    private int nrPackages;

    public CommitDependencies(){
        this.packages = new ArrayList<>();
    }

    public CommitDependencies(ArrayList<PackageInfo> packages){
        this.packages = packages;
    }

    public void addPackage(int packageName, ArrayList<Integer> dependencies){
        packages.add(new PackageInfo(packageName, dependencies));
    }

    public ArrayList<PackageInfo> getPackages() {
        return packages;
    }

    public ArrayList<String> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<String> classes) {
        this.classes = classes;
    }

    public void setNrPackages(int nrPackages) {
        this.nrPackages = nrPackages;
    }

    public int getNrPackages() {
        return nrPackages;
    }

    public int getNumberOfDependencies(){
        int count = 0;
        for(PackageInfo pi : packages){
            count += pi.getDependencies().size();
        }
        return count;
    }
}
