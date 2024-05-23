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
     * Computes the normal vector to the surface of the sphere at a given point.
     * Since a sphere is a perfectly symmetrical shape, the normal vector at any point on its surface is the unit vector pointing from the center of the sphere to the given point.
     * 
     * @param point The point on the surface of the sphere
     * @return The normal vector to the surface at the given point
     */
    @Override
    public Vector getNormal(Point point) {
        // The normal vector to a sphere is the unit vector from the center to the point
        return point.subtract(center).normalize();
    }
}