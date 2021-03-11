import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){
        DisjointSets s = new DisjointSets(g.getNbNodes()); //first create a disjoint set with respect to g
        ArrayList<Edge> es = g.listOfEdgesSorted(); //sort the edges of g by weight
        WGraph new_graph = new WGraph(); //initialize a new graph so that we can store nodes and return this later!
        
        for (int i=0;i<es.size();i++) { 
        	Edge e = es.get(i); //iterate through all the edges in the graph
        	
        	if (Kruskal.IsSafe(s, e)) { //if the edge is safe then
        		int[] nodes = e.nodes; //retrieve the nodes from the edge
        		s.union(nodes[0], nodes[1]); //merge the nodes into the same tree
        		new_graph.addEdge(e); //add the safe edge to the graph
        	}
        }
        return new_graph;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){
    	if (p.find(e.nodes[0])==p.find(e.nodes[1])) { //if the nodes have the same parent (i.e. they are already connected) 
    		return false; //then we need not consider them (as if we did, we would form a loop which violates the properties of an MST)
    	}
        return true; //if the nodes do not have the same parent then the edge is safe to add!
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}