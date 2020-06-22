package Reader;

import org.apache.tinkerpop.gremlin.process.traversal.IO;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.TextP;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;
import static org.apache.tinkerpop.gremlin.process.traversal.TextP.containing;
import static org.apache.tinkerpop.gremlin.process.traversal.TextP.notContaining;

/** Class that has a function that can read in a graphml file.
 *
 */
public class GraphmlReader {

    /**Reads in the graph associated to the Path graphMLPath
     *
     * @param graphMLPath the path of which a Graph needs to be read in. This path should direct towards a graphml file.
     * @return Graph containing the information in the graphml file given by the graphMLPath
     */
    public static Graph getGraph(Path graphMLPath, String[] names) {
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


        /*graph.traversal().V()
                .has("Type", TextP.containing("retrieved"))
                .drop().iterate();
        graph.traversal().V()
                .has("PackageType", TextP.containing("Retrieved"))
                .drop().iterate();
        graph.traversal().V()
                .has("ClassType", TextP.containing("Retrieved"))
                .drop().iterate();
        */

        graph.traversal().V()
            //.has("name", notContaining(name))
              .has("name", not(createPredicate(names)))
              .drop().iterate();

        return graph;
    }

    private static P<String> createPredicate(String[] names){
        ArrayList<TextP> textPredicates = new ArrayList<>();
        for(String name : names){
            textPredicates.add(TextP.containing(name));
        }
        //P<String> predicate = textPredicates.get(0).or(textPredicates.get(1));
        P<String> p0 = textPredicates.get(0);
        //P<String> p1 = p0.or(textPredicates.get(1));
        //P<String> p2 = p1.or(textPredicates.get(2));
        for(int i = 1; i < textPredicates.size(); ++i){
            p0 = p0.or(textPredicates.get(i));
        }
        /*TextP predicate = TextP.containing(names[0]);
        TextP predicate1 = TextP.containing(names[0]);
        P p = predicate.or(predicate1);
        for(int i = 1; i < names.length; ++i){
            predicate.or(containing(names[i]));
        }*/
        return p0;
    }

}
