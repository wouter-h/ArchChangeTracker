package DataStorage;

import java.util.ArrayList;

public class CommitDependenciesHash {
    private ArrayList<PackageInfoHash> packages;

    public CommitDependenciesHash(){
        this.packages = new ArrayList<>();
    }

    public CommitDependenciesHash(ArrayList<PackageInfoHash> packages){
        this.packages = packages;
    }

    public void addPackage(int packageName, ArrayList<Integer> dependencies){
        packages.add(new PackageInfoHash(packageName, dependencies));
    }

    public void prettyPrint(){
        for(PackageInfoHash pi : this.packages){
            System.out.println("package name: " + pi.getPackageName());
            System.out.print("\t");
            ArrayList<Integer> dependencies = pi.getDependencies();
            if(dependencies.size() > 0) {
                for (int i = 0; i < dependencies.size() - 1; ++i) {
                    System.out.print(dependencies.get(i) + ", ");
                }
                System.out.print(dependencies.get(dependencies.size() - 1));
            }
            System.out.println();
        }
    }

    public ArrayList<PackageInfoHash> getPackages() {
        return packages;
    }
}
