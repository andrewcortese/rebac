import java.util.LinkedList;
import java.util.List;

public class Node<V> {
	private int index = -1;
	private LinkedList<Integer> adjacency = new LinkedList<Integer>();
	private LinkedList<Integer> incidentEdges = new LinkedList<Integer>();
	private V component;
	
	public Node(int index, V component)
	{
		this.index = index;
		this.component = component;
	}
	
	public void addNeighbor(int node, int edge)
	{
		this.incidentEdges.add(edge);
		this.adjacency.add(node);
	}

	public int degree()
	{
		return this.adjacency.size();
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<Integer> getAdjacency() {
		return adjacency;
	}


	public List<Integer> getIncidentEdges() {
		return incidentEdges;
	}

	public V getComponent() {
		return component;
	}

	public void setComponent(V component) {
		this.component = component;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Index=%s, component=%s\n neighbors: %s\n", index, component.toString(), this.adjacency.toString());
	}

	
	
	
}
