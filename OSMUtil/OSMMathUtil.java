package OSMUtil;

/**
 * author: Lining Pan
 */
public class OSMMathUtil {
	// Retrieved from https://dzone.com/articles/distance-calculation-using-3
	// calculate distance by two geo-location(longitude & latitude)
	public static double distance(GeoLocation a, GeoLocation b) {
		return distance(a, b, Unit.MILE);
	}

	public static double distance(GeoLocation a, GeoLocation b, Unit u) {
		double theta = a.getLongitude() - b.getLongitude();
		double dist = Math.sin(deg2rad(a.getLatitude())) * Math.sin(deg2rad(b.getLatitude()))
				+ Math.cos(deg2rad(a.getLatitude())) * Math.cos(deg2rad(b.getLatitude())) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;

		dist = Unit.convert(dist, Unit.MILE, u);

		return (dist);
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}
}
