package test;


import java.io.*;
import java.util.*;

public class Open_Addressing {
     public int m; // number of SLOTS AVAILABLE
     public int A; // the default random number
     int w;
     int r;
     public int[] Table;

     protected Open_Addressing(int w, int seed, int A) {

         this.w = w;
         this.r = (int) (w-1)/2 +1;
         this.m = power2(r);
         if (A==-1){
            this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
         }
        else{
            this.A = A;
        }
         this.Table = new int[m];
         for (int i =0; i<m; i++) {
             Table[i] = -1;
         }
         
     }
     
                 /** Calculate 2^w*/
     public static int power2(int w) {
         return (int) Math.pow(2, w);
     }
     public static int generateRandom(int min, int max, int seed) {     
         Random generator = new Random(); 
                 if(seed>=0){
                    generator.setSeed(seed);
                 }
         int i = generator.nextInt(max-min-1);
         return i+min+1;
     }
        /**Implements the hash function g(k)*/
        public int probe(int key, int i) {
        	return (((this.A*key)%(power2(this.w))>>(this.w-this.r) + i) % (power2(this.r))); // linear probing method uses hash value and returns index
     }
     
     
     /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int insertKey(int key){
        	int i = 0; // initialize counter to 0
        	int n = probe(key,i); //initializes first probe, will test for conflicts in while loop below
        	
        	while ((i<this.m-1)&&(Table[n]!=-1)) { //if the count has not exceeded the number of slots available AND the slot found is already filled (-1) then
        		i += 1; // increase the count by 1 so that we can use it to retrieve a new probe
        		n = probe(key,i); //gets a new probe that is passed into the while loop again for testing
        	} 
        	
        	if (i<this.m-1){ //if the while loop terminated because an empty slot was found and NOT because the count exceeded the number of slots available
        		Table[n]=key; // insert key
        		return i; // returns the count = number of collisions
        	}
        	return i+=1; //return counter + 1 if all slots were tested and non-empty
        }
        
        /**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
        public int insertKeyArray (int[] keyArray){
            //TODO
            int collision = 0;
            for (int key: keyArray) {
                collision += insertKey(key);
            }
            return collision;
        }
            
         /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int removeKey(int key){
        	int i = 0; // initialize counter to 0
            int n = probe(key,i); // initializes first probe, will test if value in Table matches key in while loop below
            
            while ((i<m-1)&&(Table[n]!=key)) { //if the count has not exceeded the number of slots in Table and value found in Table at probe does not match our key
            	i+=1; //increase the count by 1 so that we may use it to retrieve a new probe
            	n = probe(key,i); // get a new probe using the new count and pass it into the while loop for testing again
            }
            
            if (i<m-1){ //if the while loop terminated because the key was found and NOT because the count exceeded the number of slots available
            	Table[n] = -1; // set the value at the probe to -1 (empty)
            	return i;// returns the count = number of collisions
            }
            return i+=1; //returns counter+1 if all slots have been tested and non-empty
        }
}
