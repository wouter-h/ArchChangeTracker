package main;

import Analyzer.Analyzer;
import Args.ArgsManager;
import CSV.Writer.Writer;
import DataStorage.*;
import Reader.GraphmlFile.GraphmlFileInfo;
import Reader.GraphmlFile.Util;
import Reader.GraphmlReader;
import Reader.DirectoryReader;
import org.apache.tinkerpop.gremlin.structure.Graph;
import Analyzer.DifferenceAnalyzer;
import java.io.*;
import java.util.*;

public class SmallProgram {

    public static void run(ArgsManager argsManager, ArrayList<GraphmlFileInfo> files){
        if(files.size() < 2) return;
        Writer writer = new Writer(argsManager.getOutputFileLoc());
        writer.columnNames("commit,comparedTo,packages added,packages removed,dependency added,dependency removed,class moved,number of packages added,number of packages removed,number of dependencies added,number of dependencies removed,number of moved classes,number of packages,number of dependencies,MTO,A0,A2A,A2A Copy");
        Graph g1 = GraphmlReader.getGraph(files.get(0).getFile().toPath());
        Graph g2;
        PackageLookupTable plt1 = new PackageLookupTable();
        CommitDependencies cdh1 = Analyzer.getAllDependencies(plt1, g1);
        ClassLookupTable clt1 = new ClassLookupTable();
        cdh1.setClasses(Analyzer.getClasses(clt1, g1));
        PackageLookupTable plt2 = new PackageLookupTable();
        CommitDependencies cdh2;
        ClassLookupTable clt2 = new ClassLookupTable();
        DifferenceInfo dihStub = DifferenceAnalyzer.findDifferences(plt1, new PackageLookupTable(), clt1, new ClassLookupTable(), cdh1, new CommitDependencies());
        dihStub.setGraphName(files.get(0).getCommit());
        dihStub.setComparedGraphName("");
        writer.addEntryWithNewline(dihStub, plt1, plt2, clt1, clt2, cdh1, 2);
        int i = 1;
        do{
            g2 = GraphmlReader.getGraph(files.get(i).getFile().toPath());
            cdh2 = Analyzer.getAllDependencies(plt2, g2);
            cdh2.setClasses(Analyzer.getClasses(clt2, g2));
            DifferenceInfo dih = DifferenceAnalyzer.findDifferences(plt2, plt1, clt2, clt1, cdh2, cdh1);
            dih.setGraphName(files.get(i).getCommit());
            dih.setComparedGraphName(files.get(i - 1).getCommit());
            writer.addEntryWithNewline(dih, plt2, plt1, clt2, clt1, cdh2, i + 2);
            cdh1 = cdh2;
            plt1 = plt2;
            clt1 = clt2;
            plt2 = new PackageLookupTable();
            clt2 = new ClassLookupTable();
            ++i;
        } while(i < files.size());
        writer.close();
    }

    public static void main(String[] args){
        System.out.println("In ArchChangeTracker!");
        ArgsManager argsManager = new ArgsManager();
        if(!argsManager.isValidArgs(args)){
            return;
        }
        long startTime = System.nanoTime();
        ArrayList<File> files = new DirectoryReader(argsManager.getInputFileLoc()).read();
        ArrayList<GraphmlFileInfo> fileinfos = Util.fileToGraphmlFileInfo(files);
        Util.sort(fileinfos);
        run(argsManager, fileinfos);
        long endTime = System.nanoTime();
        long diff = endTime - startTime;
        System.out.println("total run time in seconds: " + ((double) diff / 1000000000));
    }

    public void secondMainFunction(String[] args){
        System.out.println("In ArchChangeTracker!");
        ArgsManager argsManager = new ArgsManager();
        if(!argsManager.isValidArgs(args)){
            return;
        }
        long startTime = System.nanoTime();
        ArrayList<File> files = new DirectoryReader(argsManager.getInputFileLoc()).read();
        ArrayList<GraphmlFileInfo> fileinfos = Util.fileToGraphmlFileInfo(files);
        Util.sort(fileinfos);
        run(argsManager, fileinfos);
        long endTime = System.nanoTime();
        long diff = endTime - startTime;
        System.out.println("total run time in seconds: " + ((double) diff / 1000000000));
    }
}
