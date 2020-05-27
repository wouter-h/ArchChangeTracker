package Analyzer;

import DataStorage.*;
import org.apache.tinkerpop.gremlin.process.traversal.TextP;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;

public class Analyzer {

    private static String getName(Graph g, Vertex v){
        return (String) g.traversal().V(v).values("name").next();
    }

    private static ArrayList<Vertex> retrieveAllClasses(Graph g){
        GraphTraversal<Vertex, Vertex> gt = g.traversal().V().hasLabel("class").has("name", TextP.notContaining("$"));
        ArrayList<Vertex> vertices = new ArrayList<>();
        while(gt.hasNext()){
            vertices.add(gt.next());
        }
        return vertices;
    }

    private static ArrayList<ClassInfo> getClassInfos(Graph g, ArrayList<Vertex> vertices){
        GraphTraversal<Vertex, Vertex> gt = g.traversal().V(vertices);
        ArrayList<ClassInfo> classInfos = new ArrayList<>();
        List<Vertex> vertexList = gt.toList();
        for(Vertex v : vertexList){
            String str = (String) v.property("name").value();
            String[] elements = str.split("\\.");
            ClassInfo ci = new ClassInfo(str.substring(0, str.length() - elements[elements.length - 1].length()), elements[elements.length - 1]);
            classInfos.add(ci);
        }
        return classInfos;
    }

    public static ArrayList<String> getClasses(ClassLookupTable clt, Graph g){
        ArrayList<Vertex> vertices = retrieveAllClasses(g);
        ArrayList<ClassInfo> cia = getClassInfos(g, vertices);
        return collapseClasses(clt, cia);
    }

    private static ArrayList<Vertex> retrieveAllPackages(Graph g){
        GraphTraversal<Vertex, Vertex> gt = g.traversal().V().hasLabel("package");
        ArrayList<Vertex> vertices = new ArrayList<>();
        while(gt.hasNext()){
            vertices.add(gt.next());
        }
        return vertices;
    }

    public static CommitDependencies getAllDependencies(PackageLookupTable plt, Graph g){
        ArrayList<Vertex> vertices = retrieveAllPackages(g);
        findAllDependencies(plt, g, vertices);
        CommitDependencies cd = retrieveAllPackageDependencies(g, vertices, plt);
        cd.setNrPackages(vertices.size());
        return cd;
    }

    private static CommitDependencies retrieveAllPackageDependencies(Graph g, ArrayList<Vertex> vertices, PackageLookupTable plt){
        //package dependencies
        GraphTraversal<Vertex, Map<String, Vertex>> gt1 = g.traversal().V(vertices).as("x").out("packageIsAfferentOf").as("y").select("x", "y");

        //class dependencies
        //Get the packages V depends on with class dependencies <> package dependency relations
        GraphTraversal<Vertex, Map<String, Vertex>> gt2 = g.traversal().V(vertices).as("x").match(
                as("a").in("afferentOf").as("b"),
                as("b").out("belongsTo").hasId(select("x").id()).as("y")
        ).select("x", "y");
        //Get the packages V depends on with class <> class dependency relations
        GraphTraversal<Vertex, Map<String, Vertex>> gt3 = g.traversal().V(vertices).as("x").match(
                as("a").in("belongsTo").as("b"),
                as("b").in("dependsOn").as("c"),
                as("c").out("belongsTo").hasId(select("x").id()).as("y")
        ).select("x", "y");

        ArrayList<String[]> dependencies = new ArrayList<>();
        List<Map<String, Vertex>> listm1 = gt1.toList();
        for(Map<String, Vertex> m : listm1){
            dependencies.add(new String[] {getName(g, m.get("x")), getName(g, m.get("y"))});
        }

        List<Map<String, Vertex>> listm2 = gt2.toList();
        for(Map<String, Vertex> m : listm2){
            dependencies.add(new String[] {getName(g, m.get("x")), getName(g, m.get("y"))});
        }

        List<Map<String, Vertex>> listm3 = gt3.toList();
        for(Map<String, Vertex> m : listm3){
            dependencies.add(new String[] {getName(g, m.get("x")), getName(g, m.get("y"))});
        }
        return new CommitDependencies(collapseDependencies(plt, dependencies));
    }

    private static void findAllDependencies(PackageLookupTable plt, Graph g, ArrayList<Vertex> packages){
        for(Vertex v : packages){
            plt.storagePackage(getName(g, v));
        }
    }

    private static ArrayList<PackageInfo> collapseDependencies(PackageLookupTable plt, ArrayList<String[]> uncollapsedDeps){
        /*for(String[] strs : uncollapsedDeps){
            plt.storagePackage(strs[0]);
            plt.storagePackage(strs[1]);
        }*/
        ArrayList<PackageInfo> dependencies = new ArrayList<>(plt.getSize());
        for(int i = 0; i < plt.getSize(); ++i){
            dependencies.add(new PackageInfo(i, new ArrayList<Integer>()));
        }
        for(String[] strs : uncollapsedDeps){
            dependencies.get(plt.getKey(strs[0])).addDependency(plt.getKey(strs[1]));
        }
        return dependencies;
    }

    private static ArrayList<String> collapseClasses(ClassLookupTable clt, ArrayList<ClassInfo> cia){
        ArrayList<String> classes = new ArrayList<>();
        for(ClassInfo ci : cia){
            if(clt.get(ci.getName()) == null){//clas does not exist yet
                classes.add(ci.getName());
            }
            clt.store(ci);
        }
        return classes;
    }
}
