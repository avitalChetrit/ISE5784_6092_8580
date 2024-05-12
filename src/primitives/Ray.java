package primitives;
import java.awt.Point;

public class Ray {
	public Ray(Point point, Vector vector) {
        this.point = point;
        this.vector = vector.normalized(); // Ensure vector is normalized
    }	
	 public String toString() {
	        return "Ray{" +
	                "point=" + point +
	                ", vector=" + vector +
	                '}';
	    }
}