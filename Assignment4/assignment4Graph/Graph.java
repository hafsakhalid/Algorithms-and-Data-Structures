package assignment4Graph;

import java.util.Arrays;

public class Graph {
 
 boolean[][] adjacency;
 int nbNodes;
 
 public Graph (int nb){
  this.nbNodes = nb;
  this.adjacency = new boolean [nb][nb];
  for (int i = 0; i < nb; i++){
   for (int j = 0; j < nb; j++){
    this.adjacency[i][j] = false;
   }
  }
 }
 
 public void addEdge (int i, int j){
   this.adjacency[i][j] = true;
   this.adjacency[j][i] = true;
 }
 
 public void removeEdge (int i, int j){
  this.adjacency[i][j] = false;
  this.adjacency[j][i] = false;
 }
 
 public int nbEdges(){
  //start to count 
  int n = 0;
  for(int i = 0; i< nbNodes; i++){
    for(int j = 0; j< nbNodes; j++){
      if(this.adjacency[i][j] == true){
        n += 1; 
      }
      if(this.adjacency[i][j] == true && i == j){
        n += 1;
      }
    }
  }
  n = n/2;
  return n;
 }
 
 public boolean cycle(int start){
   boolean isCycle = false;
   //initiate a counter for the number of edges of the required vertex
   if(moreThanOneEdge(start) == false){
     return false;
   }
   Graph visited = new Graph(nbNodes);
   int end = start;
   isCycle = cycleHelper(start, visited, end);
   return isCycle;
 
   // DON'T FORGET TO CHANGE THE RETURN
 }
 
 public boolean cycleHelper(int start, Graph visited, int end){
  boolean isCycle = false;
   //check if there are any nodes
   for(int i=0;i<adjacency.length;i++){
     //check for edges
     if(adjacency[start][i]){
       if(!visited.adjacency[start][i]){
         visited.addEdge(start,i);
         if(cycleHelper(i,visited,end))return true;
       }
       //if we have visited
       else if(start==end)isCycle= true;
     }
   }
   //if there are no edges
   return isCycle;
 }
 
 public boolean moreThanOneEdge(int node){
   int n = 0;
   for(int i = 0; i< nbNodes; i++){
     if(this.adjacency[node][i] == true && i != node){
       n ++;
     }
   }
   if(n < 2){
     return false;
   }
   return true;
 }
 public int shortestPath(int start, int end){
   //this is set to check for self loops 
   if(this.adjacency[start][end] == true){
     return 1;
   }
   if(start == end && this.adjacency[start][end] != true){
     return nbNodes + 1;
   }
    //check to see if start and end are connected, if they are return false right away 
   boolean[] visitedboolean = new boolean[nbNodes];
   int[] graphSoFar = new int[1];
   graphSoFar[0] = start;
   int[] graphremaining = printConnectedGraph(start, visitedboolean, graphSoFar);
   System.out.println(Arrays.toString(graphremaining));
   boolean path = false;
   for(int i = 0; i < graphremaining.length; i++){
     if(graphremaining[i] == end){
       path = true;
     }
   }
   if(!path){
     return nbNodes +1;
   }
   
   int[] v = new int[1];
   v[0]=start;
   
   for(int i = 0;i<v.length;){
     v = cat(v,allNeighbors(v));
     i++;
     if(dup(v,end)){
       return i;
     }
   }
   
  return -1; // DON'T FORGET TO CHANGE THE RETURN
 }
 public int[] allNeighbors(int[]v){
   int[] buff = null;
   for(int i = 0;i<v.length;i++){
     int[] g = neighbors(v[i]);
     if(buff==null){
       buff = g;
       continue;
     }
     buff = cat(buff,g);
   }
   return buff;
   
 }
 public int[] cat(int[] v, int[]g){
   int[] buff = new int[v.length+g.length];
   for(int i = 0;i<v.length;i++){
     buff[i] = v[i];
   }
   int j = 0;
   for(int i = 0;i<g.length;i++){
     if(!dup(buff,g[i])){
       buff[v.length+j] = g[i];
       j++;
     }
   }
   int[]buff2 = new int[v.length+j];
   for(int i = 0;i<buff2.length;i++)buff2[i] = buff[i];
   return buff2;
 }
 public boolean dup(int[] v, int g){
   for (int i = 0; i<v.length;i++){
     if(v[i]==g)return true;
   }
   return false;
 }
 
 public int[] neighbors(int start){
   int size = 0;
   for(int i = 0; i<nbNodes;i++){
     if(adjacency[start][i]){
       size++;
     }
   }
   int[] q = new int[size];
   int qc = 0;
   for(int i = 0; i<nbNodes;i++){
     if(adjacency[start][i]){
       q[qc] = i;
       qc++;
     }
   }
   return q;
 }
 
public int[] printConnectedGraph(int start, boolean[] visited, int[] graphTillNow){
   for(int i = 0; i < nbNodes; i++){
       if(!visited[i] && (adjacency[start][i] || i == start)){
         int[] neighbours = getNeighboursBoolean(i, visited);
         visited[i] = true;
         graphTillNow = cat(graphTillNow, neighbours);
        
         graphTillNow = printConnectedGraph(i, visited, graphTillNow);
       }
       if(i == nbNodes -1){
         return graphTillNow;
       }
     }
   return graphTillNow;
 }
public int[] getNeighboursBoolean(int start, boolean[] visited){
   int size = 0;
   for(int i = 0; i<nbNodes;i++){
     if(adjacency[start][i] && !visited[i]){
       size++;
     }
   }
   int[] q = new int[size];
   int qc = 0;
   for(int i = 0; i<nbNodes;i++){
     if(adjacency[start][i] && !visited[i]){
       q[qc] = i;
       qc++;
     }
   }
   return q;
 }

}

