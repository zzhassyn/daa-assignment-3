package graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private final int vertices;
    private final boolean directed;

    private final List<List<Edge>> adjacencyList;
    private final List<Edge> edges;

    public Graph(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;

        adjacencyList = new ArrayList<>();
        edges = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int w) {
        Edge edge = new Edge(u, v, w);

        edges.add(edge);
        adjacencyList.get(u).add(edge);

        if (!directed) {
            adjacencyList.get(v).add(new Edge(v, u, w));
        }
    }

    public int getVertices() {
        return vertices;
    }

    public List<Edge> getNeighbors(int vertex) {
        return adjacencyList.get(vertex);
    }

    public List<Edge> getEdges() {
        return edges;
    }
}