package OSMGraph;

import OSMUtil.*;


/**
 * author: Lining Pan
 */
public class RoadVertex extends AbstractVertex {

	public RoadVertex(OSMNode o) {
		super(o, o.getLatitude(), o.getLongitude());
		// TODO Auto-generated constructor stub
	}
}
