import edu.princeton.cs.algs4.Edge;
public class CatEdge extends Edge{ 
	/**
	 * The weight of the CatEdge, initialized to 1.
	 */
	private double weight = 1;
	/**
	 * Creates a new instance of CatEdge with the given to and from vertices and a weight of 1.
	 * @param to the destination vertex of the edge.
	 * @param from the source vertex of the edge.
	 */
	public CatEdge(int to, int from){
		super(to, from, 1);
	}
	/**
	 * Changes the weight.
	 */
	public void changeWeight(){
		weight = Double.POSITIVE_INFINITY;
	}
	/**
	 * Returns the weight.
	 * @return the weight
	 */
	public double weight(){
		return weight;
	}
}
