package shortestpath;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {

    private static class State {
        int vertex;
        long distance;

        State(int vertex, long distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    public static class Result {
        public final long[] distance;
        public final int[] parent;
        public final long edgeChecks;

        public Result(long[] distance, int[] parent, long edgeChecks) {
            this.distance = distance;
            this.parent = parent;
            this.edgeChecks = edgeChecks;
        }

        public boolean isReachable(int vertex) {
            return distance[vertex] != Long.MAX_VALUE;
        }

        public List<Integer> getPath(int vertex) {

            if (!isReachable(vertex)) {
                return Collections.emptyList();
            }

            List<Integer> path = new ArrayList<>();

            int current = vertex;

            while (current != -1) {
                path.add(current);
                current = parent[current];
            }

            Collections.reverse(path);

            return path;
        }
    }

    public static Result run(Graph graph, int source) {

        int n = graph.getVertices();

        long[] distance = new long[n];
        int[] parent = new int[n];

        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE;
            parent[i] = -1;
        }

        PriorityQueue<State> queue = new PriorityQueue<>(
                Comparator.comparingLong(s -> s.distance));

        distance[source] = 0;

        queue.add(new State(source, 0));

        long edgeChecks = 0;

        while (!queue.isEmpty()) {

            State current = queue.poll();

            if (current.distance != distance[current.vertex]) {
                continue;
            }

            for (Edge edge : graph.getNeighbors(current.vertex)) {

                edgeChecks++;

                if (edge.w < 0) {
                    throw new IllegalArgumentException(
                            "Dijkstra does not support negative weights");
                }

                long newDistance = distance[current.vertex] + edge.w;

                if (newDistance < distance[edge.v]) {

                    distance[edge.v] = newDistance;

                    parent[edge.v] = current.vertex;

                    queue.add(
                            new State(edge.v, newDistance));
                }
            }
        }

        return new Result(
                distance,
                parent,
                edgeChecks);
    }
}