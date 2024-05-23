package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

/**
 * Class Tube represents a three-dimensional tube.
 */
public class Tube extends RadialGeometry {
	/** Radius of the tube */
    private final double radius;
    /**  Axis ray of the tube */
    private final Ray axisRay;
    
    /**
     * Constructs a Tube object with the given radius and axis ray.
     *
     * @param radius the radius of the tube
     * @param axisRay the axis ray of the tube
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.radius = radius;
        this.axisRay = axisRay;
    }
    

/**
 * Computes the normal vector to the surface of the tube at a given point.
 * Since a tube is a perfectly symmetrical shape, the normal vector at any point on its surface is the unit vector pointing along its axis.
 *
 * @param point The point on the surface of the tube
 * @return The normal vector to the surface at the given point
 */
@Override
public Vector getNormal(Point point) {
    // Calculate the direction vector of the tube's axis
    Vector direction = this.getDirection().dotProduct(this);
    
    // Return the normalized direction vector as the normal vector
    return direction.normalize();
}
}