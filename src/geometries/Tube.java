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
     * Returns the normal vector to the surface of the tube at a given point.
     *
     * @param point The point on the surface of the tube
     * @return The normal vector to the surface of the tube at the given point
     */
    @Override
    public Vector getNormal(Point point) {
        
        // Calculate the parameter t along the axis direction
        double t = axisRay.getDirection().dotProduct(point.subtract(axisRay.getHead()));
        
        // Calculate the closest point 'O' on the axis to the given point
        Point O = axisRay.getHead().add(axisRay.getDirection().scale(t));

        // Return the normalized vector from the closest point on the axis to the given point
        return point.subtract(O).normalize();   
    }
   
}
