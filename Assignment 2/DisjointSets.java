import java.io.*;
import java.util.*;


/****************************
*
* COMP251 template file
*
* Assignment 2, Question 1
*   No collaborators
*****************************/


public class DisjointSets {

    private int[] par;
    private int[] rank;
    
    /* contructor: creates a partition of n elements. */
    /* Each element is in a separate disjoint set */
    DisjointSets(int n) {
        if (n>0) {
            par = new int[n];
            rank = new int[n];
            for (int i=0; i<this.par.length; i++) {
                par[i] = i;
            }
        }
    }
    
    public String toString(){
        int pari,countsets=0;
        String output = "";
        String[] setstrings = new String[this.par.length];
        /* build string for each set */
        for (int i=0; i<this.par.length; i++) {
            pari = find(i);
            if (setstrings[pari]==null) {
                setstrings[pari] = String.valueOf(i);
                countsets+=1;
            } else {
                setstrings[pari] += "," + i;
            }
        }
        /* print strings */
        output = countsets + " set(s):\n";
        for (int i=0; i<this.par.length; i++) {
            if (setstrings[i] != null) {
                output += i + " : " + setstrings[i] + "\n";
            }
        }
        return output;
    }
    
    /* find representative of element i */
    public int find(int i) {
    	if (this.par[i]==i) {
    		return i; //if i is its own parent and is the representative of this set then we simply return i
    	}
    	int rep = find(this.par[i]);//find the representative of i
    	this.par[i]=rep;//make representative of the set i's parent
    	return rep; //return the representative
    }

    /* merge sets containing elements i and j */
    public int union(int i, int j) {
    	int rep_i = this.find(i);//find the representation of both element i and j
    	int rep_j = this.find(j);
    	
    	if (rep_i==rep_j) {
    		return rep_i; //we do not need to merge anything together if they are in the same set so we can just return the representative of the set!
    	}
    	
    	int rank_i = rank[rep_i];//find the rank of both sets
    	int rank_j = rank[rep_j];
    	
    	if (rank_i>rank_j) { //if the rank of j is less than the rank of i
    		this.par[rep_j] = rep_i; //make the root of i the parent of the root of j 
    		return rep_i;//return the representative of the new set where the root is the representative of i
    		
    	} else if (rank_i<rank_j) { //if the rank of i is less than the rank of j
    		this.par[rep_i] = rep_j;//make the root of j the parent of the root of i 
    		return rep_j;//return the root the new tree (aka the representative of j)
    	}
    	//if the rank of i and j are identical, then the root of the tree containing i will become a child of the root of the tree containing j
    	this.par[rep_i]=rep_j;
    	this.rank[rep_j]++; //the rank is increased by 1
    	return rep_j; //return the root the new tree (aka the representative of j)
    }
    
    public static void main(String[] args) {
        
        DisjointSets myset = new DisjointSets(6);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 1");
        myset.union(2,1);
        System.out.println(myset);
        System.out.println("-> Union 4 and 5");
        myset.union(4,5);
        System.out.println(myset);
        System.out.println("-> Union 3 and 1");
        myset.union(3,1);
        System.out.println(myset);
        System.out.println("-> Union 2 and 4");
        myset.union(2,4);
        System.out.println(myset);
        
    }

}