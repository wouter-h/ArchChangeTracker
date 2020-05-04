import org.apache.tinkerpop.gremlin.process.traversal.IO;
import org.apache.tinkerpop.gremlin.process.traversal.Text;
import org.apache.tinkerpop.gremlin.process.traversal.TextP;
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

        /*
        graph.traversal().V()
                .has("ClassType",
                        TextP.containing("Retrieved"))
                .drop().iterate();
        graph.traversal().V()
                .has("PackageType", TextP.containing("Retrieved"))
                .drop().iterate();
        */

        return graph;
    }

    public static boolean graphIsDependentOn(Graph g, String source, String target){
        Vertex v = g.traversal().V().has("name", source).next();
        GraphTraversal<Vertex, Vertex> gt1 = g.traversal().V(v).out("packageIsAfferentOf").has("name", target);
        //GraphTraversal<Vertex, Vertex> gt2 = gt1.hasId("name", "12833");
        //GraphTraversal<Vertex, Edge> gt2 = gt1.values();
        System.out.println(v.toString());
        //System.out.println(gt1.fold().next());
        //System.out.println(gt1.fold().next());
        return gt1.hasNext();
    }
}
