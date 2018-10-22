package SearchEngine;
import java.util.List;

import OSMGraph.AbstractVertex;
import OSMGraph.RoadEdge;

/**
 * author: Lining Pan
 */
public interface AbstractSearchEngine {
    public List<RoadEdge> getVertexRoute(AbstractVertex from, AbstractVertex to);
}
