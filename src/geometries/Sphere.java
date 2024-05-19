package geometries;

import primitives.Point;
import primitives.Vector;
/**
 * Class Sphere represents a three-dimensional sphere.
 */
public class Sphere extends RadialGeometry {
	/** Center point of the sphere*/
    private final Point center; 
    
    /**
     * Constructs a Sphere object with the given center point and radius.
     *
     * @param center the center point of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }
    
    /**
     * Override that returns null
     */
    @Override
	public Vector getNormal(Point point) {
	    // The normal vector to a plane is constant and can be pre-calculated
	    return null;
	}
}