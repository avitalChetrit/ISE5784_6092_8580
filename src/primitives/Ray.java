package primitives;
import primitives.Point;
import primitives.Vector;

public class Ray {
	
	private final Point head;
	private final Vector direction;
	
	public Ray(Point point, Vector vector) {
		head=point;
		direction = vector.normalize(); // Ensure vector is normalized
    }	
	
	
	/**
	* Class ClassName is the basic class representing a … of Euclidean geometry in Cartesian
	* 3-Dimensional coordinate system.
	* @author Student1 and Student2
	*/
	 @Override
	 public boolean equals(Object obj) {
		 if (this == obj) return true;
		 return (obj instanceof Ray other)
				 && this.head.equals(other.head)
				 && this.direction.equals(other.direction);
	 }
	
	 @Override
	 public String toString() {
	     return "Ray: ( head= " + head + ", direction=" + direction + " )";
	 } 

	
	
	
}