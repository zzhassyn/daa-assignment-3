package dsu;

public class UnionFind {

    private final int[] parent;
    private final int[] size;

    private long findCount;
    private long unionCount;

    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int x) {
        findCount++;
        return findRoot(x);
    }

    private int findRoot(int x) {
        if (parent[x] != x) {
            parent[x] = findRoot(parent[x]);
        }

        return parent[x];
    }

    public boolean union(int a, int b) {
        unionCount++;

        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) {
            return false;
        }

        if (size[rootA] < size[rootB]) {
            int temp = rootA;
            rootA = rootB;
            rootB = temp;
        }

        parent[rootB] = rootA;
        size[rootA] += size[rootB];

        return true;
    }

    public long getFindCount() {
        return findCount;
    }

    public long getUnionCount() {
        return unionCount;
    }
}