package Analyzer;

import DataStorage.*;

import java.util.ArrayList;

public class DifferenceAnalyzer {

    /** Finds the differences between 2 CommitDependencies. [1] will be compared to [2].
     *
     * @param plt1 The packageLookUpTable that will be compared to PackageLookUpTable 2.
     * @param plt2 The packageLookUpTable that will be compared by PackageLookUpTable 1.
     * @param clt1 The ClassLookUpTable that will be compared to ClassLookUpTable 2.
     * @param clt2 The ClassLookUpTable that will be compared by ClassLookUpTable 1.
     * @param cd1 The CommitDependencies that will be compared to CommitDependencies 2.
     * @param cd2 The CommitDependencies that will be compared by CommitDependencies 1.
     * @return DifferenceInfo object that contains the differences.
     */
    public static DifferenceInfo findDifferences(PackageLookupTable plt1, PackageLookupTable plt2, ClassLookupTable clt1, ClassLookupTable clt2, CommitDependencies cd1, CommitDependencies cd2){
        ArrayList<ChangedPackageI>[] differences = packagesChanged(plt1, plt2, cd1, cd2);
        ArrayList<Integer> added = packagesAdded(plt1, plt2, cd1, cd2);
        ArrayList<Integer> removed = packageRemoved(plt1, plt2, cd1, cd2);
        ArrayList<ClassInfo> movedClasses = classesMoved(clt1, clt2, cd1, cd2);
        return new DifferenceInfo(added, removed, differences[0], differences[1], movedClasses);
    }

