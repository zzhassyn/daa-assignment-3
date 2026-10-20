package mst;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Prim {

    public static class Result {
        public final List<Edge> edges;
        public final int totalWeight;
        public final boolean connected;

        public final long edgeChecks;

        public Result(
                List<Edge> edges,
                int totalWeight,
                boolean connected,
                long edgeChecks) {
            this.edges = edges;
            this.totalWeight = totalWeight;
            this.connected = connected;
            this.edgeChecks = edgeChecks;
        }
    }

    public static Result run(Graph graph) {

        int n = graph.getVertices();

        boolean[] visited = new boolean[n];

        List<Edge> mst = new ArrayList<>();

        PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingInt(e -> e.w));

        int totalWeight = 0;
        long edgeChecks = 0;

        if (n == 0) {
            return new Result(mst, 0, true, 0);
        }

        visited[0] = true;

        for (Edge edge : graph.getNeighbors(0)) {
            queue.add(edge);
            edgeChecks++;
        }

        while (!queue.isEmpty() && mst.size() < n - 1) {

            Edge edge = queue.poll();

            if (visited[edge.v]) {
                continue;
            }

            visited[edge.v] = true;

            mst.add(edge);
            totalWeight += edge.w;

            for (Edge next : graph.getNeighbors(edge.v)) {
                edgeChecks++;

                if (!visited[next.v]) {
                    queue.add(next);
                }
            }
        }

        boolean connected = mst.size() == n - 1;

        return new Result(
                mst,
                totalWeight,
                connected,
                edgeChecks);
    }
}