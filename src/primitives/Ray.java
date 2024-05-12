package primitives;
import primitives.Point;
import primitives.Vector;

public class Ray {
	
	public Ray(Point point, Vector vector) {
        vector = vector.normalize(); // Ensure vector is normalized
    }	
	
}