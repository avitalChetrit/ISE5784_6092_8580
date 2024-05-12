package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class Plane represents a flat geometric surface in three-dimensional space.
 */
public class Plane implements Geometry {

	private final Point point;
	private final Vector normal;
	
	/**
     * Constructs a Plane object using three points.
     *
     * <p>The constructor calculates the normal vector based on the points given, and stores one of the points
     * as the reference point of the plane.
     *
     * @param point1 the first point
     * @param point2 the second point
     * @param point3 the third point
     */
    public Plane(Point point1, Point point2, Point point3) {
        // Calculate the normal vector based on the given points
        // Implementation pending
        this.point = point1; // Store one of the points as the reference point
        this.normal = null; // Normal vector calculation pending
    }

    /**
     * Constructs a Plane object using a point and a normal vector.
     *
     * @param point  a point on the plane
     * @param normal the normal vector to the plane
     */
    public Plane(Point point1, Vector normal1) {
        this.point = point1;
        // Ensure the normal vector is normalized
        this.normal = normal1.normalize();
    }
	
	
	/**
	 * Returns the normal vector to the plane at a given point.
	 *
	 * @param point the point on the surface of the plane
	 * @return the normal vector to the plane at the given point
	 */
	@Override
	public Vector getNormal(Point point) {
	    // The normal vector to a plane is constant and can be pre-calculated
	    return normal;
	}
	

}