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
     * Returns the direction vector of the tube's axis.
     *
     * @return The direction vector of the tube's axis
     */
    public Vector getDirection() {
        return axisRay.getDirection();
    }
    /**
     * Returns the ray representing the axis of the tube.
     *
     * @return The ray representing the axis of the tube
     */
    public Ray getRay() {
        return axisRay;
    }
    
    /**
     * Returns the normal vector to the surface of the tube at a given point.
     *
     * @param point The point on the surface of the tube
     * @return The normal vector to the surface at the given point
     */
    @Override
    public Vector getNormal(Point point) {
        // Calculate the direction vector of the tube's axis
        Vector direction = this.getDirection();
        
        // Calculate the point on the axis that corresponds to the given point on the tube's surface
        Point center = this.getRay().getPoint().add(direction.scale(direction.dotProduct(point.subtract(this.getRay().getPoint()))));
        
        // Calculate the vector from the calculated center point to the given point on the tube's surface
        Vector normal = point.subtract(center);
        
        // Return the normalized normal vector
        return normal.normalize();
    }
}