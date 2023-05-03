/*
* Just wanted to give @JonahRubenstien(revered SERF of the FriendsbaltCS department) a big thank you for inspiring me and helping me to write such effective and effecint code!
*/
import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Edge;
import java.util.Random;
public class CatGame {
//Size of the grid in the game
private int n;
//Total number of tiles in the grid
private int FREEDOM;
//Index of the tile where the cat starts
private int s;
//Array that keeps track of which tiles have been marked
private boolean[] marked;
//Variable that is true if the cat is trapped
private boolean CatIsTrapped;
//Graph representation of the grid
private EdgeWeightedGraph G;
//DijkstraUndirectedSP object used to find the shortest path, Thanks Sedjewick!
private DijkstraUndirectedSP SP;
//Random number generator used to randomly mark tiles in the grid
private Random rand;
/**
 * Initializes a new CatGame object with the given size of the grid.
 * @param n the size of the grid
 */
public CatGame(int n){
	this.n = n;
	FREEDOM = n * n;
  marked = new boolean[n*n];
  s = getIndex(n/2,n/2);
	G = new EdgeWeightedGraph (FREEDOM + 1);
	for (int row = 1; row < n - 1; row ++){
		for (int col = 1; col < n - 1; col ++){
			int v = getIndex(row, col); 
			G.addEdge(new CatEdge(v, v + 1));
			G.addEdge(new CatEdge(v, v - 1));
			G.addEdge(new CatEdge(v, v + n));
			G.addEdge(new CatEdge(v, v - n));
		if(row % 2 == 0){
			G.addEdge(new CatEdge(v, v + n - 1));
			G.addEdge(new CatEdge(v, v - n - 1));
		}
		else{
			G.addEdge(new CatEdge(v, v + n + 1));
			G.addEdge(new CatEdge(v, v - n + 1));
		}
		marked[v] = false;	
		}
	}
		for(int i = 0; i < n; i++){
			int v = getIndex(0,i);
			G.addEdge(new CatEdge(v, FREEDOM));
			v = getIndex(n-1, i);
			G.addEdge(new CatEdge(v, FREEDOM));
			v = getIndex(i, 0);
			G.addEdge(new CatEdge(v, FREEDOM));
			v = getIndex(i, n-1);	
			marked[v] = false;			
		}
  rand = new Random();
  for(int i = 0; i < n; i++){
   int row = rand.nextInt(n); int col = rand.nextInt(n);
   int v = getIndex(row, col);
   if(v != s){
    for(Edge e : G.adj(v)){
     CatEdge c = (CatEdge) e;
     c.changeWeight();
    }
    marked[v] = true;
    }
   }
}
/**
 * Marks the tile at the specified row and column, and changes the cat according to DijkstraUndirectedSP
 * @param row the row of the tile to mark
 * @param col the column of the tile to mark
 */
public void markTile(int row, int col){
	int v = getIndex(row, col);
		for(Edge i : G.adj(v)){
			CatEdge c =(CatEdge) i;
			c.changeWeight();
		}
	SP = new DijkstraUndirectedSP(G, s);
	if(SP.distTo(FREEDOM) != Double.POSITIVE_INFINITY){
		CatEdge e = (CatEdge) SP.pathTo(FREEDOM).iterator().next();
		s = e.other(s);
	}
  else{
   for(Edge i :G.adj(s)){
     CatEdge c = (CatEdge) i;
     if(c.weight() != Double.POSITIVE_INFINITY){
      s = c.other(s);
      break;
     }
   }
  }
  marked[v] = true;
}
/**
 * Marks the tile at the specified row and column, with out chaning the cat
 * @param row the row of the tile to mark
 * @param col the column of the tile to mark
 */
private void startMarkTile(int row, int col){
	int v = getIndex(row, col);
	marked[v] = true;
		for(Edge i : G.adj(v)){
			CatEdge c =(CatEdge) i;
			c.changeWeight();
		}
}
/**
 * Returns whether the tile at the specified row and column has been marked
 * @param row the row of the tile to check
 * @param col the column of the tile to check
 * @return true if the tile has been marked, false otherwise
 */
public boolean marked(int row, int col){
	return marked[getIndex(row,col)];
}
/**
 * Returns the current position of the cat on the board as an array of two integers
 * @return the current position of the cat on the board
 */
public int[] getCatTile(){
	return new int[]{s/n,s%n};
}
/**
 * Returns whether the cat has escaped
 * @return true if the cat has escaped
 */
public boolean catHasEscaped(){
	return s == FREEDOM;
}
/**
 * Returns whether the cat is trapped
 * @return true if the cat is trapped
 */
public boolean catIsTrapped(){
	  for(Edge i : G.adj(s)){
      CatEdge c =(CatEdge) i;
      if(c.weight() != Double.POSITIVE_INFINITY) return false;
    }
    return true;
}
/**
 * Returns the index of the tile at the specified row and column.
 * @param row the row of the tile
 * @param col the column of the tile
 * @return the index of the tile
 */
private int getIndex(int row, int col){
  return n * row + col;
}
}
