package DataStructures;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListGraph extends AbstractGraph{

    private List<Edge>[] edges;

    /**
     * Construct a graph with the specified number of vertices
     * and directionality
     * @param numV The number of vertices
     * @param directed The directionality flag
     */
    public ListGraph(int numV, boolean directed){
        super(numV, directed);
        edges = new List[numV];
        for (int i=0; i<numV; i++)
            edges[i] = new LinkedList<Edge>();
    }

    /**
     * Insert a new edge into the graph
     * @param edge The new edge
     */
    @Override
    public void insert(Edge edge) {
        edges[edge.getSource()].add(edge);
        if (!isDirected()){
            edges[edge.getDest()].add(new Edge( edge.getDest(),
                                                edge.getSource(),
                                                edge.getWeight()));
        }
    }

    /**
     * Determine whether an edge exists
     * @param source The source vertex
     * @param dest The destination vertex
     * @return true if there is an edge from source to dest
     */
    @Override
    public boolean isEdge(int source, int dest) {
        return edges[source].contains(new Edge(source,dest));
    }

    /**
     * Get the edge between two vertices. If an edge does not exist,
     * an Edge with a weight of Double.POSITIVE_INFINITY is returned.
     * @param source The source vertex
     * @param dest The destination vertex
     * @return the Edge between these two vertices
     */
    @Override
    public Edge getEdge(int source, int dest) {
        Edge target = new Edge(source, dest, Double.POSITIVE_INFINITY);
        for (Edge edge : edges[source])
            if (edge.equals(target))
                return target;

        return target;
    }

    @Override
    public Iterator<Edge> edgeIterator(int source) {
        return edges[source].iterator();
    }

    public static void dijkstrasAlgorithm(Graph graph, int start , int[] pred, double[] dist){
        int numV = graph.getNumV();
        HashSet<Integer> vMinusS = new HashSet<Integer>(numV);

        for(int i = 0; i < numV; i++){
            if(i != start){
                vMinusS.add(i);
            }
        }

        for(int v : vMinusS){
            pred[v] = start;
            dist[v] = graph.getEdge(start,v).getWeight();
        }

        while(vMinusS.size() != 0){
            double minDist = Double.POSITIVE_INFINITY;
            int u = -1;
            for(int v : vMinusS){
                if(dist[v] < minDist){
                    minDist = dist[v];
                    u = v;
                }
            }
            vMinusS.remove(u);
            for(int v : vMinusS){
                if(graph.isEdge(u,v)){
                    double weight = graph.getEdge(u,v).getWeight();
                    if(dist[u] + weight < dist[v]){
                        dist[v] = dist[u] + weight;
                        pred[v] = u;
                    }
                }
            }
        }

    }



}
