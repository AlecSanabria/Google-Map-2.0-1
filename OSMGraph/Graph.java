package OSMGraph;

import java.util.*;
import OSMUtil.*;

/**
 * author: Lining Pan
 */
public class Graph {
	private Map<Long, AbstractVertex> verList;
	private Set<Long> nodeOnRoad;
	private Map<AbstractEdge, Set<Long>> nodeInEdge;
	private Map<AbstractVertex, LinkedList<AbstractEdge>> adjList;
	private Map<Long, Set<AbstractEdge>> way2edgeList; 
	private OSMAbstractDataModel dm;
	
	public Graph(Map<Long, AbstractVertex> v,
				 Map<AbstractVertex, LinkedList<AbstractEdge>> a,
                 Set<Long> nr,
                 Map<AbstractEdge, Set<Long>> nie,
				 Map<Long, Set<AbstractEdge>> wl,
				 OSMAbstractDataModel d) {
		verList = v;
		nodeOnRoad = nr;
		adjList = a;
		nodeInEdge = nie;
		way2edgeList = wl;
		dm = d;
	}
	
	public Iterator<AbstractEdge> getEdgeIterator(AbstractVertex v) {
		if (adjList.containsKey(v)) {
			return adjList.get(v).iterator();
		}
		return null;
	}

	public AbstractVertex getVertexById(long id){
	    return verList.getOrDefault(id,null);
    }
	
	public boolean isVertex(long id) {
		return verList.containsKey(id);
	}
	
	public Set<AbstractEdge> getEdgesContainedByWay(long id){
		if(this.way2edgeList.containsKey(id)) {
			return this.way2edgeList.get(id);
		}
		return null;
	}
	public Set<AbstractEdge> getEdgesWithInternalNode(long id){
	    Set<Long> ways = dm.getAllWayContainsNode(id);
        Set<AbstractEdge> re = new HashSet<>();
	    for(Long i : ways){
            Set<AbstractEdge> se = way2edgeList.getOrDefault(i,null);
            if(se != null){
                for(AbstractEdge e : se){
                    if(nodeInEdge.get(e).contains(id)){
                        re.add(e);
                    }
                }
            }
        }
	    return re;
    }

    public boolean isOnRoad(long id){
	    return nodeOnRoad.contains(id);
    }

    public final Set<Long> getNodeOnRoad(){
	    return nodeOnRoad;
    }
}
