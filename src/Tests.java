import connectivity.SCC;
import connectivity.TopologicalSort;
import graph.Edge;
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

                System.out.println(
                                "ALL TESTS PASSED");
        }

        private static void testSimpleGraph() {

                Graph graph = new Graph(4, false);

                graph.addEdge(0, 1, 1);
                graph.addEdge(1, 2, 2);
                graph.addEdge(2, 3, 3);
                graph.addEdge(0, 3, 10);

                Kruskal.Result k = Kruskal.run(
                                graph.getVertices(),
                                graph.getEdges());

                Prim.Result p = Prim.run(graph);

                check(
                                k.connected,
                                "Kruskal simple graph");

                check(
                                p.connected,
                                "Prim simple graph");

                check(
                                k.totalWeight == 6,
                                "Kruskal weight");

                check(
                                p.totalWeight == 6,
                                "Prim weight");

                check(
                                k.totalWeight == p.totalWeight,
                                "Kruskal and Prim same weight");

                System.out.println(
                                "Simple graph PASSED");
        }

        private static void testDisconnectedGraph() {

                Graph graph = new Graph(4, false);

                graph.addEdge(0, 1, 2);
                graph.addEdge(2, 3, 3);

                Kruskal.Result k = Kruskal.run(
                                graph.getVertices(),
                                graph.getEdges());

                Prim.Result p = Prim.run(graph);

                check(
                                !k.connected,
                                "Kruskal disconnected graph");

                check(
                                !p.connected,
                                "Prim disconnected graph");

                System.out.println(
                                "Disconnected graph PASSED");
        }

        private static void testMultipleMST() {

                Graph graph = new Graph(4, false);

                graph.addEdge(0, 1, 1);
                graph.addEdge(1, 2, 1);
                graph.addEdge(2, 3, 1);
                graph.addEdge(3, 0, 1);

                Kruskal.Result k = Kruskal.run(
                                graph.getVertices(),
                                graph.getEdges());

                Prim.Result p = Prim.run(graph);

                check(
                                k.connected,
                                "Kruskal multiple MST");

                check(
                                p.connected,
                                "Prim multiple MST");

                check(
                                k.totalWeight == 3,
                                "Kruskal multiple MST weight");

                check(
                                p.totalWeight == 3,
                                "Prim multiple MST weight");

                check(
                                k.totalWeight == p.totalWeight,
                                "Same MST weight");

                System.out.println(
                                "Multiple MST PASSED");
        }

        private static void testSingleVertex() {

                Graph graph = new Graph(1, false);

                Kruskal.Result k = Kruskal.run(
                                graph.getVertices(),
                                graph.getEdges());

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

                System.out.println(
                                "Single vertex PASSED");
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
                                isValidTopologicalOrder(
                                                graph,
                                                result.order),
                                "Topological order should be valid");

                System.out.println(
                                "Topological DAG PASSED");
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

                System.out.println(
                                "Topological cycle PASSED");
        }

        private static void testSCC() {

                Graph graph = new Graph(5, true);

                graph.addEdge(0, 1, 1);
                graph.addEdge(1, 2, 1);
                graph.addEdge(2, 0, 1);

                graph.addEdge(2, 3, 1);

                graph.addEdge(3, 4, 1);
                graph.addEdge(4, 3, 1);

                SCC.Result result = SCC.kosaraju(graph);

                check(
                                result.components.size() == 2,
                                "SCC should find 2 components");

                boolean[] seen = new boolean[5];

                int totalVertices = 0;

                for (List<Integer> component : result.components) {

                        for (int vertex : component) {

                                check(
                                                !seen[vertex],
                                                "Vertex should be in only one SCC");

                                seen[vertex] = true;

                                totalVertices++;
                        }
                }

                check(
                                totalVertices == 5,
                                "All vertices should be in SCC");

                for (int i = 0; i < 5; i++) {

                        check(
                                        seen[i],
                                        "Vertex missing from SCC");
                }

                System.out.println(
                                "SCC PASSED");
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
                                "Distance source");

                check(
                                result.distance[1] == 3,
                                "Distance vertex 1");

                check(
                                result.distance[2] == 1,
                                "Distance vertex 2");

                check(
                                result.distance[3] == 4,
                                "Distance vertex 3");

                check(
                                result.distance[4] == 7,
                                "Distance vertex 4");

                check(
                                result.getPath(4).equals(
                                                List.of(
                                                                0,
                                                                2,
                                                                1,
                                                                3,
                                                                4)),
                                "Shortest path vertex 4");

                System.out.println(
                                "Dijkstra PASSED");
        }

        private static void testDijkstraUnreachable() {

                Graph graph = new Graph(4, true);

                graph.addEdge(0, 1, 2);
                graph.addEdge(1, 2, 3);

                Dijkstra.Result result = Dijkstra.run(graph, 0);

                check(
                                result.isReachable(0),
                                "Source reachable");

                check(
                                result.isReachable(1),
                                "Vertex 1 reachable");

                check(
                                result.isReachable(2),
                                "Vertex 2 reachable");

                check(
                                !result.isReachable(3),
                                "Vertex 3 unreachable");

                check(
                                result.getPath(3).isEmpty(),
                                "Unreachable path empty");

                System.out.println(
                                "Dijkstra unreachable PASSED");
        }

        private static void testDijkstraNegativeWeight() {

                Graph graph = new Graph(2, true);

                graph.addEdge(
                                0,
                                1,
                                -5);

                boolean exceptionThrown = false;

                try {

                        Dijkstra.run(
                                        graph,
                                        0);

                } catch (IllegalArgumentException e) {

                        exceptionThrown = true;
                }

                check(
                                exceptionThrown,
                                "Negative weight rejected");

                System.out.println(
                                "Dijkstra negative weight PASSED");
        }

        private static boolean isValidTopologicalOrder(
                        Graph graph,
                        List<Integer> order) {

                int n = graph.getVertices();

                if (order.size() != n) {
                        return false;
                }

                int[] position = new int[n];

                for (int i = 0; i < n; i++) {

                        position[order.get(i)] = i;
                }

                for (int u = 0; u < n; u++) {

                        for (Edge edge : graph.getNeighbors(u)) {

                                if (position[u] >= position[edge.v]) {

                                        return false;
                                }
                        }
                }

                return true;
        }

        private static void check(
                        boolean condition,
                        String message) {

                if (!condition) {

                        throw new AssertionError(
                                        "FAILED: "
                                                        + message);
                }
        }
}