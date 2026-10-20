import connectivity.SCC;
import connectivity.TopologicalSort;
import graph.Graph;
import mst.Kruskal;
import mst.Prim;
import shortestpath.Dijkstra;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        runMSTExample();

        System.out.println();

        runTopologicalExample();

        System.out.println();

        runSCCExample();

        System.out.println();

        runDijkstraExample();
    }

    private static void runMSTExample() {

        System.out.println("=== MST ===");

        Graph graph = new Graph(9, false);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 7, 8);
        graph.addEdge(1, 2, 8);
        graph.addEdge(1, 7, 11);
        graph.addEdge(2, 3, 7);
        graph.addEdge(2, 8, 2);
        graph.addEdge(2, 5, 4);
        graph.addEdge(3, 4, 9);
        graph.addEdge(3, 5, 14);
        graph.addEdge(4, 5, 10);
        graph.addEdge(5, 6, 2);
        graph.addEdge(6, 7, 1);
        graph.addEdge(6, 8, 6);
        graph.addEdge(7, 8, 7);

        Kruskal.Result kruskal = Kruskal.run(
                graph.getVertices(),
                graph.getEdges());

        Prim.Result prim = Prim.run(graph);

        System.out.println("Kruskal weight: "
                + kruskal.totalWeight);

        System.out.println("Kruskal connected: "
                + kruskal.connected);

        System.out.println("Kruskal edge checks: "
                + kruskal.edgeChecks);

        System.out.println("Kruskal find calls: "
                + kruskal.findCount);

        System.out.println("Kruskal union calls: "
                + kruskal.unionCount);

        System.out.println();

        System.out.println("Prim weight: "
                + prim.totalWeight);

        System.out.println("Prim connected: "
                + prim.connected);

        System.out.println("Prim edge checks: "
                + prim.edgeChecks);
    }

    private static void runTopologicalExample() {

        System.out.println("=== Topological Sort ===");

        Graph graph = new Graph(6, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 4, 1);
        graph.addEdge(4, 5, 1);

        TopologicalSort.Result result = TopologicalSort.sort(graph);

        if (result.hasCycle) {
            System.out.println(
                    "Topological ordering is impossible.");
        } else {
            System.out.println(
                    "Topological order: "
                            + result.order);
        }
    }

    private static void runSCCExample() {

        System.out.println("=== SCC ===");

        Graph graph = new Graph(5, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, 1);

        graph.addEdge(2, 3, 1);

        graph.addEdge(3, 4, 1);
        graph.addEdge(4, 3, 1);

        List<List<Integer>> components = SCC.kosaraju(graph);

        System.out.println(
                "Strongly connected components:");

        for (List<Integer> component : components) {
            System.out.println(component);
        }
    }

    private static void runDijkstraExample() {

        System.out.println("=== Dijkstra ===");

        Graph graph = new Graph(6, true);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 1);
        graph.addEdge(2, 1, 2);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 5);
        graph.addEdge(3, 4, 3);

        Dijkstra.Result result = Dijkstra.run(graph, 0);

        for (int vertex = 0; vertex < graph.getVertices(); vertex++) {

            System.out.print(
                    "Vertex " + vertex + ": ");

            if (!result.isReachable(vertex)) {

                System.out.println("unreachable");

            } else {

                System.out.println(
                        "distance = "
                                + result.distance[vertex]
                                + ", path = "
                                + result.getPath(vertex));
            }
        }

        System.out.println(
                "Dijkstra edge checks: "
                        + result.edgeChecks);
    }
}