package connectivity;

import graph.Edge;
import graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class SCC {

    public static class Result {
        public final List<List<Integer>> components;
        public final long edgeChecks;

        public Result(
                List<List<Integer>> components,
                long edgeChecks) {
            this.components = components;
            this.edgeChecks = edgeChecks;
        }
    }

    private static class Counter {
        long edgeChecks;
    }

    public static Result kosaraju(Graph graph) {

        int n = graph.getVertices();

        boolean[] visited = new boolean[n];

        Deque<Integer> order = new ArrayDeque<>();

        Counter counter = new Counter();

        // First DFS
        for (int i = 0; i < n; i++) {

            if (!visited[i]) {

                dfsOrder(
                        graph,
                        i,
                        visited,
                        order,
                        counter);
            }
        }

        // Reverse graph
        Graph reversed = new Graph(n, true);

        for (int u = 0; u < n; u++) {

            for (Edge edge : graph.getNeighbors(u)) {

                counter.edgeChecks++;

                reversed.addEdge(
                        edge.v,
                        edge.u,
                        edge.w);
            }
        }

        Arrays.fill(
                visited,
                false);

        List<List<Integer>> components = new ArrayList<>();

        // Second DFS
        while (!order.isEmpty()) {

            int vertex = order.pop();

            if (!visited[vertex]) {

                List<Integer> component = new ArrayList<>();

                dfsComponent(
                        reversed,
                        vertex,
                        visited,
                        component,
                        counter);

                components.add(component);
            }
        }

        return new Result(
                components,
                counter.edgeChecks);
    }

    private static void dfsOrder(
            Graph graph,
            int vertex,
            boolean[] visited,
            Deque<Integer> order,
            Counter counter) {

        visited[vertex] = true;

        for (Edge edge : graph.getNeighbors(vertex)) {

            counter.edgeChecks++;

            if (!visited[edge.v]) {

                dfsOrder(
                        graph,
                        edge.v,
                        visited,
                        order,
                        counter);
            }
        }

        order.push(vertex);
    }

    private static void dfsComponent(
            Graph graph,
            int vertex,
            boolean[] visited,
            List<Integer> component,
            Counter counter) {

        visited[vertex] = true;

        component.add(vertex);

        for (Edge edge : graph.getNeighbors(vertex)) {

            counter.edgeChecks++;

            if (!visited[edge.v]) {

                dfsComponent(
                        graph,
                        edge.v,
                        visited,
                        component,
                        counter);
            }
        }
    }
}