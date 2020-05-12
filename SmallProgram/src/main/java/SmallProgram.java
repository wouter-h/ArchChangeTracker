import DataStorage.*;
import Analyzer.DifferenceAnalyzer;
import Analyzer.DifferenceAnalyzerHash;
import org.apache.tinkerpop.gremlin.structure.Graph;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class SmallProgram {

    static long totalTime = 0;

    public static Csvobject[] readCSVFile(String loc){
        Csvobject[] csventries = new Csvobject[45];
        try(FileReader reader = new FileReader(loc);
        BufferedReader br = new BufferedReader(reader)){
            List<String[]> arrayBufferList = new ArrayList<>(47);
            String line = null;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",", -1);
                for(String part : parts){
                    System.out.println(part);
                }
                arrayBufferList.add(parts);
            }
            arrayBufferList.remove(0);
            for(int i = 0; i < 45; ++i){
                csventries[i] = new Csvobject(null, i, 45);
            }

            for(int i = 0; i < 45; ++i){
                for(int j = 0; j < 47; ++j){
                    //System.out.println("i: " + i + " j: " + j + " arraybufferListSize: " + arrayBufferList.size() + " arraybufferList.get().size(): " + arrayBufferList.get(i).length);
                    if(j == 0){

                    } else if (j == 1) {
                        csventries[i].setPackageName(arrayBufferList.get(i)[j]);
                    } else { // j >= 2
                        if(arrayBufferList.get(i)[j].equals("")){
                            csventries[j - 2].setDependenciesIdx(i, 0);
                        } else {
                            csventries[j - 2].setDependenciesIdx(i, Integer.parseInt(arrayBufferList.get(i)[j]));
                        }
                    }
                }
            }
            //System.out.println(csventries[44].getPackageName() + csventries[44].getDependenciesIdx(38));
            //System.out.println(csventries[42].getPackageName() + csventries[42].getDependenciesIdx(44));
        } catch (IOException e){
            System.err.format("IOException: %s%n", e);
        }
        return csventries;
    }

    public static void compareCSVtoGraphml(Csvobject[] csventries, Graph g){
        int totalSum = 0;
        int totalRight = 0;
        for(int j = 40; j == 40 /*csventries.length*/; ++j){
            String target = csventries[j].getPackageName().substring(5);
            double accuracy = 0;
            int total = 0;
            int right = 0;
            for(int i = 0; i < 45/*csventries[i].getDependencies().length*/; ++i){
                int n = csventries[i].getDependenciesIdx(j);
                if(n > 0 ){
                    //System.out.println("+---------------------");
                    String source = csventries[i].getPackageName().substring(5);
                    //System.out.println("i: " + i + " j: " + j + " source: " + source + " target: " + target);
                    //System.out.println("-\n" + GraphmlReader.checkClassDependencies(g, source, target) + "-\n");
                    boolean isEqual = GraphmlReader.graphIsDependentOn(g, source, target) || GraphmlReader.checkClassDependencies(g, source, target);
                    total++;
                    if(isEqual) right++;
                    if(!isEqual){
                        System.out.println("+---------------------");
                        System.out.println("i: " + i + " j: " + j + " source: " + source + " target: " + target);
                        System.out.println("Detected: " + isEqual);
                        System.out.println("---------------------");
                    }
                }
            }
            System.out.println("j: " + j + " " + right + "/" + total + " target: " + target);
            totalSum += total;
            totalRight += right;
            accuracy = (double) right / total;
        }
        System.out.println("totalRight: " + totalRight + " totalSum: " + totalSum);
    }


    public static void analyzeGraphs(String graphml1, String graphml2){
        CommitDependencies cd1 = Analyzer.getAllDependencies(GraphmlReader.getGraph(Paths.get(graphml1)));
        CommitDependencies cd2 = Analyzer.getAllDependencies(GraphmlReader.getGraph(Paths.get(graphml2)));
        DifferenceInfo di = DifferenceAnalyzer.findDifferences(cd1, cd2);
        di.setGraphName(graphml1);
        di.setComparedGraphName(graphml2);
    }

    public static void main(String[] args){
        long programExecutionTimeStart = System.nanoTime();
        //Csvobject[] csventries = SmallProgram.readCSVFile("/home/muffin/Documents/Universiteit/master internship/structure101 spark dependency leaf packages.csv");
        //Graph g = GraphmlReader.getGraph(Paths.get("/home/muffin/Documents/Universiteit/master internship/graph-208-12_2_2020-1e6b4e813bd73f38742595b79692b2645a2d9c4d.graphml"));
        Graph g1 = GraphmlReader.getGraph(Paths.get("/home/muffin/Documents/Universiteit/master internship/graph-2858-9_5_2020-910ee21bb9631b0036c69ed02dce9fdec15f1360.graphml"));
        Graph g2 = GraphmlReader.getGraph(Paths.get("/home/muffin/Documents/Universiteit/master internship/graph-2859-9_5_2020-6b9e6b6d616f40980734ba21f056998a88d739c9.graphml"));
        long readingTime = System.nanoTime();
        long readingTimeDiff = readingTime - programExecutionTimeStart;
        System.out.println("Reading time of 2 graphml files: " + ((double) (readingTimeDiff / 10000000)));
        //compareCSVtoGraphml(csventries, g);
        CommitDependencies cd1 = null;
        CommitDependencies cd2 = null;
        //Analyzer.getAllDependencies(g1);
        //Analyzer.getAllDependencies(g2);
        cd1 = Analyzer.getAllDependencies1(g1);
        cd2 = Analyzer.getAllDependencies1(g2);
        PackageLookupTable plt3 = new PackageLookupTable();
        PackageLookupTable plt4 = new PackageLookupTable();
        CommitDependenciesHash cd3 = Analyzer.getAllDependencies1Hash(plt3, g1);
        CommitDependenciesHash cd4 = Analyzer.getAllDependencies1Hash(plt4, g2);
        for(int i = 0; i < 1; ++i) {
            /*timing program*/
            long startTime = System.nanoTime();
            /*done timing program*/
            DifferenceInfo di = DifferenceAnalyzer.findDifferences(cd2, cd1);
            di.setGraphName("some name");
            di.setComparedGraphName("some other name");
            /*timing program*/
            long endTime   = System.nanoTime();
            totalTime += endTime - startTime;
            System.out.println("\t totalTime: " + ((double) totalTime) / 10000000);
            /*done timing program*/
            /*start timing hash program*/
            startTime = System.nanoTime();
            /*done timing program*/
            DifferenceInfoHash dih = DifferenceAnalyzerHash.findDifferences(plt4, plt3, cd4, cd3);
            di.setGraphName("some name");
            di.setComparedGraphName("some other name");
            /*timing program*/
            endTime   = System.nanoTime();
            totalTime += endTime - startTime;
            System.out.println("\t totalTime hash: " + ((double) totalTime) / 10000000);
            /*done timing hash program*/
        }
        //cd1.prettyPrint();
        long programExecutionTimeEnd = System.nanoTime();
        long programExecutionTimeDifference = programExecutionTimeEnd - programExecutionTimeStart;
        System.out.println("Total program execution time in seconds:" + ((double) (programExecutionTimeDifference / 1000000000)));
    }
}
