package OSMGraph;

import OSMUtil.*;


/**
 * author: Lining Pan
 */
public class RoadEdge extends AbstractEdge {

    private OSMWay o_way;
    private double speed;
	private double distance;

	public RoadEdge(OSMWay o, AbstractVertex f, AbstractVertex t, double spd, double dis) {
		super(f, t);
		o_way = o;
        speed = spd;
		distance = dis;
	}

	@Override
	protected double getCostByTime() {
		return distance/speed;
	}

	@Override
	protected double getCostByDistance() {
		return distance;
	}

	//test speed read
	// TO BE REMOVED

	public double getSpeed(){
		return speed;
	}
	public double getDistance(){
	    return distance;
    }

    public String toString(){
	    return String.format("[Road edge: Name: %s, length %f, from: %s, to: %s]\n",
				o_way.getName(),
				distance,
				getFromNode().getLocString(),
				getToNode().getLocString()
		);
    }

    public OSMWay getOSMWay(){
	    return o_way;
    }

}
