package OSMGraph;
import java.util.LinkedList;

import OSMUtil.GeoLocation;
import OSMUtil.OSMAbstractType;

/**
 * author: Lining Pan
 */
public abstract class AbstractVertex implements GeoLocation{
	private OSMAbstractType osm_ver;
	private double center_lat;
	private double center_lon;
	private double costToGetHere = Double.POSITIVE_INFINITY;
	private boolean finished = false;
	private LinkedList<RoadEdge> path;
	
	public AbstractVertex(OSMAbstractType o, double c_lat, double c_lon) {
		osm_ver = o;
		center_lat = c_lat;
		center_lon = c_lon;
	}
	
	public long getID() {
		return osm_ver.getID();
	}

	@Override
	public double getLongitude() {
		return center_lon;
	}

	@Override
	public double getLatitude() {
		return center_lat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((osm_ver == null) ? 0 : osm_ver.hashCode());
		return result;
	}

	public String toString(){
		return String.format("[AbstractVertex: id: %d, at: %f,%f]",getID(),center_lat,center_lon);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractVertex other = (AbstractVertex) obj;
		if (osm_ver == null) {
			return other.osm_ver == null;
		} else return osm_ver.equals(other.osm_ver);
	}
	public String getLocString(){
		return String.format("%f,%f",center_lat,center_lon);
	}

	public void updateCost(AbstractVertex from, RoadEdge edge) {
		if(from == null && edge == null) {
			this.costToGetHere = 0;
		}
 		double cost = from.getCostToGetHere() + edge.getCost();
		if(cost < this.costToGetHere) {
			this.costToGetHere = cost;
			this.path = from.getPath();
			this.path.add(edge);
		}
	}
	
	public double getCostToGetHere() {
		return costToGetHere;
	}
	
	public void setFinished() {
		this.finished = true;
	}
	
	public boolean isFinished() {
		return this.finished;
	}
	
	public LinkedList<RoadEdge> getPath(){
		return this.path;
	}

}
