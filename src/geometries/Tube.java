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
     * Override that returns null
     */
    @Override
	public Vector getNormal(Point point) {
	    // The normal vector to a plane is constant and can be pre-calculated
	    return null;
	}
}