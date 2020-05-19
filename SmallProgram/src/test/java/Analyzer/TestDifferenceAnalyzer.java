package Analyzer;

import DataStorage.CommitDependencies;
import org.apache.commons.lang3.builder.Diff;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDifferenceAnalyzer {/*
    @Test
    public void testPackageChanged1(){
        CommitDependencies cd1 = new CommitDependencies();
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("package2");
        list1.add("package3");
        list1.add("package5");
        list1.add("package6");
        list1.add("package7");
        list1.add("package8");
        list1.add("package9");
        list1.add("package10");
        cd1.addPackage("package1", list1);

        CommitDependencies cd2 = new CommitDependencies();
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("package2");
        list2.add("package3");
        list2.add("package4");
        list2.add("package5");
        list2.add("package6");
        list2.add("package7");
        list2.add("package8");
        list2.add("package9");
        list2.add("package11");
        cd2.addPackage("package1", list2);

        ArrayList<String> testRes=  DifferenceAnalyzer.packagesChanged(cd1, cd2);
        assertEquals(testRes.size(), 1);
        assertEquals(testRes.get(0).equals("package1"), true);
    }

    @Test
    public void testPackageChanged2(){
        CommitDependencies cd1 = new CommitDependencies();
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("package2");
        list1.add("package3");
        list1.add("package5");
        list1.add("package6");
        list1.add("package7");
        list1.add("package8");
        list1.add("package9");
        list1.add("package10");
        cd1.addPackage("package1", list1);

        CommitDependencies cd2 = new CommitDependencies();
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("package2");
        list2.add("package3");
        list2.add("package4");
        cd2.addPackage("package1", list2);

        ArrayList<String> testRes=  DifferenceAnalyzer.packagesChanged(cd1, cd2);
        assertEquals(testRes.size(), 1);
        assertEquals(testRes.get(0).equals("package1"), true);
    }

    @Test
    public void testPackageChanged3(){
        CommitDependencies cd1 = new CommitDependencies();
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("package2");
        list1.add("package3");
        list1.add("package4");
        cd1.addPackage("package1", list1);

        CommitDependencies cd2 = new CommitDependencies();
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("package2");
        list2.add("package3");
        list2.add("package4");
        cd2.addPackage("package1", list2);

        ArrayList<String> testRes=  DifferenceAnalyzer.packagesChanged(cd1, cd2);
        assertEquals(0, testRes.size());
    }

    @Test
    public void testPackageChanged4(){
        CommitDependencies cd1 = new CommitDependencies();
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("package2");
        list1.add("package3");
        list1.add("package5");
        list1.add("package6");
        list1.add("package7");
        list1.add("package8");
        list1.add("package9");
        list1.add("package10");
        cd1.addPackage("package1", list1);

        CommitDependencies cd2 = new CommitDependencies();

        ArrayList<String> testRes=  DifferenceAnalyzer.packagesChanged(cd1, cd2);
        assertEquals(testRes.size(), 0);
    }

    @Test
    public void testPackageChanged5(){
        CommitDependencies cd1 = new CommitDependencies();
        CommitDependencies cd2 = new CommitDependencies();

        ArrayList<String> testRes=  DifferenceAnalyzer.packagesChanged(cd1, cd2);
        assertEquals(testRes.size(), 0);
    }

    @Test
    public void testPackageChanged6(){
        CommitDependencies cd1 = new CommitDependencies();
        ArrayList<String> list1 = new ArrayList<>();
        cd1.addPackage("package1", list1);

        CommitDependencies cd2 = new CommitDependencies();
        ArrayList<String> list2 = new ArrayList<>();
        cd2.addPackage("package1", list2);


        ArrayList<String> testRes=  DifferenceAnalyzer.packagesChanged(cd1, cd2);
        assertEquals(testRes.size(), 0);
    }

    @Test
    public void testPackageChanged7(){
        CommitDependencies cd1 = new CommitDependencies();
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("package2");
        list1.add("package3");
        list1.add("package5");
        list1.add("package6");
        list1.add("package7");
        list1.add("package8");
        list1.add("package9");
        list1.add("package10");
        cd1.addPackage("package1", list1);

        CommitDependencies cd2 = new CommitDependencies();
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("package2");
        list2.add("package3");
        list2.add("package4");
        cd2.addPackage("package2", list2);

        ArrayList<String> testRes=  DifferenceAnalyzer.packagesChanged(cd1, cd2);
        assertEquals(testRes.size(), 0);
    }

    @Test
    public void testPackageChanged8(){
        CommitDependencies cd1 = new CommitDependencies();
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("package2");
        list1.add("package3");
        list1.add("package4");
        cd1.addPackage("package1", list1);

        CommitDependencies cd2 = new CommitDependencies();
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("package2");
        list2.add("package3");
        list2.add("package4");
        cd2.addPackage("package2", list2);

        ArrayList<String> testRes=  DifferenceAnalyzer.packagesChanged(cd1, cd2);
        assertEquals(0, testRes.size());
    }

    @Test
    public void testAddedPackage1(){
        CommitDependencies cd1 = new CommitDependencies();
        CommitDependencies cd2 = new CommitDependencies();

        assertEquals(DifferenceAnalyzer.packagesAdded(cd1, cd2).size(), 0);
    }

    @Test
    public void testAddedPackage2(){
        CommitDependencies cd1 = new CommitDependencies();
        CommitDependencies cd2 = new CommitDependencies();

        ArrayList<String> list1 = new ArrayList<>();
        cd1.addPackage("package1", list1);
        ArrayList<String> retRes = DifferenceAnalyzer.packagesAdded(cd1, cd2);
        assertEquals(retRes.size(), 1);
        assertEquals(retRes.get(0).equals("package1"), true);
    }

    @Test
    public void testAddedPackage3(){
        CommitDependencies cd1 = new CommitDependencies();
        CommitDependencies cd2 = new CommitDependencies();

        ArrayList<String> list1 = new ArrayList<>();
        list1.add("package2");
        cd1.addPackage("package1", list1);

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("package2");
        cd2.addPackage("package1", list2);

        ArrayList<String> retRes = DifferenceAnalyzer.packagesAdded(cd1, cd2);
        assertEquals(retRes.size(), 0);
    }

    @Test
    public void testAddedPackage4(){
        CommitDependencies cd1 = new CommitDependencies();
        CommitDependencies cd2 = new CommitDependencies();


        ArrayList<String> list2 = new ArrayList<>();
        list2.add("package2");
        cd2.addPackage("package1", list2);

        ArrayList<String> retRes = DifferenceAnalyzer.packagesAdded(cd1, cd2);

        assertEquals(retRes.size(), 0);
    }

    @Test
    public void testRemovedPackage1(){
        CommitDependencies cd1 = new CommitDependencies();
        CommitDependencies cd2 = new CommitDependencies();

        assertEquals(DifferenceAnalyzer.packageRemoved(cd1, cd2).size(), 0);
    }

    @Test
    public void testRemovedPackage2(){
        CommitDependencies cd1 = new CommitDependencies();
        CommitDependencies cd2 = new CommitDependencies();

        ArrayList<String> list1 = new ArrayList<>();
        cd1.addPackage("package1", list1);
        ArrayList<String> retRes = DifferenceAnalyzer.packageRemoved(cd1, cd2);
        assertEquals(retRes.size(), 0);
    }

    @Test
    public void testRemovedPackage3(){
        CommitDependencies cd1 = new CommitDependencies();
        CommitDependencies cd2 = new CommitDependencies();

        ArrayList<String> list1 = new ArrayList<>();
        list1.add("package2");
        cd1.addPackage("package1", list1);

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("package2");
        cd2.addPackage("package1", list2);

        ArrayList<String> retRes = DifferenceAnalyzer.packageRemoved(cd1, cd2);
        assertEquals(retRes.size(), 0);
    }

    @Test
    public void testRemovedPackage4(){
        CommitDependencies cd1 = new CommitDependencies();
        CommitDependencies cd2 = new CommitDependencies();


        ArrayList<String> list2 = new ArrayList<>();
        list2.add("package2");
        cd2.addPackage("package1", list2);

        ArrayList<String> retRes = DifferenceAnalyzer.packageRemoved(cd1, cd2);

        assertEquals(1, retRes.size());
        assertEquals(retRes.get(0).equals("package1"), true);
    }*/
}
