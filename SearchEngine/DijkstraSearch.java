package SearchEngine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import OSMGraph.AbstractEdge;
import OSMGraph.AbstractVertex;
import OSMGraph.Graph;
import OSMGraph.RoadEdge;
import OSMGraph.RoadVertex;

public class DijkstraSearch implements AbstractSearchEngine {

	private Graph graph;
	
	public static void main(String[] args) {
		Map<AbstractVertex, LinkedList<AbstractEdge>> a = new HashMap<AbstractVertex, LinkedList<AbstractEdge>>();
		AbstractVertex a1 = new RoadVertex(null);
 	}
	
	public DijkstraSearch(Graph graph) {
		this.graph = graph;
	}

	// using dijkstra
	@Override
	public List<RoadEdge> getVertexRoute(AbstractVertex from, AbstractVertex to) {
		from.updateCost(null, null);
		AbstractVertex currentV = from;
		Iterator<AbstractEdge> i = this.graph.getEdgeIterator(currentV);
		boolean foundDestination = false;
		PriorityQueue<NodeWrapper> p = new PriorityQueue<NodeWrapper>();
		while (!foundDestination) {
			while (i.hasNext()) {
				AbstractEdge edge = i.next();
				if(!edge.getToNode().isFinished()) {
					edge.getToNode().updateCost(currentV, (RoadEdge) edge);
					p.add(new NodeWrapper(edge.getToNode(), edge.getToNode().getCostToGetHere()));
				}
			}
			currentV.setFinished();
			currentV = p.poll().getVertex();
			i = this.graph.getEdgeIterator(currentV);
			if(currentV == to) {
				foundDestination = true;
			}
		}

		return currentV.getPath();
	}
	

}
