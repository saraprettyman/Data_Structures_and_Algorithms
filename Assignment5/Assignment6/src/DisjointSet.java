public class DisjointSet {
    public int[] size;

    public DisjointSet(int size){
        this.size = new int[size];
        // initialize each disjoint set as a root node
        for (int i = 0; i < size; i++){
            this.size[i] = -1;
        }
    }

    public void union(int node1, int node2){
        int root1 = find(node1);
        int root2 = find(node2);
        if (root1 == root2) {
            return;
        }
        if (size[root2] < size[root1]) {
            // root2 is larger, because it is more negative
            size[root2] += size[root1]; // add the size from root1 to root2
            size[root1] = root2; // Make root2 new root
        }
        else {
        // root1 is equal or larger
            size[root1] += size[root2]; // add the size from root2 to root1
            size[root2] = root1; // Make root1 new root
        }
    }

    // determines the root of the given node
    public int find(int node) {
        if (node >= 0) {
            // if root node
            if (size[node] < 0) {
                return node;
            } else {
                int root = find(size[node]);
                size[node] = root;
                return root;
            }
        }else {
            return node;
        }
    }
}
