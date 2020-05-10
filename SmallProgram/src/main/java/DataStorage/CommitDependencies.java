package DataStorage;

import DataStorage.PackageInfo;

import java.util.ArrayList;

public class CommitDependencies {
    private ArrayList<PackageInfo> packages;

    public CommitDependencies(){
        this.packages = new ArrayList<>();
    }

    public void addPackage(String packageName, ArrayList<String> dependencies){
        packages.add(new PackageInfo(packageName, dependencies));
    }

    public void prettyPrint(){
        for(PackageInfo pi : this.packages){
            System.out.println("package name: " + pi.getPackageName());
            System.out.print("\t");
            ArrayList<String> dependencies = pi.getDependencies();
            if(dependencies.size() > 0) {
                for (int i = 0; i < dependencies.size() - 1; ++i) {
                    System.out.print(dependencies.get(i) + ", ");
                }
                System.out.print(dependencies.get(dependencies.size() - 1));
            }
            System.out.println();
        }
    }

    public ArrayList<PackageInfo> getPackages() {
        return packages;
    }
}
