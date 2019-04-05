package frc.motionProfiles.generate;

import java.util.ArrayList;

public class Util {
    public static Vector2 lerp(Vector2 a, Vector2 b, double t){
        return Vector2.add(a, Vector2.multiply(Vector2.subtract(b, a), t));
    }

    public static Vector2 quadraticCurve(Vector2 a, Vector2 b, Vector2 c, double t) {
        Vector2 p0 = Util.lerp(a, b, t);
        Vector2 p1 = Util.lerp(b, c, t);
        return Util.lerp(p0, p1, t);
    }

    public static Vector2 cubicCurve(Vector2 a, Vector2 b, Vector2 c, Vector2 d, double t) {
        Vector2 p0 = Util.quadraticCurve(a, b, c, t);
        Vector2 p1 = Util.quadraticCurve(b, c, d, t);
        return Util.lerp(p0, p1, t);
    }

    public static double slope(Vector2 a, Vector2 b) {
        double dy = a.y - b.y;
        double dx = a.x - b.x;
        return dy / dx;
	}
	
	public static double distanceBetweenPts(Vector2 a, Vector2 b){
		Vector2 d = Vector2.subtract(b, a);
		return Math.sqrt(d.x*d.x + d.y*d.y);
	}

	public static double curveDistance(ArrayList<Vector2> points){
		double distance = 0;
		for(int i = 1; i < points.size(); i++){
			distance += distanceBetweenPts(points.get(i - 1), points.get(i));
		}
		return distance;
	}
}
