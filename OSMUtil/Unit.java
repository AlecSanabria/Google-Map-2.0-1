package OSMUtil;

/**
 * author: Lining Pan
 */
public enum Unit {
	
	KILO(1.609344), NM(0.8684), MILE(1.0);
	
	private double convertFactor;
	
	private Unit(double v) {
		convertFactor = v;
	}
	public static double convert(double v, Unit from, Unit to) {
		return v / from.convertFactor * to.convertFactor;
	}
}
