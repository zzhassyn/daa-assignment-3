package graph;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GraphGenerator {

    private static final int MAX_WEIGHT = 100;

    public static Graph generateConnectedUndirected(
            int vertices,
            int edges,
            long seed) {

        Graph graph = new Graph(vertices, false);
        Random random = new Random(seed);

        Set<String> usedEdges = new HashSet<>();

        // First create a spanning tree.
        // This guarantees that the graph is connected.
        for (int v = 1; v < vertices; v++) {

            int u = random.nextInt(v);
            int weight = random.nextInt(MAX_WEIGHT) + 1;

            graph.addEdge(u, v, weight);

            usedEdges.add(undirectedKey(u, v));
        }

        // Add the remaining random edges.
        while (usedEdges.size() < edges) {

            int u = random.nextInt(vertices);
            int v = random.nextInt(vertices);

            if (u == v) {
                continue;
            }

            String key = undirectedKey(u, v);

            if (usedEdges.add(key)) {

                int weight = random.nextInt(MAX_WEIGHT) + 1;

                graph.addEdge(u, v, weight);
            }
        }

        return graph;
    }

    public static Graph generateDAG(
            int vertices,
            int edges,
            long seed) {

        Graph graph = new Graph(vertices, true);
        Random random = new Random(seed);

        Set<String> usedEdges = new HashSet<>();

        while (usedEdges.size() < edges) {

            int u = random.nextInt(vertices);
            int v = random.nextInt(vertices);

            if (u == v) {
                continue;
            }

            // Always u < v.
            // Because of this, a cycle cannot appear.
            if (u > v) {
                int temp = u;
                u = v;
                v = temp;
            }

            String key = u + "->" + v;

            if (usedEdges.add(key)) {

                int weight = random.nextInt(MAX_WEIGHT) + 1;

                graph.addEdge(u, v, weight);
            }
        }

        return graph;
    }

    public static Graph generateDirectedWithCycle(
            int vertices,
            int edges,
            long seed) {

        Graph graph = new Graph(vertices, true);
        Random random = new Random(seed);

        Set<String> usedEdges = new HashSet<>();

        // Add one cycle: 0 -> 1 -> 2 -> 0
        if (vertices >= 3 && edges >= 3) {

            addDirectedEdge(
                    graph,
                    usedEdges,
                    0,
                    1,
                    random);

            addDirectedEdge(
                    graph,
                    usedEdges,
                    1,
                    2,
                    random);

            addDirectedEdge(
                    graph,
                    usedEdges,
                    2,
                    0,
                    random);
        }

        while (usedEdges.size() < edges) {

            int u = random.nextInt(vertices);
            int v = random.nextInt(vertices);

            if (u == v) {
                continue;
            }

            String key = u + "->" + v;

            if (usedEdges.add(key)) {

                int weight = random.nextInt(MAX_WEIGHT) + 1;

                graph.addEdge(u, v, weight);
            }
        }

        return graph;
    }

    private static void addDirectedEdge(
            Graph graph,
            Set<String> usedEdges,
            int u,
            int v,
            Random random) {

        String key = u + "->" + v;

        if (usedEdges.add(key)) {

            int weight = random.nextInt(MAX_WEIGHT) + 1;

            graph.addEdge(u, v, weight);
        }
    }

    private static String undirectedKey(int u, int v) {

        int min = Math.min(u, v);
        int max = Math.max(u, v);

        return min + "-" + max;
    }
}