    /** Finds all packages that changed. Changed in this context meaning whether its dependencies changed.
     * These changed dependencies could either be new ones or removed ones.
     *
     * @param plt1 The PackageLookUpTable used to in combination with CommitDependencies 1.
     * @param plt2 The PackageLookUpTable used to in combination with CommitDependencies 2.
     * @param cd1 The CommitDependencies that will be compared to CommitDependencies 2.
     * @param cd2 The CommitDependencies that will be compared by CommitDependencies 1.
     * @return ArrayList of ChangedPackageI that stores the information of the packages that got changed.
     */
    private static ArrayList<ChangedPackageI>[] packagesChanged(PackageLookupTable plt1, PackageLookupTable plt2, CommitDependencies cd1, CommitDependencies cd2){
        ArrayList<PackageInfo> packages1 = cd1.getPackages();
        ArrayList<PackageInfo> packages2 = cd2.getPackages();
        ArrayList<ChangedPackageI> addedDependencies = new ArrayList<>();
        ArrayList<ChangedPackageI> removedDependencies = new ArrayList<>();

        for(PackageInfo p1 : packages1){
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

    /** Finds all dependencies that changed of a package 1, which will be compared to a package 2.
     * A dependency change is either a new dependency (added) or removed dependency (removed).
     *
     * @param plt1 PackageLookUpTable belonging to package 1
     * @param plt2 PackageLookUpTable belonging to package 2
     * @param p1 Package 1, the more recent package
     * @param p2 Package 2, the older package
     * @return An array of size 2, with in the first index added packages, and the second index removed packages.
     */
    private static ArrayList<Integer>[] packageChanged(PackageLookupTable plt1, PackageLookupTable plt2, PackageInfo p1, PackageInfo p2){
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

    /** Finds out which packages have been added to a CommitDependencies object 1. This CommitDepencies 1 object will be compared
     * to a CommitDependencies 2 object.
     *
     * @param plt1 The PackageLookUpTable belonging to the first commit dependencies object (cd1).
     * @param plt2 The PackageLookUpTable belonging to the second commit dependencies object (cd2).
     * @param cd1 The CommitDependencies object that will be compared to cd2.
     * @param cd2 The CommitDependencies object that will be compared by cd1.
     * @return An ArrayList of Integers with the newly added packages.
     */
    private static ArrayList<Integer> packagesAdded(PackageLookupTable plt1, PackageLookupTable plt2, CommitDependencies cd1, CommitDependencies cd2){
        ArrayList<PackageInfo> dep1 = cd1.getPackages();
        ArrayList<PackageInfo> dep2 = cd2.getPackages();
        ArrayList<Integer> addedPackages = new ArrayList<>();

        for(PackageInfo pi1 : dep1){
            Integer n = plt2.getKey(plt1.getString(pi1.getPackageName()));
            if(n == null){
                addedPackages.add(pi1.getPackageName());
            }
        }
        return addedPackages;
    }

    /** Finds out which packages have been removed in a CommitDependencies object 1. This CommitDepencies 1 object will be compared
     * to a CommitDependencies 2 object.
     *
     * @param plt1 The PackageLookUpTable belonging to the first commit dependencies object (cd1).
     * @param plt2 The PackageLookUpTable belonging to the second commit dependencies object (cd2).
     * @param cd1 The CommitDependencies object that will be compared to cd2.
     * @param cd2 The CommitDependencies object that will be compared by cd1.
     * @return An ArrayList of Integers with the removed packages.
     */
    private static ArrayList<Integer> packageRemoved(PackageLookupTable plt1, PackageLookupTable plt2, CommitDependencies cd1, CommitDependencies cd2){
        ArrayList<PackageInfo> dep1 = cd1.getPackages();
        ArrayList<PackageInfo> dep2 = cd2.getPackages();
        ArrayList<Integer> removedPackages = new ArrayList<>();

        for(PackageInfo pi2 : dep2){
            Integer n = plt1.getKey(plt2.getString(pi2.getPackageName()));
            if(n == null){
                removedPackages.add(pi2.getPackageName());
            }
        }
        return removedPackages;
    }

    /** Finds which classes have been moved in 2 commits. It purely compares the name of a class, not the contents.
     *
     * @param clt1 The ClassLookUpTable belonging to the first CommitDependencies object (cd1).
     * @param clt2 The ClassLookUpTable belonging to the second CommitDependencies object (cd2).
     * @param cd1 The first CommitDependencies object that will be compared to cd2.
     * @param cd2 The second CommitDependencies object that will be compared by cd1.
     * @return An ArrayList of ClassInfo which has the moved classes.
     */
    private static ArrayList<ClassInfo> classesMoved(ClassLookupTable clt1, ClassLookupTable clt2, CommitDependencies cd1, CommitDependencies cd2){
        ArrayList<String> classNames = cd1.getClasses();
        ArrayList<ClassInfo> movedClasses = new ArrayList<>();
        for(String className : classNames){
            ArrayList<ClassInfo> targetClasses = clt2.get(className);
            if(targetClasses != null){
                ArrayList<ClassInfo> currentClasses = clt1.get(className);
                movedClasses.addAll(changed(currentClasses, targetClasses));
            }
        }
        return movedClasses;
    }

    /** Finds which classes have been moved between 2 ArrayLists of ClassInfos.
     *
     * @param cia1 The first ArrayList of classes that will be compared to the cia2.
     * @param cia2 The second ArrayList of classes that will be compared by cia1.
     * @return An ArrayList of moved classes.
     */
    private static ArrayList<ClassInfo> changed(ArrayList<ClassInfo> cia1, ArrayList<ClassInfo> cia2){
        ArrayList<ClassInfo> movedClasses = new ArrayList<>();
        for (ClassInfo classInfo : cia1) {
            if (hasMoved(classInfo, cia2)) {
                movedClasses.add(classInfo);
            }
        }
        return movedClasses;
    }

    /** Returns whether a class has moved when compared to an ArrayList of classes. It does so by comparing the
     * package name of the classes.
     *
     * @param ci The ClassInfo (class) that will be compared.
     * @param cia The ArrayList of classes that will be checked to see whether the class has moved.
     * @return True if it moved. False otherwise.
     */
    private static boolean hasMoved(ClassInfo ci, ArrayList<ClassInfo> cia){
        for(ClassInfo classInfo : cia) {
            if (ci.getPackageName().equals(classInfo.getPackageName())) {
                return false;
            }
        }
        return true;
    }
}
