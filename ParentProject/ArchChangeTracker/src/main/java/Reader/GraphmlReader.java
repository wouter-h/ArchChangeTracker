package Reader;

import org.apache.tinkerpop.gremlin.process.traversal.IO;
import org.apache.tinkerpop.gremlin.process.traversal.TextP;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import java.io.*;
import java.nio.file.Path;

/** Class that has a function that can read in a graphml file.
 *
 */
public class GraphmlReader {

    /**Reads in the graph associated to the Path graphMLPath
     *
     * @param graphMLPath the path of which a Graph needs to be read in. This path should direct towards a graphml file.
     * @return Graph containing the information in the graphml file given by the graphMLPath
     */
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
}
