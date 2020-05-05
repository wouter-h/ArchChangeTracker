import org.apache.tinkerpop.gremlin.structure.Graph;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class SmallProgram {

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
                    System.out.println("i: " + i + " j: " + j + " arraybufferListSize: " + arrayBufferList.size() + " arraybufferList.get().size(): " + arrayBufferList.get(i).length);
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
            System.out.println(csventries[44].getPackageName() + csventries[44].getDependenciesIdx(38));
            System.out.println(csventries[42].getPackageName() + csventries[42].getDependenciesIdx(44));
        } catch (IOException e){
            System.err.format("IOException: %s%n", e);
        }
        return csventries;
    }

    public static void compareCSVtoGraphml(Csvobject[] csventries, Graph g){
        int totalSum = 0;
        int totalRight = 0;
        for(int i = 0; i < csventries.length; ++i){
            String source = csventries[i].getPackageName().substring(5);
            double accuracy = 0;
            int total = 0;
            int right = 0;
            for(int j = 0; j < csventries[i].getDependencies().length; ++j){
                int n = csventries[i].getDependenciesIdx(j);
                if(n > 0 ){
                    System.out.println("+---------------------");
                    String target = csventries[j].getPackageName().substring(5);
                    System.out.println("i: " + i + " j: " + j + " source: " + source + " target: " + target);
                    //System.out.println("-\n" + GraphmlReader.checkClassDependencies(g, source, target) + "-\n");
                    boolean isEqual = GraphmlReader.graphIsDependentOn(g, source, target) || GraphmlReader.checkClassDependencies(g, source, target);
                    total++;
                    if(isEqual) right++;
                    System.out.println("---------------------");
                }
            }
            totalSum += total;
            totalRight += right;
            accuracy = (double) right / total;
            //System.out.println("accuracy: " + accuracy + "total: " + total + " right: " + right + " source: " + source);
        }
        System.out.println("totalRight: " + totalRight + " totalSum: " + totalSum);
    }

    public static void main(String[] args){
        Csvobject[] csventries = SmallProgram.readCSVFile("/home/muffin/Documents/Universiteit/master internship/structure101 spark dependency leaf packages.csv");
        Graph g = GraphmlReader.getGraph(Paths.get("/home/muffin/Documents/Universiteit/master internship/graph-208-12_2_2020-1e6b4e813bd73f38742595b79692b2645a2d9c4d.graphml"));
        //System.out.print(GraphmlReader.graphIsDependentOn(g, "org.jivesoftware.spark.component.panes", "java.awt"));
        compareCSVtoGraphml(csventries, g);
    }
}
