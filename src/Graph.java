import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Graph {
    private int V;                  // number of vertices
    private int E;                  // number of edges
    private List<Edge> edges;       // list of edges

    public Graph(String path){
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String[] line = br.readLine().split(" ");
            V = Integer.parseInt(line[0]);
            E = Integer.parseInt(line[1]);
            edges = new ArrayList<>();
            for(int i=0 ; i<E ; i++){
                line = br.readLine().split(" ");
                int src = Integer.parseInt(line[0]);
                int dest = Integer.parseInt(line[1]);
                int weight = Integer.parseInt(line[2]);
                edges.add(new Edge(src, dest , weight));

            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public int size(){
        return V;
    }

    public void dijkstra(int src , int[] costs , int[] parents){
        boolean[] visited = new boolean[V];
        Arrays.fill(costs, Integer.MAX_VALUE);
        Arrays.fill(parents, -1);
        costs[src]= 0;

        for(int i=0 ; i<V-1 ; i++){
            int u = minDistance(costs , visited);
            visited[u] = true;
            for(Edge e : edges){
                if(e.src == u && !visited[e.dest] && costs[u] != Integer.MAX_VALUE && costs[u] + e.weight < costs[e.dest]){
                    costs[e.dest] = costs[u] + e.weight;
                    parents[e.dest] = u;
                }
            }
        }
    }

    private int minDistance(int[] costs , boolean[] visited){
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for(int i=0 ; i<V ; i++){
            if(!visited[i] && costs[i] <= min){
                min = costs[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private class Edge{
        int src;
        int dest;
        int weight;
        public Edge(int src , int dest , int weight){
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    public boolean bellmanFord(int source, int[] costs, int[] parents) {
        // Initialize the costs array and parents array
        Arrays.fill(costs, Integer.MAX_VALUE);
        Arrays.fill(parents, -1);
        costs[source] = 0;

        // Relax all edges V-1 times
        for (int i = 0; i < V - 1; i++) {
            for (Edge edge : edges) {
                int u = edge.src;
                int v = edge.dest;
                int weight = edge.weight;
                if (costs[u] != Integer.MAX_VALUE && costs[u] + weight < costs[v]) {
                    costs[v] = costs[u] + weight;
                    parents[v] = u;
                }
            }
        }

        // Check for negative cycles
        for (Edge edge : edges) {
            int u = edge.src;
            int v = edge.dest;
            int weight = edge.weight;
            if (costs[u] != Integer.MAX_VALUE && costs[u] + weight < costs[v]) {
                return false;
            }
        }

        return true;
    }
}
