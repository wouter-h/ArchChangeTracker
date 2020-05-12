import DataStorage.CommitDependencies;
import DataStorage.PackageInfo;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;

public class Analyzer {
    public static long totalTime = 0;

    public static CommitDependencies getAllDependencies(Graph g){
        CommitDependencies commitDependencies = new CommitDependencies();
        ArrayList<Vertex> vertices = retrieveAllPackages(g);
        for(Vertex v : vertices){
            ArrayList<Vertex> dependencies = retrieveAllPackageDependencies(g, v);
            String packageName = getName(g, v);
            ArrayList<String> dependencyNames = new ArrayList<>();
            for(Vertex dependency : dependencies){
                String dependencyName = getName(g, dependency);
                dependencyNames.add(dependencyName);
            }
            commitDependencies.addPackage(packageName, dependencyNames);
        }
        System.out.println("totalTime: " + (double) totalTime/10000000);//used to print execution time
        return commitDependencies;
    }

    public static String getName(Graph g, Vertex v){
        return (String) g.traversal().V(v).values("name").next();
    }

    public static ArrayList<Vertex> retrieveAllPackages(Graph g){
        GraphTraversal<Vertex, Vertex> gt = g.traversal().V().hasLabel("package");
        ArrayList<Vertex> vertices = new ArrayList<>();
        while(gt.hasNext()){
            vertices.add(gt.next());
        }
        return vertices;
    }

    public static ArrayList<Vertex> retrieveAllPackageDependencies(Graph g, Vertex v){
        //package dependencies
        GraphTraversal<Vertex, Vertex> gt1 = g.traversal().V(v).out("packageIsAfferentOf");
        //class dependencies
        //Get the packages V depends on with class dependencies <> package dependency relations
        GraphTraversal<Vertex, Vertex> gt2 = g.traversal().V().match(
                as("a").in("afferentOf").as("b"),
                as("b").out("belongsTo").hasId(v.id())
        ).select("a");
        //Get the packages V depends on with class <> class dependency relations
        GraphTraversal<Vertex, Vertex> gt3 = g.traversal().V().match(
                as("a").in("belongsTo").as("b"),
                as("b").in("dependsOn").as("c"),
                as("c").out("belongsTo").hasId(v.id())
        ).select("a");



        //collect all unique dependencies found
        /*ArrayList<Vertex> dependencies = new ArrayList<>();
        while(gt1.hasNext()){
            dependencies.add(gt1.next());
        }

        while(gt2.hasNext()){
            Vertex tempV = gt2.next();
            if(!dependencies.contains(tempV)){
                dependencies.add(tempV);
            }
        }

        while(gt3.hasNext()){
            Vertex tempV = gt3.next();
            if(!dependencies.contains(tempV)){
                dependencies.add(tempV);
            }
        }*/

        /*timing program*/
        long startTime = System.nanoTime();
        /*done timing program*/

        /*hashset variant*/

        HashSet<Vertex> dependencies = new HashSet<>(20);
        //dependencies.addAll(gt1.toList());
        dependencies.addAll(gt2.toList());
        dependencies.addAll(gt3.toList());


        /*timing program*/
        long endTime   = System.nanoTime();
        totalTime += endTime - startTime;
        /*done timing program*/
        return new ArrayList<Vertex>(dependencies);
    }

    public static CommitDependencies getAllDependencies1(Graph g){
        long startTime = System.nanoTime();
        CommitDependencies cd = retrieveAllPackageDependencies(g, retrieveAllPackages(g));
        long endTime = System.nanoTime();
        System.out.println("difference: " + ((double) endTime - startTime) / 10000000);
        return cd;
    }

    public static CommitDependencies retrieveAllPackageDependencies(Graph g, ArrayList<Vertex> vertices){
        //package dependencies
        GraphTraversal<Vertex, Map<String, Vertex>> gt1 = g.traversal().V(vertices).as("x").out("packageIsAfferentOf").as("y").select("x", "y");

        //class dependencies
        //Get the packages V depends on with class dependencies <> package dependency relations
        GraphTraversal<Vertex, Map<String, Vertex>> gt2 = g.traversal().V(vertices).as("x").match(
                as("a").in("afferentOf").as("b"),
                as("b").out("belongsTo").hasId(select("x").id())
        ).select("x", "a");
        //Get the packages V depends on with class <> class dependency relations
        GraphTraversal<Vertex, Map<String, Vertex>> gt3 = g.traversal().V(vertices).as("x").match(
                as("a").in("belongsTo").as("b"),
                as("b").in("dependsOn").as("c"),
                as("c").out("belongsTo").hasId(select("x").id())
        ).select("x", "a");

        ArrayList<String[]> dependencies = new ArrayList<>();
        List<Map<String, Vertex>> listm1 = gt1.toList();
        for(Map<String, Vertex> m : listm1){
            dependencies.add(new String[] {getName(g, m.get("x")), getName(g, m.get("y"))});
        }

        List<Map<String, Vertex>> listm2 = gt1.toList();
        for(Map<String, Vertex> m : listm2){
            dependencies.add(new String[] {getName(g, m.get("x")), getName(g, m.get("y"))});
        }

        List<Map<String, Vertex>> listm3 = gt1.toList();
        for(Map<String, Vertex> m : listm3){
            dependencies.add(new String[] {getName(g, m.get("x")), getName(g, m.get("y"))});
        }
        return new CommitDependencies(collapseDependencies(dependencies));
    }

    private static ArrayList<PackageInfo> collapseDependencies(ArrayList<String[]> uncollapsedDeps){
        ArrayList<PackageInfo> dependencies = new ArrayList<>();
        for(String[] strs : uncollapsedDeps){
            int i = isContainedIn(dependencies, strs[0]);
            if(i != -1){
                dependencies.get(i).addDependency(strs[1]);
            } else {
                ArrayList<String> localDependencies = new ArrayList<>();
                localDependencies.add(strs[1]);
                dependencies.add(new PackageInfo(strs[0], localDependencies));
            }
        }
        return dependencies;
    }

    private static int isContainedIn(ArrayList<PackageInfo> data, String s){
        int i = 0;
        for(PackageInfo d : data){
            if(d.getPackageName().equals(s)){
                return i;
            }
            ++i;
        }
        return -1;
    }
}
