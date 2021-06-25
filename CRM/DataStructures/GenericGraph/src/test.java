package DataStructures.GenericGraph.src;

import DataStructures.GenericGraph.src.GenericGraph;

import java.util.Iterator;

public class test {
    public static void main(String []args){
        GenericGraph<Integer> gg = new GenericGraph<Integer>();

        gg.addEdge(0, 1);
        gg.addEdge(0, 4);
        gg.addEdge(1, 2);
        gg.addEdge(1, 3);
        gg.addEdge(1, 4);
        gg.addEdge(2, 3);
        gg.addEdge(3, 4);

        gg.addVertex(10);
        gg.addVertex(23);

        System.out.println(gg.toString());

        System.out.println(gg.getVertexCount());
        System.out.println(gg.getEdgesCount());
        System.out.println(gg.hasEdge(1, 4));
        System.out.println(gg.hasEdge(10, 4));
        System.out.println(gg.hasVertex(23));
        System.out.println(gg.hasVertex(233));

        Iterator<Integer> itr = gg.vertexIterator();
        while (itr.hasNext()){
            Integer i = itr.next();
            System.out.println(i);
        }

        System.out.println("###############");
        itr = gg.edgeIterator(1);
        while (itr.hasNext()){
            Integer i = itr.next();
            System.out.println(i);
        }
    }
}
