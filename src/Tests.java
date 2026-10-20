import connectivity.SCC;
import connectivity.TopologicalSort;
import graph.Graph;
import mst.Kruskal;
import mst.Prim;
import shortestpath.Dijkstra;

import java.util.List;

public class Tests {

    public static void main(String[] args) {

        testSimpleGraph();
        testDisconnectedGraph();
        testMultipleMST();
        testSingleVertex();

        testTopologicalSortDAG();
        testTopologicalSortCycle();
        testSCC();

        testDijkstra();
        testDijkstraUnreachable();
        testDijkstraNegativeWeight();

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

        check(
                k.totalWeight == p.totalWeight,
                "Kruskal and Prim should have same weight");

        System.out.println("Simple graph PASSED");
    }

    private static void testDisconnectedGraph() {

        Graph graph = new Graph(4, false);

        graph.addEdge(0, 1, 2);
        graph.addEdge(2, 3, 3);

        Kruskal.Result k = Kruskal.run(graph.getVertices(), graph.getEdges());

        Prim.Result p = Prim.run(graph);

        check(
                !k.connected,
                "Kruskal should detect disconnected graph");

        check(
                !p.connected,
                "Prim should detect disconnected graph");

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

        check(
                k.totalWeight == 3,
                "Kruskal multiple MST weight");

        check(
                p.totalWeight == 3,
                "Prim multiple MST weight");

        check(
                k.totalWeight == p.totalWeight,
                "Kruskal and Prim should have same MST weight");

        System.out.println("Multiple MST PASSED");
    }

    private static void testSingleVertex() {

        Graph graph = new Graph(1, false);

        Kruskal.Result k = Kruskal.run(graph.getVertices(), graph.getEdges());

        Prim.Result p = Prim.run(graph);

        check(
                k.connected,
                "Kruskal single vertex");

        check(
                p.connected,
                "Prim single vertex");

        check(
                k.totalWeight == 0,
                "Kruskal single vertex weight");

        check(
                p.totalWeight == 0,
                "Prim single vertex weight");

        System.out.println("Single vertex PASSED");
    }

    private static void testTopologicalSortDAG() {

        Graph graph = new Graph(4, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 1);

        TopologicalSort.Result result = TopologicalSort.sort(graph);

        check(
                !result.hasCycle,
                "DAG should not have cycle");

        check(
                result.order.size() == 4,
                "Topological order should contain all vertices");

        System.out.println("Topological DAG PASSED");
    }

    private static void testTopologicalSortCycle() {

        Graph graph = new Graph(3, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, 1);

        TopologicalSort.Result result = TopologicalSort.sort(graph);

        check(
                result.hasCycle,
                "Cycle should be detected");

        System.out.println("Topological cycle PASSED");
    }

    private static void testSCC() {

        Graph graph = new Graph(5, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, 1);

        graph.addEdge(2, 3, 1);

        graph.addEdge(3, 4, 1);
        graph.addEdge(4, 3, 1);

        List<List<Integer>> components = SCC.kosaraju(graph);

        check(
                components.size() == 2,
                "SCC should find 2 components");

        int totalVertices = 0;

        for (List<Integer> component : components) {
            totalVertices += component.size();
        }

        check(
                totalVertices == 5,
                "Every vertex should be in one SCC");

        System.out.println("SCC PASSED");
    }

    private static void testDijkstra() {

        Graph graph = new Graph(5, true);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 1);
        graph.addEdge(2, 1, 2);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 5);
        graph.addEdge(3, 4, 3);

        Dijkstra.Result result = Dijkstra.run(graph, 0);

        check(
                result.distance[0] == 0,
                "Distance to source should be 0");

        check(
                result.distance[1] == 3,
                "Distance to vertex 1 should be 3");

        check(
                result.distance[2] == 1,
                "Distance to vertex 2 should be 1");

        check(
                result.distance[3] == 4,
                "Distance to vertex 3 should be 4");

        check(
                result.distance[4] == 7,
                "Distance to vertex 4 should be 7");

        check(
                result.getPath(4).equals(
                        List.of(0, 2, 1, 3, 4)),
                "Shortest path to vertex 4");

        System.out.println("Dijkstra PASSED");
    }

    private static void testDijkstraUnreachable() {

        Graph graph = new Graph(4, true);

        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 2, 3);

        Dijkstra.Result result = Dijkstra.run(graph, 0);

        check(
                result.isReachable(0),
                "Source should be reachable");

        check(
                result.isReachable(1),
                "Vertex 1 should be reachable");

        check(
                result.isReachable(2),
                "Vertex 2 should be reachable");

        check(
                !result.isReachable(3),
                "Vertex 3 should be unreachable");

        check(
                result.getPath(3).isEmpty(),
                "Unreachable path should be empty");

        System.out.println("Dijkstra unreachable PASSED");
    }

    private static void testDijkstraNegativeWeight() {

        Graph graph = new Graph(2, true);

        graph.addEdge(0, 1, -5);

        boolean exceptionThrown = false;

        try {

            Dijkstra.run(graph, 0);

        } catch (IllegalArgumentException e) {

            exceptionThrown = true;
        }

        check(
                exceptionThrown,
                "Dijkstra should reject negative weights");

        System.out.println("Dijkstra negative weight PASSED");
    }

    private static void check(
            boolean condition,
            String message) {

        if (!condition) {
            throw new AssertionError(
                    "FAILED: " + message);
        }
    }
}