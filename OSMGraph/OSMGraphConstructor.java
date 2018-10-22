package OSMGraph;

import OSMUtil.OSMAbstractDataModel;
import OSMUtil.OSMMathUtil;
import OSMUtil.OSMNode;
import OSMUtil.OSMWay;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author: Lining Pan
 */
public class OSMGraphConstructor {
    //private Set<Long> nodeSet;
    private Set<Long> waySet;
    private Set<Long> nodeOnRoad;

    private Map<Long, AbstractVertex> verList;
    private Map<AbstractVertex, LinkedList<AbstractEdge>> adjList;
    private Map<Long, Set<AbstractEdge>> way2edgeList;
    private OSMAbstractDataModel dm;
    private Map<AbstractEdge, Set<Long>> nodeInEdge;

    private Graph graph;

    public OSMGraphConstructor(OSMAbstractDataModel dm) {
        this.dm = dm;
        //nodeSet = dm.getAllNodeId();
        waySet = dm.getAllWayId();
        verList = new ConcurrentHashMap<>();
        nodeOnRoad = new HashSet<>();
        adjList = new ConcurrentHashMap<>();
        way2edgeList = new ConcurrentHashMap<>();
        nodeInEdge = new ConcurrentHashMap<>();

        graph = this.constructGraph();
    }

    public Graph getGraph(){
        return graph;
    }

    private Graph constructGraph() {

        for (Long i_w : waySet) {
            OSMWay way = dm.getWayByID(i_w);

            if (!way.isRoad()) continue;
            double speed = way.getSpeedLimit();

            List<Long> wayList = way.getNodeIdList();
            Iterator<Long> it = wayList.iterator();
            OSMNode from = dm.getNodeById(it.next());
            OSMNode last = from;
            Set<Long> inEdge = new HashSet<>();
            nodeOnRoad.add(from.getID());
            double length = 0.0;
            while (it.hasNext()) {
                OSMNode node = dm.getNodeById(it.next());
                length += OSMMathUtil.distance(last, node);

                nodeOnRoad.add(node.getID());

                if (!it.hasNext() || dm.isVertexNode(node.getID())) {
                    //construct new edge(s)
                    RoadVertex f = new RoadVertex(from);
                    RoadVertex t = new RoadVertex(node);

                    verList.put(f.getID(), f);
                    verList.put(t.getID(), t);

                    if (!adjList.containsKey(f))
                        adjList.put(f, new LinkedList<>());
                    if (!adjList.containsKey(t))
                        adjList.put(t, new LinkedList<>());
                    if (!way2edgeList.containsKey(way.getID()))
                        way2edgeList.put(way.getID(), new HashSet<>());

                    RoadEdge e = new RoadEdge(way, f, t, speed, length);
                    adjList.get(f).add(e);
                    way2edgeList.get(way.getID()).add(e);
                    nodeInEdge.put(e,inEdge);

                    if (!way.isOneway()) {
                        RoadEdge r = new RoadEdge(way, t, f, speed, length);
                        adjList.get(t).add(r);
                        way2edgeList.get(way.getID()).add(r);
                        nodeInEdge.put(r,inEdge);
                    }
                    length = 0.0;
                    from = node;
                    inEdge = new HashSet<>();
                }else{
                    inEdge.add(node.getID());
                }
                last = node;
            }
        }

        return new Graph(verList, adjList, nodeOnRoad, nodeInEdge, way2edgeList, dm);
    }
}
