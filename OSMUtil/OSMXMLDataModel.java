package OSMUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * author: Lining Pan
 */
public class OSMXMLDataModel implements OSMAbstractDataModel {

	private Map<Long,OSMNode> nodeMap;
	private Map<Long,OSMWay> wayMap;
	private Map<Long,OSMRelation> relationMap;
	private Map<Long, Set<Long>> nodeInWay;
	
	public OSMXMLDataModel(Map<Long,OSMNode> n, Map<Long,OSMWay> w, Map<Long,OSMRelation> r) {
		nodeMap = n;
		wayMap = w;
		relationMap = r;
		nodeInWay = new HashMap<>();
		if(nodeMap != null && wayMap != null) {
			for(Long k:wayMap.keySet()) {
				OSMWay curr_w = wayMap.get(k);
				for(Long nk: curr_w.getNodeIdList()) {
					if(!nodeInWay.containsKey(nk)) {
						nodeInWay.put(nk, (Set)new HashSet<>());
					}
					nodeInWay.get(nk).add(k);
				}		
			}
		}
	}
	
	@Override
	public OSMNode getNodeById(long id) {
		if(nodeMap != null)
			if(nodeMap.containsKey(id)) {
				return nodeMap.get(id);
			}
		return null;
	}

	@Override
	public Set<OSMNode> getNodeByIdSet(Collection<Long> id_set) {
		Set<OSMNode> ans = new HashSet<>();
		if(nodeMap != null)
			for(Long k:id_set)
				if(nodeMap.containsKey(k))
					ans.add(nodeMap.get(k));
		return ans;
	}

	@Override
	public Set<Long> getAllWayContainsNode(long id) {
		if(nodeInWay.containsKey(id))
			return nodeInWay.get(id);
		return null;
	}

	@Override
	public Set<Long> getAllWayContainsNode(OSMNode n) {
		return getAllWayContainsNode(n.getID());
	}

	@Override
	public Set<Long> getAllNodeId() {
		if(nodeMap != null)
			return nodeMap.keySet();
		return null;
	}

	@Override
	public OSMWay getWayByID(long id) {
		if(wayMap != null)
			if(wayMap.containsKey(id))
				return wayMap.get(id);
		return null;
	}

	@Override
	public Set<OSMWay> getWayByIdSet(Collection<Long> id_set) {
		Set<OSMWay> ans = new HashSet<>();
		if(wayMap != null)
			for(Long k:id_set)
				if(wayMap.containsKey(k))
					ans.add(wayMap.get(k));
		return ans;
	}

	@Override
	public Set<Long> getAllWayId() {
		if(wayMap != null)
			return wayMap.keySet();
		return null;
	}

	@Override
	public Set<OSMNode> getAllNodeInstance() {
		if(nodeMap != null)
			return (Set<OSMNode>) nodeMap.values();
		return null;
	}

	@Override
	public Set<OSMWay> getAllWayInstance() {
		if(wayMap != null)
			return (Set<OSMWay>) wayMap.values();
		return null;
	}

	@Override
	public boolean isVertexNode(long id) {
		Set<Long> inWay = getAllWayContainsNode(id);
		if(inWay != null) {
			return inWay.size() > 1;
		}
		return false;
	}

}
