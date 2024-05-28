package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Plane represents a flat geometric surface in three-dimensional space.
 */
public class Plane implements Geometry {

	/** point in plane */
	private final Point point;
	/** vector in plane */
	private final Vector normal;

	/**
	 * Constructs a Plane object using three points.
	 *
	 * <p>
	 * The constructor calculates the normal vector based on the points given, and
	 * stores one of the points as the reference point of the plane.
	 *
	 * @param point1 the first point
	 * @param point2 the second point
	 * @param point3 the third point
	 */
	public Plane(Point point1, Point point2, Point point3) {
		// Calculate the normal vector based on the given points
		// Implementation pending
		point = point1; // Store one of the points as the reference point
		normal = (point2.subtract(point1)).crossProduct(point3.subtract(point1)).normalize(); // Normal vector
																								// calculation pending
	}

	/**
	 * Constructs a Plane object using a point and a normal vector.
	 *
	 * @param point1  a point on the plane
	 * @param normal1 the normal vector to the plane
	 */
	public Plane(Point point1, Vector normal1) {
		point = point1;

		// Ensure the normal vector is normalized
		normal = normal1.normalize();
	}

	/**
	 * new getNormal
	 * 
	 * @return vector normal
	 */
	public Vector getNormal() {
		// The normal vector to a plane is constant and can be pre-calculated
		return normal;
	}

	/**
	 * Returns the normal vector to the surface of the tube at a given point.
	 *
	 * @param point The point on the surface of the tube
	 * @return The normal vector to the surface at the given point
	 */
	@Override
	public Vector getNormal(Point point) {
		return normal;
	}
    

	@Override
	public List<Point> findIntsersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
  
   
}
