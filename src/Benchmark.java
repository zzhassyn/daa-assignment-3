import connectivity.SCC;
import connectivity.TopologicalSort;
import graph.Graph;
import graph.GraphGenerator;
import mst.Kruskal;
import mst.Prim;
import shortestpath.Dijkstra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Benchmark {

    private static final int[] VERTICES = {
            10, 100, 1000
    };

    private static final int[] EDGES = {
            30, 500, 5000
    };

    private static final int RUNS = 5;

    private static final long SEED = 42;

    public static void main(String[] args)
            throws IOException {

        new File("results/tables").mkdirs();

        try (
                PrintWriter writer = new PrintWriter(
                        new FileWriter(
                                "results/tables/benchmark.csv"))) {

            writer.println(
                    "algorithm,vertices,edges,averageTimeNs,"
                            + "edgeChecks,findCalls,unionCalls");

            for (int i = 0; i < VERTICES.length; i++) {

                int vertices = VERTICES[i];
                int edges = EDGES[i];

                System.out.println(
                        "Running V = "
                                + vertices
                                + ", E = "
                                + edges);

                Graph undirected = GraphGenerator.generateConnectedUndirected(
                        vertices,
                        edges,
                        SEED + i);

                Graph dag = GraphGenerator.generateDAG(
                        vertices,
                        edges,
                        SEED + i);

                Graph directedCycle = GraphGenerator.generateDirectedWithCycle(
                        vertices,
                        edges,
                        SEED + i);

                benchmarkKruskal(
                        writer,
                        undirected,
                        vertices,
                        edges);

                benchmarkPrim(
                        writer,
                        undirected,
                        vertices,
                        edges);

                benchmarkTopologicalDAG(
                        writer,
                        dag,
                        vertices,
                        edges);

                benchmarkTopologicalCycle(
                        writer,
                        directedCycle,
                        vertices,
                        edges);

                benchmarkSCC(
                        writer,
                        directedCycle,
                        vertices,
                        edges);

                benchmarkDijkstra(
                        writer,
                        undirected,
                        vertices,
                        edges);

                System.out.println(
                        "Finished");

                System.out.println();
            }
        }

        System.out.println(
                "Benchmark completed.");

        System.out.println(
                "Results: results/tables/benchmark.csv");
    }

    private static void benchmarkKruskal(
            PrintWriter writer,
            Graph graph,
            int vertices,
            int edges) {

        long totalTime = 0;

        long totalEdgeChecks = 0;
        long totalFindCalls = 0;
        long totalUnionCalls = 0;

        int mstWeight = -1;

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();

            Kruskal.Result result = Kruskal.run(
                    graph.getVertices(),
                    graph.getEdges());

            long end = System.nanoTime();

            totalTime += end - start;

            totalEdgeChecks += result.edgeChecks;

            totalFindCalls += result.findCount;

            totalUnionCalls += result.unionCount;

            mstWeight = result.totalWeight;
        }

        writer.println(
                "Kruskal,"
                        + vertices + ","
                        + edges + ","
                        + totalTime / RUNS + ","
                        + totalEdgeChecks / RUNS + ","
                        + totalFindCalls / RUNS + ","
                        + totalUnionCalls / RUNS);

        System.out.println(
                "Kruskal MST weight = "
                        + mstWeight);
    }

    private static void benchmarkPrim(
            PrintWriter writer,
            Graph graph,
            int vertices,
            int edges) {

        long totalTime = 0;
        long totalEdgeChecks = 0;

        int mstWeight = -1;

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();

            Prim.Result result = Prim.run(graph);

            long end = System.nanoTime();

            totalTime += end - start;

            totalEdgeChecks += result.edgeChecks;

            mstWeight = result.totalWeight;
        }

        writer.println(
                "Prim,"
                        + vertices + ","
                        + edges + ","
                        + totalTime / RUNS + ","
                        + totalEdgeChecks / RUNS + ","
                        + "0,0");

        System.out.println(
                "Prim MST weight = "
                        + mstWeight);
    }

    private static void benchmarkTopologicalDAG(
            PrintWriter writer,
            Graph graph,
            int vertices,
            int edges) {

        long totalTime = 0;
        long totalEdgeChecks = 0;

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();

            TopologicalSort.Result result = TopologicalSort.sort(graph);

            long end = System.nanoTime();

            totalTime += end - start;

            totalEdgeChecks += result.edgeChecks;

            if (result.hasCycle) {
                throw new IllegalStateException(
                        "Generated DAG has a cycle");
            }
        }

        writer.println(
                "TopologicalDAG,"
                        + vertices + ","
                        + edges + ","
                        + totalTime / RUNS + ","
                        + totalEdgeChecks / RUNS + ","
                        + "0,0");
    }

    private static void benchmarkTopologicalCycle(
            PrintWriter writer,
            Graph graph,
            int vertices,
            int edges) {

        long totalTime = 0;
        long totalEdgeChecks = 0;

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();

            TopologicalSort.Result result = TopologicalSort.sort(graph);

            long end = System.nanoTime();

            totalTime += end - start;

            totalEdgeChecks += result.edgeChecks;
        }

        writer.println(
                "TopologicalCycle,"
                        + vertices + ","
                        + edges + ","
                        + totalTime / RUNS + ","
                        + totalEdgeChecks / RUNS + ","
                        + "0,0");
    }

    private static void benchmarkSCC(
            PrintWriter writer,
            Graph graph,
            int vertices,
            int edges) {

        long totalTime = 0;
        long totalEdgeChecks = 0;

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();

            SCC.Result result = SCC.kosaraju(graph);

            long end = System.nanoTime();

            totalTime += end - start;

            totalEdgeChecks += result.edgeChecks;
        }

        writer.println(
                "SCC,"
                        + vertices + ","
                        + edges + ","
                        + totalTime / RUNS + ","
                        + totalEdgeChecks / RUNS + ","
                        + "0,0");
    }

    private static void benchmarkDijkstra(
            PrintWriter writer,
            Graph graph,
            int vertices,
            int edges) {

        long totalTime = 0;
        long totalEdgeChecks = 0;

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();

            Dijkstra.Result result = Dijkstra.run(
                    graph,
                    0);

            long end = System.nanoTime();

            totalTime += end - start;

            totalEdgeChecks += result.edgeChecks;
        }

        writer.println(
                "Dijkstra,"
                        + vertices + ","
                        + edges + ","
                        + totalTime / RUNS + ","
                        + totalEdgeChecks / RUNS + ","
                        + "0,0");
    }
}