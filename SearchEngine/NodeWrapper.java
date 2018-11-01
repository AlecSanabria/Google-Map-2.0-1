package SearchEngine;

import OSMGraph.AbstractVertex;

public class NodeWrapper implements Comparable<NodeWrapper>{
	
	private AbstractVertex vertex;
	private double cost;
	
	public NodeWrapper(AbstractVertex v, double cost) {
		this.vertex = v;
		this.cost = cost;
	}
	
	public AbstractVertex getVertex() {
		return this.vertex;
	}

	@Override
	public int compareTo(NodeWrapper o) {
		return (int) (this.cost - o.cost);
	}

}
