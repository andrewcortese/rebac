
public class Edge<E> {
	private int index;
	private int source;
	private int destination;
	private E component;
	
	public Edge(int index, int vertex1, int vertex2, E component)
	{
		this.index = index;
		this.source = vertex1;
		this.destination = vertex2;
		this.component = component;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getSource() {
		return source;
	}

	public void setVertex1(int vertex1) {
		this.source = vertex1;
	}

	public int getDestination() {
		return destination;
	}

	public void setVertex2(int vertex2) {
		this.destination = vertex2;
	}

	public E getComponent() {
		return component;
	}

	public void setComponent(E component) {
		this.component = component;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Edge [index=" + index + ", vertex1=" + source + ", vertex2=" + destination + ", component=" + component
				+ "]";
	}
	
	
}
