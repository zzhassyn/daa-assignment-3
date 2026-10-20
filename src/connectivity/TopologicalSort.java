package connectivity;

import graph.Edge;
import graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TopologicalSort {

    public static class Result {
        public final List<Integer> order;
        public final boolean hasCycle;
        public final long edgeChecks;

        public Result(
                List<Integer> order,
                boolean hasCycle,
                long edgeChecks) {
            this.order = order;
            this.hasCycle = hasCycle;
            this.edgeChecks = edgeChecks;
        }
    }

    public static Result sort(Graph graph) {

        int n = graph.getVertices();

        int[] indegree = new int[n];

        long edgeChecks = 0;

        for (int u = 0; u < n; u++) {
            for (Edge edge : graph.getNeighbors(u)) {
                indegree[edge.v]++;
                edgeChecks++;
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> order = new ArrayList<>();

        while (!queue.isEmpty()) {

            int u = queue.poll();

            order.add(u);

            for (Edge edge : graph.getNeighbors(u)) {

                edgeChecks++;

                indegree[edge.v]--;

                if (indegree[edge.v] == 0) {
                    queue.add(edge.v);
                }
            }
        }

        boolean hasCycle = order.size() != n;

        return new Result(
                order,
                hasCycle,
                edgeChecks);
    }
}