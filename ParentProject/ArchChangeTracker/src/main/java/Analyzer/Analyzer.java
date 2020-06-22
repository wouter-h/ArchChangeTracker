package Analyzer;

import DataStorage.*;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.apache.tinkerpop.gremlin.process.traversal.TextP.containing;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;

public class Analyzer {
    /** Returns the name of a Vertex: v in a Graph: g
     *
     * @param g the graph to look for the vertex in
     * @param v the vertex to look up
     * @return the name of the vertex (the name field: "name" in the vertex)
     */
    private static String getName(Graph g, Vertex v){
        return (String) g.traversal().V(v).values("name").next();
    }

    /** Returns all vertices that have a class label in a Graph g
     *
     * @param g the Graph to find all class vertices in
     * @return ArrayList of Vertex of which the Vertex has a class label
     */
    private static ArrayList<Vertex> retrieveAllClasses(Graph g){
        GraphTraversal<Vertex, Vertex> gt = g.traversal().V().hasLabel("class");
        ArrayList<Vertex> vertices = new ArrayList<>();
        while(gt.hasNext()){
            vertices.add(gt.next());
        }
        return vertices;
    }

    /**This class creates the ClassInfo objects and returns these. It does so by retrieving the
     * name property from the vertex and retrieving the package name and class name out of this
     * name property. This package name and class name are stored inside a ClassInfo object.
     *
     * @param g the graph in which the vertices were found
     * @param vertices the vertices of which the ClassInfo objects need to be constructed.
     * @return an ArrayList of ClassInfo objects.
     */
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

    /** Takes in as parameters a ClassLookUpTable, in which the codes of the classes will be stored.
     * And a Graph in which the classes need to be found.
     *
     * @param clt the ClassLookUpTable that will be used to store the codes of the classes in.
     * @param g The graph in which the classes need to be found.
     * @return ArrayList of class names
     */
    public static ArrayList<String> getClasses(ClassLookupTable clt, Graph g){
        ArrayList<Vertex> vertices = retrieveAllClasses(g);
        ArrayList<ClassInfo> cia = getClassInfos(g, vertices);
        return collapseClasses(clt, cia);
    }

    /** Finds all package vertices in a graph g. It does so by checking if the vertex has a label "package".
     *
     * @param g the graph in which the package vertices need to be found.
     * @return ArrayList of vertices (Vertex) that have a label package.
     */
    public static ArrayList<Vertex> retrieveAllPackages(Graph g){
        GraphTraversal<Vertex, Vertex> gt = g.traversal().V().hasLabel("package");
        ArrayList<Vertex> vertices = new ArrayList<>();
        while(gt.hasNext()){
            vertices.add(gt.next());
        }
        return vertices;
    }

    /** Finds all packages and their dependencies and stores these inside a CommitDependencies object.
     * It also initializes the given PackageLookUpTable parameter
     *
     * @param plt PackageLookUpTable that will be used in combination with the CommitDependencies object.
     * @param g the graph in which the packages and dependencies need to be found.
     * @return CommitDependencies object
     */
    public static CommitDependencies getAllDependencies(PackageLookupTable plt, Graph g){
        ArrayList<Vertex> vertices = retrieveAllPackages(g);
        findAllDependencies(plt, g, vertices);  //initializes the package look up table
        CommitDependencies cd = retrieveAllPackageDependencies(g, vertices, plt);
        cd.setNrPackages(vertices.size());
        return cd;
    }

    /** Finds all package dependencies and stores these inside a CommitDependencies object and returns this
     *
     */
    private static CommitDependencies retrieveAllPackageDependencies(Graph g, ArrayList<Vertex> vertices, PackageLookupTable plt){
        //package dependencies
        GraphTraversal<Vertex, Map<String, Vertex>> gt1 = g.traversal().V(vertices).as("x").out("packageIsAfferentOf").as("y").select("x", "y");

        /*DONT THINK THIS PART IS NEEDED (NOR CORRECT)*/
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
        /*DONT THINK THIS PART IS NEEDED (NOR CORRECT)*/

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

    /** Initializes the PackageLookUpTable object. It does so by storing all packages found.
     *
     */
    private static void findAllDependencies(PackageLookupTable plt, Graph g, ArrayList<Vertex> packages){
        for(Vertex v : packages){
            plt.storagePackage(getName(g, v));
        }
    }

    /** Stores all package dependencies found.
     *
     * @param plt the PackageLookUpTable used to translate the package name to an integer code.
     * @param uncollapsedDeps the ArrayList of Strings[] that contains the dependencies {<package>, <dependency>}
     * @return ArrayList of PackageInfo objects
     */
    private static ArrayList<PackageInfo> collapseDependencies(PackageLookupTable plt, ArrayList<String[]> uncollapsedDeps){
        ArrayList<PackageInfo> dependencies = new ArrayList<>(plt.getSize());
        for(int i = 0; i < plt.getSize(); ++i){
            dependencies.add(new PackageInfo(i, new ArrayList<Integer>()));
        }
        for(String[] strs : uncollapsedDeps){
            dependencies.get(plt.getKey(strs[0])).addDependency(plt.getKey(strs[1]));
        }
        return dependencies;
    }


    /** Stores all classes found in a ClassLookUpTable so they get a code.
     *
     * @param clt The ClassLookUpTable in which the classes need to be stored.
     * @param cia The ArrayList of ClassInfo objects of which the class names will be stored inside the ClassLookUpTable.
     * @return
     */
    private static ArrayList<String> collapseClasses(ClassLookupTable clt, ArrayList<ClassInfo> cia){
        ArrayList<String> classes = new ArrayList<>();
        for(ClassInfo ci : cia){
            if(clt.get(ci.getName()) == null){//class does not exist yet
                classes.add(ci.getName());
            }
            clt.store(ci);
        }
        return classes;
    }

    public static void filterValidPackages(Graph g, PackageLookupTable allSystemPackages, ArrayList<Vertex> packages){
        ArrayList<Vertex> possibleSystemPackages = new ArrayList<>(packages.size());
        //GraphTraversal<Vertex, Vertex> gt1 = g.traversal().V(packages).has("Type", TextP.containing("retrieved"));
        //GraphTraversal<Vertex, Vertex> gt2 = g.traversal().V(packages).has("PackageType", TextP.containing("Retrieved"));
        GraphTraversal<Vertex, Vertex> gt4 = g.traversal().V(packages).has("PackageType", containing("System"));
        //GraphTraversal<Vertex, Vertex> gt3 = g.traversal().V(packages).has("ClassType", TextP.containing("Retrieved"));
        /*while(gt1.hasNext()){
            possibleSystemPackages.add(gt1.next());
        }*/
        ArrayList<Vertex> currentValidPackages = new ArrayList<>(possibleSystemPackages.size());
        while(gt4.hasNext()){
            Vertex systemPackage = gt4.next();
            allSystemPackages.storagePackage(getName(g, systemPackage));
            currentValidPackages.add(systemPackage);
        }

        /*while(gt2.hasNext()){
            possibleSystemPackages.add(gt2.next());
        }
        */
        /*while(gt3.hasNext()){
            possibleSystemPackages.add(gt3.next());
        }*/
        /*for(Vertex possibleSystemPackage : possibleSystemPackages){
            //The package is currently exists as a systempackage, so add it, even though it is a retrieved package
            if(allSystemPackages.getKey(getName(g, possibleSystemPackage)) != null){
                currentValidPackages.add(possibleSystemPackage);
            }
        }
        */
    }
}
