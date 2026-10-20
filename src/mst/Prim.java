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

        public Result(List<Edge> edges, int totalWeight, boolean connected) {
            this.edges = edges;
            this.totalWeight = totalWeight;
            this.connected = connected;
        }
    }

    public static Result run(Graph graph) {

        int n = graph.getVertices();

        boolean[] visited = new boolean[n];

        List<Edge> mst = new ArrayList<>();

        PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingInt(e -> e.w));

        int totalWeight = 0;

        if (n == 0) {
            return new Result(mst, 0, true);
        }

        visited[0] = true;

        queue.addAll(graph.getNeighbors(0));

        while (!queue.isEmpty() && mst.size() < n - 1) {

            Edge edge = queue.poll();

            if (visited[edge.v]) {
                continue;
            }

            visited[edge.v] = true;

            mst.add(edge);
            totalWeight += edge.w;

            for (Edge next : graph.getNeighbors(edge.v)) {
                if (!visited[next.v]) {
                    queue.add(next);
                }
            }
        }

        boolean connected = mst.size() == n - 1;

        return new Result(mst, totalWeight, connected);
    }
}