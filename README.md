# Assignment 3 — Graph Algorithms

## Overview

This project models a city as a graph.

- Vertices are city locations.
- Edges are roads or connections.
- Weights are road costs or distances.

The project implements:

- Disjoint Set Union (DSU)
- Kruskal
- Prim
- Topological Sort
- Strongly Connected Components (Kosaraju)
- Dijkstra

## Implementation

The graph uses an adjacency list.

For MST:
- Kruskal uses DSU.
- DSU uses path compression and union by size.
- Prim uses `PriorityQueue`.

For directed graphs:
- Topological Sort uses indegree and a queue.
- SCC uses Kosaraju's algorithm.

For shortest paths:
- Dijkstra uses `PriorityQueue`.
- Negative edge weights are not allowed.

## Complexity Analysis

| Algorithm | Time Complexity | Space Complexity |
|---|---|---|
| DSU find / union | O(α(V)) amortized | O(V) |
| Kruskal | O(E log V) | O(V + E) |
| Prim with binary heap | O(E log V) | O(V + E) |
| Topological Sort | O(V + E) | O(V) |
| Kosaraju SCC | O(V + E) | O(V + E) |
| Dijkstra with binary heap | O((V + E) log V) | O(V + E) |

`V` is the number of vertices and `E` is the number of edges.

## Experimental Setup

Three graph sizes were used:

| Size | Vertices | Edges |
|---|---:|---:|
| Small | 10 | 30 |
| Medium | 100 | 500 |
| Large | 1000 | 5000 |

A fixed random seed was used so the graphs can be generated again.

Each algorithm was run 5 times.

Execution time was measured with `System.nanoTime()`.

The MST graph was always connected.

For topological sort, both a DAG and a graph with a cycle were tested.

## Results

| Algorithm | V | E | Average Time (ns) | Edge Checks | Find Calls | Union Calls |
|---|---:|---:|---:|---:|---:|---:|
| Kruskal | 10 | 30 | 1,974,320 | 14 | 28 | 14 |
| Prim | 10 | 30 | 919,880 | 60 | 0 | 0 |
| Topological DAG | 10 | 30 | 478,560 | 60 | 0 | 0 |
| Topological Cycle | 10 | 30 | 14,580 | 30 | 0 | 0 |
| SCC | 10 | 30 | 718,340 | 90 | 0 | 0 |
| Dijkstra | 10 | 30 | 1,676,840 | 60 | 0 | 0 |
| Kruskal | 100 | 500 | 1,100,460 | 272 | 544 | 272 |
| Prim | 100 | 500 | 941,840 | 1000 | 0 | 0 |
| Topological DAG | 100 | 500 | 394,620 | 1000 | 0 | 0 |
| Topological Cycle | 100 | 500 | 108,440 | 515 | 0 | 0 |
| SCC | 100 | 500 | 682,540 | 1500 | 0 | 0 |
| Dijkstra | 100 | 500 | 338,700 | 1000 | 0 | 0 |
| Kruskal | 1000 | 5000 | 4,525,120 | 3313 | 6626 | 3313 |
| Prim | 1000 | 5000 | 9,106,800 | 10000 | 0 | 0 |
| Topological DAG | 1000 | 5000 | 1,975,540 | 10000 | 0 | 0 |
| Topological Cycle | 1000 | 5000 | 585,180 | 5046 | 0 | 0 |
| SCC | 1000 | 5000 | 2,982,060 | 15000 | 0 | 0 |
| Dijkstra | 1000 | 5000 | 2,805,640 | 10000 | 0 | 0 |

## Plots

### Execution Time vs Number of Vertices

![Execution Time vs Number of Vertices](results/plots/time_vs_vertices.png)

### Execution Time vs Number of Edges

![Execution Time vs Number of Edges](results/plots/time_vs_edges.png)

## Comparison and Discussion

For MST, Kruskal and Prim returned the same MST weight on connected graphs.

Kruskal checks sorted edges and uses DSU. Prim grows one tree and uses a priority queue.

For the largest graph, Kruskal was faster than Prim in this test.

Topological Sort on a DAG checked every edge twice: first for indegree and then during processing.

Topological Sort on a graph with a cycle can stop before all vertices are processed, so it can have fewer edge checks.

SCC checked more edges because Kosaraju uses the original graph and the reversed graph.

Dijkstra became slower when the graph became larger because it processed more edges and used a priority queue.

Small execution times can have some JVM measurement noise.

## Testing

The program was tested with:

- a normal connected graph;
- a disconnected graph;
- a graph with multiple valid MSTs and duplicate weights;
- a graph with one vertex;
- a DAG;
- a graph with a cycle;
- SCC groups;
- Dijkstra shortest paths;
- an unreachable vertex;
- a negative edge weight for Dijkstra.

Kruskal and Prim were checked to have the same MST weight.

Topological Sort was checked on both DAG and cyclic graphs.

Every vertex was checked to belong to one SCC.

Dijkstra was checked with non-negative weights and unreachable vertices.

## Conclusion

Kruskal and Prim are both useful for finding a minimum spanning tree. In this experiment, Kruskal was faster on the largest graph.

Topological Sort is useful when a directed graph has no cycle.

SCC is useful for finding groups of vertices that can reach each other.

Dijkstra is useful for shortest paths when edge weights are not negative.

The results show that execution time grows when the number of vertices and edges grows.

## Run

Compile:

```bash
javac -d out src/graph/*.java src/dsu/*.java src/mst/*.java src/connectivity/*.java src/shortestpath/*.java src/Main.java src/Tests.java src/Benchmark.java
```

Run tests:

```bash
java -cp out Tests
```

Run benchmark:

```bash
java -cp out Benchmark
```
