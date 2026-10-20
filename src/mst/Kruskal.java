package mst;

import dsu.UnionFind;
import graph.Edge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kruskal {

    public static class Result {
        public final List<Edge> edges;
        public final int totalWeight;
        public final boolean connected;

        public final long edgeChecks;
        public final long findCount;
        public final long unionCount;

        public Result(
                List<Edge> edges,
                int totalWeight,
                boolean connected,
                long edgeChecks,
                long findCount,
                long unionCount) {
            this.edges = edges;
            this.totalWeight = totalWeight;
            this.connected = connected;
            this.edgeChecks = edgeChecks;
            this.findCount = findCount;
            this.unionCount = unionCount;
        }
    }

    public static Result run(int n, List<Edge> edges) {

        List<Edge> sortedEdges = new ArrayList<>(edges);

        sortedEdges.sort(Comparator.comparingInt(e -> e.w));

        UnionFind uf = new UnionFind(n);

        List<Edge> mst = new ArrayList<>();

        int totalWeight = 0;
        long edgeChecks = 0;

        for (Edge e : sortedEdges) {

            edgeChecks++;

            if (uf.union(e.u, e.v)) {
                mst.add(e);
                totalWeight += e.w;
            }

            if (mst.size() == n - 1) {
                break;
            }
        }

        boolean connected = mst.size() == n - 1;

        return new Result(
                mst,
                totalWeight,
                connected,
                edgeChecks,
                uf.getFindCount(),
                uf.getUnionCount());
    }
}