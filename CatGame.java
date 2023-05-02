import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Edge;
import java.util.Random;
public class CatGame {
private int n;
private int FREEDOM;
private int s;
private boolean[] marked;
private boolean CatIsTrapped;
private EdgeWeightedGraph G;
private DijkstraUndirectedSP SP;
private Random rand;
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
private void startMarkTile(int row, int col){
	int v = getIndex(row, col);
	marked[v] = true;
		for(Edge i : G.adj(v)){
			CatEdge c =(CatEdge) i;
			c.changeWeight();
		}
}
public boolean marked(int row, int col){
	return marked[getIndex(row,col)];
}
public int[] getCatTile(){
	return new int[]{s/n,s%n};
}
public boolean catHasEscaped(){
	return s == FREEDOM;
}
public boolean catIsTrapped(){
	  for(Edge i : G.adj(s)){
      CatEdge c =(CatEdge) i;
      if(c.weight() != Double.POSITIVE_INFINITY) return false;
    }
    return true;
}
private int getIndex(int row, int col){
  return n * row + col;
}
}
