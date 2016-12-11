import java.util.ArrayList;
import java.util.function.Supplier;

public class Graph<N, E> {
	ArrayList<Edge<E>> edges;
	ArrayList<Node<N>> nodes;

	//ArrayList E = new ArrayList<E>();
	
	public Graph(int edgeCapacity, int nodeCapacity)
	{
		this.edges = new ArrayList<Edge<E>>(edgeCapacity);
		this.nodes = new ArrayList<Node<N>>(nodeCapacity);
	}

	/**
	 * @return the number of edges in the graph
	 * @see java.util.ArrayList#size()
	 */
	public int numEdges() {
		return edges.size();
	}

	/**
	 * @param index
	 * @return the Edge object at the given index
	 * @see java.util.ArrayList#get(int)
	 */
	public Edge<E> getEdge(int index) {
		return edges.get(index);
	}

	
	
	public int addEdgeAndNodes(int source, int dest, E edgeComponent, Supplier<N> nodeComponentSupplier)
	{
		int highestNodeIndex = Integer.max(source, dest);
		
		while(highestNodeIndex >= this.numNodes())
		{
			this.addNode(nodeComponentSupplier.get());	
		}
		
		int edgeIndex = this.addEdge(source, dest, edgeComponent);
		
		return edgeIndex;
	}
	
	public int addEdgeAndNodes(int source, int dest, E edgeComponent, N sourceComponent, N destComponent, Supplier<N> nodeComponentSupplier)
	{
		int edgeIndex = this.addEdgeAndNodes(source, dest, edgeComponent, nodeComponentSupplier);
		this.getNode(source).setComponent(sourceComponent);
		this.getNode(dest).setComponent(destComponent);
		
		return edgeIndex;
	}
	/**
	 * Create an edge between two existing nodes
	 * @param source the first node
	 * @param dest the second node
	 * @param component the edge attribute object
	 * @return
	 * Precondition: source and dest nodes must exist
	 */
	public int addEdge(int source, int dest, E component) {
		int index = this.numEdges();
		edges.add(new Edge<E>(index, source, dest, component));
		this.makeNeighbors(source, dest, index);
		return index;
	}
	
	/**
	 * The source and destination nodes are added to each other's adjacency lists.
	 * The specified edge is used as the connector
	 * @param source the index of the first node
	 * @param dest the index of the second node
	 * @param edgeIndex the index of the already-existing edge
	 */
	private void makeNeighbors(int source, int dest, int edgeIndex)
	{
		this.getNode(source).addNeighbor(dest, edgeIndex);
		this.getNode(dest).addNeighbor(source, edgeIndex);
	}
	
	public void addBidirectionalEdges(int source, int dest, E sourceToDestComponent, E destToSourceComponent)
	{
		this.addEdge(source, dest, sourceToDestComponent);
		this.addEdge(dest, source, destToSourceComponent);
	}
	
	public int addNode(N component){
		int index = this.numNodes();
		nodes.add(new Node<N>(index, component));
		return index;
	}
	
	public Node<N> getNode(int index)
	{
		return this.nodes.get(index);
	}
	
	public int numNodes()
	{
		return this.nodes.size();
	}
	
	


	/**
	 * @return the edges
	 */
	public ArrayList<Edge<E>> getEdges() {
		return edges;
	}

	/**
	 * @return the nodes
	 */
	public ArrayList<Node<N>> getNodes() {
		return nodes;
	}
	
	
	
}
