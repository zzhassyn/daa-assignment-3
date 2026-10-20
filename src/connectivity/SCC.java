package connectivity;

import graph.Edge;
import graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class SCC {

    public static List<List<Integer>> kosaraju(Graph graph) {

        int n = graph.getVertices();

        boolean[] visited = new boolean[n];
        Deque<Integer> order = new ArrayDeque<>();

        // 1. DFS on original graph
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfsOrder(graph, i, visited, order);
            }
        }

        // 2. Reverse graph
        Graph reversed = new Graph(n, true);

        for (int u = 0; u < n; u++) {
            for (Edge edge : graph.getNeighbors(u)) {
                reversed.addEdge(edge.v, edge.u, edge.w);
            }
        }

        // 3. DFS on reversed graph
        Arrays.fill(visited, false);

        List<List<Integer>> components = new ArrayList<>();

        while (!order.isEmpty()) {

            int vertex = order.pop();

            if (!visited[vertex]) {

                List<Integer> component = new ArrayList<>();

                dfsComponent(
                        reversed,
                        vertex,
                        visited,
                        component);

                components.add(component);
            }
        }

        return components;
    }

    private static void dfsOrder(
            Graph graph,
            int vertex,
            boolean[] visited,
            Deque<Integer> order) {

        visited[vertex] = true;

        for (Edge edge : graph.getNeighbors(vertex)) {
            if (!visited[edge.v]) {
                dfsOrder(graph, edge.v, visited, order);
            }
        }

        order.push(vertex);
    }

    private static void dfsComponent(
            Graph graph,
            int vertex,
            boolean[] visited,
            List<Integer> component) {

        visited[vertex] = true;
        component.add(vertex);

        for (Edge edge : graph.getNeighbors(vertex)) {
            if (!visited[edge.v]) {
                dfsComponent(
                        graph,
                        edge.v,
                        visited,
                        component);
            }
        }
    }
}