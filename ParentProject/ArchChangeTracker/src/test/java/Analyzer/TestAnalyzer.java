package Analyzer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAnalyzer {

    @Test
    public void testRetrieveAllPackageDependenciesHash(){
       /* Graph g = TinkerGraph.open();
        g.traversal().addV("package").property("id", 1).property("name", "package1").next();
        g.traversal().addV("package").property("id", 2).property("name", "package2").next();
        g.traversal().V(1).addE("packageIsAfferentOf").V(2).iterate();

        Vertex v1 = g.addV("package").property("id", 1).property("name", "package1").next();
        Vertex v2 = g.addV("package").property("id", 2).property("name", "package2");
        v1.addEdge("packageIsAfferentOf", v2);

        PackageLookupTable plt = new PackageLookupTable();
        CommitDependenciesHash cdh = Analyzer.getAllDependencies1Hash(plt, g);
        ArrayList<PackageInfoHash> packages = cdh.getPackages();
        assertEquals(2, packages.size());
    */}
}
