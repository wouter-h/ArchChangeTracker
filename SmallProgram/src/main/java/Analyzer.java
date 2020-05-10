import DataStorage.CommitDependencies;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.ArrayList;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.as;

public class Analyzer {

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
        return commitDependencies;
    }

    public static String getName(Graph g, Vertex v){
        return (String) g.traversal().V(v).values("name").next();
    }

    public static ArrayList<Vertex> retrieveAllPackages(Graph g){
        GraphTraversal<Vertex, Vertex> gt = g.traversal().V().hasLabel("package");
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
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
        ArrayList<Vertex> dependencies = new ArrayList<Vertex>();
        /*while(gt1.hasNext()){
            dependencies.add(gt1.next());
        }*/

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
        }
        return dependencies;
    }
}
