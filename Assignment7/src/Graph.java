import java.util.*;

public class Graph {
    private final GraphNode[] vertices;
    private final String name;
    private final Set<String> backEdges;

    public Graph(String name, int vertexCount) {
        this.name = name;

        vertices = new GraphNode[vertexCount];
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            vertices[vertex] = new GraphNode(vertex);
        }

        backEdges = new HashSet<>();
    }

    public boolean addEdge(int source, int destination, int capacity) {
        if (source < 0 || source >= vertices.length) return false;
        if (destination < 0 || destination >= vertices.length) return false;

        // This adds the actual requested edge, along with its capacity
        vertices[source].addEdge(source, destination, capacity);

        /*
         TODO: Line 26 Task
        */
        vertices[destination].addEdge(destination, source, 0);

        backEdges.add(destination + "," + source);
        return true;
    }

    /**
     * Algorithm to find max-flow in a network
     */
    public int findMaxFlow(int s, int t, boolean report) {
        // TODO:
        int totalFlow = 0;
        StringBuilder allPaths = new StringBuilder();

        while (hasAugmentingPath(s, t)) {
            StringBuilder path = new StringBuilder();
            int availableFlow = Integer.MAX_VALUE;
            int v = t;
            Stack<Integer> stack = new Stack<>();
            while (v != s) {
                int predecessor = vertices[v].parent;
                availableFlow = Math.min(availableFlow, getEdgeCapacity(predecessor, v));
                v = vertices[v].parent;
            }
            path.append("Flow ").append(availableFlow).append(": ");
            v = t;
            while (v != s){
                path.insert(path.indexOf(":") + 2, v + " ");
                int predecessor = vertices[v].parent;
                updateResidualGraph(predecessor, v, -availableFlow);
                updateResidualGraph(v, predecessor, availableFlow);
                v = vertices[v].parent;
            }
            path.insert(path.indexOf(":") + 2, s + " " );
            path.append("\n");
            allPaths.append(path);
            totalFlow += availableFlow;
        }
        if (report) {
            System.out.println("-- Max Flow: " + name + " --");
            System.out.println(allPaths);
            for (GraphNode vertex : vertices) {
                for (GraphNode.EdgeInfo edge : vertex.successor) {
                    // Check if the edge is not a back edge
                    if (!backEdges.contains(edge.from + "," + edge.to)) {
                        // Find the edge that goes from edge.to to edge.from
                        GraphNode.EdgeInfo reverseEdge = findReverseEdge(edge.to, edge.from);
                        if (reverseEdge != null && reverseEdge.capacity > 0) {
                            System.out.println("Edge(" + edge.from + ", " + edge.to + ") transports " + reverseEdge.capacity + " items");
                        }
                    }
                }
            }
        }
        return totalFlow;
    }

    /**
     * Algorithm to find an augmenting path in a network
     */
    private boolean hasAugmentingPath ( int s, int t){
        // TODO:
        Queue<Integer> augQueue = new LinkedList<>();
        // Reset the "parent", and visited status
        for (GraphNode vertex : vertices) {
            vertex.parent = -1;
            vertex.visited = false;
        }
        // Add s to the queue, update visited status
        augQueue.add(s);
        vertices[s].visited = true;
        while (!augQueue.isEmpty() && vertices[t].parent == -1) {
            int v = augQueue.poll();
            for (GraphNode.EdgeInfo edge : vertices[v].successor) {
                int w = edge.to; //other vertex
                if (!vertices[w].visited && edge.capacity > 0 && w != s) {
                    vertices[w].parent = v;
                    vertices[w].visited = true;
                    augQueue.add(w);
                }
            }
        }
        return (vertices[t].parent != -1);
    }

    /**
     * Algorithm to find the min-cut edges in a network
     */
    public void findMinCut ( int s){
        // TODO:
        System.out.println("\n"+ "-- Min Cut: " + name + " --");
        // Based on the max flow algorithm, compute the final residual graph
        int finalResid = computeFinalResidualGraph(s, vertices.length - 1);
        // Mark all nodes reachable from s: call this set R
        List<Integer> R = reachableVertices(s);
        List<GraphNode.EdgeInfo> cutEdges = searchR(R);
        for (GraphNode.EdgeInfo edge:cutEdges){
            System.out.println("Min Cut Edge: (" + edge.from + ", " + edge.to + ")");
        }
        System.out.println();
    }

    // Assisting functions for FindMaxFlow
    private GraphNode.EdgeInfo findReverseEdge(int from, int to) {
        for (GraphNode.EdgeInfo edge : vertices[from].successor) {
            if (edge.to == to) {
                return edge;
            }
        }
        return null;
    }

    private int getEdgeCapacity (int from, int to){
        // check to see if each edge of the from vertex is correct
        for (GraphNode.EdgeInfo edge : vertices[from].successor) {
            // if edge is found between vertices
            if (edge.to == to) {
                return edge.capacity;
            }
        }
        return 0; // If there is no edge between the given vertices
    }

    private void updateResidualGraph (int from, int to, int flow){
        // Search for the edge of 'from' to 'to'
        for (GraphNode.EdgeInfo edge : vertices[from].successor) {
            if (edge.to == to) {
                // If the edge between the given vertices is found, update its capacity.
                edge.capacity += flow;
                return;
            }
        }
        // If the edge between the given vertices is not found, add a new edge with the given flow value as capacity.
        vertices[from].addEdge(from, to, flow);
    }

    // Assisting Functions for findMinCut
    private int computeFinalResidualGraph(int s, int t) {
        // Find the max flow, which also computes the final residual graph
        return findMaxFlow(s, t, false);
    }

    public List<Integer> reachableVertices(int s) {
        List<Integer> reach = new ArrayList<>();
        for (int i=0; i< vertices.length;i++){
            vertices[i].visited = false;
        }
        reachSearch(s, reach);
        return reach;
    }

    public void reachSearch(int vertex, List<Integer> reach) {
        vertices[vertex].visited = true;
        reach.add(vertex);
        for (GraphNode.EdgeInfo edge : vertices[vertex].successor) {
            if (!vertices[edge.to].visited && edge.capacity > 0) {
                reachSearch(edge.to, reach);
            }
        }
    }
        
    private List<GraphNode.EdgeInfo> searchR(List<Integer> R){
        List<GraphNode.EdgeInfo> minCutEdges = new ArrayList<>();
        for (int v : R) {
            GraphNode vertex = vertices[v];
            for (GraphNode.EdgeInfo edge : vertex.successor) {
                if (!R.contains(edge.to)) {
                    minCutEdges.add(edge);
                }
            }
        }
        return minCutEdges;
    }
    @Override
        public String toString () {
            StringBuilder sb = new StringBuilder();

            sb.append("The Graph " + name + " \n");
            for (var vertex : vertices) {
                sb.append((vertex.toString()));
            }
            return sb.toString();
        }
}

