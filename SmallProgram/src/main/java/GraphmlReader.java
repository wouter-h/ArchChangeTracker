import org.apache.tinkerpop.gremlin.process.computer.GraphComputer;
import org.apache.tinkerpop.gremlin.process.traversal.IO;
import org.apache.tinkerpop.gremlin.process.traversal.Text;
import org.apache.tinkerpop.gremlin.process.traversal.TextP;
import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import java.io.*;
import java.util.function.Predicate;
import java.nio.file.Path;

import static org.apache.tinkerpop.gremlin.process.traversal.P.within;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;

public class GraphmlReader {

    public static Graph getGraph(Path graphMLPath) {
        Graph graph = null;
        if (graph == null) {
            graph = TinkerGraph.open();
            try {
                File graphMLfile = graphMLPath.toFile();
                if (graphMLfile.isFile() && graphMLfile.canRead()) {
                    graph.traversal().io(graphMLPath.toAbsolutePath().toString())
                            .read().with(IO.reader, IO.graphml).iterate();
                }else {
                    throw new IOException("");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        graph.traversal().V()
                .has("Type", TextP.containing("retrieved"))
                .drop().iterate();
        graph.traversal().V()
                .has("PackageType", TextP.containing("Retrieved"))
                .drop().iterate();
        graph.traversal().V()
                .has("ClassType", TextP.containing("Retrieved"))
                .drop().iterate();


        return graph;
    }

    public static boolean graphIsDependentOn(Graph g, String source, String target){
        Vertex v = g.traversal().V().has("name", source).next();
        GraphTraversal<Vertex, Vertex> gt1 = g.traversal().V(v).out("packageIsAfferentOf").has("name", target);
        //GraphTraversal<Vertex, Vertex> gt2 = gt1.hasId("name", "12833");
        //GraphTraversal<Vertex, Edge> gt2 = gt1.values();
        //System.out.println(v.toString());
        //System.out.println(gt1.fold().next());
        //System.out.println(gt1.fold().next());
        return gt1.hasNext();
    }

    public static boolean checkClassDependencies(Graph g, String source, String target) {
        //System.out.println("source in checkClassDpendencies: " + source + " target: " + target);
        //GraphTraversal<Vertex, Object> gtve = g.traversal().V().as("x").out("belongsTo").has("name", source).select("x");
        //System.out.println(gtve.toString());
        //System.out.println(gtve.next().toString());
        /*GraphTraversal<Vertex, Vertex> gtv1 = g.traversal().V().match(
                as("x").out("belongsTo").has("name", source),
                as("x").coalesce(out("isAfferentOf"), out("dependsOn")).V().out("belongsTo")).V().has("name", target)
        ).select("a");*/
        if(g.traversal().V().match(
                as("a").out("belongsTo").has("name", source),
                as("a").out("isAfferentOf").has("name", target)
        ).hasNext()){
            //System.out.println("\n\n\n returned true!\n\n\n");
            return true;
        } else if(g.traversal().V().match(
                as("a").out("belongsTo").has("name", source),
                as("a").out("dependsOn").as("b"),
                as("b").out("belongsTo").has("name", target).as("c")
        ).hasNext()){
            /*System.out.println("-\n-\n-\n---- returned true! o: " +
                    g.traversal().V().match(
                    __.as("a").out("belongsTo").has("name", TextP.endingWith(source)),
                    __.as("a").out("dependsOn").as("b"),
                    __.as("b").out("belongsTo").has("name", TextP.endingWith(target)).as("c")
                    //__.as("b").has("name", TextP.endingWith(target)).in("belongsTo").V().as("c").in("dependsOn").as("a")
                    //__.as("a").out("dependsOn").out("belongsTo").has("name", target)
            ).select("a", "b", "c").next()
                    + "----\n-\n-\n-");*/
            return true;
        }
/*
        if (g.traversal().V().as("b").where(V("b").out("belongsTo").has("name", source)).as("a").out("dependsOn").V().out("belongsTo").V().has("name", TextP.endingWith(target)).select("a").hasNext()) {
            System.out.println("a -> b -> c" + " id: " +
                    g.traversal().V().as("b")
                            .where(V("b").out("belongsTo").has("name", source))
                            .as("a").out("dependsOn")
                            .V().as("c")
                            .out("belongsTo").V().as("d")
                            .has("name", TextP.endingWith(target)).hasNext()
            );
            return true;
        } else if (g.traversal().V().as("b").where(V("b").out("belongsTo").has("name", source)).as("a").out("isAfferentOf").has("name", TextP.endingWith(target)).hasNext()) {
            System.out.println("a -> b");
            return true;
        }*/
        /*if(true){
            //Object v = gtve.next();
            //class is connected to the target package
            if(g.traversal().V().as("x").out("belongsTo").has("name", source).select("x").out("isAfferentOf").has("name", TextP.endingWith(target)).hasNext()
                //|| g.traversal().V().as("x").out("belongsTo").has("name", source).select("x").out("dependsOn").V().out("belongsTo").V().has("name", TextP.endingWith(target)).hasNext()
            ){
                System.out.println("id1: " + g.traversal().V().as("x").out("belongsTo").has("name", source).select("x").out("isAfferentOf").has("name", TextP.endingWith(target)).next().toString());
                //System.out.println("id: " + g.traversal().V().as("x").out("belongsTo").has("name", source).select("x").out("dependsOn").V().out("belongsTo").V().has("name", TextP.endingWith(target)).next().toString());
                return true;
            } else if (g.traversal().V().as("x").out("belongsTo").has("name", source).select("x").out("dependsOn").V().out("belongsTo").V().has("name", TextP.endingWith(target)).hasNext()){
                Object a = g.traversal().V().match(as("a").out("belongsTo").has("name", source),
                        as("a").out("dependsOn").V().out("belongsTo").V().has("name", TextP.endingWith(target))
                ).select("a").next();
                Object b = g.traversal().V().where(out("belongsTo").has("name", source)).as("a").out("dependsOn").V().out("belongsTo").V().has("name", TextP.endingWith(target)).select("a");
                System.out.println("a: " + a + " b:" + b);
                System.out.println("id2: " + g.traversal().V().as("x").out("belongsTo").has("name", source).select("x").out("dependsOn").V().as("z").out("belongsTo").V().has("name", TextP.endingWith(target)).as("y").select("x", "y", "z").next().toString());
                return true;
            }*/
        //OR
        // class is connected to a class that is a member of target package
        //g.traversal().V(potentialClassSourceV).out(/*array of possible edges*/"dependsOn").V().out("belongsTo").V().has("name", target).next();
        // \\}
        return false;
    }
}
