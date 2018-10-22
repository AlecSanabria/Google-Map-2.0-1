package SearchEngine;
import OSMGraph.*;
import OSMUtil.GeoLocation;

import java.util.List;

/**
 * author: Lining Pan
 */
public interface AbstractSearchEngine {
    public List<RoadEdge> getVertexRoute(AbstractVertex from, AbstractVertex to);
}
