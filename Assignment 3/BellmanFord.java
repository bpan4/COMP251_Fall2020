import java.util.*;

//no collaborators
public class BellmanFord{

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    class BellmanFordException extends Exception{
        public BellmanFordException(String str){
            super(str);
        }
    }

    class NegativeWeightException extends BellmanFordException{
        public NegativeWeightException(String str){
            super(str);
        }
    }

    class PathDoesNotExistException extends BellmanFordException{
        public PathDoesNotExistException(String str){
            super(str);
        }
    }

    BellmanFord(WGraph g, int source) throws NegativeWeightException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         */
    	this.source=source;//initialize the source, a new list of distances and a new list of predecessors using the number of nodes in the graph!
    	int n = g.getNbNodes();     
        this.distances = new int [n];
        this.predecessors  = new int [n];
        ArrayList<Edge> edges = g.getEdges();
        
        for (int i = 0; i < n; i++) { //initialize distance from source vertex to all other vertices as the MAX_VALUE
            if (i == source ) {
            	this.distances[i] = 0;
            } else {
            	this.distances[i]=Integer.MAX_VALUE;
            }
            this.predecessors[i]=-1; //-1 indicates that there are no predecessors
        }
        
        this.distances[source] = 0;// also initialize the distance from the source to itself as 0!

        for (int i = 0; i<n-1; i++){ //relaxes all the edges n-1 (number of nodes -1) times
        	//boolean update = false; //keeps track of whether or not an update was made!
        	
        	for (Edge e : edges) { //for all edges
        		int u = e.nodes[0];
        		int v = e.nodes[1];
        		int d = this.distances[u] + e.weight;
        		if ((d < this.distances[v]) && (this.distances[u] != Integer.MAX_VALUE)) {
        			//update = true;
        			this.distances[v] = d;
        			this.predecessors[v] = u;
        		}
        	}
        }
        
        //boolean nCycle = false;
        //for (int i=0; i<g.getEdges().size();i++) { //for all edges, we check whether or not there exists negative weights in the graph
        for (Edge e : edges) {
        	//Edge e = g.getEdges().get(i);
        	int u = e.nodes[0];
        	int v = e.nodes[1];
        	int d = this.distances[u]+e.weight;
        	
        	if ((this.distances[u]!=Integer.MAX_VALUE) && (d < this.distances[v])) { //if there exists a negative cycle then
        		//nCycle=true; //we update the boolean value meant to keep track of the existence of negative cycles
        		throw new NegativeWeightException("There exists negative weights in the graph."); //throw the exception because we already know if there's a negative cycle
        	}
        }
    }

    public int[] shortestPath(int destination) throws PathDoesNotExistException{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Error is thrown
         */
    	ArrayList<Integer> rev = new ArrayList<Integer>(); //unsure how long the path is yet so size is not initialized
    	rev.add(destination); //will use p to keep track of nodes in the shortest path 
        //essentially travels UP the tree from the destination to the source!
    	while (this.predecessors[destination] != -1) { //while we can traverse the graph backwards from the destination,
    		rev.add(0, this.predecessors[destination]); //we add all the nodes that are predecessors of the destination
    		destination = this.predecessors[destination];//and then we update the destination to be the node we just added
    	}
    	if (destination == this.source) { //if the final node in the path we found above in the while loop is the source node, then it means that we have a path!!!!
    		int[] p = new int[rev.size()];
    		for (int i = 0 ; i < rev.size() ; i++) {
    			p[i] = rev.get(i);
    		} return p; // we return the path, as it exists 
    	} 
    	//if the path does not exist, we have to throw an exception!
    	throw new PathDoesNotExistException("There is no path available.");
    }
    
    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}