package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
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
    /**
     * Aligns a double value to zero if it is very close to zero.
     * @param val The double value to align
     * @return The aligned value
     */
    private double alignZero(double val) {
        final double EPSILON = 0.0000001;
        return (Math.abs(val) < EPSILON) ? 0.0 : val;
    }
    public Ray getRay() {
        return axisRay;
    }
    /**
     * Returns the normal vector to the surface of the tube at a given point.
     *
     * @param point The point on the surface of the tube
     * @return The normal vector to the surface of the tube at the given point
     */
    @Override
    public Vector getNormal(Point point) {
        // Calculate the vector from the head of the axis to the given point 
        Vector v = point.subtract(axisRay.getHead());
        
        // Calculate the parameter t along the axis direction
        double t = alignZero(axisRay.getDirection().dotProduct(v));
       
        // Check if the point is on the axis of the cylinder
        if (t == 0) {
            // If the point is on the axis of the cylinder, return the normalized vector v
            return v.normalize();
        }
        
        // Calculate the closest point 'p' on the axis to the given point
        Point p = axisRay.getPoint(t);

        // Return the normalized vector from the closest point on the axis to the given point
        return point.subtract(p).normalize();   
    }
   
}