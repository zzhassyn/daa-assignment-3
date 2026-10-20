import graph.Graph;
import mst.Kruskal;
import mst.Prim;

public class Tests {

    public static void main(String[] args) {

        testSimpleGraph();
        testDisconnectedGraph();
        testMultipleMST();
        testSingleVertex();

        System.out.println("ALL TESTS PASSED");
    }

    private static void testSimpleGraph() {

        Graph graph = new Graph(4, false);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 3);
        graph.addEdge(0, 3, 10);

        Kruskal.Result k = Kruskal.run(graph.getVertices(), graph.getEdges());

        Prim.Result p = Prim.run(graph);

        check(k.connected, "Kruskal simple graph");
        check(p.connected, "Prim simple graph");

        check(k.totalWeight == 6, "Kruskal weight");
        check(p.totalWeight == 6, "Prim weight");

        check(k.totalWeight == p.totalWeight,
                "Kruskal and Prim same weight");

        System.out.println("Simple graph PASSED");
    }

    private static void testDisconnectedGraph() {

        Graph graph = new Graph(4, false);

        graph.addEdge(0, 1, 2);
        graph.addEdge(2, 3, 3);

        Kruskal.Result k = Kruskal.run(graph.getVertices(), graph.getEdges());

        Prim.Result p = Prim.run(graph);

        check(!k.connected, "Kruskal disconnected graph");
        check(!p.connected, "Prim disconnected graph");

        System.out.println("Disconnected graph PASSED");
    }

    private static void testMultipleMST() {

        Graph graph = new Graph(4, false);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 0, 1);

        Kruskal.Result k = Kruskal.run(graph.getVertices(), graph.getEdges());

        Prim.Result p = Prim.run(graph);

        check(k.connected, "Kruskal multiple MST");
        check(p.connected, "Prim multiple MST");

        check(k.totalWeight == 3, "Kruskal multiple MST weight");
        check(p.totalWeight == 3, "Prim multiple MST weight");

        check(k.totalWeight == p.totalWeight,
                "Same weight for multiple MST");

        System.out.println("Multiple MST PASSED");
    }

    private static void testSingleVertex() {

        Graph graph = new Graph(1, false);

        Kruskal.Result k = Kruskal.run(graph.getVertices(), graph.getEdges());

        Prim.Result p = Prim.run(graph);

        check(k.connected, "Kruskal single vertex");
        check(p.connected, "Prim single vertex");

        check(k.totalWeight == 0, "Kruskal single vertex weight");
        check(p.totalWeight == 0, "Prim single vertex weight");

        System.out.println("Single vertex PASSED");
    }

    private static void check(boolean condition, String message) {

        if (!condition) {
            throw new AssertionError("FAILED: " + message);
        }
    }
}