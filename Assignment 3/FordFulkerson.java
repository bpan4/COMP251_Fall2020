import java.util.*;
import java.io.File;

//no collaborators
public class FordFulkerson {
	
	public static ArrayList<Integer> hDFS(Integer s, Integer d, WGraph g, ArrayList<Integer> v){
		ArrayList<Edge> es = g.getEdges(); //initialize ArrayList to store all the edges of g
		ArrayList<Integer> ns = new ArrayList<Integer>(); //initialize ArrayLists to store the neighbours of the source node
		
		//if the source is the same as the destination, then we simply have to add the source to the ArrayList of nodes and return it
		if (d == s) { 
			ns.add(s); 
			return ns;
		} v.add(s); //otherwise, we add the source to the ArrayList of visited nodes
		
		for (Edge e:es) { //for every edge in the graph g
			Integer e1 = e.nodes[1]; //the node at the end of edge e
			//if the edge is from the source && the node at the end of the edge has not already been visited && the weight of the edge is not equal to 0
    		if ( (e.nodes[0] == s) && (!v.contains(e1)) && (e.weight != 0) ){ 
    			ArrayList<Integer> p=hDFS(e1,d,g,v); //we use the node at the end of the edge to look for its neightbours
    			
    			if (!p.isEmpty()) { // if the resulting path is not empty,
    				p.add(0,s); //then we add out source node into the path and return it
    				return p;
    			}
    		}
    	} return ns; //we return the ArrayList initialized at the beginning containing all the neighbours of the source node!
	}
	
	public static Integer minW(ArrayList<Edge> es) { //gets the bottleneck value given the edges of a path
		Integer m = es.get(0).weight; //initialize the minimal weight arbitrarily
		for (Edge e:es) { //for all edges in the ArrayList of edges given, 
			Integer w = e.weight;
			if (m > w) { //we update the minimal weight accordingly if the weight of an edge is smaller than the current minimal weight
				m = w;
			}
		} return m;//return the minimal weight
	}
	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();
		ArrayList<Integer> v = new ArrayList<Integer>();//initialize a new ArrayList to store all the visited nodes
		path = hDFS(source,destination, graph, v); //using the helper function, we get a path of all the neighbours of the source node!
		return path; //return said path as result
	}

	public static String fordfulkerson( WGraph graph){
		String answer="";
		int maxFlow = 0;
		ArrayList<Edge> edges = graph.getEdges(); //initialize a new ArrayList with all the edges in the graph
		WGraph rg = new WGraph(graph); //initialize a new graph
		ArrayList<Edge> fe = graph.getEdges(); //and initialize a new ArrayList of all the edges in the graph

		
		for (Edge e:fe) { //for all edges in the graph,
			rg.addEdge(new Edge(e.nodes[1],e.nodes[0], 0)); //we add the edge in the new graph so that there are back edges in the new graph
		}
		
		ArrayList<Integer> path = pathDFS(graph.getSource(), graph.getDestination(), rg); //get the path of the new graph
		
		while (!path.isEmpty()) { //while the path exists in the new graph, augment and update the new graph
			ArrayList<Edge> epath = new ArrayList<Edge>(); //initialize a new ArrayList to keep track of all the edges in the path
			for (int i=0 ; i<path.size()-1 ; i++) { //for all indices of the path,
				epath.add(rg.getEdge(path.get(i), path.get(i+1))); //we add the edge between two adjacent nodes to the ArrayList of the edges in the path
			}
			Integer bn= minW(epath); //getting the bottleneck value of the path
			for (Edge e:epath) { //for all the edges in the ArrayList of edges in the path,
				e.weight -= bn; //we adjust the weight of all edges in compliance with the bottleneck value
				Edge be = rg.getEdge(e.nodes[1], e.nodes[0]); //create a new "back edge" (with respect to the original edge) 
				be.weight += bn; //then, adjust the weight of the "back edge" in accordance with the bottleneck value
			}	
			maxFlow += bn; //adjust the maximum flow to include the bottleneck value
			path = pathDFS(rg.getSource(), rg.getDestination(), rg); //update path to the path found in the new graph that we created
		}
		for (Edge e: edges) { //for all the edges in the original graph,
			e.weight = rg.getEdge(e.nodes[1], e.nodes[0]).weight; //we update the weight by using the new graph we created
		}
		answer += maxFlow + "\n" + graph.toString();	
		return answer;
	}

	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
	         System.out.println(fordfulkerson(g));
	 }
}
