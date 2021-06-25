package DataStructures.GenericGraph.src;

import java.util.*;

public class GenericGraph<E> {

    private Map<E, List<E>> map;
    private boolean directed;

    /**
     * Constructor
     */
    public GenericGraph(){
        map = new HashMap<>();
        directed = false;
    }

    /**
     * Constructor
     * @param directed if true, the graph is directed.
     */
    GenericGraph(boolean directed){
        map = new HashMap<>();
        this.directed = directed;
    }

    /**
     * Add a new vertex to the graph
     * @param item to be added
     */
    public void addVertex(E item){
        map.put(item, new LinkedList<E>());
    }

    /**
     * Add the edge between source to destination.
     * Adds source and target values to the graph if the
     * source or target values are not present in the graph
     * @param source The source vertex
     * @param destination The destination vertex
     */
    public void addEdge(E source, E destination){
        if (!map.containsKey(source))
            addVertex(source);
        if (!map.containsKey(destination))
            addVertex(destination);
        map.get(source).add(destination);
        if (!directed)
            map.get(destination).add(source);
    }

    /**
     * Returns the count of vertices
     * @return the count of vertices
     */
    public int getVertexCount(){
        return map.keySet().size();
    }

    /**
     * Return the count of edges
     * @return the count of edges
     */
    public int getEdgesCount(){
        int count = 0;
        for (E e : map.keySet())
            count += map.get(e).size();
        if (!directed)
            count = count / 2;
        return count;
    }

    /**
     * Returns true if target vertex is present in the graph
     * otherwise returns false
     * @param target The target vertex
     * @return true if target vertex is present in the graph
     */
    public boolean hasVertex(E target){
        return map.containsKey(target);
    }

    /**
     * Returns true if the graph contains edge between two vertices
     * @param source The source vertex
     * @param destination The destination vertex
     * @return true if the graph contains edge between two vertices
     */
    public boolean hasEdge(E source, E destination){
        return map.get(source).contains(destination);
    }

    /**
     * Returns true if the graph is directed
     * @return true if the graph is directed
     */
    public boolean isDirected(){
        return directed;
    }

    /**
     * Returns iterator to iterate on the vertices
     * @return iterator to iterate on the vertices
     */
    public Iterator<E> vertexIterator(){
        return map.keySet().iterator();
    }

    /**
     * Returns iterator to iterate on the edges
     * @param source The source vertex
     * @return iterator to iterate on the edges
     */
    public Iterator<E> edgeIterator(E source){
        return map.get(source).iterator();
    }

    public List<E> getEdges(E source){
        return map.get(source);
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for (E v : map.keySet()) {
            builder.append(v.toString() + ": ");
            for (E w : map.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }

        return (builder.toString());
    }


}
