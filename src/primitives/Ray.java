package primitives;
import primitives.Point;
import primitives.Vector;

public class Ray {
	
	public Ray(Point point, Vector vector) {
        vector = vector.normalize(); // Ensure vector is normalized
    }	
	
	//Override equals method
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}


	// Override hashCode method
	@Override
	public int hashCode() {
	    return super.hashCode();
	}
	
}