package Analyzer;

import DataStorage.*;

import java.util.ArrayList;

public class DifferenceAnalyzerHash {

    /** Finds the difference between cd1 compared to cd2.
     *
     * @param cd1
     * @param cd2
     * @return DifferenceInfo object with the differences packages, added packages and removed packages specified
     */
    public static DifferenceInfoHash findDifferences(PackageLookupTable plt1, PackageLookupTable plt2, CommitDependenciesHash cd1, CommitDependenciesHash cd2){
        ArrayList<ChangedPackageI>[] differences = packagesChanged(plt1, plt2, cd1, cd2);
        ArrayList<Integer> added = packagesAdded(plt1, plt2, cd1, cd2);
        ArrayList<Integer> removed = packageRemoved(plt1, plt2, cd1, cd2);
        return new DifferenceInfoHash(added, removed, differences[0], differences[1]);
    }

    public static ArrayList<ChangedPackageI>[] packagesChanged(PackageLookupTable plt1, PackageLookupTable plt2, CommitDependenciesHash cd1, CommitDependenciesHash cd2){
        ArrayList<PackageInfoHash> packages1 = cd1.getPackages();
        ArrayList<PackageInfoHash> packages2 = cd2.getPackages();
        ArrayList<ChangedPackageI> addedDependencies = new ArrayList<>();
        ArrayList<ChangedPackageI> removedDependencies = new ArrayList<>();

        for(PackageInfoHash p1 : packages1){
            String str = plt1.getString(p1.getPackageName());
            Integer key2 = plt2.getKey(str);
            if(key2 != null){
                ArrayList<Integer>[] packagesChanged = packageChanged(plt1, plt2, p1, packages2.get(key2));
                if(packagesChanged[0].size() != 0){
                    addedDependencies.add(new ChangedPackageInfo(p1.getPackageName(), packagesChanged[0]));
                }
                if(packagesChanged[1].size() != 0){
                    removedDependencies.add(new ChangedPackageInfo(p1.getPackageName(), packagesChanged[1]));
                }
            }
        }
        ArrayList<ChangedPackageI>[] array = new ArrayList[2];
        array[0] = addedDependencies;
        array[1] = removedDependencies;
        return array;
    }

    private static ArrayList<Integer>[] packageChanged(PackageLookupTable plt1, PackageLookupTable plt2, PackageInfoHash p1, PackageInfoHash p2){
        ArrayList<Integer> dep1 = p1.getDependencies();
        ArrayList<Integer> dep2 = p2.getDependencies();

        ArrayList<Integer> addedPackages = new ArrayList<>();
        ArrayList<Integer> removedPackages = new ArrayList<>();
        for(Integer d1 : dep1){
            if(!dep2.contains(plt2.getKey(plt1.getString(d1)))){//added
                addedPackages.add(d1);
            }
        }
        for(Integer d2 : dep2){
            if(!dep1.contains(plt1.getKey(plt2.getString(d2)))){//removed
                removedPackages.add(d2);
            }
        }
        ArrayList<Integer>[] array = new ArrayList[2];
        array[0] = addedPackages;
        array[1] = removedPackages;
        return array;
    }

    public static ArrayList<Integer> packagesAdded(PackageLookupTable plt1, PackageLookupTable plt2, CommitDependenciesHash cd1, CommitDependenciesHash cd2){
        ArrayList<PackageInfoHash> dep1 = cd1.getPackages();
        ArrayList<PackageInfoHash> dep2 = cd2.getPackages();
        ArrayList<Integer> addedPackages = new ArrayList<>();

        for(PackageInfoHash pi1 : dep1){
            Integer n = plt2.getKey(plt1.getString(pi1.getPackageName()));
            if(n == null){
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
            Integer n = plt1.getKey(plt2.getString(pi2.getPackageName()));
            if(n == null){
                removedPackages.add(pi2.getPackageName());
            }
        }
        return removedPackages;
    }
}
