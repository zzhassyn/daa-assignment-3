import graph.Graph;
import mst.Kruskal;
import mst.Prim;

public class Main {

    public static void main(String[] args) {

        Graph graph = new Graph(9, false);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 7, 8);
        graph.addEdge(1, 2, 8);
        graph.addEdge(1, 7, 11);
        graph.addEdge(2, 3, 7);
        graph.addEdge(2, 8, 2);
        graph.addEdge(2, 5, 4);
        graph.addEdge(3, 4, 9);
        graph.addEdge(3, 5, 14);
        graph.addEdge(4, 5, 10);
        graph.addEdge(5, 6, 2);
        graph.addEdge(6, 7, 1);
        graph.addEdge(6, 8, 6);
        graph.addEdge(7, 8, 7);

        Kruskal.Result kruskal = Kruskal.run(graph.getVertices(), graph.getEdges());

        Prim.Result prim = Prim.run(graph);

        System.out.println("Kruskal:");
        System.out.println("Weight: " + kruskal.totalWeight);
        System.out.println("Connected: " + kruskal.connected);

        System.out.println();

        System.out.println("Prim:");
        System.out.println("Weight: " + prim.totalWeight);
        System.out.println("Connected: " + prim.connected);
    }
}