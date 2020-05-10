package Analyzer;

import DataStorage.CommitDependencies;
import DataStorage.DifferenceInfo;
import DataStorage.PackageInfo;

import java.util.ArrayList;

public class DifferenceAnalyzer {

    public static DifferenceInfo findDifferences(CommitDependencies cd1, CommitDependencies cd2){
        ArrayList<String> differences = packagesChanged(cd1, cd2);
        ArrayList<String> added = packagesAdded(cd1, cd2);
        ArrayList<String> removed = packageRemoved(cd1, cd2);
        return new DifferenceInfo(differences, added, removed);
    }

    public static ArrayList<String> packagesChanged(CommitDependencies cd1, CommitDependencies cd2){
        ArrayList<PackageInfo> packages1 = cd1.getPackages();
        ArrayList<PackageInfo> packages2 = cd2.getPackages();
        ArrayList<String> changedPackages = new ArrayList<>();
        for(PackageInfo p1 : packages1){
            if(packages2.contains(p1)){//COMPARES PACKAGE NAMES
                int idx = packages2.indexOf(p1);
                if(packageChanged(p1, packages2.get(idx))){
                    changedPackages.add(p1.getPackageName());
                }
            }
        }
        return changedPackages;
    }

    private static boolean packageChanged(PackageInfo p1, PackageInfo p2){
        ArrayList<String> dep1 = p1.getDependencies();
        ArrayList<String> dep2 = p2.getDependencies();
        if(dep1.size() != dep2.size()){
            return true;
        }
        for(String d1 : dep1){
            if(!dep2.contains(d1)){
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> packagesAdded(CommitDependencies cd1, CommitDependencies cd2){
        ArrayList<PackageInfo> dep1 = cd1.getPackages();
        ArrayList<PackageInfo> dep2 = cd2.getPackages();
        ArrayList<String> addedPackages = new ArrayList<>();
        for(PackageInfo pi1 : dep1){
            if(!dep2.contains(pi1)){
                addedPackages.add(pi1.getPackageName());
            }
        }
        return addedPackages;
    }

    public static ArrayList<String> packageRemoved(CommitDependencies cd1, CommitDependencies cd2){
        ArrayList<PackageInfo> dep1 = cd1.getPackages();
        ArrayList<PackageInfo> dep2 = cd2.getPackages();
        ArrayList<String> removedPackages = new ArrayList<>();
        for(PackageInfo pi2 : dep2){
            if(!dep1.contains(pi2)){
                removedPackages.add(pi2.getPackageName());
            }
        }
        return removedPackages;
    }
}
