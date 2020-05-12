package Analyzer;

import DataStorage.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DifferenceAnalyzerHash {

    /** Finds the difference between cd1 compared to cd2.
     *
     * @param cd1
     * @param cd2
     * @return DifferenceInfo object with the differences packages, added packages and removed packages specified
     */
    public static DifferenceInfoHash findDifferences(PackageLookupTable plt1, PackageLookupTable plt2, CommitDependenciesHash cd1, CommitDependenciesHash cd2){
        ArrayList<Integer> differences = packagesChanged(plt1, plt2, cd1, cd2);
        ArrayList<Integer> added = packagesAdded(plt1, plt2, cd1, cd2);
        ArrayList<Integer> removed = packageRemoved(plt1, plt2, cd1, cd2);
        return new DifferenceInfoHash(differences, added, removed);
    }

    public static ArrayList<Integer> packagesChanged(PackageLookupTable plt1, PackageLookupTable plt2, CommitDependenciesHash cd1, CommitDependenciesHash cd2){
        ArrayList<PackageInfoHash> packages1 = cd1.getPackages();
        ArrayList<PackageInfoHash> packages2 = cd2.getPackages();
        ArrayList<Integer> changedPackages = new ArrayList<>();

        for(PackageInfoHash p1 : packages1){
            String str = plt2.getString(p1.getPackageName());
            Integer key2 = plt2.getKey(str);
            if(key2 != null){
                if(packageChanged(plt1, plt2, p1, packages2.get(key2))){
                    changedPackages.add(p1.getPackageName());
                }
            }
        }

        return changedPackages;
    }

    private static boolean packageChanged(PackageLookupTable plt1, PackageLookupTable plt2, PackageInfoHash p1, PackageInfoHash p2){
        ArrayList<Integer> dep1 = p1.getDependencies();
        ArrayList<Integer> dep2 = p2.getDependencies();

        for(Integer d1 : dep1){
            if(!dep2.contains(d1)){
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Integer> packagesAdded(PackageLookupTable plt1, PackageLookupTable plt2, CommitDependenciesHash cd1, CommitDependenciesHash cd2){
        ArrayList<PackageInfoHash> dep1 = cd1.getPackages();
        ArrayList<PackageInfoHash> dep2 = cd2.getPackages();
        ArrayList<Integer> addedPackages = new ArrayList<>();

        for(PackageInfoHash pi1 : dep1){
            if(!dep2.contains(pi1)){
                addedPackages.add(pi1.getPackageName());
            }
        }
        return addedPackages;
    }

    public static ArrayList<Integer> packageRemoved(PackageLookupTable plt1, PackageLookupTable plt2, CommitDependenciesHash cd1, CommitDependenciesHash cd2){
        ArrayList<PackageInfoHash> dep1 = cd1.getPackages();
        ArrayList<PackageInfoHash> dep2 = cd2.getPackages();
        ArrayList<Integer> removedPackages = new ArrayList<>();

        for(PackageInfoHash pi2 : dep2){
            if(!dep1.contains(pi2)){
                removedPackages.add(pi2.getPackageName());
            }
        }
        return removedPackages;
    }
}
